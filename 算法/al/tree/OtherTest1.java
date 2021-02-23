package com.ccc.fizz.al.tree;

import java.util.*;

public class OtherTest1 {
    //114. 二叉树展开为链表
    //给定一个二叉树，原地将它展开为一个单链表。
    /*           1
                / \
               2   5
              / \   \
             3   4   6

            1
             \
              2
               \
                3
                 \
                  4
                   \
                    5
                     \
                      6*/
    //展开规则为原来左子树接到右子树位置，右子树再接到现在的右子树上
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        flatten(root.left);
        flatten(root.right);

        if (root.left != null) {
            TreeNode rightTemp = root.right;
            root.right = root.left;//左子树接到右子树位置上
            root.left = null;//左子树置为空

            //指针移到当前右子树底部，接上原来右子树
            while (root.right != null) {
                root = root.right;
            }
            root.right = rightTemp;
        }
    }

    //100. 相同的树
    //给定两个二叉树，编写一个函数来检验它们是否相同。
    //
    //如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        return p !=null && q != null && p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    //110. 平衡二叉树
    //一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
    public boolean isBalanced(TreeNode root) {
        return getHeight(root) != -1;
    }

    public int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = getHeight(root.left);
        if (left == -1) {
            return -1;
        }

        int right = getHeight(root.right);
        if (right == -1) {
            return -1;
        }

        return Math.abs(left - right) > 1 ? -1 : Math.max(left, right) + 1;
    }

    //968. 监控二叉树
    /*给定一个二叉树，我们在树的节点上安装摄像头。

    节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。

    计算监控树的所有节点所需的最小摄像头数量。*/
    public int minCameraCover(TreeNode root) {
        int[] min = minCameraDfs(root);
        //对于根节点而言，不存在父节，在状态1和状态2中返回一个小值
        return Math.min(min[0], min[1]);
    }

    public int[] minCameraDfs(TreeNode root) {
        if (root == null) {
            //对于null节点的父节点来说，不存在当前节点有相机的情况，返回一个无效最大值，因为会出现2倍最大值溢出变为最小值的情况，返回最大值/2
            return new int[]{Integer.MAX_VALUE / 2, 0, 0};
        }

        int[] left = minCameraDfs(root.left);
        int[] right = minCameraDfs(root.right);

        int[] state = new int[3];
        //状态1 表示当前节点有相机：min(左子节点没有相机的情况+右子节点没有相机的情况，左子节点有相机的情况+右子节点被父节点覆盖的情况，左子节点被父节点覆盖的情况+右子节点有相机的情况)
        state[0] = 1 + mathMin(left[2] + right[2], left[0] + right[2], left[2] + right[0]);
        //状态2 表示当前节点被子节点覆盖：min(左子节点有相机的情况+右子节点右相机的情况，左子节点有相机的情况+右子节点被子节点覆盖的情况，左子节点被子节点覆盖的情况+右子节点有相机的情况)
        state[1] = mathMin(left[0] + right[0], left[0] + right[1], left[1] + right[0]);
        //状态3 表示当前节点被父节点覆盖的情况：min((左子节点有相机的情况+右子节点右相机的情况，左子节点有相机的情况+右子节点被子节点覆盖的情况，左子节点被子节点覆盖的情况+右子节点有相机的情况，左子节点被子节点覆盖的情况+右子节点被子节点覆盖的情况)
        state[2] = mathMin(left[0] + right[0], left[0] + right[1], left[1] + right[0], left[1] + right[1]);
        return state;
    }

    public int mathMin(int... values) {
        Integer min = null;
        for (int i = 0; i < values.length; i++) {
            if (min == null) {
                min = values[i];
                continue;
            }
            min = Math.min(min, values[i]);
        }
        return min;
    }

    //贪心实现 无法证明
    int minC = 0;
    public int minCameraCover1(TreeNode root) {
        int[] min = minCameraDfs(root);
        //对于根节点而言，不存在父节，在状态1和状态2中返回一个小值
        return Math.min(min[0], min[1]);
    }

    //0表示该节点无覆盖，1表示有相机，2表示有被覆盖
    public int minCameraDfs2(TreeNode root) {
        if (root == null) {
            return 2;
        }

        int left = minCameraDfs2(root.left);
        int right = minCameraDfs2(root.right);

        if (left == 2 && right == 2) {
            return 0;
        }
        //子节点无覆盖时，才给当前加上相机，最优策略
        if (left == 0 || right == 0) {
            minC++;
            return 1;
        }

        if (left == 1|| right == 1) {
            return 2;
        }
        return -1;
    }

    //834. 树中距离之和
    List<List<Integer>> graph;
    int[] sumCount;
    int[] nodeCount;
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        //构建图
        graph = new ArrayList<>();
        sumCount = new int[N];
        nodeCount = new int[N];

        for (int i = 0; i < N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            //起点
            int src = edges[i][0];
            //终点
            int des = edges[i][1];
            //无向图路径构建
            graph.get(src).add(des);
            graph.get(des).add(src);
        }

        //初始化node，每一个节点都只有自身1
        Arrays.fill(nodeCount, 1);
        postDfs(0 ,-1);
        preDfs(0, -1, N);
        return sumCount;
    }

    //递推公式为 当前节点距离和=所有子节点距离和之和+所有子节点数量
    //节点数量 = 所有子节点数量 + 1（本身）
    //nodecount[i] = sum(nodecount[child]) + 1
    //sumcount[i] = sum(sumcount[child] + nodecount[child])
    public void postDfs(int root, int parent) {
        //获得所有相连路径
        List<Integer> neighbors = graph.get(root);
        for (Integer neighbor : neighbors) {
            if (neighbor == parent) {//遇到父节点，跳到，这里处理子节点
                continue;
            }
            //后序遍历，自底向上处理所有子节点
            postDfs(neighbor, root);
            nodeCount[root] += nodeCount[neighbor];
            sumCount[root] += sumCount[neighbor] + nodeCount[neighbor];
        }
    }

    //第二步dfs，自顶向下
    //经过前面一部之后，sumCount已经放入所有子树的路径和，这一步求子树之外的路径和
    //对于A，B两个相邻节点，分为两个部分
    //A总路径和 = A的子树和 + B的子树和 + count(B)（即B中所有节点向A走一步）
    //ans(A) = sum(A) + sum(B) + count(B)
    //ans(B) = sum(A) + sum(B) + count(A)
    //count(A) + count(B) = N
    //ans(A) = ans(B) + count(B) - count(A) -> ans(child) = ans(root) + count(root) - count(child) -> ans(child) = ans(root) + N - 2count(child)
    //从直观上理解，对于child节点相当于，root中的child部分向child靠拢，那么每个child中节点都少走一步，共少走count(child)
    //而非child部分向child靠拢则需要多走一步，每个非child部分多走一步，共多走N-count(child)
    public void preDfs(int root, int parent, int N) {
        List<Integer> neighbors = graph.get(root);
        for (Integer neighbor : neighbors) {
            if (neighbor == parent) {//遇到父节点，跳到，这里处理子节点
                continue;
            }

            sumCount[neighbor] = sumCount[root] + N - 2 * nodeCount[neighbor];
            preDfs(neighbor, root, N);
        }
    }

    //654. 最大二叉树
    /*给定一个不含重复元素的整数数组 nums 。一个以此数组直接递归构建的 最大二叉树 定义如下：

    二叉树的根是数组 nums 中的最大元素。
    左子树是通过数组中 最大值左边部分 递归构造出的最大二叉树。
    右子树是通过数组中 最大值右边部分 递归构造出的最大二叉树。
    返回有给定数组 nums 构建的 最大二叉树 。*/
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructDfs(nums, 0, nums.length - 1);
    }

    public TreeNode constructDfs(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        if (start == end) {
            return new TreeNode(nums[start]);
        }

        int max = nums[start];
        int maxIndex = start;

        for (int i = start; i <= end; i++) {
            if (nums[i] > max) {
                max = nums[i];
                maxIndex = i;
            }
        }

        TreeNode root = new TreeNode(max);
        root.left = constructDfs(nums, start, maxIndex - 1);
        root.right = constructDfs(nums, maxIndex + 1, end);
        return root;
    }

    //652. 寻找重复的子树
    /*给定一棵二叉树，返回所有重复的子树。对于同一类的重复子树，你只需要返回其中任意一棵的根结点即可。

    两棵树重复是指它们具有相同的结构以及相同的结点值。*/
    //序列化树，如果两个数树相同，那么其序列化值一定相同，前序遍历序列化所有树进行判断
    List<TreeNode> dupList;
    Map<String, Integer> dupMap;
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        dupList = new ArrayList<>();
        dupMap = new HashMap<>();
        serDfs(root);
        return dupList;
    }

    public String serDfs(TreeNode root) {
        if (root == null) {
            return "#";
        }

        //这里string可以考虑用stringbuffer，可以降低空间复杂度
        String serStr = root.val + "," + serDfs(root.left) + serDfs(root.right);
        int count = dupMap.getOrDefault(serStr, 0);
        if (count == 0) {
            dupMap.put(serStr, 1);
        } else if (count == 1) {
            dupMap.put(serStr, 2);
            dupList.add(root);
        }
        return serStr;
    }

    //面试题 04.02. 最小高度树
    //给定一个有序整数数组，元素各不相同且按升序排列，编写一个算法，创建一棵高度最小的二叉搜索树。
    //二分切割数组
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedDfs(nums, 0, nums.length - 1);
    }

    public TreeNode sortedDfs(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        if (start == end) {
            return new TreeNode(nums[start]);
        }

        int mid = (start + end) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedDfs(nums, start, mid - 1);
        root.right = sortedDfs(nums, mid + 1, end);
        return root;
    }

    //572. 另一个树的子树
    //给定两个非空二叉树 s 和 t，检验 s 中是否包含和 t 具有相同结构和节点值的子树。s 的一个子树包括 s 的一个节点和这个节点的所有子孙。s 也可以看做它自身的一棵子树。
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null) {
            return false;
        }
        return isSameTree(s, t) || isSubtree(s.left, t) || isSubtree(s.right, t);
    }

    //814. 二叉树剪枝
    /*给定二叉树根结点 root ，此外树的每个结点的值要么是 0，要么是 1。

    返回移除了所有不包含 1 的子树的原二叉树。( 节点 X 的子树为 X 本身，以及所有 X 的后代。)*/
    public TreeNode pruneTree(TreeNode root) {
        return pruneDfs(root) ? root : null;
    }

    public boolean pruneDfs(TreeNode root) {
        if (root == null) {
            return false;
        }

        boolean leftFlag = pruneDfs(root.left);
        if (!leftFlag) {
            root.left = null;
        }

        boolean rightFlag = pruneDfs(root.right);
        if (!rightFlag) {
            root.right = null;
        }

        return root.val == 1 || leftFlag || rightFlag;
    }

    //863. 二叉树中所有距离为 K 的结点
    /*给定一个二叉树（具有根结点 root）， 一个目标结点 target ，和一个整数值 K 。

    返回到目标结点 target 距离为 K 的所有结点的值的列表。 答案可以以任何顺序返回。*/
    //转为无向图，寻找k路径的节点
    Map<Integer, List<TreeNode>> graphK;
    List<Integer> distanList;
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        graphK = new HashMap<>();
        distanList = new ArrayList<>();
        disDfs1(root);
        addTreeDfs(target, null, K);
        return distanList;
    }

    public void disDfs1(TreeNode root) {
        if (root == null) {
            return;
        }

        if (!graphK.containsKey(root.val)) {
            graphK.put(root.val, new ArrayList<>());
        }

        if (root.left != null) {
            if (!graphK.containsKey(root.left.val)) {
                graphK.put(root.left.val, new ArrayList<>());
            }

            graphK.get(root.val).add(root.left);
            graphK.get(root.left.val).add(root);
            disDfs1(root.left);
        }

        if (root.right != null) {
            if (!graphK.containsKey(root.right.val)) {
                graphK.put(root.right.val, new ArrayList<>());
            }

            graphK.get(root.val).add(root.right);
            graphK.get(root.right.val).add(root);
            disDfs1(root.right);
        }
    }

    public void addTreeDfs(TreeNode target, TreeNode par, int K) {
        if (target == null) {
            return;
        }

        if (K == 0) {
            distanList.add(target.val);
            return;
        }

        for (TreeNode node : graphK.get(target.val)) {
            if (node == par) {
                continue;
            }
            addTreeDfs(node, target, K - 1);
        }
    }

    //另一思路
    //节点翻转，把目标父节点转为另一颗树，求k-1即可
    List<Integer> disList;
    TreeNode par;
    public List<Integer> distanceK1(TreeNode root, TreeNode target, int K) {
        disList = new ArrayList<>();
        par = null;

        findParDfs(root, target, null);
        disDfs(target, K);
        disDfs(par, K - 1);
        return disList;
    }
    //父节点的反向递归，由一个标志位自底向上传递
    public boolean findParDfs(TreeNode root, TreeNode target, TreeNode parent) {
        if (root == null) {
            return false;
        }

        if (root == target) {
            par = parent;
            return true;
        }

        if (findParDfs(root.left, target, root)) {
            root.left = parent;
            return true;
        }

        if (findParDfs(root.right, target, root)) {
            root.right = parent;
            return true;
        }
        return false;
    }

    public void disDfs(TreeNode root, int k) {
        if (root == null) {
            return;
        }

        if (k == 0) {
            disList.add(root.val);
            return;
        }
        disDfs(root.left, k - 1);
        disDfs(root.right, k - 1);
    }
}
