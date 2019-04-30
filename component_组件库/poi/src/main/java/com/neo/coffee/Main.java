package com.neo.coffee;

import com.neo.utils.export_poi.ExportUtils;
import com.neo.utils.export_poi.TemplateBean;
import net.sf.json.JSONObject;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author:zhangYu Date: Created in 2019/4/30.
 * Description:
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        ImportExcel importExcel = new ImportExcel();

        Map<Integer, String[]> integerMap = importExcel.readExcelContent("classpath:Import_test.xlsx");
        List<Coffee> coffees = new ArrayList<Coffee>();
        for (String[] value:integerMap.values()){
            JSONObject jsonObject = new JSONObject().fromObject(value[2]);
            Coffee coffee = (Coffee)JSONObject.toBean(jsonObject, Coffee.class);
            coffee.setId(Long.valueOf(value[0]));
            coffee.setName(value[1]);
            coffees.add(coffee);
        }
        String fileName = "测试名称";
        TemplateBean excelTempLateData = ExportUtils.parseExcelTempLate("ExcelTempLate/test_tempLate.xlsx");
        File file = ResourceUtils.getFile("classpath:export_test.xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ExportUtils.getExportHandler().exportExcel(fileName, excelTempLateData, coffees, null, fileOutputStream);
    }
}
