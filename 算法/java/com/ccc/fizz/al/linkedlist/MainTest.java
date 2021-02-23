package com.ccc.fizz.al.linkedlist;

public class MainTest {
    public static void main(String[] args) {
        SortListTest test = new SortListTest();
        ListNode root = new ListNode(4, new ListNode(2, new ListNode(1, new ListNode(3))));
        test.sortList(root);

        ListNode l1 = new ListNode(1, new ListNode(3, new ListNode(5)));
        ListNode l2 = new ListNode(2, new ListNode(4, new ListNode(6)));
        merge(l1, l2);
    }

    public static void merge(ListNode l1, ListNode l2) {
        ListNode head = l1;

        while (l1 != null && l2 != null) {
            ListNode l1Temp = l1.next;
            ListNode l2Temp = l2.next;

            l1.next = l2;
            l1 = l1Temp;

            l2.next = l1;
            l2 = l2Temp;
        }

        System.out.println("");
    }
}
