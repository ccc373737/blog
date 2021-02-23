package com.ccc.fizz.al.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepthTest {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    //111. 二叉树的最小深度
    //一般深度的问题都需要使用前序
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (root.left == null) {
            return 1 + minDepth(root.right);
        }

        if (root.right == null) {
            return 1 + minDepth(root.left);
        }

        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }

    //二叉树的节点个数
    public int countNodes1(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return 1 + countNodes1(root.left) + countNodes1(root.right);
    }

    //222. 完全二叉树的节点个数
    //对于完全二叉树，子节点有两种情况
    //1.左右子树高度相同，那么也为完全二叉树，有公式2 ^ n - 1
    //2.左右子树高度不同，那么分别递归左右子树
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = 0;
        int rightDepth = 0;
        TreeNode left = root.left;
        TreeNode right = root.right;

        while (left != null) {
            leftDepth++;
            left = left.left;
        }

        while(right != null) {
            rightDepth++;
            right = right.right;
        }

        if (leftDepth == rightDepth) {
            //移位替代2^n，初始值为n=0，实际这里的初始值为2
            return (2 << leftDepth) - 1;
        }

        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    //129. 求根到叶子节点数字之和
    /*给定一个二叉树，它的每个结点都存放一个 0-9 的数字，每条从根到叶子节点的路径都代表一个数字。

    例如，从根到叶子节点路径 1->2->3 代表数字 123。

    计算从根到叶子节点生成的所有数字之和。

    说明: 叶子节点是指没有子节点的节点。*/
    //思路：前序+回溯
    int sum;

    public int sumNumbers(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return sumStr(root, String.valueOf(root.val));
    }

    public int sumStr(TreeNode root, String str) {
        if (root.left == null && root.right == null) {
            return Integer.valueOf(str);
        }

        if (root.left == null) {
            return sumStr(root.right, str + root.right.val);
        }

        if (root.right == null) {
            return sumStr(root.left, str + root.left.val);
        }

        return sumStr(root.left, str + root.left.val) + sumStr(root.right, str + root.right.val);
    }

    //124. 二叉树中的最大路径和
    //给定一个非空二叉树，返回其最大路径和。
    //
    //本题中，路径被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。该路径至少包含一个节点，且不一定经过根节点。
    //对于一个节点，为null，则返回0
    //  a
    // / \
    //b   c
    //a + b + c形成一个闭环的路径，这个路径可以直接和最大路径比较
    //否则a作为一个子节点，其最大值为max(a+b,a+c)，返回上一层使用
    int max;
    public int maxPathSum(TreeNode root) {
        max = Integer.MIN_VALUE;
        if (root == null) {
            return 0;
        }

        dfs(root);
        return max;
    }

    public int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        //如果是负数，该节点舍去
        int left = Math.max(0, dfs(root.left));
        int right = Math.max(0, dfs(root.right));

        max = Math.max(max, left + right + root.val);

        return Math.max(0, Math.max(left, right) + root.val);
    }

    //671. 二叉树中第二小的节点
    /*给定一个非空特殊的二叉树，每个节点都是正数，并且每个节点的子节点数量只能为 2 或 0。如果一个节点有两个子节点的话，那么该节点的值等于两个子节点中较小的一个。

    更正式地说，root.val = min(root.left.val, root.right.val) 总成立。

    给出这样的一个二叉树，你需要输出所有节点中的第二小的值。如果第二小的值不存在的话，输出 -1 。*/
    //建议直接遍历加入set，取出第二小，时间复杂度：O(N)O(N)
    public int findSecondMinimumValue(TreeNode root) {
        return findDfs(root, root.val);
    }

    public int findDfs(TreeNode root, int val) {
        if (root == null) {
            return -1;
        }

        //只找比根节点大的值，根节点为最小值
        if (root.val > val) {
            return root.val;
        }
        //下面是与根节点相同的情况
        int left = findDfs(root.left, val);
        int right = findDfs(root.right, val);

        //存在子节点的子节点
        if (left >= 0 && right >= 0) {
            return Math.min(left, right);
        }

        //只有子节点
        return Math.max(left, right);
    }


    //366. 寻找二叉树的叶子节点
    /*给你一棵二叉树，请按以下要求的顺序收集它的全部节点：

    依次从左到右，每次收集并删除所有的叶子节点 重复如上过程直到整棵树为空*/
    //反向定义深度，即叶子节点为0，叶子节点上一层为1，后序遍历，相同深度的放在一起
    List<List<Integer>> leavesList;
    public List<List<Integer>> findLeaves(TreeNode root) {
        leavesList = new ArrayList<>();
        findLeavesDfs(root);
        return leavesList;
    }

    public int findLeavesDfs(TreeNode root) {
        if (root == null) {
            return -1;
        }

        int left = findLeavesDfs(root.left);
        int right = findLeavesDfs(root.right);

        int max = 1 + Math.max(left, right);
        if (max > leavesList.size() - 1) {
            List<Integer> sonList = new ArrayList<>();
            sonList.add(root.val);
            leavesList.add(sonList);
        } else {
            leavesList.get(max).add(root.val);
        }

        return max;
    }

    //865. 具有所有最深节点的最小子树
   /* 给定一个根为 root 的二叉树，每个节点的深度是 该节点到根的最短距离 。

    如果一个节点在 整个树 的任意节点之间具有最大的深度，则该节点是 最深的 。

    一个节点的 子树 是该节点加上它的所有后代的集合。

    返回能满足 以该节点为根的子树中包含所有最深的节点 这一条件的具有最大深度的节点。*/
    //寻找左右节点最大深度
    //如果left==right，那么当前节点即为最大节点
    //如果left<right，那么最深度节点在右边，向右递归
    //如果left>right，向左递归
    //由于要返回节点以及重复计算，需要map放入节点
    Map<TreeNode, Integer> map = new HashMap<>();

    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        if (root == null) {
            return null;
        }

        int left = deepestDfs(root.left);
        int right = deepestDfs(root.right);

        if (left == right) {
            return root;
        } else if (left < right) {
            return subtreeWithAllDeepest(root.right);
        } else {
            return subtreeWithAllDeepest(root.left);
        }
    }

    public int deepestDfs(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (map.containsKey(root)) {
            return map.get(root);
        }

        int val = 1 + Math.max(deepestDfs(root.left), deepestDfs(root.right));
        map.put(root, val);
        return val;
    }

}
