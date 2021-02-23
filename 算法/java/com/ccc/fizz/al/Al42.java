package com.ccc.fizz.al;

//不用加减乘除做加法
public class Al42 {
    public int add(int a, int b) {
        while (b != 0) {
            int tempAdd = a ^ b;
            int tempCarry = (a & b) << 1;
            a = tempAdd;
            b = tempCarry;
        }
        return a;
    }
}
