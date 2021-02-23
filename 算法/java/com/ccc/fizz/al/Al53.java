package com.ccc.fizz.al;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 * **/
public class Al53 {
    //给定数组 nums = [-1, 0, 1, 2, -1, -4]，
    //
    //满足要求的三元组集合为：
    //[
    //  [-1, 0, 1],
    //  [-1, -1, 2]
    //]
    static List<List<Integer>> list;

    public List<List<Integer>> threeSum(int[] nums) {
        list = new ArrayList<>();
        int k = 0;

        while (k > nums.length - 1) {
            if (nums[k] > 0) {
                return list;
            }

            int left = k + 1;
            int right = nums.length - 1;

            while (right > left) {
                if (nums[k] + nums[left] + nums[right] == 0) {
                    list.add(new ArrayList<>(Arrays.asList(nums[k], nums[left], nums[right])));
                    left++;
                    right--;
                } else if (nums[k] + nums[left] + nums[right] < 0) {
                    left++;
                } else if (nums[k] + nums[left] + nums[right] < 0) {
                    right--;
                }
            }

        }
        //未完
        return list;
    }
}
