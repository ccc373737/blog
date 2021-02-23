package com.ccc.fizz.al.tree;

public class inorderTest {
    //二叉搜索树的第k大节点
    int min;
    int sk;
    public int kthLargest(TreeNode root, int k) {
        min = 0;
        sk = k;
        kDfs(root);
        return min;
    }

    public void kDfs(TreeNode root) {
        if (root == null) {
            return;
        }

        kDfs(root.right);
        if (--sk == 0) {
            min = root.val;
            return;
        }
        kDfs(root.left);

    }
}
