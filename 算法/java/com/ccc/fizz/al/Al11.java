package com.ccc.fizz.al;

/**
 *
 *输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。
 * **/
public class Al11 {
    
    static char[] st = {'0','1','2','3','4','5','6','7','8','9'};

    static int count;

    static StringBuffer sb;

    public static void main(String[] args) {
        count = 8;
        sb = new StringBuffer();
        char[] number = new char[count];

        cal(number, 0);

        if (sb.substring(0,1).equals(",")) {
            sb.deleteCharAt(0);
        }
        System.out.println(sb.deleteCharAt(sb.length() - 1));
    }

    public static void cal(char[] number, int i) {
        if (i == count) {
            sb.append(removeZero(number) + ",");
            return;
        }

        for (char c : st) {
            number[i] = c;
            cal(number, i + 1);
        }
    }

    public static String removeZero(char[] target) {
        boolean flag = false;
        String str = "";
        for (char c : target) {
            if (c != 48) {
                flag = true;
            }

            if (flag) {
                str += c;
            }
        }
        return str;
    }
}
