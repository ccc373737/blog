package com.ccc.fizz.al;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据。
 *
 * 请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。
 *
 * **/
public class Al22 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(5);

        root.left = node1;
        root.right = node4;

        node1.left = node2;
        node1.right = node3;
        String a = serialize(root);
        System.out.println("1,2,3,#,#,4,#,#,5,#,#");
        deserialize(a);
    }

    //BFS
    public static String serialize1(TreeNode root) {

        if (root == null) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();

            if (temp == null) {
                sb.append(",#");
            } else {
                sb.append("," + temp.val);
                queue.offer(temp.left);
                queue.offer(temp.right);
            }
        }
        //去掉第一个逗号
        return sb.substring(1, sb.length()).toString();
    }

    // Decodes your encoded data to tree.
    public static TreeNode deserialize1(String data) {
        if (data.isEmpty() || "".equals(data)) {
            return null;
        }

        String[] array = data.split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        int index = 1;
        //生成根节点
        TreeNode root = new TreeNode(Integer.valueOf(array[0]));
        queue.offer(root);

        while (!queue.isEmpty() && index <= array.length - 1) {
            TreeNode temp = queue.poll();

            //指针+1 获取左节点
            String left = array[index++];
            //左节点不为空，设置左节点，并放入队列
            if (!"#".equals(left)) {
                temp.left = new TreeNode(Integer.valueOf(left));
                queue.offer(temp.left);
            }

            String right = array[index++];
            if (!"#".equals(right)) {
                temp.right = new TreeNode(Integer.valueOf(right));
                queue.offer(temp.right);
            }
        }
        return root;
    }

    //DFS
    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        doDFS(root, sb);
        return sb.substring(1, sb.length()).toString();
    }

    public static void doDFS(TreeNode root, StringBuffer sb) {
        if (root == null) {
            sb.append(",#");
        } else {
            sb.append("," + root.val);
            doDFS(root.left, sb);
            doDFS(root.right, sb);
        }
    }

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        if (data.isEmpty() || "".equals(data)) {
            return null;
        }

        Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));

        return undoDFS(queue);
    }

    public static TreeNode undoDFS(Queue<String> queue) {
        String temp = queue.poll();

        if ("#".equals(temp)) {
            return null;
        } else {
            TreeNode node = new TreeNode(Integer.valueOf(temp));
            node.left = undoDFS(queue);
            node.right = undoDFS(queue);
            return node;
        }
    }
}
