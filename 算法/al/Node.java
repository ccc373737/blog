package com.ccc.fizz.al;

public class Node {

    private Node next;

    private Integer value;

    public Node(Integer value) {
        this.value = value;
    }

    public Node( Integer value, Node next) {
        this.next = next;
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        Node temp = this;

        while (temp != null) {
            buffer.append(temp.value);
            temp = temp.next;
        }
        return buffer.toString();
    }
}
