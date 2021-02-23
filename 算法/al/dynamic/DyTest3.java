package com.ccc.fizz.al.dynamic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DyTest3 {

    public int maxProfit1(int[] prices) {
        int result = 0;
        int min = prices[0];

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > min) {
                result = Math.max(result, prices[i] - min);
            } else {
                min = prices[i];
            }
        }
        return result;
    }


    //123. 买卖股票的最佳时机 III
    //给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
    public int maxProfit(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }

        //定义dp为第i天完成j笔交易当前持股或不持股的最大利润
        //j为0 1 2
        //k为0或1 0为不持股 1为持股
        int[][][] dp = new int[prices.length][3][2];

        dp[0][1][1] = -prices[0];
        dp[0][2][1] = -prices[0];//这种情况虽然不可能 但仍需要设置初始值

        for (int i = 1; i < prices.length; i++) {
            //完成一笔交易时持股状态
            dp[i][1][1] = Math.max(dp[i - 1][1][1], -prices[i]);
            //完成一笔交易时非持股状态
            dp[i][1][0] = Math.max(dp[i - 1][1][0], dp[i - 1][1][1] + prices[i]);
            //完成两笔交易时持股状态
            dp[i][2][1] = Math.max(dp[i - 1][2][1], dp[i - 1][1][0] - prices[i]);
            //完成两笔交易时非持股状态
            dp[i][2][0] = Math.max(dp[i - 1][2][0], dp[i - 1][2][1] + prices[i]);
        }

        return Math.max(0, Math.max(dp[prices.length - 1][1][0], dp[prices.length - 1][2][0]));
    }

    //股票买卖3 状态压缩版
    public int maxProfitSp(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }

        //定义dp为第i天完成j笔交易当前持股或不持股的最大利润
        //j为0 1 2
        //k为0或1 0为不持股 1为持股
        int[][] dp = new int[3][2];

        dp[1][1] = -prices[0];
        dp[2][1] = -prices[0];//这种情况虽然不可能 但仍需要设置初始值

        for (int i = 1; i < prices.length; i++) {
            int i1 = Math.max(dp[1][1], -prices[i]);
            int i2 = Math.max(dp[1][0], dp[1][1] + prices[i]);
            int i3 = Math.max(dp[2][1], dp[1][0] - prices[i]);
            int i4 = Math.max(dp[2][0], dp[2][1] + prices[i]);

            dp[1][1] = i1;
            dp[1][0] = i2;
            dp[2][1] = i3;
            dp[2][0] = i4;
        }

        return Math.max(0, Math.max(dp[1][0], dp[2][0]));
    }

    public int maxProfit4(int k, int[] prices) {
        if (prices.length < 2) {
            return 0;
        }
        //j为交易次数
        //k为0或1 0为不持股 1为持股
        int[][] dp = new int[k + 1][2];

        for (int i = 1; i <= k; i++) {
            dp[i][1] = -prices[0];//对每次持股状态设置初始值
        }

        for (int i = 1; i < prices.length; i++) {
            for (int j = 1; j <= k; j++) {
                dp[j][1] = Math.max(dp[j][1], dp[j-1][0] - prices[i]);
                dp[j][0] = Math.max(dp[j][0], dp[j][1] + prices[i]);
            }
        }

        int result = 0;
        for (int i = 1; i <= k; i++) {
            result = Math.max(result, dp[i][0]);
        }
        return result;
    }

    //714. 买卖股票的最佳时机含手续费
    public int maxProfit(int[] prices, int fee) {
        //第i天时持股或不持股的最大收益 1持股 0不持股
        //由于只和前一天有关 可以状态压缩
        int[][] dp = new int[prices.length][2];
        dp[0][1] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i] - fee);
        }

        return dp[prices.length - 1][0];
    }

    //309. 最佳买卖股票时机含冷冻期
    public int maxProfitFree(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }

        //第i天时有三个状态
        //0:可以买入股票的不持股状态
        //1:持股状态
        //2:不可以买入股票的不持股状态，即冷冻期
        int[][] dp = new int[prices.length][3];
        dp[0][1] = -prices[0];

        //依然可以状态压缩
        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2]);//上一天的状态0或上一天的状态2（将进入解冻状态）
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
            dp[i][2] = dp[i - 1][1] + prices[i];//只能由持股状态卖出转化而来
        }

        return Math.max(dp[prices.length - 1][0], dp[prices.length - 1][2]);
    }

    //120. 三角形最小路径和
    //给定一个三角形 triangle ，找出自顶向下的最小路径和。
    //
    //每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。也就是说，如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
    public int minimumTotal(List<List<Integer>> triangle) {
        //dp[i]为包含当前路径的最小值
        int[] dp = new int[triangle.get(triangle.size() - 1).size()];
        dp[0] = triangle.get(0).get(0);

        for (int i = 1; i < triangle.size(); i++) {
            List<Integer> cur = triangle.get(i);

            //最右侧值只能来自上一行最右侧
            dp[cur.size() - 1] = dp[cur.size() - 2] + cur.get(cur.size() - 1);

            //状态压缩 需要倒序
            for (int j = cur.size() - 2; j >= 1; j--) {
                dp[j] = Math.min(dp[j - 1], dp[j]) + cur.get(j);
            }

            //最左侧值只能来自上一行最左侧
            dp[0] = dp[0] + cur.get(0);

        }

        int result = Integer.MAX_VALUE;
        for (int i = 0; i < dp.length; i++) {
            result = Math.min(result, dp[i]);
        }

        return result;
    }

    //139
    //给定一个非空字符串 s 和一个包含非空单词的列表 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
    //说明：
    //
    //拆分时可以重复使用字典中的单词。
    //你可以假设字典中没有重复的单词。
    public boolean wordBreak(String s, List<String> wordDict) {
        //定义dp为0-i的索引的字符串是否可以拆分出现在字典中
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        Set<String> set = new HashSet<>(wordDict);
        for (int i = 0; i <= s.length(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                //一个字符串能否拆分取决于0-i是否已经成功和i-j是否在字典中
                //如果某次判断成功 表示0-i可以拆分 直接break
                if (dp[j] && set.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }

    //486. 预测赢家
    public boolean PredictTheWinner(int[] nums) {
        Integer[][] mem = new Integer[nums.length][nums.length];
        return getPredictNum(nums, 0 , nums.length - 1, mem) >= 0;
    }

    //区间left-right之间可以取得净胜分
    public int getPredictNum(int[] nums, int left, int right, Integer[][] mem) {
        if (left > right) {
            return 0;
        }

        if (mem[left][right] != null) {
            return mem[left][right];
        }

        int leftChoose = nums[left] - getPredictNum(nums, left + 1, right, mem);
        int rightChoose = nums[right] - getPredictNum(nums, left, right - 1, mem);
        mem[left][right] = Math.max(leftChoose, rightChoose);

        return mem[left][right];
    }

    public boolean PredictTheWinnerDy(int[] nums) {
        int[][] dp = new int[nums.length][nums.length];

        for (int i = 0; i < nums.length; i++) {
            dp[i][i] = nums[i];
        }

        for (int i = nums.length - 2; i >= 0; i--) {
            for (int j = i + 1; j < nums.length; j++) {
                dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
            }
        }

        return dp[0][nums.length - 1] >= 0;
    }

    //474. 一和零
    /*
    给你一个二进制字符串数组 strs 和两个整数 m 和 n 。

    请你找出并返回 strs 的最大子集的大小，该子集中 最多 有 m 个 0 和 n 个 1 。

    如果 x 的所有元素也是 y 的元素，集合 x 是集合 y 的 子集 。*/
    //二维0-1背包问题
    public int findMaxForm(String[] strs, int m, int n) {
        //背包容量
        int[][] dp = new int[m + 1][n + 1];

        int oneCount = 0;
        int zeroCount = 0;
        for (String str : strs) {
            oneCount = 0;
            zeroCount = 0;
            for (char val : str.toCharArray()) {
                if (val == '0') {
                    zeroCount++;
                } else {
                    oneCount++;
                }
            }

            if (zeroCount > m || oneCount > n) {
                continue;
            }

            //倒序遍历背包
            for (int i = m; i >= zeroCount; i--) {
                for (int j = n; j >= oneCount; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - zeroCount][j - oneCount] + 1);
                }
            }
        }

        return dp[m][n];
    }

    //91. 解码方法 一条包含字母 A-Z 的消息通过以下映射
    //给你一个只含数字的 非空 字符串 num ，请计算并返回 解码 方法的 总数 。
    //
    //题目数据保证答案肯定是一个 32 位 的整数。


}
