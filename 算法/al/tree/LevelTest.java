package com.ccc.fizz.al.tree;

import java.util.*;

public class LevelTest {
    //993. 二叉树的堂兄弟节点
    /*在二叉树中，根节点位于深度 0 处，每个深度为 k 的节点的子节点位于深度 k+1 处。

    如果二叉树的两个节点深度相同，但父节点不同，则它们是一对堂兄弟节点。

    我们给出了具有唯一值的二叉树的根节点 root，以及树中两个不同节点的值 x 和 y。

    只有与值 x 和 y 对应的节点是堂兄弟节点时，才返回 true。否则，返回 false。*/
    //层序遍历，xy处于同一层且不是同一个父节点
    public boolean isCousins(TreeNode root, int x, int y) {
        Queue<TreeNode> queue = new LinkedList<>();
        Set<Integer> set = new HashSet<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (poll.left != null && poll.right != null &&
                  ((poll.left.val == x && poll.right.val == y) ||  (poll.left.val == y && poll.right.val == x))) {
                    return false;
                }

                if (poll.left != null) {
                    queue.offer(poll.left);
                }

                if (poll.right != null) {
                    queue.offer(poll.right);
                }
                set.add(poll.val);
            }

            if (set.contains(x) && set.contains(y)) {
                return true;
            }

            set.clear();
        }
        return false;
    }

    //1302. 层数最深叶子节点的和
    //层序遍历，最后一层的节点和
    public int deepestLeavesSum(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int result = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            int sum = 0;

            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                sum += poll.val;

                if (poll.left != null) {
                    queue.offer(poll.left);
                }

                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }

            result = sum;
        }
        return result;
    }

    //1602. 找到二叉树中最近的右侧节点
    //给定一棵二叉树的根节点 root 和树中的一个节点 u ，返回与 u 所在层中距离最近的右侧节点，当 u 是所在层中最右侧的节点，返回 null 。
    //层序遍历，找循环中的下一个节点，如果是最后一个返回null
    public TreeNode findNearestRightNode(TreeNode root, TreeNode u) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();

                if (poll == u && i < size - 1) {
                    return queue.poll();
                } else if (poll == u && i == size - 1) {
                    return null;
                }

                if (poll.left != null) {
                    queue.offer(poll.left);
                }

                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
        }
        return null;
    }

    //662. 二叉树最大宽度
    /*给定一个二叉树，编写一个函数来获取这个树的最大宽度。树的宽度是所有层中的最大宽度。这个二叉树与满二叉树（full binary tree）结构相同，但一些节点为空。

    每一层的宽度被定义为两个端点（该层最左和最右的非空节点，两端点间的null节点也计入长度）之间的长度。*/
    public int widthOfBinaryTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        LinkedList<Integer> indexList = new LinkedList<>();
        indexList.add(1);

        int width = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            width = Math.max(width, indexList.getLast() - indexList.getFirst() + 1);
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                int index = indexList.removeFirst();

                if (poll.left != null) {
                    queue.offer(poll.left);
                    indexList.add(2 * index - 1);
                }

                if (poll.right != null) {
                    queue.offer(poll.right);
                    indexList.add(2 * index);
                }
            }
        }
        return width;
    }

    //513. 找树左下角的值
    public int findBottomLeftValue(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int left = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (i == 0) {
                    left = poll.val;
                }

                if (poll.left != null) {
                    queue.offer(poll.left);
                }

                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
        }
        return left;
    }

    //958. 二叉树的完全性检验
    /*给定一个二叉树，确定它是否是一个完全二叉树。

    百度百科中对完全二叉树的定义如下：

    若设二叉树的深度为 h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。（注：第 h 层可能包含 1~ 2h 个节点。）*/
    //层序遍历 false条件为某一层出现null且下一层有节点或之后出现节点
    public boolean isCompleteTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        TreeNode pre = root;
        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean flag = true;
            for (int i = 0; i < size; i++) {
                TreeNode current = queue.poll();

                if (pre == null && current != null) {
                    return false;
                }

                pre = current;
                if (current != null) {
                    queue.offer(current.left);
                    queue.offer(current.right);
                }
            }
        }
        return true;
    }


}
