package com.ccc.fizz.al.tree;

import java.util.*;

public class NTreeTest {
    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    List<Integer> list;

    public List<Integer> preorder(Node root) {
        list = new ArrayList<>();
        dfs(root);
        return list;
    }

    public void dfs(Node root) {
        if (root == null) {
            return;
        }

        list.add(root.val);

        if (root.children != null) {
            for (Node child : root.children) {
                dfs(child);
            }
        }
    }

    public List<Integer> preorder1(Node root) {
        List<Integer> list = new ArrayList<>();
        Stack<Node> stack = new Stack<>();

        if (root == null) {
            return list;
        }

        stack.push(root);

        while (!stack.isEmpty()) {
            Node temp = stack.pop();
            list.add(temp.val);
            if (temp.children != null) {
                for (int i = temp.children.size() - 1; i >= 0; i--) {
                    stack.push(temp.children.get(i));
                }
            }
        }
        return list;
    }

    public List<Integer> postorder1(Node root) {
        List<Integer> list = new ArrayList<>();
        Stack<Node> stack = new Stack<>();

        if (root == null) {
            return list;
        }

        stack.push(root);

        while (!stack.isEmpty()) {
            Node temp = stack.pop();
            list.add(temp.val);
            if (temp.children != null) {
                for (Node child : temp.children) {
                    stack.push(child);
                }
            }
        }
        Collections.reverse(list);
        return list;
    }

    //给定一个 N 叉树，找到其最大深度。
    //最大深度是指从根节点到最远叶子节点的最长路径上的节点总数。
    public int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }

        int max = 0;
        if (root.children != null) {
            for (Node child : root.children) {
                max = Math.max(max, maxDepth(child));
            }
        }
        return max + 1;
    }
}
