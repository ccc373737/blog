package com.ccc.fizz.al;

public class Al4 {

    public static void main(String[] args) {
        Node node = convert(new Node(1,new Node(2,new Node(3,new Node(4, new Node(5,new Node(6)))))), 4);
        System.out.println(node.toString());
    }

    public static Node convert(Node node, int step) {
        //判断节点
        Node judge = node;

        Node head = node;
        Node newOne = null;

        for (int i = 0; i < step; i++) {

            //递归边界条件
            if (judge == null) {
                return node;
            }
            judge = judge.getNext();
        }

        for (int i = 0; i < step; i++) {
            Node temp = head.getNext();

            head.setNext(newOne);
            newOne = head;

            head = temp;
        }

        return concat(newOne, convert(head, step));
    }

    public static Node concat(Node first, Node second) {
        Node temp = first;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(second);
        return first;
    }
}

