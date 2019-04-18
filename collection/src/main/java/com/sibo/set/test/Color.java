package com.sibo.set.test;

/**
 * @Author:zhangYu Date: Created in 2019/4/18.
 * Description:
 */
enum Color{
    RED("RED"),
    BLUE("BLUE"),
    YELLOW("YELLOW"),
    BLACK("BLACK");

    String name;

    Color(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}

