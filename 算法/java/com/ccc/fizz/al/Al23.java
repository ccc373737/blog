package com.ccc.fizz.al;

/**
 * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
 *
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 * 摩尔投票
 * [1,2,3,2,2,2,5,4,2]
 * **/
public class Al23 {

    public int majorityElement(int[] nums) {
        int max = 0;
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            if (count == 0) {
                max = nums[i];
                count++;
            } else {
                count = nums[i] != max ? count-1 : count+1;
            }
        }

        return max;
    }
}
