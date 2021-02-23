package com.ccc.fizz;

import java.util.EnumMap;
import java.util.EnumSet;

public class EnumTest {
    public static void main(String[] args) {
        EnumDemo black = EnumDemo.BLACK;
        System.out.println(EnumDemo.valueOf("BLACK"));
        System.out.println(EnumDemo.class.getEnumConstants()[1]);

        //可添加删除的枚举 EnumSet
        EnumSet<EnumDemo> po = EnumSet.noneOf(EnumDemo.class);//noneof方法接受枚举类
        po.add(EnumDemo.BLACK);
        po.addAll(EnumSet.of(EnumDemo.BLACK, EnumDemo.GREEN));

        //EnumMap 其中的key来自枚举数据
        EnumMap<EnumDemo, String> map = new EnumMap<EnumDemo, String>(EnumDemo.class);
        map.put(EnumDemo.BLACK,"blasdad");

        System.out.println(EnumDemo.GRAY);
    }
}
