package com.ccc.fizz.al.stack;

import com.ccc.fizz.al.linkedlist.ListNode;

import java.util.*;

public class StackTest1 {
    //856. 括号的分数
    /*给定一个平衡括号字符串 S，按下述规则计算该字符串的分数：

    () 得 1 分。
    AB 得A + B分，其中 A 和 B 是平衡括号字符串。
    (A) 得2 * A分，其中 A 是平衡括号字符串。*/
    //(()(())) 6
    //遇到(入栈，压入一位，遇到)，弹出两位2 * a + b，计算结果再压入，总数量不变
    //a表示下一层深度，b表示上一个同级的分数
    //由于初始括号没有上一个同级，先压入0保持平衡
    public int scoreOfParentheses(String S) {
        char[] vals = S.toCharArray();
        Stack<Integer> stack = new Stack<>();
        stack.push(0);

        for (char val : vals) {
            if (val == '(') {
                stack.push(0);
            } else {
                int a = stack.pop();
                int b = stack.pop();
                stack.push(Math.max(2 * a, 1) + b);
            }
        }
        return stack.pop();
    }

    //496. 下一个更大元素 I
    /*给你两个 没有重复元素 的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。
      请你找出 nums1 中每个元素在 nums2 中的下一个比其大的值。
      nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出 -1 。*/
    // nums1 = [4,1,2], nums2 = [1,3,4,2] 输出: [-1,3,-1]
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length];

        Map<Integer, Integer> map = new HashMap<>();
        //递减栈 压入值（值或索引都可以 因为唯一）
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums2.length; i++) {
            while (!stack.isEmpty() && nums2[i] > nums2[stack.peek()]) {
                map.put(nums2[stack.pop()], nums2[i]);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {//没有下一个最大值的置-1
            map.put(nums2[stack.pop()], -1);
        }

        for (int i = 0; i < result.length; i++) {
            result[i] = map.get(nums1[i]);
        }
        return result;
    }

    //503. 下一个更大元素 II
    /*给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。*/
    //输入: [1,2,1]
    //输出: [2,-1,2]
    public int[] nextGreaterElements2(int[] nums) {
        int[] result = new int[nums.length];
        Arrays.fill(result, -1);

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                result[stack.pop()] = nums[i];
            }
            stack.push(i);
        }

        //第二次循环，处理存在最大值为第二次循环的情况 第二次循环不需要再压入栈，因为第一次已经压入了所有情况
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                result[stack.pop()] = nums[i];
            }
        }
        return result;
    }

    //1190. 反转每对括号间的子串
    public String reverseParentheses(String s) {
        char[] vals = s.toCharArray();
        Stack<Integer> stack = new Stack<>();//栈中存放最近的(的索引位置，遇到)时，翻转起点和终点之间的char

        for (int i = 0; i < vals.length; i++) {
            if (vals[i] == '(') {
                stack.push(i);
            } else if (vals[i] == ')') {
                int start = stack.pop();
                reverseCharArray(vals, start, i);
            }
        }

        StringBuffer sb = new StringBuffer();
        for (char val : vals) {
            if (val == '(' || val == ')') {
                continue;
            }
            sb.append(val);
        }
        return sb.toString();
    }
    
    public void reverseCharArray(char[] vals, int start, int end) {
        while (start < end) {
            char temp = vals[start];
            vals[start] = vals[end];
            vals[end] = temp;
            start++;
            end--;
        }
    }

    //1019. 链表中的下一个更大节点
    /*
给出一个以头节点 head 作为第一个节点的链表。链表中的节点分别编号为：node_1, node_2, node_3, ... 。

每个节点都可能有下一个更大值（next larger value）：对于 node_i，如果其 next_larger(node_i) 是 node_j.val，那么就有 j > i 且  node_j.val > node_i.val，而 j 是可能的选项中最小的那个。如果不存在这样的 j，那么下一个更大值为 0 。

返回整数答案数组 answer，其中 answer[i] = next_larger(node_{i+1}) 。

注意：在下面的示例中，诸如 [2,1,5] 这样的输入（不是输出）是链表的序列化表示，其头节点的值为 2，第二个节点值为 1，第三个节点值为 5 。*/
    //由于链表长度位置，数组不可拓展用一个Treemap保存索引对于的值
    public int[] nextLargerNodes(ListNode head) {
        Map<Integer, Integer> map = new TreeMap<>();

        Stack<int[]> stack = new Stack<>();
        int index = 0;
        while (head != null) {
            while (!stack.isEmpty() && head.val > stack.peek()[1]) {
                int[] temp = stack.pop();
                map.put(temp[0], head.val);
            }

            stack.push(new int[]{index, head.val});
            index++;
            head = head.next;
        }

        while (!stack.isEmpty()) {
            map.put(stack.pop()[0], 0);
        }

        int[] result = new int[map.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = map.get(i);
        }

        return result;
    }

    //331. 验证二叉树的前序序列化
    //前序栈验证，当栈为空，但是preorder没有遍历到最后，返回false
    public boolean isValidSerialization(String preorder) {
        char[] vals = preorder.toCharArray();
        Stack<Integer> stack = new Stack<>();
        boolean flag = false;
        for (int i = 0; i < vals.length; i++) {
            if (i != 0 && stack.isEmpty() && !flag) {//终止条件为不是初始化情况且栈为空了
                return false;
            }

            if (vals[i] == '#') {
                //为2的时候表示弹出左子树，并减1
                //为1的时候表示弹出右子树，如果上一级也为1，也要弹出，不断向上递归
                while (!stack.isEmpty() && stack.peek() == 1) {
                    stack.pop();
                }

                if (!stack.isEmpty()) {
                    stack.push(stack.pop() - 1);
                }
            } else if (vals[i] == ',') {
                if (flag) {
                    stack.push(2);
                    flag = false;
                }
            } else {
                flag = true;
            }
        }
        return stack.isEmpty() && !flag;
    }

    //962. 最大宽度坡
    /*给定一个整数数组 A，坡是元组 (i, j)，其中  i < j 且 A[i] <= A[j]。这样的坡的宽度为 j - i。
    找出 A 中的坡的最大宽度，如果不存在，返回 0 。
    输入：[9,8,1,0,1,9,4,0,4,1]
    输出：7
    解释：
    最大宽度的坡为 (i, j) = (2, 9): A[2] = 1 且 A[9] = 1.*/
    public int maxWidthRamp(int[] A) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < A.length; i++) {
            if (stack.isEmpty() || A[i] < A[stack.peek()]) {
                stack.push(i);//压入索引
            }
        }

        int result = 0;
        for (int i = A.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && (A[i] >= A[stack.peek()] || i < stack.peek())) {
                result = Math.max(result, i - stack.pop());
            }
        }
        return result;
    }

    //1124. 表现良好的最长时间段
    public int longestWPI(int[] hours) {
        int[] diff = new int[hours.length + 1];

        for (int i = 0; i < hours.length; i++) {
            diff[i + 1] = diff[i] + (hours[i] > 8 ? 1 : -1);
        }

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < diff.length; i++) {
            if (stack.isEmpty() || diff[i] < diff[stack.peek()]) {
                stack.push(i);//压入索引
            }
        }

        int result = 0;
        for (int i = diff.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && (diff[i] > diff[stack.peek()] || i < stack.peek())) {
                result = Math.max(result, i - stack.pop());
            }
        }
        return result;
    }

    //946. 验证栈序列
    //给定 pushed 和 popped 两个序列，每个序列中的 值都不重复，只有当它们可能是在最初空栈上进行的推入 push 和弹出 pop 操作序列的结果时，返回 true；否则，返回 false 。
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();

        int index = 0;
        for (int i = 0; i < pushed.length; i++) {
            stack.push(pushed[i]);
            while (!stack.isEmpty() && stack.peek() == popped[index]) {
                stack.pop();
                index++;
            }
        }
        return index == popped.length;
    }

    //921. 使括号有效的最少添加
    public int minAddToMakeValid(String S) {
        char[] vals = S.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < vals.length; i++) {
            if (stack.isEmpty()) {
                stack.push(vals[i]);
                continue;
            }

            if (vals[i] == ')' && stack.peek() == '(') {
                stack.pop();
            } else {
                stack.push(vals[i]);
            }
        }
        return stack.size();
    }

    public int minAddToMakeValid1(String S) {
        while (S.contains("()")) {
            S =  S.replace("()","");
        }
        return S.length();
    }
}
