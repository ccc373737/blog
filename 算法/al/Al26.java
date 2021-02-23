package com.ccc.fizz.al;

/**
 * 输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
 *
 * 要求时间复杂度为O(n)。
 * **/
public class Al26 {
    public int maxSubArray(int[] nums) {
        int temp = nums[0];
        int max = temp;
        for (int i = 1; i < nums.length; i++) {
            temp = Math.max(temp + nums[i], nums[i]);
            max = Math.max(temp, max);
        }

        return max;
    }
}
