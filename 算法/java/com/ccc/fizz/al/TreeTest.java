package com.ccc.fizz.al;

public class TreeTest {
    public static void main(String[] args) {
        /**      10
         *   6        14
         * 4   8   12   16
         * **/
        BinaryTreeNode root = new BinaryTreeNode(new BinaryTreeNode(new BinaryTreeNode(4), 6, new BinaryTreeNode(8)), 10, new BinaryTreeNode(new BinaryTreeNode(12), 14, new BinaryTreeNode(16)));
        after(root);
    }

    //根左右
    public static void front(BinaryTreeNode root) {
        if (root.getLeftNode() == null || root.getRightNode() == null) {
            System.out.println(root.getValue());
            return;
        }

        System.out.println(root.getValue());
        front(root.getLeftNode());
        front(root.getRightNode());
    }

    //左根右
    public static void mid(BinaryTreeNode root) {
        if (root.getLeftNode() == null || root.getRightNode() == null) {
            System.out.println(root.getValue());
            return;
        }

        mid(root.getLeftNode());
        System.out.println(root.getValue());
        mid(root.getRightNode());
    }

    //左右根
    public static void after(BinaryTreeNode root) {
        if (root.getLeftNode() == null || root.getRightNode() == null) {
            System.out.println(root.getValue());
            return;
        }

        after(root.getLeftNode());
        after(root.getRightNode());
        System.out.println(root.getValue());
    }
}
