package com.ccc.fizz.al;

import java.util.Arrays;

/**
 * 将一个字符串中的空格替换成 "%20"。
 * "A B" -> "A%20B"
 * **/
public class Al3 {
    public static void main(String[] args) {
        System.out.println(replace2("A B D F C S"));
    }

    public static String replace1(String target) {


        return target;
    }

    public static String replace2(String target) {
        char[] chars = target.toCharArray();

        char[] temp = new char[chars.length * 3];
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != ' ') {
                temp[index++] = chars[i];
            } else {
                temp[index++] = '%';
                temp[index++] = '2';
                temp[index++] = '0';
            }
        }

        return String.valueOf(temp);
    }
}
