package com.ccc.fizz.al.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeInvertTest {
    //翻转一棵二叉树。
    //前序或后序都可以
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);

        root.left = right;
        root.right = left;
        return root;
    }

    //上下反转二叉树
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        //当前节点为null或左子节点为null，不用反转，直接返回
        if (root == null || root.left == null) {
            return root;
        }

        //后序操作，先保存左右节点
        TreeNode left = root.left;
        TreeNode right = root.right;
        //递归处理左节点
        left = upsideDownBinaryTree(left);

        //左节点变为根节点，右子节点变为左节点，父节点变为右节点
        left.left = right;
        left.right = root;
        return left;
    }

    int index;
    List<Integer> flist;
    //971. 翻转二叉树以匹配先序遍历
    public List<Integer> flipMatchVoyage(TreeNode root, int[] voyage) {
        index = 0;
        flist = new ArrayList<>();
        if (!dfs(root, voyage)) {
            flist.clear();
            flist.add(-1);
        };
        return flist;
    }

    public boolean dfs(TreeNode root, int[] voyage) {
        if (root == null) {
            return true;
        }
        //长度不匹配或值不同
        if (index >= voyage.length || root.val != voyage[index]) {
            return false;
        }

        index++;
        //存在左节点，且左节点不等，需要翻转
        if (root.left != null && root.left.val != voyage[index]) {
            //当前节点翻转
            flist.add(root.val);
            return dfs(root.right, voyage) && dfs(root.left, voyage);
        } else {//左节点为null的情况也包含在这里
            return dfs(root.left, voyage) && dfs(root.right, voyage);
        }
    }

    //951. 翻转等价二叉树
    /*我们可以为二叉树 T 定义一个翻转操作，如下所示：选择任意节点，然后交换它的左子树和右子树。

    只要经过一定次数的翻转操作后，能使 X 等于 Y，我们就称二叉树 X 翻转等价于二叉树 Y。

    编写一个判断两个二叉树是否是翻转等价的函数。这些树由根节点 root1 和 root2 给出。*/
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        //递归到底部，返回true
        if (root1 == null && root2 == null) {
            return true;
        }

        //节点不相等的情况
        if (root1 == null || root2 == null || root1.val != root2.val) {
            return false;
        }

        //否则比较子节点，左=左，右=右或者翻转之后的左=右，右=左
        return (flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right)) ||
               (flipEquiv(root1.right, root2.left) && flipEquiv(root1.left, root2.right));
    }
}
