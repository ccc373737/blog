package com.ccc.fizz.al.other;

public class Test1 {

    //53. 最大子序和
    //前缀和递增算法
    public int maxSubArray(int[] nums) {
        int[] suffix = new int[nums.length + 1];
        for (int i = 1; i < suffix.length; i++) {
            suffix[i] = suffix[i-1] + nums[i-1];
        }

        int max = Integer.MIN_VALUE;
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 1; i < suffix.length; i++) {
            if (suffix[i] < suffix[endIndex]) {
                max = Math.max(max, suffix[i-1] - suffix[startIndex]);
                startIndex = endIndex;
            }

            endIndex++;
        }
        return max;
    }
}
