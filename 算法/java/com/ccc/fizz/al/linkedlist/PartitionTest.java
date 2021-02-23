package com.ccc.fizz.al.linkedlist;

//给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
//你应当保留两个分区中每个节点的初始相对位置。
public class PartitionTest {
    public ListNode partition(ListNode head, int x) {
        ListNode left = new ListNode(0);
        ListNode leftHead = left;

        ListNode right = new ListNode(0);
        ListNode rightHead = right;

        while (head != null) {
            if (head.val < x) {
                left.next = head;
                left = left.next;
            } else {
                right.next = head;
                right = right.next;
            }

            head = head.next;
        }

        left.next = rightHead.next;
        right.next = null;
        return leftHead.next;
    }

    public ListNode partition1(ListNode head, int x) {
        ListNode newHead = new ListNode(0);
        ListNode leftPre = newHead;
        leftPre.next = head;
        ListNode leftCurrent = head;


        ListNode right = new ListNode(x);
        ListNode rightCurrent = right;

        while (leftCurrent != null) {
            if (leftCurrent.val < x) {
                leftPre = leftCurrent;
                leftCurrent = leftCurrent.next;
            } else {
                rightCurrent.next = leftCurrent;
                rightCurrent = rightCurrent.next;

                leftPre.next = leftCurrent.next;
                leftCurrent = leftCurrent.next;
            }
        }

        leftPre.next = right.next;
        rightCurrent.next = null;
        return newHead.next;
    }
}
