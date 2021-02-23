package com.ccc.fizz.al.linkedlist;

//给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
public class RotateRightTest {
    //输入: 1->2->3->4->5->NULL, k = 2
    //输出: 4->5->1->2->3->NULL
    //解释:
    //向右旋转 1 步: 5->1->2->3->4->NULL
    //向右旋转 2 步: 4->5->1->2->3->NULL
    //
    //输入: 0->1->2->NULL, k = 4
    //输出: 2->0->1->NULL
    //解释:
    //向右旋转 1 步: 2->0->1->NULL
    //向右旋转 2 步: 1->2->0->NULL
    //向右旋转 3 步: 0->1->2->NULL
    //向右旋转 4 步: 2->0->1->NULL
    public ListNode rotateRight1(ListNode head, int k) {
        if (head == null) {
            return null;
        }

        ListNode fast = null;
        ListNode slow = head;

        for (int i = 0; i < k; i++) {
            if (fast == null) {
                fast = head;
            }
            fast = fast.next;
        }

        if (fast == null) {
            return head;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        ListNode newHead = slow.next;

        slow.next = null;
        fast.next = head;

        return newHead;
    }

    //先统计链表长度，利用k % size获取余数
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return null;
        }

        ListNode nodeI = head;
        int size = 0;
        while (nodeI != null) {
            size++;
            nodeI = nodeI.next;
        }

        //得到需要走的步数
        size = k % size;
        //不用移动
        if (size == 0) {
            return head;
        }

        ListNode slow = head;
        ListNode fast = head;

        for (int i = 0; i < size; i++) {
            fast = fast.next;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        ListNode newHead = slow.next;
        fast.next = head;
        slow.next = null;
        return newHead;
    }
}
