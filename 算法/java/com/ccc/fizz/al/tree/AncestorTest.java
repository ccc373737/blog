package com.ccc.fizz.al.tree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AncestorTest {

    //235. 二叉搜索树的最近公共祖先
    /*所有节点的值都是唯一的。
    p、q 为不同节点且均存在于给定的二叉搜索树中。*/
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        //自顶向下遍历，找到的第一个node在p和q之中的即为目标
        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        } else if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor(root.left, p, q);
        } else {
            return root;
        }
    }

    //236. 二叉树的最近公共祖先
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        //注意这个地方可以前序遍历，因为找到了某个节点，而另一个节点位于这个节点之下
        //那么当前节点就是最近祖先，可以直接返回这个节点
        if (root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor1(root.left, p, q);
        TreeNode right = lowestCommonAncestor1(root.right, p, q);

        //找到节点从底上浮，不满足的节点必然返回null，最后得到的节点就是目标
        if (left == null && right == null) {
            return null;
        } else if (left != null && right == null) {
            return left;
        } else if (left == null && right != null) {
            return right;
        } else {
            return root;
        }
    }

    //1644. 二叉树的最近公共祖先 II
    //可能公共祖先不存在，那么返回null
    int count;
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        count = 2;
        TreeNode target = ancestor2Dfs(root, p, q);
        System.out.println(count);
        return count == 0 ? target : null;
    }

    public TreeNode ancestor2Dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        TreeNode left = ancestor2Dfs(root.left, p, q);
        TreeNode right = ancestor2Dfs(root.right, p, q);

        //与上一道题不同，这里只能使用后序遍历，因为存在不在树中的情况，必须遍历所有节点统计数量
        //前序遍历相当于做了剪枝
        if (root.val == p.val || root.val == q.val) {
            count--;
            return root;
        }

        //找到节点从底上浮，不满足的节点必然返回null，最后得到的节点就是目标
        if (left == null && right == null) {
            return null;
        } else if (left != null && right == null) {
            return left;
        } else if (left == null && right != null) {
            return right;
        } else {
            return root;
        }
    }

    //1650. 二叉树的最近公共祖先 III
    //给定一棵二叉树中的两个节点 p 和 q，返回它们的最近公共祖先节点（LCA）。
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }

    //用一个set保存第一个节点的所有父节点
    //遍历第二个节点的父节点，如果在set中，就找到target
    public Node lowestCommonAncestor3(Node p, Node q) {
        Set<Node> set = new HashSet<>();
        while (p != null) {
            set.add(p);
            p = p.parent;
        }

        while (q != null) {
            if (set.contains(q)) {
                return q;
            }
            q = q.parent;
        }
        return null;
    }

    //1676. 二叉树的最近公共祖先 IV
    //给定一棵二叉树的根节点 root 和 TreeNode 类对象的数组（列表） nodes，返回 nodes 中所有节点的最近公共祖先（LCA）。数组（列表）中所有节点都存在于该二叉树中，且二叉树中所有节点的值都是互不相同的。
    //用一个set保存所有需要节点，每找到一个就删除一个，最后如果set为空表示找到target
    //如果题目可以保证节点一定在树中，那么前序剪枝直接返回
    Set<TreeNode> set;
    public TreeNode lowestCommonAncestor4(TreeNode root, TreeNode[] nodes) {
        set = new HashSet<>(Arrays.asList(nodes));
        TreeNode target = ancestor4Dfs(root);
        return set.isEmpty() ? target : null;
    }

    public TreeNode ancestor4Dfs(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode left = ancestor4Dfs(root.left);
        TreeNode right = ancestor4Dfs(root.right);

        if (set.contains(root)) {
            set.remove(root);
            return root;
        }

        if (left == null && right == null) {
            return null;
        } else if (left != null && right == null) {
            return left;
        } else if (left == null && right != null) {
            return right;
        } else {
            return root;
        }
    }

    //1026. 节点与其祖先之间的最大差值
    //前序遍历，自顶向下，不断传入之前节点的最大最小值
    int maxDiff;
    public int maxAncestorDiff(TreeNode root) {
        diffDfs(root, root.val, root.val);
        return maxDiff;
    }

    public void diffDfs(TreeNode root, int min, int max) {
        if (root == null) {
            return;
        }

        maxDiff = Math.max(maxDiff, Math.max(Math.abs(root.val - min), Math.abs(max - root.val)));

        min = Math.min(root.val, min);
        max = Math.max(root.val, max);

        diffDfs(root.left, min, max);
        diffDfs(root.right, min, max);
    }

    //1123. 最深叶节点的最近公共祖先
    //计算深度，向深度大的一边遍历，直到到底部或者左右深度相同
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        int left = getMaxDepth(root.left);
        int right = getMaxDepth(root.right);

        if (left < right) {
            return lcaDeepestLeaves(root.right);
        } else if (left > right) {
            return lcaDeepestLeaves(root.left);
        } else {
            return root;
        }
    }

    public int getMaxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(getMaxDepth(root.left), getMaxDepth(root.right)) + 1;
    }


}
