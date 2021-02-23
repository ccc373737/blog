package com.ccc.fizz.al;

/**
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 *
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
 *
 *
 * **/
public class Al46 {
    //对于公共父节点定义为
    //1.root的左右节点分别包含p和q
    //2.root为p，root的左右节点其中一个包含q

    //递归左右节点的情况
    //1.左右节点均为空，返回null
    //2.左右节点不为空，返回root
    //3.左节点为空，直接返回右节点，此时右节点可能是p或q本身，可能p和q都包含在右节点中，但无论如何都是返回右节点
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) {
            return root;
        }

        if (left == null) {
            return right;
        }

        if (right == null) {
            return left;
        }

        return null;
    }
}
