package com.ccc.fizz.al.tree;

import java.util.*;

public class TreeOrder {
    //前序遍历
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while (!stack.isEmpty() || current != null) {
            if (current != null) {
                list.add(current.val);
                stack.push(current);
                current = current.left;
            } else {
                current = stack.pop().right;
            }
        }
        return list;
    }

    //前序遍历2
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty() ) {
            TreeNode pop = stack.pop();

            if (pop != null) {
                list.add(pop.val);
            } else {
                continue;
            }

            stack.push(pop.right);
            stack.push(pop.left);
        }
        return list;
    }

    //中序遍历
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while (!stack.isEmpty() || current != null) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                list.add(stack.peek().val);
                current = stack.pop().right;
            }
        }
        return list;
    }

    //后序遍历1
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode temp = stack.pop();
            if (temp != null) {
                list.add(temp.val);
            } else {
                continue;
            }
            //前序顺序为根左右，这里切换为根右左
            stack.push(temp.left);
            stack.push(temp.right);
        }
        //反转之后变为左右根
        Collections.reverse(list);
        return list;
    }

    //后序遍历2 利用辅助指针
    public List<Integer> postorderTraversal2(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        TreeNode rightTemp = null;

        while (!stack.isEmpty() || current != null) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                //判断是否有右节点
                if (stack.peek().right != null && stack.peek().right != rightTemp) {
                    current = stack.peek().right;
                } else {
                    rightTemp = stack.pop();
                    list.add(rightTemp.val);
                }
            }
        }
        return list;
    }

    //层序遍历1
    //广度优先和队列
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> one = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode temp = null;

        if (root == null) {
            return list;
        }

        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            one.clear();

            for (int i = 0; i < size; i++) {
                temp = queue.poll();
                one.add(temp.val);
                if (temp.left != null) {
                    queue.offer(temp.left);
                }

                if (temp.right != null) {
                    queue.offer(temp.right);
                }
            }

            list.add(new ArrayList<>(one));
        }

        return list;
    }

    //层序遍历2 返回其节点值自底向上的层序遍历。 上一个方法的反转。。。
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> one = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode temp = null;

        if (root == null) {
            return list;
        }

        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            one.clear();

            for (int i = 0; i < size; i++) {
                temp = queue.poll();
                one.add(temp.val);
                if (temp.left != null) {
                    queue.offer(temp.left);
                }

                if (temp.right != null) {
                    queue.offer(temp.right);
                }
            }

            list.add(new ArrayList<>(one));
        }
        //反转list，也是可以使用linkedList，然后每次使用addFirst加到最前面
        Collections.reverse(list);
        return list;
    }


}
