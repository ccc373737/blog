package com.ccc.fizz.al.linkedlist;

//对链表进行插入排序。
//1.插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
//2.每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
//3.重复直到所有输入数据插入完为止。

public class InsertionSortListTest {
    //输入: -1->5->3->4->0
    //输出: -1->0->3->4->5
    public ListNode insertionSortList(ListNode head) {
        ListNode pre = null;
        ListNode current = head;
        //外部大循环
        while (current != null) {
            ListNode littlePrev = null;
            ListNode littleCurrent = head;
            //内部小循环
            while (littleCurrent.val < current.val && littleCurrent != current) {
                littlePrev = littleCurrent;
                littleCurrent = littleCurrent.next;
            }

            ListNode temp = current.next;
            //不改变位置的情况
            if (littleCurrent == current) {
                pre = current;
                current = temp;
            } else {
                //头节点大于某个值，即需要改变头节点
                if (littlePrev == null) {
                    head = current;
                } else {
                    littlePrev.next = current;
                }

                current.next = littleCurrent;
                current = temp;
                pre.next = current;
            }
        }

        return head;
    }
}
