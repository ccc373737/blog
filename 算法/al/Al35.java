package com.ccc.fizz.al;

import java.util.HashSet;
import java.util.Set;

/**
 * 输入两个链表，找出它们的第一个公共节点。
 *
 * **/
public class Al35 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<>();
        while (headA != null) {
            set.add(headA);
            headA = headA.next;
        }

        while (headB != null) {
            if (set.contains(headB)) {
                return headB;
            }
            headB = headB.next;
        }

        return null;
    }
}
