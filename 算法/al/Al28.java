package com.ccc.fizz.al;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。一个数字可能有多个翻译。请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。
 *
 *
 * 输入: 12258
 * 输出: 5
 * 解释: 12258有5种不同的翻译，分别是"bccfi", "bwfi", "bczi", "mcfi"和"mzi"
 * **/
public class Al28 {

    public static void main(String[] args) {
        System.out.println(translateNum(506));
    }

    public static int translateNum(int num) {
        List<String> list = new ArrayList<>();
        while (num > 0) {
            list.add(String.valueOf(num % 10));
            num = num / 10;
        }

        Collections.reverse(list);

        if (list.size() == 1 || list.size() == 0) {
            return 1;
        }

        if (list.size() == 2) {
            return judge(list.get(0), list.get(1)) + 1;
        }

        //构建dp
        int[] dp = new int[list.size()];

        dp[0] = 1;
        dp[1] = judge(list.get(0), list.get(1)) + 1;

        for (int i = 2; i < list.size(); i++) {
            if (judge(list.get(i-1), list.get(i)) > 0) {
                dp[i] = dp[i-1] + dp[i-2];
            } else {
                dp[i] = dp[i-1];
            }
        }
        return dp[dp.length - 1];
    }

    public static int judge(String a, String b) {
        int temp = "0".equals(a) ? 0 : Integer.valueOf(a + b) > 25 ? 0 : 1;
        return temp;
    }
}
