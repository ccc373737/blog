package com.ccc.fizz.al.slid;

import java.util.List;
import java.util.PriorityQueue;

public class SlidTest2 {


    //567. 字符串的排列
    /*给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。

    换句话说，第一个字符串的排列之一是第二个字符串的子串。

    输入: s1 = "ab" s2 = "eidbaooo"
    输出: True
    解释: s2 包含 s1 的排列之一 ("ba").

    输入: s1= "ab" s2 = "eidboaoo"
    输出: False*/
    public boolean checkInclusion(String s1, String s2) {
        if (s1.isEmpty() || s1.length() > s2.length()) {
            return false;
        }

        int[] curr = new int[26];
        for (char val : s1.toCharArray()) {
            curr[getIndex(val)]++;
        }

        char[] vals = s2.toCharArray();
        int length = s1.length();
        int count = length;
        //初始化滑动窗口
        for (int i = 0; i < length; i++) {
            curr[getIndex(vals[i])]--;
            if (curr[getIndex(vals[i])] >= 0) {
                count--;
            }
        }

        if (count == 0) {
            return true;
        }


        int start = 0;
        for (int i = length; i < vals.length; i++) {
            curr[getIndex(vals[start])]++;
            if (curr[getIndex(vals[start])] > 0) {
                count++;
            }
            start++;

            curr[getIndex(vals[i])]--;
            if (curr[getIndex(vals[i])] >= 0) {
                count--;
            }

            if (count == 0) {
                return true;
            }
        }
        return false;
    }

    public int getIndex(char val) {
        return (int) val - (int) 'a';
    }

    //632. 最小区间
    //你有 k 个 非递减排列 的整数列表。找到一个 最小 区间，使得 k 个列表中的每个列表至少有一个数包含在其中。
    //归并数组，每一轮归并都可以得到n个数组中的最小值进行比较，用大项堆做排序处理
    public int[] smallestRange(List<List<Integer>> nums) {
        int[] indexs = new int[nums.size()];
        //每一轮之后需要推进的数组编号
        int nextIndex = 0;
        int left = Integer.MIN_VALUE / 2;
        int right = Integer.MAX_VALUE / 2;
        int max = Integer.MIN_VALUE;
        //x表示值 y表示数组位置
        PriorityQueue<int[]> queue = new PriorityQueue<>(nums.size(), (x, y) -> Integer.compare(x[0], y[0]));
        //处理头元素
        for (int i = 0; i < nums.size(); i++) {
            max = Math.max(max, nums.get(i).get(nextIndex));
            queue.offer(new int[]{nums.get(i).get(nextIndex), i});
        }

        while (indexs[nextIndex] < nums.get(nextIndex).size()) {
            int[] cur = queue.poll();

            if (max - cur[0] < right - left) {
                right = max;
                left = cur[0];
            }

            nextIndex = cur[1];
            indexs[nextIndex]++;

            if (indexs[nextIndex] < nums.get(nextIndex).size()) {
                max = Math.max(max, nums.get(nextIndex).get(indexs[nextIndex]));
                queue.offer(new int[]{nums.get(nextIndex).get(indexs[nextIndex]), nextIndex});
            }
        }
        return new int[]{left, right};
    }

    //1004. 最大连续1的个数 III
    /*
    给定一个由若干 0 和 1 组成的数组 A，我们最多可以将 K 个值从 0 变成 1 。

    返回仅包含 1 的最长（连续）子数组的长度。*/
    //滑动窗口 不断统计k的个数，还有k就伸张，k小于0就收缩
   public int longestOnes(int[] A, int K) {
        int result = 0;
        int start = 0;
        for (int i = 0; i < A.length; i++) {
             if (A[i] == 0) {
                K--;
            }

            while (K < 0) {
                if (A[start++] == 0) {
                    K++;
                }
            }

            result = Math.max(result, i - start + 1);
        }
        return result;
    }

    //424. 替换后的最长重复字符
    public int characterReplacement(String s, int k) {
        char[] vals = s.toCharArray();
        int[] count = new int[26];

        int maxCount = 0;
        int result = 0;
        int start = 0;
        int end = 0;

        while (end < s.length()) {
            count[(int) vals[end] - (int) 'A']++;
            maxCount = Math.max(maxCount, count[(int) vals[end] - (int) 'A']);

            if (end - start - maxCount + 1 > k) {
                count[(int) vals[start] - (int) 'A']--;
                start++;
            }

            result = Math.max(result, end - start + 1);
            end++;
        }
        return result;
    }

    //1234. 替换子串得到平衡字符串
    //
    public int balancedString(String s) {
        char[] vals = s.toCharArray();
        int[] count = new int[26];
        for (char val : vals) {
            count[(int) val - (int) 'A']++;
        }

        int pi = vals.length / 4;
        int left = 0;
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < vals.length; i++) {
            count[(int) vals[i] - (int) 'A']--;
            while (left <= i + 1 && count[4] <= pi && count[16] <= pi && count[17] <= pi &&count[22] <= pi) {
                result = Math.min(result, i - left + 1);

                if (left <= i) {
                    count[(int) vals[left++] - (int) 'A']++;
                } else {
                    break;
                }
            }
        }
        return result;
    }
}
