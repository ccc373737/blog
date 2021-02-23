package com.ccc.fizz.al.queue;

import java.util.LinkedList;
import java.util.Queue;

//346. 数据流中的移动平均值
//给定一个整数数据流和一个窗口大小，根据该滑动窗口的大小，计算其所有整数的移动平均值。
public class MovingAverage {

    Queue<Integer> queue;
    int size;
    int sum;

    public MovingAverage(int size) {
        queue = new LinkedList<>();
        this.size = size;
        sum = 0;
    }

    public double next(int val) {
        queue.offer(val);
        sum += val;

        if (queue.size() > size) {
            sum -= queue.poll();
        }

        return sum * 1.0 / queue.size();
    }
}
