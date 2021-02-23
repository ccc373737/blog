package com.ccc.fizz.al;

/**
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。
 * **/
public class Al15 {

    public static void main(String[] args) {
        int[] t = new int []{1,2,3,4};
        exchange(t);
        System.out.println(t);

        System.out.println(0 & 1);
    }

    public static int[] exchange(int[] nums) {
        if (nums.length == 1) {
            return nums;
        }

        int start = 0, end = nums.length - 1;

        while (start < end) {
            //前半部分跳过奇数
            while (start < end && (nums[start] & 1) == 1) {
                start++;
            }

            //后半部分跳过偶数
            while (start < end && (nums[end] & 1) == 0) {
                end--;
            }

            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
        }

        return nums;
    }
}
