package com.ccc.fizz.al.greedy;

import java.util.Arrays;

public class GreedyTest1 {
    //455. 分发饼干
    //假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。
    //
    //对每个孩子 i，都有一个胃口值 g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j，都有一个尺寸 s[j] 。如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满足。你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);

        int result = 0;
        int gIndex = 0;
        int sIndex = 0;
        while (gIndex < g.length && sIndex < s.length) {
            if (g[gIndex] <= s[sIndex]) {
                result++;
                gIndex++;
            }
            sIndex++;
        }
        return result;
    }

    //376. 摆动序列
    //如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为摆动序列。第一个差（如果存在的话）可能是正数或负数。少于两个元素的序列也是摆动序列。
    //
    //例如， [1,7,4,9,2,5] 是一个摆动序列，因为差值 (6,-3,5,-7,3) 是正负交替出现的。相反, [1,4,7,2,5] 和 [1,7,4,5,5] 不是摆动序列，第一个序列是因为它的前两个差值都是正数，第二个序列是因为它的最后一个差值为零。
    //
    //给定一个整数序列，返回作为摆动序列的最长子序列的长度。 通过从原始序列中删除一些（也可以不删除）元素来获得子序列，剩下的元素保持其原始顺序。
    public int wiggleMaxLength(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }

        int result = 1;
        int pre = 0;//初始为0，相等于加一个相等的头节点
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                continue;
            }

            int cur = nums[i] > nums[i - 1] ? 1 : -1;//上升或下降两种状态

            if (cur != pre) {//如果与上一次差值不一致，result+1
                result++;
            }

            pre = cur;//更新为pre
        }
        return result;
    }

    //动态规划1
    public int wiggleMaxLengthD1(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }
        //由于存在结尾时递减或递增两种情况，则需要两个dp
        int[] up = new int[nums.length];
        int[] down = new int[nums.length];
        //初始为1
        up[0] = 1;
        down[0] = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {//递增情况
                down[i] = down[i - 1];//递减序列长度不变
                up[i] = down[i - 1] + 1;//递增长度+1
            } else if (nums[i] < nums[i - 1]) {//递减情况
                up[i] = up[i - 1];
                down[i] = up[i - 1] + 1;
            } else {//相等情况，两个序列长度都不变
                up[i] = up[i - 1];
                down[i] = down[i - 1];
            }
        }
        return Math.max(up[nums.length - 1], down[nums.length - 1]);
    }

    //动态规划2，由于数组中状态只和上一个有关，可以进行状态压缩
    public int wiggleMaxLengthD2(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }

        int up = 1, down = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                up = down + 1;
            } else if (nums[i] < nums[i - 1]) {//递减情况
                down = up + 1;
            }
        }
        return Math.max(up, down);
    }

    //53. 最大子序和
    //给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
    //当某段前序列和出现负数，则可以从头开始计算
    public int maxSubArray(int[] nums) {
        int result = Integer.MIN_VALUE;
        int pre = 0;
        for (int i = 0; i < nums.length; i++) {
            pre += nums[i];
            //比较当前和最大值
            result = Math.max(result, pre);
            //  如果和小于等于0，表示之前序列起到负作用，则抛弃之前累计的和从头开始计算
            if (pre <= 0) {
                pre = 0;
            }
        }
        return result;
    }

    //122. 买卖股票的最佳时机 II
    //给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
    //
    //设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
    //
    //注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
    public int maxProfit(int[] prices) {
        int result = 0;
        for (int i = 1; i < prices.length; i++) {
            int diff = prices[i] - prices[i - 1];
            result += diff > 0 ? diff : 0;
        }
        return result;
    }

    //55. 跳跃游戏
    //给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
    //数组中的每个元素代表你在该位置可以跳跃的最大长度。
    //判断你是否能够到达最后一个下标。
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        int len = nums[0];
        for (int i = 0; i <= len; i++) {//不断扩大边界，即之前的位置全部可以到达
            len = Math.max(len, i + nums[i]);
            if (len >= nums.length - 1) {//直到某一次超过边界，满足条件
                return true;
            }
        }
        return false;//遍历到最后没有跳出返回false
    }

    //45. 跳跃游戏 II
    //给定一个非负整数数组，你最初位于数组的第一个位置。
    //
    //数组中的每个元素代表你在该位置可以跳跃的最大长度。
    //
    //你的目标是使用最少的跳跃次数到达数组的最后一个位置。
    public int jump(int[] nums) {
        int result = 0;
        int max = 0;
        int nextMax = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            nextMax = Math.max(nextMax, i + nums[i]);
            if (i >= max) {
                result++;
                max = nextMax;
            }
        }
        return result - 1;
    }

    //
    public int largestSumAfterKNegations(int[] A, int K) {
        Arrays.sort(A);
        for (int i = 0; i < A.length; i++) {
            if (K > 0 && A[i] <= 0) {
                K--;
                A[i] *= -1;
            } else {
                break;
            }
        }

        Arrays.sort(A);
        if ((K & 1) == 1) {//奇数
            A[0] *= -1;
        }

        int result = 0;
        for (int val : A) {
            result += val;
        }
        return result;
    }

    //134. 加油站
    public int canCompleteCircuit(int[] gas, int[] cost) {
        for (int i = 0; i < gas.length; i++) {
            int count = 0;
            int thG = 0;
            int index = i;
            while (count < gas.length && thG >= 0) {
                thG += gas[index] - cost[index];
                count++;
                index = (index + 1) % gas.length;
            }

            if (thG >= 0) {
                return i;
            }
        }
        return -1;
    }

    //贪心解法
    //开始位置必不可能在负区间，必然是负区间的下一个正数
    public int canCompleteCircuit1(int[] gas, int[] cost) {
        int result = 0;
        int sum = 0;
        int cur = 0;
        for (int i = 0; i < gas.length; i++) {
            sum += gas[i] - cost[i];
            cur += gas[i] - cost[i];
            if (cur < 0) {
                result = i + 1;
                cur = 0;
            }
        }

        return sum < 0 ? -1 : result;
    }

    //135. 分发糖果
    //老师想给孩子们分发糖果，有 N 个孩子站成了一条直线，老师会根据每个孩子的表现，预先给他们评分。
    //
    //你需要按照以下要求，帮助老师给这些孩子分发糖果：
    //
    //每个孩子至少分配到 1 个糖果。
    //评分更高的孩子必须比他两侧的邻位孩子获得更多的糖果。
    //那么这样下来，老师至少需要准备多少颗糖果呢？
    //两次遍历 分别确认当前位置比左右位置大
    public int candy(int[] ratings) {
        int[] cur = new int[ratings.length];
        Arrays.fill(cur, 1);

        //从前向后遍历，后一位要比当前大
        for (int i = 0; i < ratings.length - 1; i++) {
            if (ratings[i + 1] > ratings[i]) {
                cur[i + 1] = cur[i] + 1;
            }
        }

        //从后向前遍历，前一位比当前大，需要判断上一次遍历的值的这次+1的值，取最大值
        for (int i = ratings.length - 1; i >= 1; i--) {
            if (ratings[i - 1] > ratings[i]) {
                cur[i - 1] = Math.max(cur[i - 1], cur[i] + 1);
            }
        }

        int result = 0;
        for (int val : cur) {
            result += val;
        }
        return result;
    }

    //738. 单调递增的数字
    //给定一个非负整数 N，找出小于或等于 N 的最大的整数，同时这个整数需要满足其各个位数上的数字是单调递增。
    public int monotoneIncreasingDigits(int N) {
        char[] vals = (N + "").toCharArray();

        int start = vals.length;
        for (int i = vals.length - 1; i > 0; i--) {
            if (vals[i - 1] > vals[i]) {//出现不符合情况 前一位大于后一位
                start = i;//记录出现的位置 这些位置需要变为9
                vals[i - 1]--;//上一个位置需要减1
            }
        }

        for (int i = start; i < vals.length; i++) {
            vals[i] = '9';
        }

        return Integer.valueOf(new String(vals));
    }
}
