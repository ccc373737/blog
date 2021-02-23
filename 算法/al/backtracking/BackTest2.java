package com.ccc.fizz.al.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BackTest2 {
    List<List<String>> result;
    LinkedList<String> tempList;
    public List<List<String>> partition(String s) {
        result = new ArrayList<>();
        tempList = new LinkedList<>();
        parBacking(s, 0);
        return result;
    }

    public void parBacking(String target, int start) {
        if (start >= target.length()) {
            return;
        }

        for (int i = start; i < target.length(); i++) {
            if (isSymmetry(target, start, i)) {
                tempList.add(target.substring(start, i + 1));
            } else {
                continue;
            }

            parBacking(target, i + 1);

            if (tempList.size() > 0 && i == target.length() - 1) {
                result.add(new ArrayList<>(tempList));
            }
            tempList.removeLast();
        }
    }

    public boolean isSymmetry(String target, int start, int end) {
        while (start < end) {
            if (!(target.charAt(start) == target.charAt(end))) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    List<String> addList;
    public List<String> restoreIpAddresses(String s) {
        addList = new ArrayList<>();
        tempList = new LinkedList<>();
        addressBacking(s, 0);
        return addList;
    }

    public void addressBacking(String s, int start) {
        if (start < s.length() && tempList.size() >= 4) {
            return;
        }

        if (start >= s.length() && tempList.size() < 4) {
            return;
        }

        if (start >= s.length()) {
            addList.add(String.join(".", tempList));
            return;
        }

        for (int i = start; i < s.length(); i++) {
            String temp = s.substring(start, i + 1);
            if (temp.charAt(0) == '0' && temp.length() > 1) {
                break;
            }

            if (isVaild(temp)) {
                tempList.add(temp);
            } else {
                continue;
            }

            addressBacking(s, i + 1);
            tempList.removeLast();
        }
    }

    public boolean isVaild(String target) {
        return target.length() <= 3 && Integer.valueOf(target) >= 0 && Integer.valueOf(target) <= 255;
    }


    //78. 子集
    //给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
    //解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
    List<List<Integer>> setResult;
    LinkedList<Integer> subTempList;
    public List<List<Integer>> subsets(int[] nums) {
        setResult = new ArrayList<>();
        subTempList = new LinkedList<>();
        subSetsBacking(nums, 0);
        return setResult;
    }

    public void subSetsBacking(int[] nums, int start) {
        setResult.add(new ArrayList<>(subTempList));

        if (start >= nums.length) {
            return;
        }

        for (int i = start; i < nums.length; i++) {
            subTempList.add(nums[i]);
            subSetsBacking(nums, i + 1);
            subTempList.removeLast();
        }
    }

    //90. 子集 II
    //给定一个可能包含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
    //
    //说明：解集不能包含重复的子集。
    List<List<Integer>> setResultWithDup;
    LinkedList<Integer> subTempListWithDup;
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        setResultWithDup = new ArrayList<>();
        subTempListWithDup = new LinkedList<>();

        Arrays.sort(nums);
        subsetsWithDupBack(nums, 0);
        return setResultWithDup;
    }

    public void subsetsWithDupBack(int[] nums, int start) {
        setResultWithDup.add(new ArrayList<>(subTempListWithDup));
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }

            subTempListWithDup.add(nums[i]);
            subsetsWithDupBack(nums, i + 1);
            subTempListWithDup.removeLast();
        }
    }

    //491. 递增子序列
    //给定一个整型数组, 你的任务是找到所有该数组的递增子序列，递增子序列的长度至少是2。
    List<List<Integer>> subsequencesResult;
    LinkedList<Integer> subsequencesTempList;
    public List<List<Integer>> findSubsequences(int[] nums) {
        subsequencesResult = new ArrayList<>();
        subsequencesTempList = new LinkedList<>();
        findSubseBack(nums, 0);
        return subsequencesResult;
    }

    public void findSubseBack(int[] nums, int start) {
        if (subsequencesTempList.size() >= 2) {
            subsequencesResult.add(new ArrayList<>(subsequencesTempList));
        }

        for (int i = start; i < nums.length; i++) {
            if ((i > start && isSame(nums, start, i)) || (subsequencesTempList.size() > 0 && nums[i] < subsequencesTempList.getLast())) {
                continue;
            }

            subsequencesTempList.add(nums[i]);
            findSubseBack(nums, i + 1);
            subsequencesTempList.removeLast();
        }
    }

    public boolean isSame(int[] nums, int start, int end) {
        for (int i = start; i <= end - 1; i++) {
            if (nums[i] == nums[end]) {
                return true;
            }
        }
        return false;
    }

}
