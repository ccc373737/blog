package com.ccc.fizz.al.stack;

import java.util.Stack;

//设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
//
//push(x) —— 将元素 x 推入栈中。
//pop() —— 删除栈顶的元素。
//top() —— 获取栈顶元素。
//getMin() —— 检索栈中的最小元素。
public class MinStack {
    Stack<Integer> stack;
    Stack<Integer> minStack;
    /** initialize your data structure here. */
    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int x) {
        stack.push(x);

        if (minStack.isEmpty() || x <= minStack.peek()) {
            minStack.push(x);
        }
    }

    public void pop() {
        if (minStack.peek().equals(stack.pop())) {
            minStack.pop();
        }
    }

    public int top(){
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
