package com.ccc.fizz.al.linkedlist;
//给你两个链表 list1 和 list2 ，它们包含的元素分别为 n 个和 m 个。
//
//请你将 list1 中第 a 个节点到第 b 个节点删除，并将list2 接在被删除节点的位置。
//
//下图中蓝色边和节点展示了操作后的结果：
//
//请你返回结果链表的头指针。

public class mergeInBetweenTest {
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        int index = b - a;

        ListNode fast = list1;
        ListNode slow = list1;
        //slow前指针
        ListNode pre = list1;
        for (int i = 0; i < index; i++) {
            fast = fast.next;
        }

        for (int i = 0; i < a; i++) {
            pre = slow;
            slow = slow.next;
            fast = fast.next;
        }

        pre.next = list2;

        ListNode l2Pre = list2;
        while (l2Pre.next != null) {
            l2Pre = l2Pre.next;
        }

        l2Pre.next = fast.next;
        return list1;
    }
}
