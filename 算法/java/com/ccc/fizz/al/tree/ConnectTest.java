package com.ccc.fizz.al.tree;

import java.util.LinkedList;
import java.util.Queue;

//填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
//
//初始状态下，所有 next 指针都被设置为 NULL。
//
//你只能使用常量级额外空间。
//使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。
public class ConnectTest {
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };

    public Node connect(Node root) {
        if (root == null) {
            return null;
        }

        Queue<Node> queue = new LinkedList<>();
        Node pre = null;
        Node current = null;
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                current = queue.poll();

                if (current.left != null) {
                    queue.offer(current.left);
                }

                if (current.right != null) {
                    queue.offer(current.right);
                }

                //前一个和当前连接
                if (pre != null) {
                    pre.next = current;
                }
                pre = current;
            }
            //每层结束前指针设置为null
            pre = null;
        }
        return root;
    }

    //使用常数级空间
    //利用完美二叉树的特性，必然左右节点都存在
    public Node connect1(Node root) {
        if (root == null) {
            return null;
        }

        Node current = root;
        while (current.left != null) {
            Node temp = current.left;

            //存在下一个节点，
            //1.左子节点下一个为右子节点
            //2.右子节点下一个为当前节点下一个的左子节点
            while (current.next != null) {
                current.left.next = current.right;
                current.right.next = current.next.left;
                current = current.next;
            }
            //没有下一个节点的内部处理
            current.left.next = current.right;

            //进入下一层
            current = temp;
        }
        return root;
    }

    //递归解法
    public Node connect2(Node root) {
        if (root == null) {
            return null;
        }

        Node left = root.left;
        Node right = root.right;

        while (left != null) {
            left.next = right;
            left = left.right;
            right = right.left;
        }

        connect2(root.left);
        connect2(root.right);
        return root;
    }

    //给定一个二叉树 实现右节点指向功能
    public Node connect3(Node root) {
        if (root == null) {
            return null;
        }

        Node current = root;

        //某一层头节点
        Node head = null;
        //上一个节点
        Node pre = null;
        while (current != null) {
            //虚拟头节点
            head = new Node(-1);
            pre = head;

            //某一层的处理，当作一个链表，不断连接节点的子节点
            while (current != null) {
                if (current.left != null) {
                    pre.next = current.left;
                    pre = pre.next;
                }

                if (current.right != null) {
                    pre.next = current.right;
                    pre = pre.next;
                }
                current = current.next;
            }
            current = head.next;
        }
        return root;
    }
}
