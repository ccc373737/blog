package com.ccc.fizz.al;

import java.util.PriorityQueue;

/**
 * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
 *
 * **/
public class MedianFinder {

    PriorityQueue<Integer> bigOrderQueue;

    PriorityQueue<Integer> smallOrderQueue;

    int count;

    public MedianFinder() {
        smallOrderQueue = new PriorityQueue<>();
        bigOrderQueue = new PriorityQueue<>((num1, num2) -> (num2 - num1));
        count = 0;
    }

    public void addNum(int num) {
        count++;
        //奇数大项堆，偶数小项堆
        if ((count & 1) == 1) {
            smallOrderQueue.offer(num);
            bigOrderQueue.offer(smallOrderQueue.poll());
        } else {
            bigOrderQueue.offer(num);
            smallOrderQueue.offer(bigOrderQueue.poll());
        }
    }

    public double findMedian() {
        return (count & 1) == 1 ? bigOrderQueue.peek() : new Double(smallOrderQueue.peek() + bigOrderQueue.peek()) / 2;
    }
}
