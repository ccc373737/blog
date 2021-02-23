package com.ccc.fizz.al.tree;

import java.util.*;

public class SeriaTest {
    //297. 二叉树的序列化与反序列化
    public String serialize(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        StringBuffer sb = new StringBuffer();
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            if (poll == null) {
                sb.append("#,");
            } else {
                sb.append(poll.val + ",");;
                queue.offer(poll.left);
                queue.offer(poll.right);
            }
        }
        return sb.substring(0, sb.length() - 1).toString();
    }

    //1,2,3,#,#,4,5,#,#,#,#
    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty() || "#".equals(data)) {
            return null;
        }

        String[] arr = data.split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.valueOf(arr[0]));//压入根节点
        queue.offer(root);
        int index = 0;

        while (index < arr.length && !queue.isEmpty()) {
            TreeNode poll = queue.poll();

            index++;
            if (index < arr.length) {//创建左节点
                if ("#".equals(arr[index])) {
                    poll.left = null;
                } else {
                    poll.left = new TreeNode(Integer.valueOf(arr[index]));
                    queue.offer(poll.left);
                }
            }


            index++;
            if (index < arr.length) {//创建右节点
                if ("#".equals(arr[index])) {
                    poll.right = null;
                } else {
                    poll.right = new TreeNode(Integer.valueOf(arr[index]));
                    queue.offer(poll.right);
                }
            }
        }
        return root;
    }
    //449. 序列化和反序列化二叉搜索树
    //对于普通二叉树，需要前序和中序或中序和后序确定
    //而搜索二叉树，只需要前序遍历即可确定
    public String serialize1(TreeNode root) {
        if (root == null) {
            return null;
        }

        Stack<TreeNode> stack = new Stack<>();
        StringBuffer sb = new StringBuffer();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode pop = stack.pop();
            sb.append(pop.val + ",");

            if (pop.right != null) {
                stack.push(pop.right);
            }

            if (pop.left != null) {
                stack.push(pop.left);
            }
        }

        return sb.substring(0, sb.length() - 1).toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize1(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }

        String[] split = data.split(",");
        int[] vals = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            vals[i] = Integer.valueOf(split[i]);
        }

        return bstPreDfs(vals, 0, vals.length - 1);
    }

    public TreeNode bstPreDfs(int[] pre, int preStart, int preEnd) {
        if (preStart > preEnd) {
            return null;
        }

        if (preStart == preEnd) {
            return new TreeNode(pre[preStart]);
        }

        TreeNode root = new TreeNode(pre[preStart]);

        int split = preEnd;
        //这个标志位为ture表示从循环中break，即后续数组中有比当前值大的树，存在右子树
        //为false表示，后面数字全部小于当前，只存在左子树，后面全部元素进入左子树递归逻辑
        boolean flag = false;
        for (int i = preStart + 1; i <= preEnd; i++) {
            if (pre[i] > pre[preStart]) {
                split = i;
                flag = true;
                break;
            }
        }

        int size = flag ? split - preStart - 1 : preEnd - preStart;
        root.left = bstPreDfs(pre, preStart + 1, preStart + size);
        root.right = bstPreDfs(pre, preStart + size + 1, preEnd);
        return root;
    }

    //428. 序列化和反序列化 N 叉树
    static class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    //     1
    //    /|\
    //   3 2 4
    //  /\
    // 5  6
    //序列化时形成1[3[5[]6[]]2[]4[]]
    public String serialize2(Node root) {
        Stack<Node> stack = new Stack<>();
        StringBuffer sb = new StringBuffer();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node pop = stack.pop();
            sb.append(pop.val);

            sb.append("[");

            if (pop.children != null) {//空子节点判断
                for (Node child : pop.children) {//深度遍历
                    sb.append(serialize2(child));
                }
            }

            sb.append("]");
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public Node deserialize2(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }

        int index = 0;
        Stack<Node> stack = new Stack<>();

        int val = 0;
        Node root = null;
        while (index < data.length()) {
            if (data.charAt(index) == '[') {//遇到[时，把之前累加的val转成node，并压入栈
                Node temp = new Node(val, new ArrayList<>());
                val = 0;

                if (root == null) {
                    root = temp;
                }

                stack.push(temp);
            } else if (data.charAt(index) == ']') {//遇到]时，返回压入的节点，并作为上一个节点的子节点加入
                Node pop = stack.pop();
                if (!stack.isEmpty()) {
                    stack.peek().children.add(pop);
                }
            } else {//计算值
                val = val * 10 + ((int)data.charAt(index) - (int)'0');
            }
            index++;
        }
        return root;
    }

}