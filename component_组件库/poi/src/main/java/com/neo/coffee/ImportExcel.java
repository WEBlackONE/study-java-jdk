package com.neo.coffee;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author maokaixuan
 * @Description //读取excel数据并导入到数据库
 * @Date 20:13 2019/2/15
 * @Param
 * @return
 **/
public class ImportExcel {
    /**
     * @return java.util.Map
     * @Author maokaixuan
     * @Description //读取Excel数据内容
     * @Date 13:24 2019/2/16
     * @Param [is]
     **/
    public Map<Integer, String[]> readExcelContent(String path)  {
        Map<Integer, String[]> content = new HashMap<Integer, String[]>();
        Workbook wb = null;
        try {
            File file = ResourceUtils.getFile(path);
            wb = WorkbookFactory.create(file);
        } catch (IOException e) {
            //log.info("文件类型不匹配，请重新选择！", e);
            //throw new BaseException("文件类型不匹配，请重新选择！", e);
            e.printStackTrace();
            System.out.println("文件类型不匹配，请重新选择");
        } catch (InvalidFormatException e) {
            //log.info("文件类型不匹配，请重新选择！", e);
            //throw new BaseException("文件类型不匹配，请重新选择！", e);
            e.printStackTrace();
            System.out.println("文件类型不匹配，请重新选择！");
        }

        Sheet sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = getExcelRealRow(sheet);
        if (rowNum < 1) {
            //throw new BaseException("导入文件数据为空！");
            System.out.println("导入文件数据为空");
        }
        //由于第0行是表头在这里表数据索引从1开始
        Row row = sheet.getRow(1);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            String[] strValue = new String[colNum];
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                strValue[j] = getCellFormatValue(row.getCell((short) j)).trim();
                j++;
            }
            content.put(i, strValue);
        }
        return content;
    }

    // 获取Excel表的真实行数
    int getExcelRealRow(Sheet sheet) {
        boolean flag = false;
        for (int i = 1; i <= sheet.getLastRowNum(); ) {
            Row r = sheet.getRow(i);
            if (r == null) {
                // 如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
                sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                continue;
            }
            flag = false;
            for (Cell c : r) {
                if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                i++;
                continue;
            } else {
                // 如果是空白行（即可能没有数据，但是有一定格式）
                if (i == sheet.getLastRowNum())// 如果到了最后一行，直接将那一行remove掉
                    sheet.removeRow(r);
                else//如果还没到最后一行，则数据往上移一行
                    sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
            }
        }
        return sheet.getLastRowNum();
    }

    /**
     * @return java.lang.String
     * @Author maokaixuan
     * @Description //根据HSSFCell类型设置数据
     * @Date 17:51 2019/3/3
     * @Param [cell]
     **/
    private String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC:
                case HSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cellvalue = sdf.format(date);
                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cellvalue = String.valueOf(cell.getStringCellValue().trim());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

}
