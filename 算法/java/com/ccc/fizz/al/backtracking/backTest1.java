package com.ccc.fizz.al.backtracking;

import java.util.*;

public class backTest1 {
    //77. 组合
    //给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
    List<List<Integer>> combineResult;
    LinkedList<Integer> tempList;
    public List<List<Integer>> combine(int n, int k) {
        combineResult = new ArrayList<>();
        tempList = new LinkedList<>();
        combineBack(1, n, k);
        return combineResult;
    }

    public void combineBack(int start, int n, int k) {
        if (k == 0) {
            combineResult.add(new ArrayList<>(tempList));
            return;
        }

        for (int i = start; i <= n - k + 1; i++) {
            tempList.add(i);
            combineBack(i + 1, n, k - 1);
            tempList.removeLast();
        }
    }

    //216. 组合总和 III
    //找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
    public List<List<Integer>> combinationSum3(int k, int n) {
        combineResult = new ArrayList<>();
        tempList = new LinkedList<>();
        combinSumback(1, k, n);
        return combineResult;
    }

    public void combinSumback(int start, int k, int target) {
        if (target < 0) {
            return;
        }

        if (k == 0 && target == 0) {//所有的数用完，使用target为0
            combineResult.add(new ArrayList<>(tempList));
            return;
        }

        for (int i = start; i <= 9; i++) {
            tempList.add(start);
            combinSumback(start + 1, k - 1, target - start);
            tempList.removeLast();
            start++;
        }
    }

    //17. 电话号码的字母组合
    //给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
    //输入：digits = "23"
    //输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
    char[][] array = new char[][]{{}, {'a','b','c'}, {'d','e','f'}, {'g','h','i'}, {'j','k','l'}, {'m','n','o'}, {'p','q','r','s'}, {'t','u','v'}, {'w','x','y','z'}};
    List<String> letterResult;
    StringBuffer letterSb;
    public List<String> letterCombinations(String digits) {
        letterResult = new ArrayList<>();
        if (digits.isEmpty()) {
            return letterResult;
        }

        letterSb = new StringBuffer();
        letterBacking(digits.toCharArray(), 0);
        return letterResult;
    }

    public void letterBacking(char[] digits, int start) {
        if (start == digits.length) {
            letterResult.add(letterSb.toString());
            return;
        }

        char[] temp = array[(int) digits[start] - (int) '1'];
        for (int i = 0; i < temp.length; i++) {
            letterSb.append(temp[i]);
            letterBacking(digits, start + 1);
            letterSb.deleteCharAt(letterSb.length() - 1);
        }
    }

    //39. 组合总和
    //给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
    //candidates 中的数字可以无限制重复被选取。
    //candidates = [2,3,5], target = 8,
    //[[2,2,2,2],[2,3,3],[3,5]]
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        combineResult = new ArrayList<>();
        tempList = new LinkedList<>();

        Arrays.sort(candidates);
        combinationSumBack(candidates, 0, target);
        return combineResult;
    }

    public void combinationSumBack(int[] candidates, int start, int target) {
        if (target < 0) {
            return;
        }

        if (target == 0) {
            combineResult.add(new ArrayList<>(tempList));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            if (target - candidates[i] < 0) {//剪枝，前提是排序
                break;
            }
            tempList.add(candidates[i]);
            combinationSumBack(candidates, i, target - candidates[i]);
            tempList.removeLast();
        }
    }

    //给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
    //candidates 中的每个数字在每个组合中只能使用一次。
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        combineResult = new ArrayList<>();
        tempList = new LinkedList<>();

        Arrays.sort(candidates);
        combinationSum2Back(candidates, 0, target);
        return combineResult;
    }

    public void combinationSum2Back(int[] candidates, int start, int target) {
        if (target < 0) {
            return;
        }

        if (target == 0) {
            combineResult.add(new ArrayList<>(tempList));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            if (target - candidates[i] < 0) {//剪枝，前提是排序
                break;
            }

            if (i > start && candidates[i] == candidates[i - 1]) {//单次遍历中去重
                continue;
            }

            tempList.add(candidates[i]);
            combinationSum2Back(candidates, i + 1, target - candidates[i]);
            tempList.removeLast();
        }
    }
}
