package com.ccc.fizz.al.tree;

import java.util.*;

public class PathTest {
    //112. 路径总和
    //给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
    public boolean hasPathSum(TreeNode root, int sum) {
        //不存在子节点
        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null) {
            return sum == root.val;
        }

        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }

    //113. 路径总和 II
    //给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
    List<List<Integer>> list;
    public List<List<Integer>> pathSum2(TreeNode root, int sum) {
        list = new ArrayList<>();
        LinkedList<Integer> temp = new LinkedList<>();

        if (root == null) {
            return list;
        }
        pathSum(root, sum, temp);
        return list;
    }

    public void pathSum(TreeNode root, int sum, LinkedList<Integer> tempList) {
        if (root == null) {
            return;
        }

        tempList.add(root.val);
        if (root.left == null && root.right == null && sum == root.val) {
            list.add(new ArrayList<>(tempList));
            tempList.removeLast();
            return;
        }

        pathSum(root.left, sum - root.val, tempList);
        pathSum(root.right, sum - root.val, tempList);
        tempList.removeLast();
    }

    //257. 二叉树的所有路径
    //给定一个二叉树，返回所有从根节点到叶子节点的路径。
    List<String> paths;
    public List<String> binaryTreePaths(TreeNode root) {
        paths = new ArrayList<>();
        if (root == null) {
            return paths;
        }

        pathDfs(root, "");
        return paths;
    }

    public void pathDfs(TreeNode root, String path) {
        path += root.val;

        if (root.left == null && root.right == null) {
            paths.add(path);
            return;
        }

        if (root.left != null) {
            pathDfs(root.left, path + "->");
        }

        if (root.right != null) {
            pathDfs(root.right, path + "->");
        }
    }

    //437. 路径总和 III
    /*给定一个二叉树，它的每个结点都存放着一个整数值。

    找出路径和等于给定数值的路径总数。

    路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。

    二叉树不超过1000个节点，且节点数值范围是 [-1000000,1000000] 的整数。*/
    public int pathSum3(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }
        return pathSumDfs3(root, sum) + pathSum3(root.left, sum) + pathSum3(root.right, sum);
    }

    public int pathSumDfs3(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        sum -= root.val;
        return (sum == 0 ? 1 : 0) + pathSumDfs3(root.left, sum) + pathSumDfs3(root.right, sum);
    }

    //重点求差值
    //前缀和思想，如target = 8，当前节点总和为15，那么以当前节点为终点符合条件的路径为 当前节点之前的节点和为7的路径个数
    //这里需要一个map保存之前节点和的路径个数
    public int pathSum(TreeNode root, int sum) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0,1);
        return pathSumDfs(root, map, 0, sum);
    }

    public int pathSumDfs(TreeNode root, Map<Integer, Integer> map, int now, int sum) {
        if (root == null) {
            return 0;
        }

        now += root.val;
        int count = map.getOrDefault(now - sum, 0);


        map.put(now, map.getOrDefault(now, 0) + 1);

        int left = pathSumDfs(root.left, map, now, sum);
        int right = pathSumDfs(root.right, map, now, sum);
        //回溯
        map.put(now, map.get(now) - 1);

        return count + left + right;
    }

    //543. 二叉树的直径
    //给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
    //后序遍历，自底向上求直径
    int diameter;
    public int diameterOfBinaryTree(TreeNode root) {
        diameter = 0;
        diameterDfs(root);
        return diameter;
    }

    public int diameterDfs(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = diameterDfs(root.left);
        int right = diameterDfs(root.right);

        diameter = Math.max(diameter, left + right);

        return 1 + Math.max(left, right);
    }


    Map<Integer, Integer> pathValMap;
    int resultP;
    public int pathSum6(TreeNode root, int target) {
        pathValMap = new HashMap<>();
        pathValMap.put(0, 1);
        resultP = 0;
        prePathDfs(root, 0, target);
        return resultP;
    }

    public void prePathDfs(TreeNode root, int sum, int target) {
        if (root == null) {
            return;
        }

        sum += root.val;
        resultP += pathValMap.getOrDefault(sum - target, 0);

        pathValMap.put(sum, pathValMap.getOrDefault(sum, 0) + 1);
        prePathDfs(root.left, sum, target);
        prePathDfs(root.right , sum, target);
        pathValMap.put(sum, pathValMap.get(sum) - 1);
    }
}
