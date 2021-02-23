package com.ccc.fizz.al.dynamic;

import java.util.Arrays;

public class DyTest1 {

    //746. 使用最小花费爬楼梯
    public int minCostClimbingStairs(int[] cost) {
        int one = 0;
        int two = cost[0];

        for (int i = 1; i < cost.length; i++) {
            int temp = Math.min(one, two) + cost[i];
            one = two;
            two = temp;
        }

        //只有一个时返回第一个元素，大于一个时返回倒数第一个和倒数第二个
        return cost.length == 1 ? two : Math.min(one, two);
    }

    //62. 不同路径
    /*一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）
    机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
    问总共有多少条不同的路径？*/
    public int uniquePaths(int m, int n) {
        int dp[] = new int[n];
        Arrays.fill(dp, 1);

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] += dp[j-1];
            }
        }

        return dp[n - 1];
    }

    //63. 不同路径 II
    //一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
    //
    //机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
    //
    //现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
    public int uniquePathsWithObstacles(int[][] grids) {
        int[][] dp = new int[grids.length][grids[0].length];

        //初始化 到障碍物之前都为1
        for (int i = 0; i < grids.length && grids[i][0] == 0; i++) {
            dp[i][0] = 1;
        }

        for (int j = 0; j < grids[0].length && grids[0][j] == 0; j++) {
            dp[0][j] = 1;
        }

        for (int i = 1; i < grids.length; i++) {
            for (int j = 1; j < grids[0].length; j++) {
                if (grids[i][j] == 1) {//遇到障碍物设置为0
                    dp[i][j] = 0;
                } else {//非障碍物相加
                    dp[i][j] = dp[i][j-1] + dp[i-1][j];
                }
            }
        }

        return dp[grids.length - 1][grids[0].length - 1];
    }

    //343. 整数拆分
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];

        dp[2] = 1;//初始化 整数为2时 只能为1
        for (int i = 3; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                //前半长度拆分和不拆分的情况
                dp[i] = Math.max(dp[i], Math.max((i - j) * j, dp[i - j] * j));
            }
        }
        return dp[n];
    }

    //给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                //左边数量 * 右边数量的所有情况
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }

    //416. 分割等和子集
    //给定一个只包含正整数的非空数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
    public boolean canPartition(int[] vals) {
        int sum = 0;
        for (int val : vals) {
            sum += val;
        }

        if ((sum & 1) == 1) {
            return false;
        }

        //转化为从数组取n个元素，使和为target
        int target = sum / 2;

        //10背包问题
        int[] dp = new int[target + 1];

        for (int i = 0; i < vals.length; i++) {
            for (int j = target; j >= vals[i]; j--) {//滚动数组倒序
                dp[j] = Math.max(dp[j], dp[j - vals[i]] + vals[i]);
            }

            if (dp[target] == target) {
                return true;
            }
        }

        return false;
    }

    //1049. 最后一块石头的重量 II
    //有一堆石头，每块石头的重量都是正整数。
    //
    //每一回合，从中选出任意两块石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
    //如果 x == y，那么两块石头都会被完全粉碎；
    //如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
    //最后，最多只会剩下一块石头。返回此石头最小的可能重量。如果没有石头剩下，就返回 0。
    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for (int val : stones) {
            sum += val;
        }

        //将石头分为两堆 两堆尽量取的接近 使差最小
        int cap = sum / 2;
        int[] dp = new int[cap + 1];
        for (int i = 0; i < stones.length; i++) {
            for (int j = cap; j >= stones[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - stones[i]] + stones[i]);
            }
        }

        return sum - 2 * dp[cap];
    }

    //494. 目标和
    //给定一个非负整数数组，a1, a2, ..., an, 和一个目标数，S。现在你有两个符号 + 和 -。对于数组中的任意一个整数，你都可以从 + 或 -中选择一个符号添加在前面。
    //返回可以使最终数组和为目标数 S 的所有添加符号的方法数。
    //所有正数和为x，所有负数即为sum - x，即x - sum + x = S
    //x = S + sum / 2 求从数组中取n个元素和为x的所有情况
    public int findTargetSumWays(int[] nums, int S) {
        int sum = 0;
        for (int val : nums) {
            sum += val;
        }
        if (S > sum || (((sum + S) % 2) & 1) == 1) {
            return 0;
        }

        int cap = (sum + S) / 2;
        int[] dp = new int[cap + 1];

        dp[0] = 1;//初始化0时默认为1种情况
        for (int i = 0; i < nums.length; i++) {
            for (int j = cap; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];//求和
            }
        }

        return dp[cap];
    }

    //518. 零钱兑换 II
    //给定不同面额的硬币和一个总金额。写出函数来计算可以凑成总金额的硬币组合数。假设每一种面额的硬币有无限个。
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int i = 0; i < coins.length; i++) {
            //组合情况 + 完全背包不限 从前向后遍历
            for (int j = coins[i]; j <= amount; j++) {
                dp[j] += dp[j - coins[i]];
            }
        }
        return dp[amount];
    }

    //377. 组合总和 Ⅳ
    //给定一个由正整数组成且不存在重复数字的数组，找出和为给定目标正整数的组合的个数。
    //nums = [1, 2, 3]
    //target = 4
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;

        //排序情况，需要先遍历容量，因为先遍历物品表示某2只能出现在1之后，3只能出现在2之后
        //排列时前后顺序不同也认为一种情况
        for (int j = 0; j <= target; j++) {
            for (int i = 0; i < nums.length; i++) {
                if (j >= nums[i]) {
                    dp[j] += dp[j - nums[i]];
                }
            }
        }

        return dp[target];
    }

    //322. 零钱兑换
    //给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE - 1);
        dp[0] = 0;

        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j <= amount; j++) {//组合情况
                //求最小个数
                dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);
            }

        }

        return dp[amount] == Integer.MAX_VALUE - 1 ? -1 : dp[amount];
    }

    //279. 完全平方数
    //给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。
    //输入：n = 13
    //输出：2
    //解释：13 = 4 + 9
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE - 1);
        dp[0] = 0;

        for (int i = 0; i * i <= n; i++) {
            for (int j = i * i; j <= n; j++) {
                dp[j] = Math.min(dp[j], dp[j - i * i] + 1);
            }
        }

        return dp[n] == Integer.MAX_VALUE - 1 ? -1 : dp[n];
    }

}
