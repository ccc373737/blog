package com.ccc.fizz.al;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * 请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，每个节点除了有一个 next 指针指向下一个节点，还有一个 random 指针指向链表中的任意节点或者 null。
 *
 * **/
public class Al19 {

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

    Map<Node, Node> map = new HashMap<>();

    Deque<Node> deque = new ArrayDeque<>();

    public Node copyRandomList(Node head) {
        return dfs(head);
    }

    public Node dfs(Node root) {
        if (root == null) {
            return null;
        }

        if (map.get(root) != null) {
            return map.get(root);
        }

        Node copy = new Node(root.val);
        map.put(root, copy);

        copy.next = dfs(root.next);
        copy.random = dfs(root.random);

        return copy;
    }

    public Node bfs(Node root) {
        if (root == null) {
            return null;
        }

        Node head = new Node(root.val);
        map.put(root, head);
        deque.add(root);

        while (!deque.isEmpty()) {
            Node node = deque.pop();

            if (node.next != null && map.get(node.next) == null) {
                map.put(node.next, new Node(node.next.val));
                deque.add(node.next);
            }

            if (node.random != null && map.get(node.random) == null) {
                map.put(node.random, new Node(node.random.val));
                deque.add(node.random);
            }

            map.get(node).next = map.get(node.next);
            map.get(node).random = map.get(node.random);
        }

        return head;
    }
}
