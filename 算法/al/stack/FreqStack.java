package com.ccc.fizz.al.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*实现 FreqStack，模拟类似栈的数据结构的操作的一个类。

FreqStack 有两个函数：

push(int x)，将整数 x 推入栈中。
pop()，它移除并返回栈中出现最频繁的元素。
如果最频繁的元素不只一个，则移除并返回最接近栈顶的元素。*/
//对于最大频率元素可以用map实现，元素 -> 个数
//对于先后顺序的要求可以用分层栈实现，层数编号为元素个数
//575745分为三层
//1层：574
//2层：57
//3层：5
public class FreqStack {

    Map<Integer, Integer> countMap;
    Map<Integer, Stack<Integer>> stackMap;
    int level = 0;

    public FreqStack() {
        countMap = new HashMap<>();
        stackMap = new HashMap<>();
    }

    public void push(int x) {
        int count = countMap.getOrDefault(x, 0) + 1;
        countMap.put(x, count);

        if (!stackMap.containsKey(count)) {
            stackMap.put(count, new Stack<>());
        }
        stackMap.get(count).push(x);

        level = Math.max(level, count);
    }

    public int pop() {
        Integer pop = stackMap.get(level).pop();

        if (stackMap.get(level).isEmpty()) {
            level--;
        }

        countMap.put(pop, countMap.get(pop) - 1);
        return pop;
    }
}
