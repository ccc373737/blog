package com.ccc.fizz.al;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 给定一个数组 nums 和滑动窗口的大小 k，请找出所有滑动窗口里的最大值。
 *
 * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
 * 输出: [3,3,5,5,6,7]
 * 解释:
 *
 *   滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 *
 * **/
public class Al41 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0 || k == 0) {
            return new int[]{};
        }

        Deque<Integer> deque = new ArrayDeque<>();
        int[] max = new int[nums.length + 1 - k];
        int index = 0;

        //第一组
        for (int i = 0; i < k; i++) {
            while (!deque.isEmpty() && deque.getLast() < nums[i]) {
                deque.removeLast();
            }
            deque.addLast(nums[i]);
        }
        max[index++] = deque.getFirst();

        for (int i = k; i < nums.length; i++) {
            if (deque.getFirst() == nums[i - k]) {
                deque.removeFirst();
            }

            while (!deque.isEmpty() && deque.getLast() < nums[i]) {
                deque.removeLast();
            }
            deque.addLast(nums[i]);

            max[index++] = deque.getFirst();
        }

        return max;
    }
}
