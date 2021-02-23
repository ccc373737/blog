package com.ccc.fizz.al;

import java.util.Arrays;

//一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
//
//机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
//
//问总共有多少条不同的路径？
public class Al58 {

    public int uniquePaths(int m, int n) {
        int dp[][] = new int[m+1][n+1];

        dp[1][0] = 1;
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }

        return dp[m][n];
    }

    //二维数组一维化
    //只需要知道第一行的所有值和下一行的当前值
    //有dp[i] = dp[i]（上一行的值） + dp[i-1]（当前值）
    public int uniquePaths1(int m, int n) {
        int dp[] = new int[n];
        Arrays.fill(dp, 1);

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] += dp[j-1];
            }
        }

        return dp[n - 1];
    }
}
