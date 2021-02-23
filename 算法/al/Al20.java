package com.ccc.fizz.al;

public class Al20 {

    int temp = 0;
    int max = 0;

    public void maxDepth1(TreeNode root) {
        if (root == null) {
            return;
        }

        temp++;

        if (root.left == null && root.right == null) {
            max = Math.max(max, temp);
        }

        maxDepth1(root.left);
        maxDepth1(root.right);
        temp--;
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }

        return isBalanced(root.left) && isBalanced(root.right) && Math.abs(maxDepth(root.right) - maxDepth(root.left)) <= 1;
    }
}
