package com.ccc.fizz.al.tree;

import java.util.Stack;

public class SuffixTreeTest {
    class Node {
        Node left;
        Node right;
        String val;

        Node(String val) {
            this.val = val;
        }

        public int evaluate() {
            switch (val) {
                case "+" : return left.evaluate() + right.evaluate();
                case "-" : return left.evaluate() - right.evaluate();
                case "*" : return left.evaluate() * right.evaluate();
                case "/" : return left.evaluate() / right.evaluate();
                default : return Integer.valueOf(val);
            }
        };
    }

    Node buildTree(String[] postfix) {
        Stack<Node> stack = new Stack<>();
        for (String s : postfix) {
            Node node = new Node(s);
            if ("+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s)) {
                node.right = stack.pop();
                node.left = stack.pop();
            }
            stack.push(node);
        }
        return stack.pop();
    }
}
