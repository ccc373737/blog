package com.ccc.fizz.al.linkedlist;

//输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的循环双向链表。要求不能创建任何新的节点，只能调整树中节点指针的指向。
public class TreeToDoublyListTest {
    static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

    Node pre;
    Node head;
    //中序遍历天然可以获得上一个节点和当前的关系
    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }
        pre = null;
        deal(root);

        head.left = pre;
        pre.right = head;
        return head;
    }

    public void deal(Node current) {
        if (current == null) {
            return;
        }

        deal(current.left);

        if (pre != null) {
            pre.right = current;
            current.left = pre;
        } else {//pre为空，找到了头节点，保存头节点
            head = current;
        }

        pre = current;

        deal(current.right);
    }
}
