package com.ccc.fizz.al.linkedlist;

//反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
public class ReverseListTest2 {
    //输入: 1->2->3->4->5->NULL, m = 2, n = 4
    //输出: 1->4->3->2->5->NULL
    public static void main(String[] args) {
        ListNode root = new ListNode(3);
        root.next = new ListNode(5);

        reverseBetween1(root, 1, 2);
    }

    public static ListNode reverseBetween1(ListNode head, int m, int n) {
        ListNode prev = null;
        ListNode innerPrev = null;
        ListNode innerTail = null;
        ListNode curr = head;
        int index = 1;
        while (curr != null) {
            if (index == m) {
               innerTail = curr;
            }

            while (index >= m && index <= n) {
                ListNode temp = curr.next;

                curr.next = innerPrev;
                innerPrev = curr;

                curr = temp;
                index++;
            }

            if (index > n) {
                if (prev == null) {
                    prev = innerPrev;
                    head = prev;
                } else {
                    prev.next = innerPrev;
                }

                innerTail.next = curr;
                break;
            }

            prev = curr;
            curr = curr.next;
            index++;
        }

        return head;
    }

    //在反转n个元素基础上反转部分链表的方法
    public static ListNode reverseBetween(ListNode head, int m, int n) {
        //如果m为1，相当于直接反转前n个元素
        if (m == 1) {
            return reverseN(head, n);
        }

        ListNode prev = null;
        ListNode curr = head;
        //m不为1，减少m和n的值，同时移动指针，直到m为1时，剩下就是反转前n个元素的逻辑
        while (m > 1) {
            prev = curr;
            curr = curr.next;
            m--;
            n--;
        }

        prev.next = reverseN(curr, n);
        return head;
    }

    //反转前n个元素
    public static ListNode reverseN(ListNode head, int n) {
        ListNode prev = null;
        ListNode current = head;

        for (int i = 0; i < n; i++) {
            ListNode temp = current.next;

            current.next = prev;
            prev = current;

            current = temp;
        }
        head.next = current;
        return prev;
    }


}
