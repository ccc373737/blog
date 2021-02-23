package com.ccc.fizz.al.stack;

import java.util.LinkedList;
import java.util.Queue;

//225. 用队列实现栈使用队列实现栈的下列操作：
//
//push(x) -- 元素 x 入栈
//pop() -- 移除栈顶元素
//top() -- 获取栈顶元素
//empty() -- 返回栈是否为空
//注意:
//
//你只能使用队列的基本操作-- 也就是 push to back, peek/pop from front, size, 和 is empty 这些操作是合法的。
//你所使用的语言也许不支持队列。 你可以使用 list 或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
public class MyStack {
    Queue<Integer> queue;
    /** Initialize your data structure here. */
    public MyStack() {
        queue = new LinkedList<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        queue.offer(x);
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        int count = queue.size() - 1;
        for (int i = 0; i < count; i++) {
            queue.offer(queue.poll());
        }
        return queue.poll();
    }

    /** Get the top element. */
    public int top() {
        int count = queue.size() - 1;
        for (int i = 0; i < count; i++) {
            queue.offer(queue.poll());
        }

        int top = queue.peek();
        queue.offer(queue.poll());
        return top;
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queue.isEmpty();
    }
}
