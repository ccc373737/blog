package com.ccc.fizz.al.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class StackBasicTest {
    //20. 有效的括号
    //给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (cur == '(' || cur == '{' || cur == '[') {
                stack.push(cur);
            }

            if (cur == ')') {
                if (stack.isEmpty() || stack.peek() != '(') {
                    return false;
                }
                stack.pop();
            }

            if (cur == '}') {
                if (stack.isEmpty() || stack.peek() != '{') {
                    return false;
                }
                stack.pop();
            }

            if (cur == ']') {
                if (stack.isEmpty() || stack.peek() != '[') {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    //42. 接雨水
    //给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int max = 0;
        if (height.length < 2) {
            return max;
        }

        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int index = stack.pop();

                if (!stack.isEmpty()) {
                    int width = i - stack.peek() - 1;
                    int he = Math.min(height[i], height[stack.peek()]) - height[index];
                    max += width * he;
                }
            }
            stack.push(i);
        }
        return max;
    }

    //84. 柱状图中最大的矩形
    //给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
    //
    //求在该柱状图中，能够勾勒出来的矩形的最大面积。
    public int largestRectangleArea(int[] heights) {
        int[] numCopy = new int[heights.length + 1];
        for (int i = 0; i < heights.length; i++) {
            numCopy[i] = heights[i];
        }
        numCopy[heights.length] = 0;

        Stack<Integer> stack = new Stack<>();
        int max = 0;

        for (int i = 0; i < numCopy.length; i++) {
            while (!stack.isEmpty() && numCopy[i] < numCopy[stack.peek()]) {
                int index = stack.pop();
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                int he = numCopy[index];

                max = Math.max(max, width * he);
            }
            stack.push(i);
        }
        return max;
    }

    //739. 每日温度
    /*请根据每日 气温 列表，重新生成一个列表。对应位置的输出为：要想观测到更高的气温，至少需要等待的天数。如果气温在这之后都不会升高，请在该位置用 0 来代替。

    例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，
                       你的输出应该是 [1,  1,  4,  2,  1,  1,  0,  0]。*/
    public int[] dailyTemperatures(int[] T) {
        int[] result = new int[T.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < T.length; i++) {
            while (!stack.isEmpty() && T[i] > T[stack.peek()]) {
                int index = stack.pop();
                result[index] = i - index;
            }

            stack.push(i);
        }
        return result;
    }

    //394. 字符串解码
    //输入：s = "2[abc]3[cd]ef"
    //输出："abcabccdcdcdef"
    //输入：s = "abc3[cd]xyz"
    //输出："abccdcdcdxyz"
    //双栈一个保存值，一个保存个数，遇到[时压入栈，遇到]时计算值
    public String decodeString(String s) {
        Stack<Integer> numStack = new Stack<>();
        Stack<String> strStack = new Stack<>();
        int currNum = 0;
        String currStr = "";

        for (char val : s.toCharArray()) {
            if (val == '[') {
                numStack.push(currNum);
                strStack.push(currStr);
                //置为初始值
                currNum = 0;
                currStr = "";
            } else if (val == ']') {
                //注意这时有三个值，一个从strStack弹出的值，为整体字符串的前缀
                //一个从numStack中弹出的值为当前字符串循环次数
                //一个currStr是当前字符串
                int count = numStack.pop();
                String pre = strStack.pop();
                for (int i = 0; i < count; i++) {
                    pre += currStr;
                }
                currStr = pre;//这里将处理后的字符串赋值，作为之后的前缀字符串
            } else if (Character.isDigit(val)) {//为数字情况
                currNum = currNum * 10 + ((int) val - (int) '0');
            } else {//为字母情况
                currStr += val;
            }
        }
        return currStr;
    }

    //456. 132模式
    public boolean find132pattern(int[] nums) {
        System.out.println(nums);
        if (nums.length < 3) {
            return false;
        }
        //维护一个最小前值的数组
        int[] preMin = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                preMin[i] = nums[i];
            } else {
                preMin[i] = Math.min(preMin[i-1], nums[i]);
            }
        }

        //倒序维护一个递减栈
        //当这个值大于前一个值时，不断弹出栈，找到之前序列中比这个值小的值，比较每个值与最小前值
        Stack<Integer> stack = new Stack<>();
        stack.push(nums[nums.length - 1]);
        for (int i = nums.length - 2; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] > stack.peek()) {
                //如果当前栈里的元素小于i对应的min，那么肯定也小于a[i-1] 到a[1]对应min
                //所以出栈元素一定不符合条件
                int pop = stack.pop();
                if (pop > preMin[i]) {
                    return true;
                }
            }
            stack.push(nums[i]);

        }
        return false;
    }

    //402. 移掉K位数字
    //给定一个以字符串表示的非负整数 num，移除这个数中的 k 位数字，使得剩下的数字最小。
    //递增栈
    public String removeKdigits(String num, int k) {
        char[] vals = num.toCharArray();
        Deque<Character> deque = new ArrayDeque();

        for (int i = 0; i < vals.length; i++) {
            while (k > 0 && !deque.isEmpty() && vals[i] < deque.peekLast()) {
                deque.pollLast();
                k--;
            }

            deque.addLast(vals[i]);
        }

        while (k > 0) {
            deque.pollLast();
            k--;
        }

        while (!deque.isEmpty() && deque.getFirst() == '0') {
            deque.pollFirst();
        }

        String result = "";
        while (!deque.isEmpty()) {
            result += deque.pollFirst();
        }
        return result.isEmpty() ? "0" : result;
    }

    //316. 去除重复字母
    public String removeDuplicateLetters(String s) {
        char[] vals = s.toCharArray();
        int[] count = new int[26];//计算出现个数
        boolean[] flags = new boolean[26];//是否出现

        for (char val : vals) {
            count[charOffset(val)]++;
        }

        Deque<Character> deque = new ArrayDeque<>();
        for (char val : vals) {
            count[charOffset(val)]--;

            if (flags[charOffset(val)]) {
                continue;
            }

            while (!deque.isEmpty() && val < deque.peekLast() && count[charOffset(deque.peekLast())] > 0) {
                flags[charOffset(deque.pollLast())] = false;
            }

            flags[charOffset(val)] = true;
            deque.addLast(val);
        }

        String result = "";
        while (!deque.isEmpty()) {
            result += deque.pollFirst();
        }
        return result;
    }

    public int charOffset(char val) {
        return (int) val - (int) 'a';
    }

}
