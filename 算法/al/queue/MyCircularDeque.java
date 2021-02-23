package com.ccc.fizz.al.queue;
/*设计实现双端队列。
你的实现需要支持以下操作：

MyCircularDeque(k)：构造函数,双端队列的大小为k。
insertFront()：将一个元素添加到双端队列头部。 如果操作成功返回 true。
insertLast()：将一个元素添加到双端队列尾部。如果操作成功返回 true。
deleteFront()：从双端队列头部删除一个元素。 如果操作成功返回 true。
deleteLast()：从双端队列尾部删除一个元素。如果操作成功返回 true。
getFront()：从双端队列头部获得一个元素。如果双端队列为空，返回 -1。
getRear()：获得双端队列的最后一个元素。 如果双端队列为空，返回 -1。
isEmpty()：检查双端队列是否为空。
isFull()：检查双端队列是否满了。*/
public class MyCircularDeque {

    int[] queue;
    int startIndex;
    int endIndex;
    int size;
    int curSize;

    /** Initialize your data structure here. Set the size of the deque to be k. */
    public MyCircularDeque(int k) {
        queue = new int[k];
        startIndex = 0;
        endIndex = -1;//endIndex起始设置为-1，第一个元素进入后就变为0，数组的初始位置
        size = k;
        curSize = 0;
    }

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    public boolean insertFront(int value) {
        if (isFull()) {
            return false;
        }

        curSize++;
        queue[getIndex(++startIndex)] = value;

        if (curSize == 1) {
            endIndex = startIndex;
        }
        return true;
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    public boolean insertLast(int value) {
        if (isFull()) {
            return false;
        }

        curSize++;
        queue[getIndex(--endIndex)] = value;

        if (curSize == 1) {
            startIndex = endIndex;
        }
        return true;
    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    public boolean deleteFront() {
        if (isEmpty()) {
            return false;
        }

        curSize--;
        startIndex--;
        return true;
    }

    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    public boolean deleteLast() {
        if (isEmpty()) {
            return false;
        }

        curSize--;
        endIndex++;
        return true;
    }

    /** Get the front item from the deque. */
    public int getFront() {
        return isEmpty() ? -1 : queue[getIndex(startIndex)];
    }

    /** Get the last item from the deque. */
    public int getRear() {
        return isEmpty() ? -1 : queue[getIndex(endIndex)];
    }

    /** Checks whether the circular deque is empty or not. */
    public boolean isEmpty() {
        return curSize == 0;
    }

    /** Checks whether the circular deque is full or not. */
    public boolean isFull() {
        return curSize == size;
    }

    public int getIndex(int index) {
        if (index < 0) {
            return Math.abs(index) % size == 0 ? 0 : (size - (Math.abs(index) % size));
        } else {
            return index % size;
        }
    }
}
