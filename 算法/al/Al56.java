package com.ccc.fizz.al;

import java.util.Arrays;

//给定一个非负整数数组，你最初位于数组的第一个位置。
//
//数组中的每个元素代表你在该位置可以跳跃的最大长度。
//
//判断你是否能够到达最后一个位置。
public class Al56 {
    public static void main(String[] args) {
        canJump(new int[]{2,3,1,1,0});
    }

    static boolean[] shadow;
    //输入: [3,2,1,0,4]
    //输出: false
    //解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
    public static boolean canJump(int[] nums) {
        shadow = new boolean[nums.length];
        Arrays.fill(shadow, true);
        return canAr(nums, nums.length - 1);
    }

    public static boolean canAr(int[] nums, int index) {
        if (!shadow[index]) {
            return false;
        }

        if (index == 0) {
            return true;
        }

        for (int i = index; i > 0; i--) {
            int temp = index - i;
            if (temp >= 0 && nums[temp] + temp >= index && canAr(nums, temp)) {
                return true;
            }
        }

        shadow[index] = false;
        return false;
    }

    //从开始遍历跳，不断更新最大值，如果最大值小于某次index，表示无法跳到，如果遍历到最后，表示成功
    public boolean canJump1(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > max) {
                return false;
            }

            max = Math.max(max, i + nums[i]);
        }

        return true;
    }
}
