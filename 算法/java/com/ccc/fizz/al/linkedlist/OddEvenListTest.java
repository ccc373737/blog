package com.ccc.fizz.al.linkedlist;


//给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。

//请尝试使用原地算法完成。你的算法的空间复杂度应为 O(1)，时间复杂度应为 O(nodes)，nodes 为节点总数。
public class OddEvenListTest {
    //输入: 2->1->3->5->6->4->7->NULL
    //输出: 2->3->6->7->1->5->4->NULL

    //整体思路为取出所有偶数位，在将奇数链表和偶数链表连接
    public ListNode oddEvenList1(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode evenList = new ListNode(1);
        ListNode preEven = evenList;
        ListNode oddList = head;
        ListNode pre = oddList;
        int index = 1;
        while (oddList != null) {
            if ((index & 1) == 1) {
                pre = oddList;
                oddList = oddList.next;
            } else {//偶数位，当前节点加入偶数链表，pre.next = odd.next
                //加入偶数链表，并断开最后一个
                preEven.next = oddList;
                preEven = preEven.next;

                pre.next = oddList.next;
                oddList = oddList.next;
            }

            index++;
        }

        //奇数链表和偶数链表相连
        pre.next = evenList.next;
        preEven.next = null;

        return head;
    }

    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return null;
        }

        //head本身为奇数链表头节点，oddIndex为奇数链表指针
        ListNode oddIndex = head;

        //even为偶数链表头节点
        ListNode even = head.next;
        //evenIndex为偶数链表指针
        ListNode evenIndex = even;
        //1.奇数链表跳过偶数指针
        //2.奇数指针指向最后
        //3.偶数链表跳过奇数指针
        //4.偶数指针指向最后
        while (evenIndex != null && evenIndex.next != null) {
            oddIndex.next = evenIndex.next;
            oddIndex = oddIndex.next;

            evenIndex.next = oddIndex.next;
            evenIndex = evenIndex.next;
        }

        oddIndex.next = even;
        return head;
    }
}
