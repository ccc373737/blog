package com.ccc.fizz.al.backtracking;

import java.util.*;

public class BackTest3 {

    //46. 全排列
    //给定一个 没有重复 数字的序列，返回其所有可能的全排列。
    List<List<Integer>> permuteResult;
    LinkedList<Integer> permuteTempList;
    boolean[] isFind;
    public List<List<Integer>> permute(int[] nums) {
        permuteResult = new ArrayList<>();
        permuteTempList = new LinkedList<>();
        isFind = new boolean[nums.length];
        premuteBack(nums);
        return permuteResult;
    }

    public void premuteBack(int[] nums) {
        if (permuteTempList.size() == nums.length) {
            permuteResult.add(new ArrayList<>(permuteTempList));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (isFind[i]) {
                continue;
            }

            permuteTempList.add(nums[i]);
            isFind[i] = true;

            premuteBack(nums);

            permuteTempList.removeLast();
            isFind[i] = false;
        }
    }

    //47. 全排列 II
    //给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
    //输入：nums = [1,1,2]
    //输出：[[1,1,2],[1,2,1],[2,1,1]]
    List<List<Integer>> permuteUniqueResult;
    LinkedList<Integer> permuteUniqueTempList;
    boolean[] isFindUnique;
    public List<List<Integer>> permuteUnique(int[] nums) {
        permuteUniqueResult = new ArrayList<>();
        permuteUniqueTempList = new LinkedList<>();
        isFindUnique = new boolean[nums.length];
        premuteUniqueBack(nums);
        return permuteUniqueResult;
    }

    public void premuteUniqueBack(int[] nums) {
        if (permuteUniqueTempList.size() == nums.length) {
            permuteUniqueResult.add(new ArrayList<>(permuteUniqueTempList));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (isFindUnique[i]) {
                continue;
            }

            if (isSame(nums, 0, i)) {
                continue;
            }

            permuteUniqueTempList.add(nums[i]);
            isFindUnique[i] = true;

            premuteUniqueBack(nums);

            permuteUniqueTempList.removeLast();
            isFindUnique[i] = false;
        }
    }

    public boolean isSame(int[] nums, int start, int end) {
        for (int i = start; i <= end - 1; i++) {
            if (nums[i] == nums[end] && !isFindUnique[i]) {
                return true;
            }
        }
        return false;
    }

    //332. 重新安排行程
    LinkedList<String> result;
    List<String> findItineraryResult;
    boolean[] findAry;
    public List<String> findItinerary(List<List<String>> tickets) {
        result = new LinkedList<>();
        findAry = new boolean[tickets.size()];
        result.add("JFK");
        findItineraryBack(tickets, "JFK");
        return findItineraryResult;
    }

    public void findItineraryBack(List<List<String>> tickets, String target) {
        if (findItineraryResult != null) {
            return;
        }

        if (result.size() == tickets.size() + 1) {
            findItineraryResult = new ArrayList<>(result);
            return;
        }

        Map<List<String>, Integer> map = new TreeMap<List<String>, Integer>((x, y) -> x.get(1).compareTo(y.get(1)));

        for (int i = 0; i < tickets.size(); i++) {//得到全部相同的起始点和位置
            if (target.equals(tickets.get(i).get(0)) && !findAry[i]) {
                map.put(tickets.get(i), i);
            }
        }

        //按字典序排序，保证第一个得到的结果就是最优的
        for (Map.Entry<List<String>, Integer> entry : map.entrySet()) {
            result.add(entry.getKey().get(1));
            findAry[entry.getValue()] = true;

            findItineraryBack(tickets, entry.getKey().get(1));

            result.removeLast();
            findAry[entry.getValue()] = false;
        }
    }
}
