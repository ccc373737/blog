package com.ccc.fizz.al;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//给定一个 没有重复 数字的序列，返回其所有可能的全排列。
public class Al54 {

    public static void main(String[] args) {
        subsets(new int[]{1,2,3});
    }

    static List<List<Integer>> list;

    public static List<List<Integer>> permute(int[] nums) {
        list = new ArrayList<>();
        addList(new ArrayList<>(), nums, 0);
        return list;
    }

    public static void addList(List<Integer> sonList, int[] nums, int index) {
        if (nums.length == index) {
            List<Integer> temp = new ArrayList<>(sonList);
            list.add(temp);
            System.out.println(Arrays.toString(temp.toArray()));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (sonList.contains(nums[i])) {
                continue;
            }

            sonList.add(nums[i]);
            addList(sonList, nums, index + 1);
            sonList.remove(sonList.size() - 1);
        }
    }

    //给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
    //说明：解集不能包含重复的子集。
    public static List<List<Integer>> subsets(int[] nums) {
        list = new ArrayList<>();
        addSub(new ArrayList<>(), nums, 0);
        return list;
    }

    public static void addSub(List<Integer> sonList, int[] nums, int index) {
        List<Integer> temp = new ArrayList<>(sonList);
        list.add(temp);
        System.out.println(Arrays.toString(temp.toArray()));

        for (int i = index; i < nums.length; i++) {
            sonList.add(nums[i]);
            addSub(sonList, nums, i + 1);
            sonList.remove(sonList.size() - 1);
        }
    }
}
