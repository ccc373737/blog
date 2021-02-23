package com.ccc.fizz.al.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class StackTest3 {
    //71. 简化路径
    /*以 Unix 风格给出一个文件的绝对路径，你需要简化它。或者换句话说，将其转换为规范路径。

    在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..） 表示将目录切换到上一级（指向父目录）；两者都可以是复杂相对路径的组成部分。更多信息请参阅：Linux / Unix中的绝对路径 vs 相对路径

    请注意，返回的规范路径必须始终以斜杠 / 开头，并且两个目录名之间必须只有一个斜杠 /。最后一个目录名（如果存在）不能以 / 结尾。此外，规范路径必须是表示绝对路径的最短字符串。*/

    //"/a/./b/../../c/" 输出："/c"
    //"/a/../../b/../c//.//" 输出："/c"
    //"/a//b////c/d//././/.." 输出："/a/b/c"
    //以/为分隔符，一共就如下几种情况
    // 1.表示当前目录，不用处理 2 空白不用处理 3..向后退 4路径需要压入
    public String simplifyPath(String path) {
        String[] split = path.split("/");
        Stack<String > stack = new Stack<>();

        for (String val : split) {
            if (!stack.isEmpty() && "..".equals(val)) {
                stack.pop();
            } else if (!".".equals(val) && !val.isEmpty() && !"..".equals(val)) {
                stack.push(val);
            }
        }

        if (stack.isEmpty()) {
            return "/";
        }

        StringBuffer sb = new StringBuffer();
        for (String s : stack) {
            sb.append("/" + s);
        }
        return sb.toString();
    }

    //1047. 删除字符串中的所有相邻重复项
    /*给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。

    在 S 上反复执行重复项删除操作，直到无法继续删除。*/
    public String removeDuplicates(String S) {
        Stack<Character> stack = new Stack<>();
        for (char val : S.toCharArray()) {
            if (!stack.isEmpty() && val == stack.peek()) {
                stack.pop();
            } else {
                stack.push(val);
            }
        }

        StringBuffer sb = new StringBuffer();
        for (Character val : stack) {
            sb.append(val);
        }
        return sb.toString();
    }

    //844. 比较含退格的字符串
    /*给定 S 和 T 两个字符串，当它们分别被输入到空白的文本编辑器后，判断二者是否相等，并返回结果。 # 代表退格字符。

    注意：如果对空文本输入退格字符，文本继续为空。*/
    public boolean backspaceCompare(String S, String T) {
        Stack<Character> stack1 = new Stack<>();
        Stack<Character> stack2 = new Stack<>();

        for (char val : S.toCharArray()) {
            if (!stack1.isEmpty() && '#' == val) {
                stack1.pop();
            } else if ('#' != val) {
                stack1.push(val);
            }
        }

        for (char val : T.toCharArray()) {
            if (!stack2.isEmpty() && '#' == val) {
                stack2.pop();
            } else if ('#' != val) {
                stack2.push(val);
            }
        }

        if (stack1.size() != stack2.size()) {
            return false;
        }

        while (!stack1.isEmpty()) {
            if (stack1.pop() != stack2.pop()) {
                return false;
            }
        }
        return true;
    }

    //双指针解法
    public boolean backspaceCompare1(String S, String T) {
        int si = S.length() - 1;
        int ti = T.length() - 1;

        int sbackCount = 0;
        int tbackCount = 0;

        while (si >= 0 || ti >= 0) {
            while (si >= 0) {
                if (S.charAt(si) == '#') {
                    sbackCount++;
                    si--;
                } else if (sbackCount == 0) {
                    break;
                } else {
                    sbackCount--;
                    si--;
                }

            }

            while (ti >= 0) {
                if (T.charAt(ti) == '#') {
                    tbackCount++;
                    ti--;
                } else if (tbackCount == 0) {
                    break;
                } else {
                    tbackCount--;
                    ti--;
                }
            }

            if (si == -1 && ti == -1) {
                return true;
            }

            if (si == -1 || ti == -1) {
                return false;
            }

            if (S.charAt(si) != T.charAt(ti)) {
                return false;
            }
            si--;
            ti--;
        }
        return si == -1 && ti == -1;
    }

    //1209. 删除字符串中的所有相邻重复项 II
    /*给你一个字符串 s，「k 倍重复项删除操作」将会从 s 中选择 k 个相邻且相等的字母，并删除它们，使被删去的字符串的左侧和右侧连在一起。

    你需要对 s 重复进行无限次这样的删除操作，直到无法继续为止。

    在执行完所有删除操作后，返回最终得到的字符串。*/
    public String removeDuplicates(String s, int k) {
        Stack<combi> stack = new Stack<>();
        for (char val : s.toCharArray()) {
            if (stack.isEmpty() || val != stack.peek().val) {
                stack.push(new combi(1, val));
            } else {
                if (++stack.peek().count == k) {
                    stack.pop();
                }
            }
        }

        StringBuffer sb = new StringBuffer();
        for (combi combi : stack) {
            for (int i = 0; i < combi.count; i++) {
                sb.append(combi.val);
            }
        }
        return sb.toString();
    }
    
    class combi {
        int count;
        char val;

        public combi(int count, char val) {
            this.count = count;
            this.val = val;
        }
    }

    //1249. 移除无效的括号
    public String minRemoveToMakeValid(String s) {
        char[] vals = s.toCharArray();
        //保存无效的索引
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < vals.length; i++) {
            if (vals[i] == '(') {
                stack.push(i);
            } else if (vals[i] == ')') {
                if (!stack.isEmpty() && vals[stack.peek()] == '(') {
                    stack.pop();
                } else {
                    stack.push(i);
                }
            }
        }

        StringBuffer sb = new StringBuffer(s);
        while (!stack.isEmpty()) {
            sb.deleteCharAt(stack.pop());
        }
        return sb.toString();
    }

    //1441. 用栈操作构建数组
    public List<String> buildArray(int[] target, int n) {
        List<String> list = new ArrayList<>();
        int index = 1;

        for (int i = 0; i < target.length; i++) {
            while (target[i] != index) {
                list.add("Push");
                list.add("Pop");
                index++;
            }
            list.add("Push");
            index++;
        }
        return list;
    }

    //300. 最长递增子序列
    //给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
    //动态规划 n^2复杂度
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        int result = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            result = Math.max(result, dp[i]);
        }
        return result;
    }
}