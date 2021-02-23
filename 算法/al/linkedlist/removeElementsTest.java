package com.ccc.fizz.al.linkedlist;

//删除链表中等于给定值 val 的所有节点。
public class removeElementsTest {
    //输入: 1->2->6->3->4->5->6, val = 6
    //输出: 1->2->3->4->5
    public ListNode removeElements(ListNode head, int val) {
        //头节点或者前几个节点都为val的情况
        while (head != null && head.val == val) {
            head = head.next;
        }

        ListNode prev= null;
        ListNode curr = head;

        while (curr != null) {
            if (curr.val == val) {
                prev.next = curr.next;
                curr = curr.next;
            } else {
                prev = curr;
                curr = curr.next;
            }
        }

        return head;
    }

    //递归解法
    public ListNode removeElements1(ListNode head, int val) {
        if(head==null)
            return null;
        head.next=removeElements1(head.next,val);
        if(head.val==val){
            return head.next;
        }else{
            return head;
        }
    }
}
