package com.ccc.fizz.al;

import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 输入一棵二叉树和一个整数，打印出二叉树中节点值的和为输入整数的所有路径。从树的根节点开始往下一直到叶节点所经过的节点形成一条路径。
 *               5
 *              / \
 *             4   8
 *            /   / \
 *           11  13  4
 *          /  \    / \
 *         7    2  5   1
 * **/
public class Al18 {

    List<List<Integer>> sumList = new ArrayList<>();

    LinkedList<Integer> one = new LinkedList<>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        addOne(root, sum);
        return sumList;
    }

    public void addOne(TreeNode root, int sum) {
        if (root != null) {
            one.add(root.val);
            sum -= root.val;

            if (sum == 0 && root.left == null && root.right == null) {
                sumList.add(new ArrayList<>(one));
            } else {
                addOne(root.left, sum);
                addOne(root.right, sum);
            }

            //无论成功与否，需要回溯删除
            one.removeLast();
        }
    }
}
