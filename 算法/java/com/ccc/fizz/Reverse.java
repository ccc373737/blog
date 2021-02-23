package com.ccc.fizz;

public class Reverse {
    static class Node {
        private Node next;

        private int value;

        public Node(int value, Node next) {
            this.next = next;
            this.value = value;
        }

        public String toP() {
            StringBuffer text = new StringBuffer(value + "");
            Node temp = this;
            while(temp.next != null) {
                temp = temp.next;
                text.append(" -> " + temp.value);
            }
            return text.toString();
        }
    }

    public static void main(String[] args) {
        Node node = new Node(1, new Node(2, new Node(3, new Node(4, null))));

        System.out.println(node.toP());
    }

    public static Node reverse(Node node) {
        return node;
    }
}
