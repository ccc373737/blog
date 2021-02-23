package com.ccc.fizz.al.linkedlist;
//给定两个（单向）链表，判定它们是否相交并返回交点。请注意相交的定义基于节点的引用，而不是基于节点的值。换句话说，如果一个链表的第k个节点与另一个链表的第j个节点是同一节点（引用完全相同），则这两个链表相交
public class GetIntersectionNodeTest {
    //比较简单的思路是利用hashmap找到A B中完全一致的节点

    //如果保证O(1)空间复杂度，
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode one = headA;
        ListNode two = headB;

        //如果链表不相交，那么都遍历了A和B，最终在A==B==null处跳出
        while (one != two) {
            one = one == null ? headB : one.next;
            two = two == null ? headA : two.next;

        }
        return one;
    }
}
