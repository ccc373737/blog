package com.ccc.fizz.al.stack;

import java.util.Arrays;
import java.util.Stack;

public class StackTest2 {
    /*
    你现在是一场采用特殊赛制棒球比赛的记录员。这场比赛由若干回合组成，过去几回合的得分可能会影响以后几回合的得分。

    比赛开始时，记录是空白的。你会得到一个记录操作的字符串列表 ops，其中 ops[i] 是你需要记录的第 i 项操作，ops 遵循下述规则：

    整数 x - 表示本回合新获得分数 x
    "+" - 表示本回合新获得的得分是前两次得分的总和。题目数据保证记录此操作时前面总是存在两个有效的分数。
    "D" - 表示本回合新获得的得分是前一次得分的两倍。题目数据保证记录此操作时前面总是存在一个有效的分数。
    "C" - 表示前一次得分无效，将其从记录中移除。题目数据保证记录此操作时前面总是存在一个有效的分数。
    请你返回记录中所有得分的总和。*/
    public int calPoints(String[] ops) {
        Stack<Integer> stack = new Stack<>();
        for (String op : ops) {
            if ("+".equals(op)) {
                Integer pop = stack.pop();
                Integer temp = pop + stack.peek();

                stack.push(pop);
                stack.push(temp);
            } else if ("C".equals(op)) {
                stack.pop();
            } else if ("D".equals(op)) {
                stack.push(2 * stack.peek());
            } else {
                stack.push(Integer.valueOf(op));
            }
        }

        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }

    //1021. 删除最外层的括号
    /*有效括号字符串为空 ("")、"(" + A + ")" 或 A + B，其中 A 和 B 都是有效的括号字符串，+ 代表字符串的连接。例如，""，"()"，"(())()" 和 "(()(()))" 都是有效的括号字符串。

如果有效字符串 S 非空，且不存在将其拆分为 S = A+B 的方法，我们称其为原语（primitive），其中 A 和 B 都是非空有效括号字符串。

给出一个非空有效字符串 S，考虑将其进行原语化分解，使得：S = P_1 + P_2 + ... + P_k，其中 P_i 是有效括号字符串原语。

对 S 进行原语化分解，删除分解中每个原语字符串的最外层括号，返回 S 。。*/
    //"(()())(())(()(()))"
    //"()()()()(())"
    public String removeOuterParentheses(String S) {
        int pre = 0;
        int cur = 0;
        StringBuffer sb = new StringBuffer();
        for (char val : S.toCharArray()) {
            pre = cur;
            cur = val == '(' ? (cur + 1) : (cur - 1);
            //只有从 没有括号变为1个括号或从1个括号变为没有括号时需要删除
            if ((pre == 1 && cur == 0) || (pre == 0 && cur == 1)) {
                continue;
            }

            sb.append(val);
        }
        return sb.toString();
    }

    //907. 子数组的最小值之和
    //给定一个整数数组 A，找到 min(B) 的总和，其中 B 的范围为 A 的每个（连续）子数组
    public int sumSubarrayMins(int[] arr) {
        int MOD = 1000000007;
        Stack<Integer> stack = new Stack<>();
        int[] right = new int[arr.length];
        //默认情况即一直是递增数组，那么每个元素最大可以延伸到n
        Arrays.fill(right, arr.length);
        //计算以i为最小值的向右延伸距离 因为存在相等的情况，需要在左数组或右数组中进行一次相等的判断
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[i] <= arr[stack.peek()]) {
                Integer pop = stack.pop();
                right[pop] = i;
            }
            stack.push(i);
        }

        stack.clear();

        int[] left = new int[arr.length];
        //左边的默认情况为-1
        Arrays.fill(left, -1);
        for (int i = arr.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[i] < arr[stack.peek()]) {
                Integer pop = stack.pop();
                left[pop] = i;
            }
            stack.push(i);
        }

        long result = 0;
        //计算总和
        for (int i = 0; i < arr.length; i++) {
            result += arr[i] % MOD * (right[i] - i) * (i - left[i]) % MOD;
            result %= MOD;
        }
        return (int) result;
    }

    //单次遍历解法
    public int sumSubarrayMins1(int[] arr) {
        int MOD = 1000000007;
        Stack<Integer> stack = new Stack<>();
        long result = 0;

        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[i] <= arr[stack.peek()]) {
                Integer pop = stack.pop();
                int right = i - pop;
                int left = pop - (stack.isEmpty() ? -1 : stack.peek());
                result += right * left % MOD * arr[pop] % MOD;
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            Integer pop = stack.pop();
            int right = arr.length - pop;
            int left = pop - (stack.isEmpty() ? -1 : stack.peek());
            result += right * left % MOD * arr[pop] % MOD;
        }
        return (int)result;
    }

    //1130. 叶值的最小代价生成树
    //贪心策略，尽量保证较小的值在底部
    //由于是中序遍历，需要用递减栈来保证顺序
    public int mctFromLeafValues(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        int result = 0;

        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[i] > stack.peek()) {
                Integer pop = stack.pop();
                if (stack.isEmpty()) {//以pop为基准，上一个元素或下一个元素为左子树或右子树，得到一个较小的值进入结果集，同时上一个元素或下一个元素的较小值为下一轮的一个值
                    result += pop * arr[i];
                } else {
                    result += pop * Math.min(stack.peek(), arr[i]);
                }

            }
            stack.push(arr[i]);
        }

        while (!stack.isEmpty()) {
            Integer pop = stack.pop();
            if (!stack.isEmpty()) {
                result += pop * stack.peek();
            }

        }
        return result;
    }

    //85. 最大矩形
    //给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
    //可以转换为求水量的题目，但是由于不知道那一层是底，需要遍历每一层
    public int maximalRectangle(char[][] matrix) {
        int result = 0;
        if (matrix.length == 0) {
            return result;
        }

        int[] one = new int[matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                one[j] = matrix[i][j] == '0' ? 0 : (one[j] + 1);
            }
            result = Math.max(result, largestRectangleArea(one));
        }
        return result;
    }

    public int largestRectangleArea(int[] vals) {
        int[] valsCopy = new int[vals.length + 1];
        for (int i = 0; i < vals.length; i++) {
            valsCopy[i] = vals[i];
        }
        valsCopy[vals.length] = -1;

        Stack<Integer> stack = new Stack<>();
        int result = 0;

        for (int i = 0; i < valsCopy.length; i++) {
            while (!stack.isEmpty() && valsCopy[i] < valsCopy[stack.peek()]) {
                Integer pop = stack.pop();

                int width = stack.isEmpty() ? i : (i - stack.peek() - 1);
                int height = valsCopy[pop];
                result = Math.max(result, width * height);
            }

            stack.push(i);
        }
        return result;
    }

    //面试题 17.21. 直方图的水量
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int result = 0;

        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                Integer pop = stack.pop();
                if (!stack.isEmpty()) {
                    int width = i - stack.peek() - 1;
                    int he = Math.min(height[i], height[stack.peek()]) - height[pop];
                    result += width * he;
                }
            }

            stack.push(i);
        }
        return result;
    }
}
