package com.ccc.fizz.al;

/*
* 假设把某股票的价格按照时间先后顺序存储在数组中，请问买卖该股票一次可能获得的最大利润是多少？
* */
public class Al8 {
    public static void main(String[] args) {
        System.out.println(getMax(new int[]{1,2}));
    }

    public static int getMax(int[] price) {
        if (price.length == 0) {
            return 0;
        }

        int[] dp = new int[price.length];

        int cost = Integer.MAX_VALUE;
        cost = Math.min(price[0], cost);

        for (int i = 1; i < price.length; i++) {
            cost = Math.min(cost, price[i]);
            dp[i] = Math.max(dp[i-1], price[i] - cost);
        }

        return dp[price.length - 1];
    }
}
