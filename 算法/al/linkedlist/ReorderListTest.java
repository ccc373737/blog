package com.ccc.fizz.al.linkedlist;

//给定一个单链表 L：L0→L1→…→Ln-1→Ln ，
//将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→…
//
//你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
public class ReorderListTest {
    //给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3.
    public void reorderList(ListNode head) {
        //快慢指针找中点
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        //反转后半部分链表
        ListNode right = reverseList(slow);
        ListNode left = head;

        //合并两个链表
        while (right != null && left != null) {
            ListNode leftTemp = left.next;

            left.next = right;

            ListNode rightTemp = right.next;

            if (rightTemp != null) {
                right.next = leftTemp;
                left = leftTemp;
            }

            right = rightTemp;
        }
    }

    public ListNode reverseList(ListNode head) {
        ListNode newHead = null;
        while (head != null) {
            ListNode next = head.next;

            head.next = newHead;
            newHead = head;

            head = next;
        }

        return newHead;
    }
}
