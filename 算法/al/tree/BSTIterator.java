package com.ccc.fizz.al.tree;

import java.util.Stack;

//173. 二叉搜索树迭代器
/*实现一个二叉搜索树迭代器。你将使用二叉搜索树的根节点初始化迭代器。
调用 next() 将返回二叉搜索树中的下一个最小的数。*/
//用一个辅助栈实现
public class BSTIterator {

    Stack<TreeNode> stack;

    public BSTIterator(TreeNode root) {
        stack = new Stack<>();
        push(root);
    }

    //一路向左压入到底，如果弹出的节点有右节点，再压入
    //保证栈的有序性
    public void push(TreeNode root) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }

    public int next() {
        TreeNode pop = stack.pop();
        if (pop.right != null) {
            push(pop.right);
        }

        return pop.val;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }
}
