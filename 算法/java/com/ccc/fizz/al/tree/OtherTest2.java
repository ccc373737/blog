package com.ccc.fizz.al.tree;

import java.util.*;

public class OtherTest2 {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    //545. 二叉树的边界
    //二叉树的边界是按顺序包括 左边界 、叶节点 和 右边界 而 不包括重复的节点 。
    //左边界 的定义是从根到 最左侧 节点的路径。右边界 的定义是从根到 最右侧 节点的路径。
    //思路为三次dfs，分别获得左边界，叶节点和右边界 合并
    List<Integer> boundLeftList;
    List<Integer> boundRightList;
    List<Integer> leavesList;
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        boundLeftList = new ArrayList<>();
        boundRightList = new ArrayList<>();
        leavesList = new ArrayList<>();

        List<Integer> targetList = new ArrayList<>();
        if (root == null) {
            return targetList;
        }

        targetList.add(root.val);
        if (root.left == null && root.right == null) {
            return targetList;
        }

        boundLeftDfs(root.left);
        leavesDfs(root);
        boundRightDfs(root.right);

        targetList.addAll(boundLeftList);
        targetList.addAll(leavesList);
        targetList.addAll(boundRightList);
        return targetList;
    }

    public void boundLeftDfs(TreeNode root) {
        if (root == null) {
            return;
        }
        //非叶子节点就加入且属于左边界加入，左边的处理方式时前序
        if (root.left != null || root.right != null) {
            boundLeftList.add(root.val);
        }
        //注意题目边界的定义为到最左侧节点的路径，即如果存在左节点，就一路向左递归
        //只有当左节点不存在，而右节点存在时，才转向右，所以这里条件需要return，因为存在左节点，就不走右路径
        if (root.left != null) {
            boundLeftDfs(root.left);
            return;
        }

        if (root.right != null) {
            boundLeftDfs(root.right);
            return;
        }
    }

    //右边处理方式后序
    public void boundRightDfs(TreeNode root) {
        if (root == null) {
            return;
        }

        if (root.right != null) {
            boundRightDfs(root.right);
            if (root.left != null || root.right != null) {
                boundRightList.add(root.val);
            }
            return;
        }

        if (root.left != null) {
            boundRightDfs(root.left);
            if (root.left != null || root.right != null) {
                boundRightList.add(root.val);
            }
            return;
        }
    }

    public void leavesDfs(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            leavesList.add(root.val);
        }

        leavesDfs(root.left);
        leavesDfs(root.right);
    }

    //1530. 好叶子节点对的数量
    /*给你二叉树的根节点 root 和一个整数 distance 。

    如果二叉树中两个 叶 节点之间的 最短路径长度 小于或者等于 distance ，那它们就可以构成一组 好叶子节点对 。

    返回树中 好叶子节点对的数量 。*/
    int pairsCount;
    public int countPairs(TreeNode root, int distance) {
        pairsCount = 0;
        getLeavesCountListDfs(root, distance);
        return pairsCount;
    }

    public List<Integer> getLeavesCountListDfs(TreeNode root, int distance) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        if (root.left == null && root.right == null) {
            list.add(1);
            return list;
        }

        List<Integer> leftList = getLeavesCountListDfs(root.left, distance);
        List<Integer> rightList= getLeavesCountListDfs(root.right, distance);

        for (Integer l1 : leftList) {
            for (Integer r1 : rightList) {
                if (l1 + r1 <= distance) {
                    pairsCount++;
                }
            }
        }

        for (Integer l1 : leftList) {
            if (l1 + 1 > distance) {//剪枝处理
                continue;
            }
            list.add(l1 + 1);
        }

        for (Integer r1 : rightList) {
            if (r1 + 1 > distance) {//剪枝处理
                continue;
            }
            list.add(r1 + 1);
        }
        return list;
    }

    //面试题 17.12. BiNode
    /*二叉树数据结构TreeNode可用来表示单向链表（其中left置空，right为下一个链表节点）。实现一个方法，把二叉搜索树转换为单向链表，要求依然符合二叉搜索树的性质，转换操作应是原址的，也就是在原始的二叉搜索树上直接修改。

    返回转换后的单向链表的头节点。*/
    TreeNode biHead;
    TreeNode biPre;
    public TreeNode convertBiNode(TreeNode root) {
        biHead = null;
        biPre = null;
        convertDfs(root);
        if (biPre != null) {
            biPre.left = null;
        }
        return biHead;
    }

    public void convertDfs(TreeNode root) {
        if (root == null) {
            return;
        }

        convertDfs(root.left);

        if (biPre == null) {
            biHead = root;
        } else {
            biPre.left = null;
            biPre.right = root;
        }

        biPre = root;

        convertDfs(root.right);
    }

    //1367. 二叉树中的列表
    /*给你一棵以 root 为根的二叉树和一个 head 为第一个节点的链表。

    如果在二叉树中，存在一条一直向下的路径，且每个点的数值恰好一一对应以 head 为首的链表中每个节点的值，那么请你返回 True ，否则返回 False 。

    一直向下的路径的意思是：从树中某个节点开始，一直连续向下的路径。*/
    public boolean isSubPath(ListNode head, TreeNode root) {
        return root != null && (isSubDfs(head, root) || isSubPath(head, root.left) || isSubPath(head, root.right));
    }

    public boolean isSubDfs(ListNode head, TreeNode root) {
        if (head == null && root == null) {
            return true;
        }

        if (root == null) {
            return false;
        }

        if (head == null) {
            return true;
        }

        return head.val == root.val && (isSubDfs(head.next, root.left) || isSubDfs(head.next, root.right));
    }

    //面试题 04.08. 首个共同祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        if (root == p || root == q) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p ,q);

        if (left == null) {
            return right;
        } else if (right == null) {
            return left;
        } else {
            return root;
        }
    }

    //987. 二叉树的垂序遍历
    /*给定二叉树，按垂序遍历返回其结点值。

    对位于 (X, Y) 的每个结点而言，其左右子结点分别位于 (X-1, Y-1) 和 (X+1, Y-1)。

    把一条垂线从 X = -infinity 移动到 X = +infinity ，每当该垂线与结点接触时，我们按从上到下的顺序报告结点的值（ Y 坐标递减）。

    如果两个结点位置相同，则首先报告的结点值较小。

    按 X 坐标顺序返回非空报告的列表。每个报告都有一个结点值列表。*/
    Map<Integer, List<int[]>> verMap;
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        verMap = new TreeMap<>((x, y) -> x -y);
        verDfs(root, 0, 0);
        List<List<Integer>> result = new ArrayList<>();

        for (Map.Entry<Integer, List<int[]>> entry : verMap.entrySet()) {
            List<Integer> temp = new ArrayList<>();

            List<int[]> entryList = entry.getValue();
            Collections.sort(entryList, (x, y) -> {
                if (x[0] != y[0]) {
                    return x[0] - y[0];
                } else {
                    return x[1] - y[1];
                }
            });

            for (int[] ints : entryList) {
                temp.add(ints[1]);
            }

            result.add(temp);
        }
        return result;
    }

    public void verDfs(TreeNode root, int x, int y) {
        if (root == null) {
            return;
        }
        verDfs(root.left, x - 1, y + 1);

        if (!verMap.containsKey(x)) {
            verMap.put(x, new ArrayList<>());
        }

        verMap.get(x).add(new int[]{y, root.val});

        verDfs(root.right, x + 1, y + 1);
    }

    public List<Integer> pathInZigZagTree(int label) {
        List<Integer> result = new ArrayList<>();
        result.add(label);

        while (label > 1) {
            int level = (int) (Math.log(label) / Math.log(2));//计算层数
            int levelStart = (int) Math.pow(2, level);//当前层起始位置

            int remain = (label - levelStart) / 2;//计算上一层中的与start的差值
            label = levelStart - 1 - remain;//得到上一层的label
            result.add(label);
        }

        Collections.reverse(result); //翻转数组
        return result;
    }
}


