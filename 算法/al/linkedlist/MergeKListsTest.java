package com.ccc.fizz.al.linkedlist;

//给你一个链表数组，每个链表都已经按升序排列。
//请你将所有链表合并到一个升序链表中，返回合并后的链表。
public class MergeKListsTest {
    //输入：lists = [[1,4,5],[1,3,4],[2,6]]
    //输出：[1,1,2,3,4,4,5,6]
    //解释：链表数组如下：
    //[
    //  1->4->5,
    //  1->3->4,
    //  2->6
    //]
    //将它们合并到一个有序链表中得到。
    //1->1->2->3->4->4->5->6
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }

        return deal(lists, 0 , lists.length - 1);
    }

    //归并思路，切分数组，然后合并
    public ListNode deal(ListNode[] lists, int left, int right) {
        if (right - left == 0) {
            return lists[left];
        }

        if (right - left == 1) {
            return merge(lists[left], lists[right]);
        }

        int mid = (left + right) / 2;
        return merge(deal(lists, left, mid-1), deal(lists, mid, right));
    }

    public ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }

        if (l1.val < l2.val) {
            l1.next = merge(l1.next, l2);
            return l1;
        } else {
            l2.next = merge(l1, l2.next);
            return l2;
        }
    }
}
