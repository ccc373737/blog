package com.ccc.fizz.al.linkedlist;

import java.util.HashMap;
import java.util.Map;

//我们用一个由 n 个节点组成的链表来表示输入/输出中的链表。每个节点用一个 [val, random_index] 表示：
//
//val：一个表示 Node.val 的整数。
//random_index：随机指针指向的节点索引（范围从 0 到 n-1）；如果不指向任何节点，则为  null 。
public class CopyRandomListTest {
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        Map<Node, Node> map = new HashMap<>();

        //处理头节点
        Node oldOne = head;
        Node newHead = null;
        Node newOne = null;

        while (oldOne != null) {
            //处理头节点
            if (newHead == null) {
                newHead = new Node(oldOne.val);
                newOne = newHead;
            } else {
                Node temp = new Node(oldOne.val);
                newOne.next = temp;
                newOne = newOne.next;
            }

            map.put(oldOne, newOne);
            oldOne = oldOne.next;
        }

        oldOne = head;
        newOne = newHead;

        while (oldOne != null) {
            newOne.random = map.get(oldOne.random);

            oldOne = oldOne.next;
            newOne = newOne.next;
        }

        return newHead;
    }
}
