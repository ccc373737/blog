package com.ccc.fizz.al.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class NestedIterator implements Iterator<Integer> {
    Deque<NestedInteger> deque;

    public NestedIterator(List<NestedInteger> nestedList) {
        deque = new ArrayDeque<>();
        for (NestedInteger nestedInteger : nestedList) {
            deque.addLast(nestedInteger);
        }
    }

    @Override
    public Integer next() {
        return deque.pollFirst().getInteger();
    }

    @Override
    public boolean hasNext() {
        while (!deque.isEmpty() && !deque.peekFirst().isInteger()) {
            NestedInteger val = deque.pollFirst();
            for (int i = val.getList().size() - 1; i >= 0; i--) {
                deque.addFirst(val.getList().get(i));
            }
        }
        return !deque.isEmpty();
    }
}
