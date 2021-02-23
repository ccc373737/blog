package com.ccc.fizz.al;

/**
 * 我们把只包含质因子 2、3 和 5 的数称作丑数（Ugly Number）。求按从小到大的顺序的第 n 个丑数。
 * **/
public class Al31 {

    public static void main(String[] args) {
        nthUglyNumber(10);
    }

    public static int nthUglyNumber(int n) {
        int[] dp = new int[n];

        dp[0] = 1;
        int twoIndex = 0, threeIndex = 0, fiveIndex = 0;
        for (int i = 1; i < n; i++) {
            dp[i] = Math.min(Math.min(dp[twoIndex] * 2, dp[threeIndex] * 3), dp[fiveIndex] * 5);

            if (dp[i] == dp[twoIndex] * 2) {
                twoIndex++;
            }

            if (dp[i] == dp[threeIndex] * 3) {
                threeIndex++;
            }

            if (dp[i] == dp[fiveIndex] * 5) {
                fiveIndex++;
            }
        }

        return dp[n-1];
    }
}
