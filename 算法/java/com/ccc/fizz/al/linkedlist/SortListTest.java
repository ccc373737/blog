package com.ccc.fizz.al.linkedlist;

//给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
//
//进阶：
//
//你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
public class SortListTest {

    //寻找中值分割，进行归并排序
    public ListNode sortList(ListNode head) {
        ListNode pre = null;
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        //边界条件，只有一个节点的情况
        if (pre == null) {
            return head;
        }
        //断开pre指针，即将前后分为两个部分
        pre.next = null;

        return merge(sortList(head), sortList(slow));
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
