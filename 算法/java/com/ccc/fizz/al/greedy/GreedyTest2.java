package com.ccc.fizz.al.greedy;

import java.util.*;

public class GreedyTest2 {
    //860. 柠檬水找零
    //在柠檬水摊上，每一杯柠檬水的售价为 5 美元。
    //
    //顾客排队购买你的产品，（按账单 bills 支付的顺序）一次购买一杯。
    //
    //每位顾客只买一杯柠檬水，然后向你付 5 美元、10 美元或 20 美元。你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 5 美元。
    //
    //注意，一开始你手头没有任何零钱。
    //
    //如果你能给每位顾客正确找零，返回 true ，否则返回 false 。
    public boolean lemonadeChange(int[] bills) {
        int fiveCount = 0;
        int tenCount = 0;
        for (int bill : bills) {
            if (bill == 5) {
                fiveCount++;
            } else if (bill == 10) {
                tenCount++;
                if (fiveCount == 0) {
                    return false;
                }
                fiveCount--;
            } else if (bill == 20) {
                if (tenCount > 0 && fiveCount > 0) {
                    tenCount--;
                    fiveCount--;
                } else if (tenCount == 0 && fiveCount >= 3) {
                    fiveCount -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    //406. 根据身高重建队列
    //假设有打乱顺序的一群人站成一个队列，数组 people 表示队列中一些人的属性（不一定按顺序）。每个 people[i] = [hi, ki] 表示第 i 个人的身高为 hi ，前面 正好 有 ki 个身高大于或等于 hi 的人。
    //
    //请你重新构造并返回输入数组 people 所表示的队列。返回的队列应该格式化为数组 queue ，其中 queue[j] = [hj, kj] 是队列中第 j 个人的属性（queue[0] 是排在队列前面的人）。
    //先按照身高倒序排，保证高个在前，在根据j去插队，因为之前的升高一定高，直接查到j位置即可
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }  else {
                    return o2[0] - o1[0];
                }
            }
        });

        for (int i = 1; i < people.length; i++) {
            int[] temp = people[i];
            for (int j = i; j > temp[1]; j--) {
                people[j] = people[j - 1];
            }
            people[temp[1]] = temp;
        }
        return people;
    }


    public int[][] reconstructQueue1(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }  else {
                    return o2[0] - o1[0];
                }
            }
        });

        //使用链表加速
        LinkedList<int[]> list = new LinkedList();
        for (int[] p : people) {
            list.add(p[1], p);
        }
        return list.toArray(new int[people.length][2]);
    }

    //452. 用最少数量的箭引爆气球
    //在二维空间中有许多球形的气球。对于每个气球，提供的输入是水平方向上，气球直径的开始和结束坐标。由于它是水平的，所以纵坐标并不重要，因此只要知道开始和结束的横坐标就足够了。开始坐标总是小于结束坐标。
    //
    //一支弓箭可以沿着 x 轴从不同点完全垂直地射出。在坐标 x 处射出一支箭，若有一个气球的直径的开始和结束坐标为 xstart，xend， 且满足  xstart ≤ x ≤ xend，则该气球会被引爆。可以射出的弓箭的数量没有限制。 弓箭一旦被射出之后，可以无限地前进。我们想找到使得所有气球全部被引爆，所需的弓箭的最小数量。
    //
    //给你一个数组 points ，其中 points [i] = [xstart,xend] ，返回引爆所有气球所必须射出的最小弓箭数。
    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) {
            return 0;
        }

        Arrays.sort(points, (x,y) -> x[0] > y[0] ? 1 : -1);

        int result = 1;
        int[] min = points[0];
        for (int i = 1; i < points.length; i++) {
            if (min[1] >= points[i][0]) {
                min[1] = Math.min(min[1], points[i][1]);
            } else {
                result++;
                min = points[i];
            }
        }
        return result;
    }

    //435. 无重叠区间
    //给定一个区间的集合，找到需要移除区间的最小数量，使剩余区间互不重叠。
    //
    //注意:
    //
    //可以认为区间的终点总是大于它的起点。
    //区间 [1,2] 和 [2,3] 的边界相互“接触”，但没有相互重叠。
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) {
            return 0;
        }

        Arrays.sort(intervals, (x,y) -> x[0] > y[0] ? 1 : -1);

        int result = 1;
        int[] min = intervals[0];

        for (int i = 1; i < intervals.length; i++) {
            if (min[1] > intervals[i][0]) {
                min[1] = Math.min(min[1], intervals[i][1]);
            } else {
                result++;//找非重叠区间
                min = intervals[i];
            }
        }

        return intervals.length - result;
    }

    //56. 合并区间
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (x,y) -> x[0] - y[0]);

        List<int[]> result = new ArrayList<>();
        int[] pre = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            if (pre[1] >= intervals[i][0]) {
                pre = new int[]{pre[0], Math.max(pre[1], intervals[i][1])};
            } else {
                result.add(pre);
                pre = intervals[i];
            }
        }
        result.add(pre);

        return result.toArray(new int[result.size()][2]);
    }

    //763. 划分字母区间
    //字符串 S 由小写字母组成。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。返回一个表示每个字符串片段的长度的列表。
    //1.记录每个字符出现的最后位置
    //2.遍历字符串，当某个字符位置为 之前所有字符出现的最大值，表示这一段区域只有满足条件
    public List<Integer> partitionLabels(String S) {
        int[] index = new int[26];
        char[] vals = S.toCharArray();

        for (int i = 0; i < vals.length; i++) {
            //记录每个字符出现的最后位置
            index[(int) vals[i] - (int) 'a'] = Math.max(index[(int) vals[i] - (int) 'a'], i);
        }

        List<Integer> list = new ArrayList<>();
        int maxTemp = 0;
        int start = -1;//设置初始为-1，方便减
        for (int i = 0; i < vals.length; i++) {
            maxTemp = Math.max(maxTemp, index[(int) vals[i] - (int) 'a']);

            if (maxTemp == i) {//符合条件
                list.add(i - start);
                start = i ;
            }
        }
        return list;
    }
}
