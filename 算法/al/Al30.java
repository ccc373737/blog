package com.ccc.fizz.al;

import java.util.HashMap;
import java.util.Map;

/**
 * 请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
 *
 * **/
public class Al30 {

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("pwwkew"));
    }

    static int currentCount;

    static int startIndex;

    public static int lengthOfLongestSubstring1(String s) {
        char[] array = s.toCharArray();

        int maxCount = 0;
        currentCount = 0;

        startIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (!contain(array[i], array, i)) {
                currentCount += 1;
                maxCount = Math.max(maxCount, currentCount);
            }
        }
        return maxCount;
    }

    public static boolean contain(char target, char[] array, int index) {
        for (int i = startIndex; i < index; i++) {
            if (array[i] == target) {
                //开始指针为相等字符的后一位
                startIndex = i + 1;
                //当前个数为 当前指针减去开始指针+1
                currentCount = index - startIndex + 1;
                return true;
            }
        }

        return false;
    }

    //hash表优化的滑动窗口
    public static int lengthOfLongestSubstring(String s) {
        char[] array = s.toCharArray();
        //每次将字符对应的索引位置放入map，如果这个字符重复，则这个字符下一个位置为start
        Map<Character, Integer> map = new HashMap<>();

        int start = 0;
        int max = 0;

        for (int i = 0; i < array.length; i++) {
            if (map.containsKey(array[i])) {
                //start的更新出现两种情况
                //1.这个字符正在最长子字符串中，那么start更新为这个字符位置+1
                //2.这个字符不在字符串中，即start位置已经被其他字符更新到更右的位置 pwwkep，更新到第二个p时，start位置已经被第二个w更新到2
                start = Math.max(start, map.get(array[i]) + 1);
            }
            map.put(array[i], i);
            max = Math.max(max, i - start + 1);
        }
        return max;
    }
}
