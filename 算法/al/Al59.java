package com.ccc.fizz.al;

//给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
//
//说明：每次只能向下或者向右移动一步。
//
public class Al59 {
    //输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
    //输出：7
    //解释：因为路径 1→3→1→1→1 的总和最小。
    public int minPathSum(int[][] dp) {
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] += dp[i-1][0];
        }

        for (int j = 1; j < dp[0].length; j++) {
            dp[0][j] += dp[0][j-1];
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + dp[i][j];
            }
        }

        return dp[dp.length-1][dp[0].length-1];
    }
}
