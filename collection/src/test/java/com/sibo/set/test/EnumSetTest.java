package com.sibo.set.test;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * @Author:zhangYu Date: Created in 2019/4/18.
 * Description:
 */
public class EnumSetTest {

    public static void main(String[] args) {
        //allOf()-创建一个EnumSet集合，集合元素就是Season枚举类的全部枚举值
        EnumSet<Color> colors1 = EnumSet.allOf(Color.class);
        System.out.println("allOf()======>"+colors1);
        //=================================================================
        //noneof()-创建一个EnumSet空集合，指定其集合元素是Color类的枚举值
        EnumSet<Color> colors2 = EnumSet.noneOf(Color.class);
        System.out.println("noneof()======>"+colors2);
        //=================================================================
        //add()
        colors2.add(Color.BLUE);
        colors2.add(Color.BLACK);
        System.out.println("noneof()+add()======>"+colors2);
        //=================================================================
        //of()-以指定枚举值创建EnumSet集合
        EnumSet<Color> colors3 = EnumSet.of(Color.BLACK, Color.RED);
        System.out.println("of()======>"+colors3);
        //=================================================================
        //range()-创建一个包含两个枚举值范围内所有枚举值的EnumSet集合
        EnumSet<Color> colors4 = EnumSet.range(Color.RED, Color.YELLOW);
        System.out.println("range()======>"+colors4);
        //=================================================================
        //complementOf() 补集
        EnumSet<Color> colors5 = EnumSet.complementOf(colors4);
        System.out.println("complementOf()======>"+colors5);
        //=================================================================
        //copyOf();
        EnumSet<Color> colors6_1 = EnumSet.copyOf(colors4);
        System.out.println("copyOf()======>"+colors6_1);
        //=================================================================
        ArrayList<Color> colors = new ArrayList<Color>();
        colors.add(Color.BLACK);
        colors.add(Color.BLUE);
        EnumSet<Color> colors6_2 = EnumSet.copyOf(colors);
        System.out.println("copyOf()======>"+colors6_2);
        //=================================================================
    }
}
