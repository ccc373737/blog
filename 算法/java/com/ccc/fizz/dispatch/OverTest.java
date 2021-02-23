package com.ccc.fizz.dispatch;

import java.io.Serializable;

public class OverTest {

    //优先度最高 直接匹配char
    public static void sayHello(char arh) {
        System.out.println("hello char");
    }

    //'a'自动转型为int
    public static void sayHello(int arh) {
        System.out.println("hello int");
    }

    //int转型为long
    public static void sayHello(long arh) {
        System.out.println("hello long");
    }

    //没有基础类型 发生自动装箱
    public static void sayHello(Character arh) {
        System.out.println("hello Character");
    }

    //继续找到Character的父类或父接口
    public static void sayHello(Serializable arh) {
        System.out.println("hello Serializable");
    }

    //找到顶级父类
    public static void sayHello(Object arh) {
        System.out.println("hello Object");
    }

    //变长参数的优先级是最低的
    public static void sayHello(char... arh) {
        System.out.println("hello char...");
    }

    public static void main(String[] args) {
        sayHello('a');
    }
}
