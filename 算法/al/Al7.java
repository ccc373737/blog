package com.ccc.fizz.al;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 给定一个正整数 n，将其拆分为至少两个正整数的和，并使这些整数的乘积最大化。 返回你可以获得的最大乘积。
 * **/
public class Al7 {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        System.out.println(getMaxDi(10));
    }

    public static int getMax(int target, Map<Integer, Integer> map) {
        if (target == 0 || target == 1) {
            return 0;
        }

        if (map.containsKey(target)) {
            return map.get(target);
        }

        int max = 0;
        for (int i = 1; i < target; i++) {
            max = Math.max(max, Math.max(i * (target - i), i * getMax(target - i, map)));
            map.put(target, max);
        }
        return max;
    }

    /*
    * 动态规划 自底向上
    * i=0,dp[i]=0;
    * i=1,dp[i]=0;
    * i=2,dp[i]=1;
    * i=3,dp[i]=2;
    * i>3,dp[i]=max(sum,max(x * (i-x), x * dp[i-x]))
    *
    * */
    public static int getMaxDi(int target) {
        int[] dp = new int[target + 1];

        dp[2] = 1;
        dp[3] = 2;

        for (int i = 4; i <= target; i++) {
            for (int x = 1; x < i; x++) {
                dp[i] = Math.max(dp[i], Math.max(x * (i-x), x * dp[i-x]));
            }
        }
        return dp[target];
    }
}
