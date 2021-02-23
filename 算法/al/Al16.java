package com.ccc.fizz.al;

public class Al16 {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode getKthFromEnd(ListNode head, int k) {
        int start = 1;
        ListNode kIndex = head;
        while (head.next != null) {
            head = head.next;
            start++;
            if (start - 0 > k) {
                kIndex = head.next;
            }
        }

        return kIndex;
    }

    //输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的。
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }
        if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }
}
