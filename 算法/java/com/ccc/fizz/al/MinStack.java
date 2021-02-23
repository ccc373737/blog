package com.ccc.fizz.al;

import java.util.Stack;

public class MinStack {
    Stack<Integer> current;

    Stack<Integer> min;

    public MinStack() {
        current = new Stack<>();
        min = new Stack<>();
    }

    public void push(int x) {
        current.push(x);
        if (min.isEmpty() || min.peek() >= x) {
            min.push(x);
        }
    }

    public void pop() {
        if (min.peek().equals(current.pop())) {
            min.pop();
        }
    }

    public int top() {
        return current.peek();
    }

    public int min() {
        return min.peek();
    }
}
