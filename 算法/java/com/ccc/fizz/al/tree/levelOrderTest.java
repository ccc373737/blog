package com.ccc.fizz.al.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class levelOrderTest {

    //给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
    //输入: [1,2,3,null,5,null,4]
    //输出: [1, 3, 4]
    //解释:
    //
    //   1            <---
    // /   \
    //2     3         <---
    // \     \
    //  5     4       <---

    //依然是层序遍历的变形 只放入每层的最后一个节点
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode temp = null;

        if (root == null) {
            return list;
        }

        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                temp = queue.poll();
                if (temp.left != null) {
                    queue.offer(temp.left);
                }

                if (temp.right != null) {
                    queue.offer(temp.right);
                }
            }
            list.add(temp.val);
        }

        return list;
    }

    //给定一个非空二叉树, 返回一个由每层节点平均值组成的数组
    //层序遍历 计算每层平均值
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode temp = null;

        if (root == null) {
            return list;
        }

        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            double sum = 0;
            for (int i = 0; i < size; i++) {
                temp = queue.poll();
                sum += temp.val;
                if (temp.left != null) {
                    queue.offer(temp.left);
                }

                if (temp.right != null) {
                    queue.offer(temp.right);
                }
            }
            list.add(sum / size);
        }

        return list;
    }

    //给定一个 N 叉树，返回其节点值的层序遍历。（即从左到右，逐层遍历）。
    //
    //树的序列化输入是用层序遍历，每组子节点都由 null 值分隔（参见示例）。
    //N叉树层序遍历
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

    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> one = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        Node temp = null;

        if (root == null) {
            return list;
        }

        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            one.clear();

            for (int i = 0; i < size; i++) {
                temp = queue.poll();
                one.add(temp.val);
                //放入所有子节点
                if (temp.children != null) {
                    for (Node child : temp.children) {
                        queue.offer(child);
                    }
                }
            }

            list.add(new ArrayList<>(one));
        }

        return list;
    }

    //在每个树行中找最大值
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();

        if (root == null) {
            return list;
        }

        TreeNode temp = null;
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                temp = queue.poll();
                if (temp.left != null) {
                    queue.offer(temp.left);
                }

                if (temp.right != null) {
                    queue.offer(temp.right);
                }

                max = Math.max(max, temp.val);
            }
            list.add(max);
        }

        return list;
    }
}
