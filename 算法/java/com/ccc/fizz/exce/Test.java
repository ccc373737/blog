package com.ccc.fizz.exce;

public class Test {
    public static void main(String[] args) {
        ExeDemo e = new ExeDemo();
        try {
            e.getMsg();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
