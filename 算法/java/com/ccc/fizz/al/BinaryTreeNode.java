package com.ccc.fizz.al;

public class BinaryTreeNode {

    private BinaryTreeNode rightNode;

    private BinaryTreeNode leftNode;

    private Integer value;

    public BinaryTreeNode(Integer value) {
        this.value = value;
    }

    public BinaryTreeNode(BinaryTreeNode leftNode, Integer value, BinaryTreeNode rightNode) {
        this.rightNode = rightNode;
        this.leftNode = leftNode;
        this.value = value;
    }

    public BinaryTreeNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(BinaryTreeNode rightNode) {
        this.rightNode = rightNode;
    }

    public BinaryTreeNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(BinaryTreeNode leftNode) {
        this.leftNode = leftNode;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
