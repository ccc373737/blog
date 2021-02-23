package com.ccc.fizz.lam;

import java.util.function.Consumer;

public class lamTest1 {

    public static void getMsg(String msg, Consumer action) {
        //函数式接口接受的参数
        action.accept(msg);
    }

    public static void main(String[] args) {
        //lambda表达式中具体的行为
        getMsg("Hlll", i -> System.out.println(i));
        System.gc();
    }
}
