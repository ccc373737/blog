package com.ccc.fizz.al.tree;

import java.util.*;

public class DepthTest2 {
    //1026. 节点与其祖先之间的最大差值
    /*给定二叉树的根节点 root，找出存在于 不同 节点 A 和 B 之间的最大值 V，其中 V = |A.val - B.val|，且 A 是 B 的祖先。
      （如果 A 的任何子节点之一为 B，或者 A 的任何子节点是 B 的祖先，那么我们认为 A 是 B 的祖先）*/
    //先序遍历，传入之前节点的最大最小值，与当前root值求绝对值
    int maxDiff;

    public int maxAncestorDiff(TreeNode root) {
        diffDfs(root, root.val, root.val);
        return maxDiff;
    }

    public void diffDfs(TreeNode root, int min, int max) {
        if (root == null) {
            return;
        }

        int diff = Math.max(Math.abs(root.val - min), Math.abs(root.val - max));
        maxDiff = Math.max(maxDiff, diff);

        min = Math.min(root.val, min);
        max = Math.max(root.val, max);

        diffDfs(root.left, min, max);
        diffDfs(root.right, min, max);
    }

    //1123.给你一个有根节点的二叉树，找到它最深的叶节点的最近公共祖先。
    //寻找深度，判断左右节点深度，如果相同，那么当前节点为最近公告祖先
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        if (root == null) {
            return null;
        }

        int left = maxDepth(root.left);
        int right = maxDepth(root.right);

        if (left == right) {
            return root;
        } else if (left < right) {
            return lcaDeepestLeaves(root.right);
        } else {
            return lcaDeepestLeaves(root.left);
        }
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    //1315. 祖父节点值为偶数的节点和
    /*给你一棵二叉树，请你返回满足以下条件的所有节点的值之和：

    该节点的祖父节点的值为偶数。（一个节点的祖父节点是指该节点的父节点的父节点。）
    如果不存在祖父节点值为偶数的节点，那么返回 0 。*/
    int max;
    public int sumEvenGrandparent(TreeNode root) {
        max = 0;
        evenDfs(root);
        return max;
    }

    public void evenDfs(TreeNode root) {
        if (root == null) {
            return;
        }

        if ((root.val & 1) == 0) {
            evenAdd(root.left, 1);
            evenAdd(root.right, 1);
        }

        evenDfs(root.left);
        evenDfs(root.right);
    }

    public void evenAdd(TreeNode root, int depth) {
        if (root == null) {
            return;
        }

        if (depth == 0) {
            max += root.val;
            return;
        }

        evenAdd(root.left, depth - 1);
        evenAdd(root.right, depth - 1);
    }

    //1379. 找出克隆二叉树中的相同节点
    /*给你两棵二叉树，原始树 original 和克隆树 cloned，以及一个位于原始树 original 中的目标节点 target。

    其中，克隆树 cloned 是原始树 original 的一个 副本 。

    请找出在树 cloned 中，与 target 相同 的节点，并返回对该节点的引用（在 C/C++ 等有指针的语言中返回 节点指针，其他语言返回节点本身）。*/
    public final TreeNode getTargetCopy(final TreeNode original, final TreeNode cloned, final TreeNode target) {
        if (original == null) {
            return null;
        }
        if (original == target) {
            return cloned;
        }

        TreeNode left = getTargetCopy(original.left, cloned.left, target);
        if (left != null) {
            return left;
        }

        TreeNode right = getTargetCopy(original.right, cloned.right, target);
        if (right != null) {
            return right;
        }

        return null;
    }

    //1448. 统计二叉树中好节点的数目
    //给你一棵根为 root 的二叉树，请你返回二叉树中好节点的数目。
    //
    //「好节点」X 定义为：从根到该节点 X 所经过的节点中，没有任何节点的值大于 X 的值。
    //前序遍历，并传入之前节点的最大值
    int goodCount;
    public int goodNodes(TreeNode root) {
        goodCount = 0;
        goodNodesDfs(root, root.val);
        return goodCount;
    }

    public void goodNodesDfs(TreeNode root, int max) {
        if (root == null) {
            return;
        }

        if (root.val >= max) {
            goodCount++;
        }

        max = Math.max(max, root.val);
        goodNodesDfs(root.left, max);
        goodNodesDfs(root.right, max);
    }

    //687. 最长同值路径
    /*给定一个二叉树，找到最长的路径，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。

    注意：两个节点之间的路径长度由它们之间的边数表示。*/
    //左半边同值 + 右半边同值，左半边同值最大值，右半边同值最大值
    int maxUnival;
    public int longestUnivaluePath(TreeNode root) {
        maxUnival = 0;
        valuePathDfs(root);
        return maxUnival;
    }

    public int valuePathDfs(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = valuePathDfs(root.left);
        int right = valuePathDfs(root.right);

        left = (root.left != null && root.val == root.left.val) ? left + 1 : 0;
        right = (root.right != null && root.val == root.right.val) ? right + 1 : 0;

        maxUnival = Math.max(maxUnival, left + right);
        return Math.max(left, right);
    }

