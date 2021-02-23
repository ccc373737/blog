package com.ccc.fizz.al;

/**
 * 给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。
 *
 * 返回删除后的链表的头节点。
 * **/
public class Al13 {

    public static Node deleteOne(Node node, int val) {
        if (node.getValue() == val) {
            return node.getNext();
        }

        Node curr = node;

        while (curr.getNext() != null && curr.getNext().getValue() != val) {
            curr = curr.getNext();

        }
        curr.setNext(curr.getNext().getNext());

        return node;
    }
}
