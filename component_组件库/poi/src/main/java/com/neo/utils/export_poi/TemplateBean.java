package com.neo.utils.export_poi;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Author:zhangYu Date: Created in 2018/12/10.
 * Description: 提取到的模板类
 */
@Data
@AllArgsConstructor
public class TemplateBean {

    /**
     * 标题
     */
    private List<String> headerNames;
    /**
     * code
     */
    private List<String> valueNames;


}
