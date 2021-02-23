package com.ccc.fizz.al;

public class Fi {
    public static void main(String[] args) {
        //System.out.println(getSum1(100));
        System.out.println(getSum2(3));
    }

    public static int getSum1(int count) {
        if (count == 0) {
            return 0;
        }

        if (count == 1) {
            return 1;
        }

        return getSum1(count - 1) + getSum1(count - 2);
    }

    /*
    * i = 0, dp[i] = 0;
    * i = 1, dp[i] = 1;
    * i >= 2, dp[i] = dp[i-1] + dp[i-2]
    * */
    public static int getSum2(int count) {
        int[] array = new int[count + 1];
        array[0] = 0;
        array[1] = 1;

        int sum = 0;
        for (int i = 2; i < count + 1; i++) {
            array[i] = array[i - 1] + array[i - 2];
        }
        return array[count];
    }
}
