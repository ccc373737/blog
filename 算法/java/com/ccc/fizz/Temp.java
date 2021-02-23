package com.ccc.fizz;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Temp {
    public static void main(String[] args) {
        int[] num = new int[] {6,2,3,12,7,4,8,3,4,5};
                //1 4 3 1 3
    }

    public static int rob(int[] num) {
        int length = num.length;
        if (length == 1) {
            return 0;
        }
        if (length == 1) {
            return num[0];
        }

        if (length == 2) {
            return Math.max(num[0], num[1]);
        }

        int[] dp = new int[length];
        dp[0] = num[0];
        dp[1] = Math.max(num[0], num[1]);
        for (int i = 2 ; i < length ; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + num[i]);
        }
        return dp[length - 1];
    }

}
