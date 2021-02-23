package com.ccc.fizz.al;

import java.util.ArrayList;
import java.util.List;

/**
 * 输入一个正整数 target ，输出所有和为 target 的连续正整数序列（至少含有两个数）。
 *
 * 序列内的数字由小到大排列，不同序列按照首个数字从小到大排列。
 *
 * **/
public class Al33 {
    public static void main(String[] args) {
        findContinuousSequence(15);
    }

    public static int[][] findContinuousSequence(int target) {
        int left = 1;
        int right = 1;
        int sum = 1;
        List<int[]> list = new ArrayList<>();
        while (left <= target / 2) {
            if (sum == target) {
                int[] array = new int[right - left + 1];
                int temp = 0;
                for (int i = left; i <= right; i++) {
                    array[temp++] = i;
                }
                list.add(array);

                right++;
                sum += right;
            } else if (sum < target) {
                right++;
                sum += right;
            } else if (sum > target) {
                sum -= left;
                left++;
            }
        }

        int[][] bigArray = new int[list.size()][];
        for (int i = 0; i< bigArray.length; i++) {
            bigArray[i] = list.get(i);
        }
        return bigArray;
    }
}
