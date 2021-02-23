package com.ccc.fizz.al.linkedlist;

//多级双向链表中，除了指向下一个节点和前一个节点指针之外，它还有一个子链表指针，可能指向单独的双向链表。这些子列表也可能会有一个或多个自己的子项，依此类推，生成多级数据结构，如下面的示例所示。
//给你位于列表第一级的头节点，请你扁平化列表，使所有结点出现在单级双链表中。
public class FlattenTest {
    public static void main(String[] args) {
        Node root = new Node(1);
        root.next = new Node(2, new Node(4));
        root.child = new Node(3);

        flatten(root);
    }

    static class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    static Node pre;

    //即把整个链表想成一个二叉树，用前序遍历不断改变当前节点和之前节点的关系
    public static Node flatten(Node head) {
        pre = null;
        setNode(head);
        return head;
    }

    public static void setNode(Node root) {
        if (root == null) {
            return;
        }
        //注意这里由于是一个双向链表
        //  1
        // / \
        //3   2
        //改变之后1和3相互指向，那么原来next就丢失了，所以需要一个节点保存next
        Node next = root.next;
        if (pre != null) {
            root.prev = pre;
            pre.next = root;
        }

        pre = root;

        setNode(root.child);
        root.child = null;
        setNode(next);
    }
}
