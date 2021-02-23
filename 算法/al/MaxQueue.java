package com.ccc.fizz.al;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

class MaxQueue {

    Queue<Integer> queue;

    Deque<Integer> maxQueue;

    public MaxQueue() {
        queue = new LinkedList<>();
        maxQueue = new ArrayDeque<>();
    }

    public int max_value() {
        if (queue.isEmpty()) {
            return -1;
        }

        return maxQueue.peek();
    }

    public void push_back(int value) {
        queue.offer(value);

        while (!maxQueue.isEmpty() && maxQueue.getLast() < value) {
            maxQueue.removeLast();
        };
        maxQueue.add(value);
    }

    public int pop_front() {
        if (queue.isEmpty()) {
            return -1;
        }

        Integer poll = queue.poll();
        if (poll.equals(maxQueue.peek())) {
            maxQueue.poll();
        }
        return poll;
    }
}
