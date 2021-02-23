package com.ccc.fizz.al;

//给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和
public class Al55 {

    public static void main(String[] args) {
        maxSubArray1(new int[]{-2,1,-3,4,-1,2,1,-5,4});
    }
    //输入: [-2,1,-3,4,-1,2,1,-5,4]
    //输出: 6
    //解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
    public int maxSubArray(int[] nums) {
        int current = nums[0];
        int max = current;

        for (int i = 1; i < nums.length; i++) {
            current = Math.max(current, 0);
            current += nums[i];
            if (current < 0) {
                max = Math.max(max, nums[i]);
            } else {
                max = Math.max(max, current);
            }
        }

        return max;
    }
    public static int maxSubArray1(int[] nums) {
        int temp = nums[0];
        int max = temp;

        for (int i = 1; i < nums.length; i++) {
            temp = Math.max(temp + nums[i], nums[i]);
            max = Math.max(max, temp);
        }

        return max;
    }
}
