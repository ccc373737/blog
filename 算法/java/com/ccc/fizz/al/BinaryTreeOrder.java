package com.ccc.fizz.al;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinaryTreeOrder {

    List<Integer> all = new ArrayList<>();

    //递归
    public void ex(TreeNode root) {
        if (root == null) {
            return;
        }

        System.out.println(root.val);
        ex(root.left);
        ex(root.right);
    }


    //前序迭代遍历
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        //curr相当于递归的入参，stack相当于递归的栈用于存放临时变量
        while (curr != null || !stack.isEmpty()) {
            //永远先压入左节点，压入之前输出root
            if (curr != null) {
                list.add(curr.val);
                stack.push(curr);
                curr = curr.left;
            } else {
                curr = stack.pop().right;
            }
        }

        return list;
    }

    //中序迭代
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        //curr相当于递归的入参，stack相当于递归的栈用于存放临时变量
        while (curr != null || !stack.isEmpty()) {
            //永远先压入左节点
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {//弹出中节点输出，并放入右节点
                curr = stack.pop();
                list.add(curr.val);
                curr = curr.right;
            }
        }

        return list;
    }

    //后序迭代
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        TreeNode lastRight = null;

        //curr相当于递归的入参，stack相当于递归的栈用于存放临时变量
        while (curr != null || !stack.isEmpty()) {
            //永远先压入左节点
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {//弹出中节点后需要判断是否存在右节点，如果存在压入，否则输出中节点
                curr = stack.peek();
                if (curr.right == null || curr.right == lastRight) {
                    curr = stack.pop();
                    list.add(curr.val);
                    lastRight = curr;
                    curr = null;
                } else {
                    curr = curr.right;
                }
            }
        }

        return list;
    }

}
