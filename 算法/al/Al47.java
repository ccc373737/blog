package com.ccc.fizz.al;

/**
 *求 1+2+...+n ，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
 *
 * **/
public class Al47 {

    int temp = 0;

    public int sumNums(int n) {
        boolean x = n > 1 && sumNums(n - 1) > 0;
        temp += n;
        return temp;
    }
}
