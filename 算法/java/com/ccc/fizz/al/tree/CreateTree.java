package com.ccc.fizz.al.tree;

import java.util.Stack;

public class CreateTree {

    //617. 合并二叉树
    /*给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。

    你需要将他们合并为一个新的二叉树。合并的规则是如果两个节点重叠，那么将他们的值相加作为节点合并后的新值，否则不为 NULL 的节点将直接作为新二叉树的节点。*/
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return null;
        } else if (t1 == null && t2 != null) {
            return t2;
        } else if (t1 != null && t2 == null) {
            return t1;
        } else {
            t1.val += t2.val;
            t1.left = mergeTrees(t1.left, t2.left);
            t1.right = mergeTrees(t1.right, t2.right);
            return t1;
        }
    }

    //450. 删除二叉搜索树中的节点
    //给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if (root.val > key) {
            root.left = deleteNode(root.left, key);
            return root;
        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);
            return root;
        } else {
            TreeNode newRoot = root.left;
            if (newRoot == null) {
                return root.right;
            }

            TreeNode leftTemp = root.left;
            TreeNode rightTemp = root.right;

            while (leftTemp.right != null) {
                leftTemp = leftTemp.right;
            }

            leftTemp.right = rightTemp;
            return newRoot;
        }
    }

    //623. 在二叉树中增加一行
    /*给定一个二叉树，根节点为第1层，深度为 1。在其第 d 层追加一行值为 v 的节点。

    添加规则：给定一个深度值 d （正整数），针对深度为 d-1 层的每一非空节点 N，为 N 创建两个值为 v 的左子树和右子树。

    将 N 原先的左子树，连接为新节点 v 的左子树；将 N 原先的右子树，连接为新节点 v 的右子树。

    如果 d 的值为 1，深度 d - 1 不存在，则创建一个新的根节点 v，原先的整棵树将作为 v 的左子树*/
    public TreeNode addOneRow(TreeNode root, int v, int d) {
        //d为1时特殊情况处理
        if (d == 1) {
            TreeNode newRoot = new TreeNode(v);
            newRoot.left = root;
            return newRoot;
        }

        return addOneRowDfs(root, v, d);
    }

    //d为2时，实际上就是当前节点左右节点为新节点，然后和原节点拼接
    //大于2时，向下递归
    public TreeNode addOneRowDfs(TreeNode root, int v, int d) {
        if (root == null) {
            return null;
        }

        if (d == 2) {
            TreeNode leftTemp = root.left;
            TreeNode rightTemp = root.right;

            root.left = new TreeNode(v);
            root.right = new TreeNode(v);

            root.left.left = leftTemp;
            root.right.right = rightTemp;
            return root;
        }

        root.left = addOneRowDfs(root.left, v, d - 1);
        root.right = addOneRowDfs(root.right, v, d - 1);
        return root;
    }

    //776. 拆分二叉搜索树
    /*给你一棵二叉搜索树（BST）、它的根结点 root 以及目标值 V。

    请将该树按要求拆分为两个子树：其中一个子树结点的值都必须小于等于给定的目标值 V；另一个子树结点的值都必须大于目标值 V；树中并非一定要存在值为 V 的结点。

    除此之外，树中大部分结构都需要保留，也就是说原始树中父节点 P 的任意子节点 C，假如拆分后它们仍在同一个子树中，那么结点 P 应仍为 C 的父结点。

    你需要返回拆分后两个子树的根结点 TreeNode，顺序随意。*/
    //设定数组第一个元素为左侧元素，第二个元素为右侧元素
    public TreeNode[] splitBST(TreeNode root, int V) {
        if (root == null) {
            return new TreeNode[]{null, null};
        }else if (root.val > V) {//目标在左侧
            TreeNode[] leftTemp = splitBST(root.left, V);
            root.left = leftTemp[1];//root左接较大的树，右边为一个独立的树
            return new TreeNode[]{leftTemp[0], root};
        } else  {
            TreeNode[] rightTemp = splitBST(root.right, V);
            root.right = rightTemp[0];//root右接较小的树，左边那为一个独立的树
            return new TreeNode[]{root, rightTemp[1]};
        }
    }

    //701. 二叉搜索树中的插入操作
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (root.val > val) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }

        return root;
    }

    //105. 从前序与中序遍历序列构造二叉树 //可视[10,2,14,1,5,11,17,null,null,4,7,null,null,16,18]
    //10 2 1 5 4 7 14 11 17 16 18
    //1 2 4 5 7 10 11 14 16 17 18
    //1 4 7 5 2 11 16 18 17 14 10
    public TreeNode buildTree1(int[] preorder, int[] inorder) {
        //坚持左闭右闭的原则
        return buildTree1Dfs(preorder, 0, preorder.length - 1, inorder, 0 ,inorder.length - 1);
    }

    public TreeNode buildTree1Dfs(int [] pre, int preStart, int preEnd, int[] ino, int inoStart, int inoEnd) {
        if (preStart > preEnd) {
            return null;
        }

        if (preStart == preEnd) {
            return new TreeNode(pre[preStart]);
        }

        TreeNode root = new TreeNode(pre[preStart]);

        int split = preStart;
        //找在中序中的分割点
        for (int i = inoStart; i <= inoEnd; i++) {
            if (ino[i] == pre[preStart]) {
                split = i;
                break;
            }
        }
        //计算子元素的数组大小
        int size = split - inoStart;
        //递归解决
        root.left = buildTree1Dfs(pre, preStart + 1, preStart + size, ino, inoStart, split - 1);
        root.right = buildTree1Dfs(pre, preStart + size + 1, preEnd, ino, split + 1, inoEnd);
        return root;
    }

    //106. 从中序与后序遍历序列构造二叉树
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        return buildTree2Dfs(postorder, 0, postorder.length - 1, inorder, 0 , inorder.length - 1);
    }

    public TreeNode buildTree2Dfs(int [] post, int postStart, int postEnd, int[] ino, int inoStart, int inoEnd) {
        if (postStart > postEnd) {
            return null;
        }

        if (postStart == postEnd) {
            return new TreeNode(post[postEnd]);
        }

        TreeNode root = new TreeNode(post[postEnd]);

        int split = postStart;
        //找在中序中的分割点 用后序的最后一个去匹配
        for (int i = inoStart; i <= inoEnd; i++) {
            if (ino[i] == post[postEnd]) {
                split = i;
                break;
            }
        }
        //计算子元素的数组大小
        int size = split - inoStart;
        //递归解决
        root.left = buildTree2Dfs(post, postStart, postStart + size - 1, ino, inoStart, split - 1);
        root.right = buildTree2Dfs(post, postStart + size , postEnd - 1, ino, split + 1, inoEnd);
        return root;
    }

    //889. 根据前序和后序遍历构造二叉树 返回正确的一个
    //[10,2,1,5,4,7,14,11,17,16,18]
    //[1,4,7,5,2,11,16,18,17,14,10]
    //在前序和中序中，可以直接用start节点到中序中去找到相应的中间位，也就是根节点，然后区分左右遍历
    //而在前序和后序中，直接用start必然找到后序的中最后一个无法区分左右，所有需要start+1节点，这个节点是左子树的根节点，在后序中处于一个左侧遍历的最后一位，以此来区分左右区间
    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        return prePostDfs(pre, 0, pre.length - 1, post, 0 ,post.length - 1);
    }

    public TreeNode prePostDfs(int[] pre, int preStart, int preEnd, int[] post, int postStart, int postEnd) {
        if (preStart > preEnd) {
            return null;
        }

        if (preStart == preEnd) {
            return new TreeNode(pre[preStart]);
        }

        TreeNode root = new TreeNode(pre[preStart]);

        int split = preStart;
        for (int i = postStart; i <= postEnd; i++) {
            if (post[i] == pre[preStart + 1]) {
                split = i;
                break;
            }
        }
        //计算子元素的数组大小
        int size = split - postStart;
        //递归解决
        root.left = prePostDfs(pre, preStart + 1, preStart + 1 + size, post, postStart, split);
        root.right = prePostDfs(pre, preStart + 1 + size + 1, preEnd, post, split + 1, postEnd);
        return root;
    }

    //1008. 前序遍历构造二叉搜索树
    public TreeNode bstFromPreorder(int[] preorder) {
        return bstPreDfs(preorder, 0, preorder.length - 1);
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

    //1028. 从先序遍历还原二叉树
    //10-2--1--5---4---7-14--11--17---16---18
    public TreeNode recoverFromPreorder(String pre) {
        Stack<TreeNode> stack = new Stack<>();
        //当前字符串索引位置
        int index = 0;
        while (index < pre.length()) {
            //当前深度
            int depth = 0;
            //根据-寻找深度
            while (index < pre.length() && pre.charAt(index) == '-') {
                depth++;
                index++;
            }
            //非-字符寻找值
            int value = 0;
            while (index < pre.length() && pre.charAt(index) != '-') {
                value = value * 10 + ((int) pre.charAt(index) - (int) '0');
                index++;
            }

            TreeNode current = new TreeNode(value);
            if (stack.isEmpty()) {//栈为空，为根节点
                stack.push(current);
                continue;
            }

            while (stack.size() > depth) {//当深度小于栈深度时，需要弹出栈
                stack.pop();
            }

            if (stack.peek().left == null) {//先压入左侧
                stack.peek().left = current;
            } else {
                stack.peek().right = current;
            }
            stack.push(current);
        }
        return stack.get(0);
    }

}
