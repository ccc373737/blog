package com.ccc.fizz.al.tree;

import java.util.*;

public class SymmetricTest {
    //101给定一个二叉树，检查它是否是镜像对称的。
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return twoIsSymmetric(root.left, root.right);
    }

    public boolean twoIsSymmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }

        if (left == null || right == null) {
            return false;
        }
        //节点值相同且。。。
        return left.val == right.val && twoIsSymmetric(left.right, right.left) && twoIsSymmetric(left.left, right.right);
    }

    //栈遍历解法
    public boolean isSymmetric1(TreeNode root) {
        if (root == null) {
            return false;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root.left);
        stack.push(root.right);

        while (!stack.isEmpty()) {
            TreeNode left = stack.pop();
            TreeNode right = stack.pop();
            //都为null，进入下一次
            if (left == null && right == null) {
                continue;
            }
            //不相等情况返回false
            if (left == null || right == null || left.val != right.val) {
                return false;
            }

            stack.push(left.right);
            stack.push(right.left);

            stack.push(left.left);
            stack.push(right.right);
        }
        return true;
    }
}
