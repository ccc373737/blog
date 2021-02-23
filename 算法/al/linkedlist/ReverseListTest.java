package com.ccc.fizz.al.linkedlist;

//反转一个单链表。
public class ReverseListTest {
    public static void main(String[] args) {
        ListNode root = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println("");

    }
    
    public static ListNode reverseList(ListNode head) {
        ListNode newHead = null;
        while (head != null) {
            ListNode next = head.next;

            head.next = newHead;
            newHead = head;

            head = next;
        }

        return newHead;
    }

    public static ListNode reverse(ListNode head) {
        if (head.next == null) return head;
        ListNode last = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return last;
    }
}
