package com.neo.utils.export_poi;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.util.List;


/**
 * @Author:zhangYu Date: Created in 2018/12/10.
 * Description:
 */
@Log4j2
public class ExportHandler<T> {

    protected ExportHandler() {
    }

    /**
     * @param fileName     :导出文件名称
     * @param templateBean : 封装好的模板数据
     * @param dataList     需要导出的数据（集合）
     * @Author:zhangYu
     * @Date:created in 2018/12/10 17:47
     * @Description:
     */
    public void exportExcel(String fileName, TemplateBean templateBean, List<T> dataList, HttpServletRequest request, OutputStream outputStream) {
        //填充工作表
        XSSFWorkbook xssfSheets = doWriteExcel(fileName, templateBean, dataList);

        try {
            //写入数据
            xssfSheets.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 功能：数据写入工作簿
     */
    public XSSFWorkbook doWriteExcel(String fileName, TemplateBean templateBean, List<T> dataList) {
        /* （三）声明一个工作薄：包括构建工作簿、表格、样式*/
        XSSFWorkbook xssfSheets = new XSSFWorkbook();
        XSSFSheet sheet = xssfSheets.createSheet(fileName);
        //sheet.setDefaultColumnWidth((short) 20);
        // 生成一个样式
        XSSFCellStyle style = xssfSheets.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        XSSFRow row;
        // 获取表的第一行
        List<String> hearderNames = templateBean.getHeaderNames();
        List<String> valueNames = templateBean.getValueNames();
        row = sheet.createRow(0);
        //填充表头
        for (int i = 0; i < hearderNames.size(); i++) {
            row.createCell(i).setCellValue(hearderNames.get(i));
        }
        //填充数据
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < valueNames.size(); j++) {
                    Method method = null;//反射拿到对应方法
                    String methodName = null;
                    try {
                        methodName = "get" + ExportUtils.firstUpper(valueNames.get(j));
                        method = dataList.get(i).getClass().getMethod(methodName, new Class[]{});
                    } catch (NoSuchMethodException e) {
                        log.error(e.getMessage(), e);
                    }
                    Object value = null;
                    try {
                        value = method.invoke(dataList.get(i), new Object[]{});
                        //对null值做处理
                        value = value != null ? value : "";
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                    if (value instanceof String) {
                        row.createCell(j).setCellValue((String) value);
                    } else if (value instanceof Integer) {
                        row.createCell(j).setCellValue((Integer) value);
                    } else if (value instanceof Long) {
                        row.createCell(j).setCellValue((Long) value);
                    } else {
                        row.createCell(j).setCellValue(value + "");
                    }
                }
            }
        } else {
            log.warn("导出数据为空");
        }
        return xssfSheets;
    }


}
