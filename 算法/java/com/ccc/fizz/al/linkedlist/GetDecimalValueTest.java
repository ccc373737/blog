package com.ccc.fizz.al.linkedlist;

//给你一个单链表的引用结点 head。链表中每个结点的值不是 0 就是 1。已知此链表是一个整数数字的二进制表示形式。
//
//请你返回该链表所表示数字的 十进制值 。
public class GetDecimalValueTest {
    public int getDecimalValue(ListNode head) {
        int result = 0;
        //移位运算，如1011的过程为1 -> 10 -> 101 -> 1011
        while (head != null) {
            result = (result << 1) + head.val;
            head = head.next;
        }
        return result;
    }
}
