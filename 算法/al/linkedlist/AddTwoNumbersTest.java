package com.ccc.fizz.al.linkedlist;

import java.util.Stack;

//给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
//
//你可以假设除了数字 0 之外，这两个数字都不会以零开头。
//如果输入链表不能修改该如何处理？换句话说，你不能对列表中的节点进行翻转。
public class AddTwoNumbersTest {
    //输入：(7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
    //输出：7 -> 8 -> 0 -> 7

    //使用栈实现倒序，一般需要倒序的可以用栈实现
    public static ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        Stack<Integer> l1Stack = new Stack<>();
        while (l1 != null) {
            l1Stack.push(l1.val);
            l1 = l1.next;
        }

        Stack<Integer> l2Stack = new Stack<>();
        while (l2 != null) {
            l2Stack.push(l2.val);
            l2 = l2.next;
        }

        ListNode current = null;
        int carry = 0;
        //l1栈不为空或l2栈不为空或者进位标志为1
        while (!l1Stack.isEmpty() || !l2Stack.isEmpty() || carry == 1) {
            //两个数之和+进位
            int l1Temp = l1Stack.isEmpty() ? 0 : l1Stack.pop();
            int l2Temp = l2Stack.isEmpty() ? 0 : l2Stack.pop();
            int value = l1Temp + l2Temp + carry;

            carry = 0;

            ListNode temp;
            if (value < 10) {
                temp = new ListNode(value);
            } else {
                //大于10 取余数 进位标志为1
                temp = new ListNode(value % 10);
                carry = 1;
            }
            temp.next = current;
            current = temp;
        }

        return current;
    }

    //数位倒序存放
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode current = head;
        int carry = 0;

        while (l1 != null || l2 != null || carry == 1) {
            int a = l1 == null ? 0 : l1.val;
            int b = l2 == null ? 0 : l2.val;
            int value = a + b + carry;

            carry = 0;

            ListNode temp;
            if (value < 10) {
                temp = new ListNode(value);
            } else {
                //大于10 取余数 进位标志为1
                temp = new ListNode(value % 10);
                carry = 1;
            }

            current.next = temp;
            current = current.next;

            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }
        return head.next;
    }
}
