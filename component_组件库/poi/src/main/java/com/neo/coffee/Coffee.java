package com.neo.coffee;

import lombok.Data;

/**
 * @Author:zhangYu Date: Created in 2019/4/30.
 * Description:
 */
@Data
public class Coffee {

    private Long id;
    private String name;

    private String milkWater;

    private String beanWater;

    private String beanTime;

    private String sugarWater;

    private String milkTime;

    private String hotWater;

    private String sugarTime;

    private String chocolateTime;

    private String chocolateWater;
    private String bitterCoffeeTime;
    private String bitterCoffeeWater;
    private String cupTime;
    private String cupWater;

    public String getBitterCoffeeTime() {
        if (bitterCoffeeTime!=null){
            return "速溶咖啡时间:"+bitterCoffeeTime;
        }else {
            return bitterCoffeeTime;
        }

    }

    public String getBitterCoffeeWater() {
        if (bitterCoffeeWater!=null){
            return "速溶咖啡水:"+bitterCoffeeWater;
        }else {
            return bitterCoffeeWater;
        }
    }

    public String getCupTime() {
        if (cupTime!=null){
            return "饮料杯时间:"+cupTime;
        }else {
            return cupTime;
        }
    }

    public String getCupWater() {
        if (cupWater!=null){
            return "饮料杯水:"+cupWater;
        }else {
            return cupWater;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMilkWater() {
        if (milkWater!=null){
            return  "牛奶水："+milkWater;
        }else {
            return milkWater;
        }

    }

    public String getBeanWater() {
        if (beanWater!=null){
            return  "豆水："+beanWater;
        }else {
            return beanWater;
        }
    }

    public String getBeanTime() {
        if (beanTime!=null){
            return  "豆时："+beanTime;
        }else {
            return beanTime;
        }
    }

    public String getSugarWater() {
        if (sugarWater!=null){
            return  "糖水："+sugarWater;
        }else {
            return sugarWater;
        }
    }

    public String getMilkTime() {
        if (milkTime!=null){
            return  "牛奶时间："+milkTime;
        }else {
            return milkTime;
        }
    }

    public String getHotWater() {
        if (hotWater!=null){
            return  "热水："+hotWater;
        }else {
            return hotWater;
        }
    }

    public String getSugarTime() {
        if (sugarTime!=null){
            return  "糖时间："+sugarTime;
        }else {
            return sugarTime;
        }
    }

    public String getChocolateTime() {
        if (chocolateTime!=null){
            return  "巧克力时间:"+chocolateTime;
        }else {
            return chocolateTime;
        }
    }

    public String getChocolateWater() {
        if (chocolateWater!=null){
            return  "巧克力水:"+chocolateWater;
        }else {
            return chocolateWater;
        }
    }
}
