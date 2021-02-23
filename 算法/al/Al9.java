package com.ccc.fizz.al;

public class Al9 {

    public static void main(String[] args) {
        System.out.println(getProfit(new int[]{1,3,2,8,4,9}, 2));
    }

    public static int getProfit(int[] num, int fee) {
        if (num.length == 0 || num.length == 1) {
            return 0;
        }

        int[] dp = new int[num.length];
        int min1 = num[0];
        int min2 = num[0];
        int lastMax = 0;
        int j = 1;
        for (int i = 1; i < num.length; i++) {
            min1 = Math.min(num[i], min1);
            min2 = Math.min(num[i], min2);

            if (num[i] + fee < lastMax) {
                j = i;
                min2 = Math.min(min2, num[j]);
            }

            if (num[i] - min1 - fee > dp[i-1] || dp[j-1] + (num[i] - min2 - fee) > dp[i-1] ) {
                lastMax = num[i];
                
            }
            dp[i] = Math.max( Math.max(dp[i-1], num[i] - min1 - fee), dp[j-1] + (num[i] - min2 - fee));

            if (dp[i] < 0) {
                dp[i] = 0;
            }
        }

        return dp[dp.length - 1];
    }
}
