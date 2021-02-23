package com.ccc.fizz.al;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Al52 {

    static List<List<Integer>> list;

    public static void main(String[] args) {
        combinationSum2(new int[]{10,1,2,7,6,1,5}, 8);
    }

    //给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
    //
    //candidates 中的数字可以无限制重复被选取。
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        list = new ArrayList<>();
        Arrays.sort(candidates);
        putCan(new ArrayList<>(), candidates, 0, 0, target);
        return list;
    }

    public static void putCan(List<Integer> sonList, int[] candidates, int index, int now, int target) {
        for (int i = index; i < candidates.length; i++) {
            if (candidates[i] + now > target) {
                break;
            }

            now += candidates[i];
            sonList.add(candidates[i]);

            if (now == target) {
                List<Integer> temp = new ArrayList<>(sonList);
                list.add(temp);

                System.out.println(Arrays.toString(temp.toArray()));
            } else if (now < target) {
                putCan(sonList, candidates, i, now, target);
            }

            now -= candidates[i];
            sonList.remove(sonList.get(sonList.size() - 1));
        }
    }

    //给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
    //
    //candidates 中的每个数字在每个组合中只能使用一次。
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        list = new ArrayList<>();
        Arrays.sort(candidates);
        addList(new ArrayList<>(), candidates, 0, 0, target);
        return list;
    }

    public static void addList(List<Integer> sonList, int[] nums, int index, int now, int target) {
        for (int i = index; i < nums.length; i++) {
            //最关键的去重剪枝，循环内出现重复 1 1 2 5 6 7 10
            //1...10一定是1...1...10的真子集
            //i > 10保证剪枝一直出现在循环内
            if (i > index && nums[i - 1] == nums[i]) {
                continue;
            }

            //已经大于target的情况 剪枝
            if (now + nums[i] > target) {
                break;
            }

            now += nums[i];
            sonList.add(nums[i]);

            if (now == target) {
                List<Integer> temp = new ArrayList<>(sonList);
                list.add(temp);
                System.out.println(Arrays.toString(temp.toArray()));
            } else if (now < target) {
                //每次递进一层，因为一个数字只能用一次
                addList(sonList, nums, i + 1, now, target);
            }

            now -= nums[i];
            sonList.remove(sonList.size() - 1);
        }
    }
}
