package com.ccc.fizz.al.tree;

public class SuffixTreeTest2 {
    class Node {
        char val;
        Node left;
        Node right;
        Node() {this.val = ' ';}
        Node(char val) { this.val = val; }
        Node(char val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public Node expTree(String s) {
        if (s == null || s.isEmpty()) return null;
        // 存储数字及表达式
        int idx1 = 0;
        Node[] nums = new Node[s.length()];
        // 存储符号
        int idx2 = 0;
        Node[] ops = new Node[s.length()];

        for (char c : s.toCharArray()) {
            switch(c) {
                case '(':
                    ops[idx2++] = new Node(c);
                    break;
                case ')':
                    // 出栈所有操作符
                    while (idx2 > 0 && ops[idx2 - 1].val != '(') {
                        transform(nums, idx1, ops, idx2);
                        idx1--;
                        idx2--;
                    }
                    // 出栈开括号
                    idx2--;
                    break;
                case '+':
                case '-':
                    while (idx2 > 0 && ops[idx2 - 1].val != '(') {
                        transform(nums, idx1, ops, idx2);
                        idx1--;
                        idx2--;
                    }
                    // 入栈符号
                    ops[idx2++] = new Node(c);
                    break;
                case '*':
                case '/':
                    // 出栈高优先级符号
                    while (idx2 > 0 && (ops[idx2 - 1].val == '*' || ops[idx2 - 1].val == '/')) {
                        transform(nums, idx1, ops, idx2);
                        idx1--;
                        idx2--;
                    }
                    // 入栈符号
                    ops[idx2++] = new Node(c);
                    break;
                // 数字
                default:
                    nums[idx1++] = new Node(c);
                    break;
            }
        }
        // 最后的符号拼接
        while (idx2 > 0) {
            transform(nums, idx1, ops, idx2);
            idx1--;
            idx2--;
        }
        // 最后只剩余一个节点，就是根节点
        return nums[idx1 - 1];
    }

    private void transform(Node[] nums, int idx1, Node[] ops, int idx2) {
        // 操作符与数字结合
        Node num2 = nums[--idx1], num1 = nums[--idx1];
        ops[idx2 - 1].left = num1;
        ops[idx2 - 1].right = num2;
        nums[idx1++] = ops[--idx2];
    }

}
