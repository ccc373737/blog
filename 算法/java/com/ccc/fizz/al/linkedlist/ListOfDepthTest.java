package com.ccc.fizz.al.linkedlist;

import com.ccc.fizz.al.TreeNode;

import java.util.*;

//给定一棵二叉树，设计一个算法，创建含有某一深度上所有节点的链表（比如，若一棵树的深度为 D，则会创建出 D 个链表）。返回一个包含所有深度的链表的数组。
public class ListOfDepthTest {
    public ListNode[] listOfDepth(TreeNode tree) {
        List<ListNode> list = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(tree);

        //层序遍历
        while (!queue.isEmpty()) {
            //记录每层的个数
            int size = queue.size();
            //创建虚拟头节点
            ListNode head = new ListNode(-1);
            ListNode node = head;
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                node.next = new ListNode(poll.val);
                node = node.next;

                if (poll.left != null) {
                    queue.offer(poll.left);
                }

                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }

            list.add(head.next);
        }

        return list.toArray(new ListNode[list.size()]);
    }
}
