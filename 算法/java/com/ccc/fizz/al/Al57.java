package com.ccc.fizz.al;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//给出一个区间的集合，请合并所有重叠的区间。
public class Al57 {
    //输入: intervals = [[1,3],[2,6],[8,10],[15,18]]
    //输出: [[1,6],[8,10],[15,18]]
    //解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (x, y) -> x[0] - y[0]);
        List<int[]> list = new ArrayList<>();

        int[] temp = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= temp[1]) {
                temp = new int[]{temp[0], Math.max(temp[1], intervals[i][1])};
            } else {
                list.add(temp);
                temp = intervals[i];
            }
        }
        list.add(temp);

        return list.toArray(new int[list.size()][]);
    }
}
