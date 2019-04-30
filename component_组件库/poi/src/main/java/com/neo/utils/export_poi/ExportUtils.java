package com.neo.utils.export_poi;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:JayceKoba Date: Created in 2018/12/11.
 * Description: 导出工具
 */
@Log4j2
public class ExportUtils {


    private static ExportHandler exportHandler = null;

    /**
     * @Author:zhangYu
     * @Date:created in 2018/12/19 10:35
     * @Description: 单例获取导出处理器
     */
    public static ExportHandler getExportHandler() {
        if (exportHandler == null) {
            synchronized (ExportHandler.class) {
                if (exportHandler == null) {
                    exportHandler = new ExportHandler();
                }
            }
        }
        return exportHandler;
    }


    /**
     * @param fileName 导出的文件名
     * @Author:zhangYu
     * @Date:created in 2018/12/19 10:39
     * @Description: 设置导出的response信息
     */
    public static HttpServletResponse setResponse(HttpServletRequest request, HttpServletResponse response, String fileName) {
        try {
            final String userAgent = request.getHeader("USER-AGENT").toLowerCase();
            if (StringUtils.contains(userAgent, "msie")) {//IE浏览器
                fileName = URLEncoder.encode(fileName, "utf-8");
            } else if (StringUtils.contains(userAgent, "edge")) {//IE Edge 浏览器
                fileName = new String(fileName.getBytes("gbk"), "iso-8859-1");
            } else if (StringUtils.contains(userAgent, "mozilla")) {//google,火狐浏览器
                fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            } else {
                fileName = URLEncoder.encode(fileName, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        return response;
    }

    /**
     * @param classPathTemplateName classpath下的模板名称
     * @Author:zhangYu
     * @Date:created in 2018/12/19 10:39
     * @Description: 解析模板数据
     */
    public static TemplateBean parseExcelTempLate(String classPathTemplateName) {
        //读取文件
        InputStream resourceAsStream = ExportUtils.class.getClassLoader().getResourceAsStream(classPathTemplateName);
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(resourceAsStream);
        } catch (Exception e) {
            log.error("无法加载模板文件-" + classPathTemplateName + "!  " + e.getMessage(), e);
            throw new RuntimeException("无法加载模板文件-" + classPathTemplateName);
        }
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<String> titleList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        int rowNum = sheet.getPhysicalNumberOfRows();
        XSSFRow row;
        int cellNum;
        for (int i = 0; i < rowNum; i++) {
            row = sheet.getRow(i);
            cellNum = row.getPhysicalNumberOfCells();
            for (int j = 0; j < cellNum; j++) {
                String cellValue = row.getCell(j).getStringCellValue();
                if (i == 0) {
                    titleList.add(cellValue);
                } else if (i == 1) {
                    cellValue = cellValue.substring(cellValue.indexOf("{") + 1, cellValue.lastIndexOf("}"));
                    valueList.add(cellValue);
                }
            }
        }
        try {
            resourceAsStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TemplateBean(titleList, valueList);
    }

    /**
     * 将字符串的第一个字母转换成大写
     */
    public static String firstUpper(String string) {
        char[] charArray = string.toCharArray();
        charArray[0] -= 32;
        return String.valueOf(charArray);
    }


}