    //965. 单值二叉树
    public boolean isUnivalTree(TreeNode root) {
        return isUnivalTreeDfs(root, root.val);
    }

    public boolean isUnivalTreeDfs(TreeNode root, int val) {
        if (root == null) {
            return true;
        }

        return root.val == val && isUnivalTreeDfs(root.left, val) && isUnivalTreeDfs(root.right, val);
    }

    //1110. 删点成林
    /*给出二叉树的根节点 root，树上每个节点都有一个不同的值。

    如果节点值在 to_delete 中出现，我们就把该节点从树上删去，最后得到一个森林（一些不相交的树构成的集合）。

    返回森林中的每棵树。你可以按任意顺序组织答案。*/
    Set<Integer> dSet;
    List<TreeNode> dList;
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        dSet = new HashSet<>();
        for (int i : to_delete) {
            dSet.add(i);
        }

        dList = new ArrayList<>();
        TreeNode temp = delNodesDfs(root);
        if (temp != null) {
            dList.add(temp);
        }
        return dList;
    }

    public TreeNode delNodesDfs(TreeNode root) {
        if (root == null) {
            return null;
        }

        //后序遍历，找左右节点，为null表示下一个节点为空或被删除
        TreeNode left = delNodesDfs(root.left);
        TreeNode right = delNodesDfs(root.right);

        //被删除
        if (dSet.contains(root.val)) {
            if (left != null) {
                dList.add(left);
            }

            if (right != null) {
                dList.add(right);
            }
            //返回null，在上一层递归中可以与父节点断开
            return null;
        }

        root.left = left;
        root.right = right;
        return root;
    }

    //1339. 分裂二叉树的最大乘积
    /*给你一棵二叉树，它的根为 root 。请你删除 1 条边，使二叉树分裂成两棵子树，且它们子树和的乘积尽可能大。

    由于答案可能会很大，请你将结果对 10^9 + 7 取模后再返回。*/
    long sumProduct;
    long resultProduct;
    public int maxProduct(TreeNode root) {
        sumProduct = productGetSum(root);
        productGetResult(root);
        return (int)(resultProduct % (1e9 + 7));
    }

    public long productGetSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return root.val + productGetSum(root.left) + productGetSum(root.right);
    }

    public long productGetResult(TreeNode root) {
        if (root == null) {
            return 0;
        }

        long val = productGetResult(root.left) + productGetResult(root.right) + root.val;
        resultProduct = Math.max(resultProduct, val * (sumProduct - val));
        return val;
    }

    //1372. 二叉树中的最长交错路径
    int maxZigZag;
    public int longestZigZag(TreeNode root) {
        maxZigZag = 0;
        longestDfs(root);
        return maxZigZag;
    }

    public int[] longestDfs(TreeNode root) {
        if (root.left == null && root.right == null) {
            return new int[]{0,0};
        }

        int left = 0;
        int right = 0;

        //左路径=子右路径+1
        if (root.right != null) {
            left = longestDfs(root.right)[0] + 1;
        }

        if (root.left != null) {
            right = longestDfs(root.left)[1] + 1;
        }

        maxZigZag = Math.max(maxZigZag, Math.max(left, right));

        return new int[]{right, left};
    }

    //404. 左叶子之和
    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (root.left != null && root.left.left == null && root.left.right == null) {
            return root.left.val + sumOfLeftLeaves(root.right);
        }

        return sumOfLeftLeaves(root.left) + sumOfLeftLeaves(root.right);
    }

    //872. 叶子相似的树
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List l1 = getLeaves(root1, new ArrayList<>());
        List l2 = getLeaves(root2, new ArrayList<>());

        if (l1.size() != l2.size()) {
            return false;
        }

        for (int i = 0; i < l1.size(); i++) {
            if (l1.get(i) != l2.get(i)) {
                return false;
            }
        }
        return true;
    }

    public List getLeaves(TreeNode root, List<Integer> list) {
        if (root == null) {
            return list;
        }

        if (root.left == null && root.right == null) {
            list.add(root.val);
            return list;
        }

        getLeaves(root.left, list);
        getLeaves(root.right, list);
        return list;
    }

    //337. 打家劫舍 III
    /*在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。

    计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。*/
    //最大金额为max(root.val + 左节点子节点最大 + 右节点子节点最大，左节点最大 + 右节点最大)
    public int rob(TreeNode root) {
        int[] count = robDfs(root);
        return Math.max(count[0], count[1]);
    }

    //数组第一个元素为包括根节点的最大情况，第二个元素为不包括根节点的最大情况
    public int[] robDfs(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }

        int[] left = robDfs(root.left);
        int[] right = robDfs(root.right);

        return new int[]{root.val + left[1] + right[1], Math.max(left[0], left[1]) + Math.max(right[0], right[1])};
    }

}
