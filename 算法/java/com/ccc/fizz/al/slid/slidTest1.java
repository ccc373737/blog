package com.ccc.fizz.al.slid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class slidTest1 {
    //给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的 连续 子数组，并返回其长度。如果不存在符合条件的子数组，返回 0。
    //
    //输入：s = 7, nums = [2,3,1,2,4,3]
    //输出：2
    //解释：子数组 [4,3] 是该条件下的长度最小的子数组。
    public int minSubArrayLen(int s, int[] nums) {
        int result = Integer.MAX_VALUE;
        int sum = 0;
        int start = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            while (sum >= s && start <= i) {
                result = Math.min(result, i - start + 1);
                sum -= nums[start];
                start++;
            }
        }

        return result == Integer.MAX_VALUE ? 0 : result;
    }

    //3. 无重复字符的最长子串
    //给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
    //abcabcbb abc
    //pwwkew wke
    public int lengthOfLongestSubstring(String s) {
        //字符 -> 最后出现的位置
        Map<Character, Integer> map = new HashMap<>();
        int start = 0;
        int result = 0;

        char[] vals = s.toCharArray();
        for (int i = 0; i < vals.length; i++) {
            if (map.containsKey(vals[i]) && map.get(vals[i]) >= start) {
                start = map.get(vals[i]) + 1;
            }
            result = Math.max(result, i - start + 1);
            map.put(vals[i], i);
        }
        return result;
    }

    //76. 最小覆盖子串
    /*给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
        注意：如果 s 中存在这样的子串，我们保证它是唯一的答案。
        输入：s = "ADOBECODEBANC", t = "ABC"
        输出："BANC"*/
    public String minWindow(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();

        //初始化所有需要的字符个数
        for (char val : t.toCharArray()) {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }

        int count = t.length();
        int start = 0;
        String result = "";
        char[] vals = s.toCharArray();
        for (int i = 0; i < vals.length; i++) {
            int temp = map.getOrDefault(vals[i], 0) - 1;
            if (temp >= 0) {
                count--;
            }
            map.put(vals[i], temp);

            while (count == 0 && start <= i) {
                if (result.isEmpty() || i - start + 1 < result.length()) {
                    result = s.substring(start, i + 1);
                }

                map.put(vals[start], map.get(vals[start]) + 1);
                if (map.get(vals[start]) > 0) {//表示需要字符被删除了，不需要的字符在这个算法一定小于等于0
                    count++;
                }
                start++;
            }
        }
        return result;
    }

    //438. 找到字符串中所有字母异位词
    //给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引。
    //
    //字符串只包含小写英文字母，并且字符串 s 和 p 的长度都不超过 20100。
    //s: "cbaebabacd" p: "abc" [0,6]
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> list = new ArrayList<>();
        if (s.isEmpty() || p.length() > s.length()) {
            return list;
        }

        int[] curr = new int[26];
        for (char val : p.toCharArray()) {
            curr[getIndex(val)]++;
        }

        char[] vals = s.toCharArray();
        int length = p.length();
        int count = length;
        //初始化滑动窗口
        for (int i = 0; i < length; i++) {
            curr[getIndex(vals[i])]--;
            if (curr[getIndex(vals[i])] >= 0) {
                count--;
            }
        }

        if (count == 0) {
            list.add(0);
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
                list.add(start);
            }
        }
        return list;
    }

    public int getIndex(char val) {
        return (int) val - (int) 'a';
    }

    //904. 水果成篮
    //[1,2,3,2,2] 4 [2,3,2,2]
    //[3,3,3,1,2,1,1,2,3,3,4] 5 [1,2,1,1,2]
    //换句话说，寻找连续的最大的子数组，该子数组中只能出现两种类型元素
    public int totalFruit(int[] tree) {
        Map<Integer, Integer> map = new HashMap<>();
        int start = 0;
        int result = 0;
        for (int i = 0; i < tree.length; i++) {
            map.put(tree[i], map.getOrDefault(tree[i], 0) + 1);

            if (map.size() <= 2) {
                result = Math.max(result, i - start + 1);
            }

            while (map.size() > 2) {
                if (map.get(tree[start]) == 1) {
                    map.remove(tree[start]);
                } else {
                    map.put(tree[start], map.get(tree[start]) - 1);
                }
                start++;
            }
        }
        return result;
    }

    public int lengthOfLongestSubstringTwoDistinct(String s) {
        char[] vals = s.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        int start = 0;
        int result = 0;
        for (int i = 0; i < vals.length; i++) {
            map.put(vals[i], map.getOrDefault(vals[i], 0) + 1);

            if (map.size() <= 2) {
                result = Math.max(result, i - start + 1);
            }

            while (map.size() > 2) {
                if (map.get(vals[start]) == 1) {
                    map.remove(vals[start]);
                } else {
                    map.put(vals[start], map.get(vals[start]) - 1);
                }
                start++;
            }
        }
        return result;
    }

    //930. 和相同的二元子数组
    //在由若干 0 和 1  组成的数组 A 中，有多少个和为 S 的非空子数组。
    //输入：A = [1,0,1,0,1], S = 2
    //101 1010 0101 101
    public int numSubarraysWithSum(int[] A, int S) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int sum = 0;
        int result = 0;
        for (int val : A) {
            sum += val;
            result += map.getOrDefault(sum - S, 0);

            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return result;
    }

    //978. 最长湍流子数组
    public int maxTurbulenceSize(int[] arr) {
        int result = 1;
        int start = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            result = Math.max(result, i - start + 1);

            if ((i & 1) == 1) {//奇数
                if (arr[i] <= arr[i + 1]) {//不满足
                    start = i + 1;
                }
            } else {
                if (arr[i] >= arr[i + 1]) {
                    start = i + 1;
                }
            }
        }
        return result;
    }
}
