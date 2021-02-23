package com.ccc.fizz.al;

/**
 * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的循环双向链表。要求不能创建任何新的节点，只能调整树中节点指针的指向。
 * **/
public class Al21 {

    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    };

    private Node pre;

    private Node head;

    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }
        order(root);
        head.left = pre;
        pre.right = head;
        return head;
    }

    public void order(Node root) {
        if (root == null) {
            return;
        }

        if (root.left != null) {
            order(root.left);
        }

        if (pre != null) {//上一个节点与这个节点形成双向
            pre.right = root;
            root.left = pre;
        } else {//第一次出现pre == null，即是最小节点 头节点
            head = root;
        }
        //保存上一个节点
        pre = root;

        if (root.right != null) {
            order(root.right);
        }

    }
}
