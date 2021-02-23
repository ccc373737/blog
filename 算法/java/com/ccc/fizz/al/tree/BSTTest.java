package com.ccc.fizz.al.tree;

import java.util.ArrayList;
import java.util.List;

public class BSTTest {
    //96. 不同的二叉搜索树
    //给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
    //G(8) = G(0) * G(7) + G(1) * G(6)...
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= i - 1; j++) {
                dp[i] += dp[j] * dp[i - j - 1];
            }
        }
        return dp[n];
    }

    //95给定一个整数 n，生成所有由 1 ... n 为节点所组成的 二叉搜索树 。
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new ArrayList<>();
        }

        List<TreeNode>[] dp = new List[n + 1];
        dp[0] = new ArrayList<>();
        dp[0].add(null);

        for (int i = 1; i <= n; i++) {
            dp[i] = new ArrayList<>();
            for (int j = 1; j <= i; j++) {
                List<TreeNode> left = dp[j-1];
                List<TreeNode> right = dp[i-j];
                for (TreeNode leftVal : left) {
                    for (TreeNode rightVal : right) {
                        TreeNode root = new TreeNode(j);
                        root.left = leftVal;
                        root.right = treeOff(rightVal, j);
                        dp[i].add(root);
                    }
                }
            }
        }
        return dp[n];
    }

    public TreeNode treeOff(TreeNode root, Integer offset) {
        if (root == null) {
            return null;
        }

        TreeNode temp = new TreeNode(root.val + offset);
        temp.left = treeOff(root.left, offset);
        temp.right = treeOff(root.right, offset);
        return temp;
    }

    //98. 验证二叉搜索树
    /*给定一个二叉树，判断其是否是一个有效的二叉搜索树。

    假设一个二叉搜索树具有如下特征：

    节点的左子树只包含小于当前节点的数。
    节点的右子树只包含大于当前节点的数。
    所有左子树和右子树自身必须也是二叉搜索树。*/
    //不能简单得判断当前左右节点大小，必须要中序遍历比较当前和上一个
    TreeNode pre;
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }

        if (!isValidBST(root.left)) {
            return false;
        }

        if (pre != null && pre.val >= root.val) {
            return false;
        }
        pre = root;

        if (!isValidBST(root.right)) {
            return false;
        }
        return true;
    }

    //99. 恢复二叉搜索树
    /*给你二叉搜索树的根节点 root ，该树中的两个节点被错误地交换。请在不改变其结构的情况下，恢复这棵树。

    进阶：使用 O(n) 空间复杂度的解法很容易实现。你能想出一个只使用常数空间的解决方案吗？*/
    //通用方法是用一个list按中序遍历保存值，然后排序，然后直接再按中序遍历赋值
    //O(1)的方法不使用辅助空间
    //[2,1,4,3]，当第一次出现pre>current，要交换的第一个值为pre
    //当第二次出现pre>current，要交换的第二个为current
    TreeNode preR;
    TreeNode one;
    TreeNode two;
    public void recoverTree(TreeNode root) {
        findNot(root);

        if (one != null && two != null) {
            int temp = one.val;
            one.val = two.val;
            two.val = temp;
        }
    }

    public void findNot(TreeNode root) {
        if (root == null) {
            return;
        }

        findNot(root.left);

        if (preR != null && preR.val >= root.val) {
            if (one == null) {
                //这里同时需要保存当前值为two，如1324这种情况，相邻的两个节点即为要交换的对象
                one = preR;
                two = root;
            } else {
                two = root;
            }
        }
        preR = root;

        findNot(root.right);
    }

    //108. 将有序数组转换为二叉搜索树
    public TreeNode sortedArrayToBST(int[] nums) {
        return bstDfs(nums, 0, nums.length - 1);
    }

    public TreeNode bstDfs(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = (start + end ) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = bstDfs(nums, start, mid - 1);
        root.right = bstDfs(nums, mid + 1, end);
        return root;
    }

    //230. 二叉搜索树中第K小的元素
    int kth;
    int numk;
    public int kthSmallest(TreeNode root, int k) {
        kth = k;
        kthDfs(root);
        return numk;
    }

    public void kthDfs(TreeNode root) {
        if (root == null) {
            return;
        }

        kthDfs(root.left);

        if (--kth == 0) {
            numk = root.val;
        }
        kthDfs(root.right);
    }

    //270. 最接近的二叉搜索树值
    //给定一个不为空的二叉搜索树和一个目标值 target，请在该二叉搜索树中找到最接近目标值 target 的数值。
    public int closestValue(TreeNode root, double target) {
        int left = root.val;
        int right = root.val;
        while (root != null) {
            //目标在左侧 更新右边界
            if (root.val > target) {
                right = root.val;
                root = root.left;
            } else if (root.val < target) {//目标在右侧
                left = root.val;
                root = root.right;
            } else {
                return root.val;
            }
        }
        return Math.abs(right - target) > Math.abs(target - left) ? left : right;
    }

    //700. 二叉搜索树中的搜索
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }

        if (root.val > val) {
            return searchBST(root.left, val);
        } else if (root.val < val) {
            return searchBST(root.right, val);
        } else {
            return root;
        }
    }

    //给定一个二叉树，找到其中最大的二叉搜索树（BST）子树，并返回该子树的大小。其中，最大指的是子树节点数最多的。
    class bstInfo {
        int max;
        int min;
        int size;

        public bstInfo(int max, int min, int size) {
            this.max = max;
            this.min = min;
            this.size = size;
        }
    }

    public int largestBSTSubtree(TreeNode root) {
        return getBstInfo(root).size;
    }

    public bstInfo getBstInfo(TreeNode root) {
        if (root == null) {
            return new bstInfo(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        }

        bstInfo left = getBstInfo(root.left);
        bstInfo right = getBstInfo(root.right);

        if (right.min > root.val && root.val > left.max) {
            return new bstInfo(Math.max(right.max, root.val), Math.min(left.min, root.val), left.size + right.size + 1);
        }
        return new bstInfo(Integer.MAX_VALUE, Integer.MIN_VALUE, Math.max(right.size, left.size));
    }

    //给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
    //中序遍历，天然有序
    List<Integer> mode;
    TreeNode preMode;
    int allCount;
    int currentCount;
    public int[] findMode(TreeNode root) {
        mode = new ArrayList<>();
        findModeDfs(root);

        int[] temp = new int[mode.size()];
        for (int i = 0; i < mode.size(); i++) {
            temp[i] = mode.get(i);
        }
        return temp;
    }

    public void findModeDfs(TreeNode root) {
        if (root == null) {
            return;
        }

        findModeDfs(root.left);

        //第一个节点，初始化一些值
        if (preMode == null) {
            preMode = root;
            currentCount = 1;
        } else {
            if (preMode.val == root.val) {
                currentCount++;
            } else {
                currentCount = 1;
            }
        }
        preMode = root;

        if (currentCount == allCount) {//达到最大值，加入list
            mode.add(root.val);
        } else if (currentCount > allCount) {//超过最大值，清空list，并加入当前值
            mode.clear();
            mode.add(root.val);
            allCount = currentCount;
        }

        findModeDfs(root.right);
    }

    //510. 二叉搜索树中的中序后继 II
    /*给定一棵二叉搜索树和其中的一个节点 node ，找到该节点在树中的中序后继。

    如果节点没有中序后继，请返回 null 。

    一个结点 node 的中序后继是键值比 node.val大所有的结点中键值最小的那个。*/
    //你能否在不访问任何结点的值的情况下解决问题?
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }

    //利用中序遍历，
    public Node inorderSuccessor(Node node) {
        Node one = null;

        if (node.right != null) {//中间节点情况，直接找下一个最小值
            one = node.right;
            while (one.left != null) {
                one = one.left;
            }
        } else {//根节点
            /*if (node.parent == null) {
                one = node.right;
            } else {
                while (node.parent != null) {//从node出发找到最近的左子节为当前的父节点，即存在右节点可以寻找最小值
                    Node par = node.parent;
                    if (par.left == node) {
                        one = par;
                        break;
                    }
                    node = node.parent;
                }
            }*/
            //有父节点且父节点右节点为node，不断向上找父节点
            //直到出现有父节点且左节点为node，那么当前节点就为下一个
            while (node.parent != null && node.parent.right == node) {
                node = node.parent;
            }
            one = node.parent;
        }

        return one;
    }

    //538. 把二叉搜索树转换为累加树
    int currentSum;
    public TreeNode convertBST(TreeNode root) {
        currentSum = 0;
        convertDfs(root);
        return root;
    }

    //中序遍历倒序 右中左的顺序
    public void convertDfs(TreeNode root) {
        if (root == null) {
            return;
        }

        convertDfs(root.right);

        currentSum += root.val;
        root.val = currentSum;

        convertDfs(root.left);
    }

    //938. 二叉搜索树的范围和
    public int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null) {
            return 0;
        }

        //目标在右侧
        if (root.val < low) {
            return rangeSumBST(root.right, low, high);
        } else if (root.val > high) {
            return rangeSumBST(root.left, low, high);
        } else {
            return root.val + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high);
        }
    }

    //1305. 两棵二叉搜索树中的所有元素
    /*给你 root1 和 root2 这两棵二叉搜索树。

    请你返回一个列表，其中包含 两棵树 中的所有整数并按 升序 排序。.*/
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> list1 = getAllDfs(root1, new ArrayList<>());
        List<Integer> list2 = getAllDfs(root2, new ArrayList<>());

        List<Integer> all = new ArrayList<>();
        int i1 = 0;
        int i2 = 0;
        //归并排序
        while (i1 < list1.size() && i2 < list2.size()) {
            if (list1.get(i1) < list2.get(i2)) {
                all.add(list1.get(i1++));
            } else {
                all.add(list2.get(i2++));
            }
        }

        while (i1 < list1.size()) {
            all.add(list1.get(i1++));
        }

        while (i2 < list2.size()) {
            all.add(list2.get(i2++));
        }

        return all;
    }

    public List<Integer> getAllDfs(TreeNode root, List<Integer> list) {
        if (root == null) {
            return list;
        }

        getAllDfs(root.left, list);
        list.add(root.val);
        getAllDfs(root.right, list);
        return list;
    }

    //285. 二叉搜索树中的顺序后继
    /*给你一个二叉搜索树和其中的某一个结点，请你找出该结点在树中顺序后继的节点。

    结点 p 的后继是值比 p.val 大的结点中键值最小的结点。*/
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        TreeNode next = null;
        while (root != null) {
            if (root.val > p.val) {//节点值在左侧，同时保存当前节点，即当前节点有可能是下一个最小值
                next = root;
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return next;
    }

    //530. 二叉搜索树的最小绝对差
    TreeNode preDiff;
    int minDiff;
    public int getMinimumDifference(TreeNode root) {
        preDiff = null;
        minDiff = Integer.MAX_VALUE;
        getMinimumDifferenceDfs(root);
        return minDiff;
    }

    public void getMinimumDifferenceDfs(TreeNode root) {
        if (root == null) {
            return;
        }

        getMinimumDifferenceDfs(root.left);

        if (preDiff != null) {
            minDiff = Math.min(minDiff, root.val - preDiff.val);
        }
        preDiff = root;

        getMinimumDifferenceDfs(root.right);
    }

    //669. 修剪二叉搜索树
    /*给你二叉搜索树的根节点 root ，同时给定最小边界low 和最大边界 high。通过修剪二叉搜索树，使得所有节点的值在[low, high]中。修剪树不应该改变保留在树中的元素的相对结构（即，如果没有被移除，原有的父代子代关系都应当保留）。 可以证明，存在唯一的答案。

    所以结果应当返回修剪好的二叉搜索树的新的根节点。注意，根节点可能会根据给定的边界发生改变。*/
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) {
            return null;
        }

        //目标值在右侧
        if (root.val < low) {
            return trimBST(root.right, low, high);
        } else if (root.val > high) {
            return trimBST(root.left, low, high);
        } else {
            //目标在范围中，需要保留，同时修建左子树和右子树
            root.left = trimBST(root.left, low, high);
            root.right = trimBST(root.right, low, high);
            return root;
        }
    }

}
