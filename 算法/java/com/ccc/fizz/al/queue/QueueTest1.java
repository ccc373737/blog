package com.ccc.fizz.al.queue;

import java.util.*;

public class QueueTest1 {
    //621. 任务调度器
    /*给你一个用字符数组 tasks 表示的 CPU 需要执行的任务列表。其中每个字母表示一种不同种类的任务。任务可以以任意顺序执行，并且每个任务都可以在 1 个单位时间内执行完。在任何一个单位时间，CPU 可以完成一个任务，或者处于待命状态。

      然而，两个 相同种类 的任务之间必须有长度为整数 n 的冷却时间，因此至少有连续 n 个单位时间内 CPU 在执行不同的任务，或者在待命状态。

      你需要计算完成所有任务所需要的 最短时间 。*/
    public int leastInterval(char[] tasks, int n) {
        int maxCount = 0;
        int equalsCount = 0;
        int[] everyCount = new int[26];

        for (char val : tasks) {
            int index = (int) val - (int) 'A';
            everyCount[index]++;

            if (everyCount[index] > maxCount) {
                maxCount = everyCount[index];
                equalsCount = 0;
            } else if (everyCount[index] == maxCount) {
                equalsCount++;
            }
        }
        return Math.max(tasks.length, (n + 1) * (maxCount - 1) + 1 + equalsCount);
    }

    //剑指 Offer 59 - I. 滑动窗口的最大值
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0 || k == 0) {
            return nums;
        }

        Deque<Integer> deque = new ArrayDeque<>(k);
        int[] result = new int[nums.length - k + 1];

        //构造初始
        for (int i = 0; i < k; i++) {
            while (!deque.isEmpty() && nums[i] > deque.peekLast()) {
                deque.removeLast();
            }
            deque.addLast(nums[i]);
        }
        result[0] = deque.peekFirst();

        for (int i = k; i < nums.length; i++) {
            while (!deque.isEmpty() && nums[i] > deque.peekLast()) {
                deque.removeLast();
            }
            deque.addLast(nums[i]);
            //如果头元素为滑动时删除的元素，那么去掉头元素
            if (deque.peekFirst() == nums[i - k]) {
                deque.removeFirst();
            }

            result[i - k + 1] = deque.peekFirst();
        }
        return result;
    }

    //面试题 17.09. 第 k 个数
    //有些数的素因子只有 3，5，7，请设计一个算法找出第 k 个数。注意，不是必须有这些素因子，而是必须不包含其他的素因子。例如，前几个数按顺序应该是 1，3，5，7，9，15，21。
    public int getKthMagicNumber(int k) {
        int[] result = new int[k];
        result[0] = 1;

        int threeIndex = 0;
        int fiveIndex = 0;
        int sevenIndex = 0;
        for (int i = 1; i < k; i++) {
            int one = 3 * result[threeIndex];
            int two = 5 * result[fiveIndex];
            int three = 7 * result[sevenIndex];

            int min = Math.min(Math.min(one, two), three);
            if (min == one) threeIndex++;
            if (min == two) fiveIndex++;
            if (min == three) sevenIndex++;

            result[i] = min;

        }
        return result[k-1];
    }

    //582. 杀死进程
    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < ppid.size(); i++) {
            if (!map.containsKey(ppid.get(i))) {
                map.put(ppid.get(i), new ArrayList<>());
            }
            //加入父进程到所有子进程的映射关系
            map.get(ppid.get(i)).add(pid.get(i));
        }

        List<Integer> result = new ArrayList<>();
        Queue<Integer> dealQueue = new LinkedList<>();
        dealQueue.offer(kill);

        while (!dealQueue.isEmpty()) {
            Integer poll = dealQueue.poll();
            result.add(poll);

            if (map.containsKey(poll)) {
                dealQueue.addAll(map.get(poll));
            }
        }
        return result;
    }
}
