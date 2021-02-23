package com.ccc.fizz.al.dynamic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DyTest2 {
    //72. 编辑距离
    /*给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。

    你可以对一个单词进行如下三种操作：

    插入一个字符
    删除一个字符
    替换一个字符*/
    public int minDistance(String word1, String word2) {
        char[] word1Vals = (" " + word1).toCharArray();
        char[] word2Vals = (" " + word2).toCharArray();

        //dp[i][j]表示从字符0-i字符串到0-j字符串变化的最小次数
        int[][] dp = new int[word1Vals.length][word2Vals.length];

        //初始化从0变化到某个字符串，最小次数即为字符串的长度
        for (int i = 1; i < word1Vals.length; i++) {
            dp[i][0] = dp[i - 1][0] + 1;
        }

        for (int j = 1; j < word2Vals.length; j++) {
            dp[0][j] = dp[0][j - 1] + 1;
        }

        for (int i = 1; i < word1Vals.length; i++) {
            for (int j = 1; j < word2Vals.length; j++) {
                if (word1Vals[i] == word2Vals[j]) {
                    //当前字符串相同，即上一次变化的次数
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    //变化 删除或插入的最小次数
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }
        return dp[word1Vals.length - 1][word2Vals.length - 1];
    }

    //718. 最长重复子数组
    //给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。
    public int findLength(int[] A, int[] B) {
        //dp[i][j]表示以i和j结尾的数组的最长公共长度
        int[][] dp = new int[A.length + 1][B.length + 1];

        int result = 0;
        for (int i = 0; i <= A.length; i++) {
            for (int j = 0; j <= B.length; j++) {
                if (i == 0 || j == 0) {//初始化为0
                    dp[i][j] = 0;
                } else if (A[i - 1] == B[j - 1]) {//当前字符相同，+1
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {//不相同时，重置为0
                    dp[i][j] = 0;
                }
                result = Math.max(result, dp[i][j]);
            }
        }
        return result;
    }

    //300. 最长递增子序列
    //给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
    //
    //子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        int result = 1;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }

                result = Math.max(result, dp[i]);
            }
        }
        return result;
    }

    //128. 最长连续序列
    //给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
    //输入：nums = [100,4,200,1,3,2]
    //输出：4
    //解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int val : nums) {
            set.add(val);
        }

        int result = 0;
        for (int val : nums) {
            //不包含val-1时，表示到某个区间的最小值
            if (!set.contains(val - 1)) {
                int current = val;
                int countTemp = 1;

                while (set.contains(current + 1)) {//向上累加 直到区间结束
                    current += 1;
                    countTemp += 1;
                }

                result = Math.max(result, countTemp);
            }
        }
        //由于每次到区间底部才开始计数，总体复杂度为n
        return result;
    }

    //152. 乘积最大子数组
    //给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
    public int maxProduct(int[] nums) {
        long[] maxNums = new long[nums.length];
        long[] minNums = new long[nums.length];

        maxNums[0] = nums[0];
        minNums[0] = nums[0];

        int result = (int) maxNums[0];
        for (int i = 1; i < nums.length; i++) {
            //由于考虑负数，每次都到记录最大值和最小值
            maxNums[i] = Math.max(nums[i], Math.max(maxNums[i - 1] * nums[i], minNums[i - 1] * nums[i]));
            minNums[i] = Math.min(nums[i], Math.min(maxNums[i - 1] * nums[i], minNums[i - 1] * nums[i]));

            result = (int) Math.max(result, maxNums[i]);
        }

        return result;
    }


    //32. 最长有效括号
    //给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
    public int longestValidParentheses(String s) {
        //以某个字符为结尾的最大连续括号长度
        int[] dp  = new int[s.length()];
        char[] vals = s.toCharArray();

        int result = 0;
        for (int i = 1; i < vals.length; i++) {
            if (vals[i] == ')') {//只有出现)时才有长度
                if (vals[i - 1] == '(') {//上一个字符匹配成功，至少有2个单位长度，dp[i] = dp[i - 2] + 2
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] - 1 >= 0 ) {
                    //上一个字符也为)，则判断有上一个匹配开始位置的上一个字符
                    //这个字符为( 上一个匹配基础上+2
                    //这个字符再上个字符为) 这个基础上再加上上上个匹配长度
                    if (vals[i - dp[i - 1] - 1] == '(') {
                        dp[i] = dp[i- 1] + 2;

                        if (i - dp[i - 1] - 2 >= 0 && vals[i - dp[i - 1] - 2] == ')') {
                            dp[i] += dp[i - dp[i - 1] - 2];
                        }
                    }
                }
                result = Math.max(result, dp[i]);
            }
        }

        return result;
    }

    //221. 最大正方形
    //在一个由 '0' 和 '1' 组成的二维矩阵内，找到只包含 '1' 的最大正方形，并返回其面积。
    public int maximalSquare(char[][] matrix) {
        int[][] dp = new int[matrix.length][matrix[0].length];

        int maxSide = 0;
        //初始化
        for (int i = 0; i < matrix.length; i++) {
            dp[i][0] = (int) matrix[i][0] - (int) '0';
            maxSide = Math.max(maxSide, dp[i][0]);
        }

        for (int j = 0; j < matrix[0].length; j++) {
            dp[0][j] =  (int) matrix[0][j] - (int) '0';
            maxSide = Math.max(maxSide, dp[0][j]);
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    //最大变成为左，上，左上正方形边长的最小值 + 1
                    dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i][j-1], dp[i-1][j])) + 1;

                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        return maxSide * maxSide;
    }

    //1143. 最长公共子序列
    //给定两个字符串 text1 和 text2，返回这两个字符串的最长公共子序列的长度。
    //
    //一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
    //例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。两个字符串的「公共子序列」是这两个字符串所共同拥有的子序列。
    //
    //若这两个字符串没有公共子序列，则返回 0。
    public int longestCommonSubsequence(String text1, String text2) {
        //定义dp[i][j]为str1 0-i的字符和str2 0-j的字符的最大公共子序列
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];

        char[] vals1 = text1.toCharArray();
        char[] vals2 = text2.toCharArray();

        for (int i = 0; i <= vals1.length; i++) {
            for (int j = 0; j <= vals2.length; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else if (vals1[i - 1] == vals2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[text1.length()][text2.length()];
    }

    //198. 打家劫舍
    //你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
    //给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
    public int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int one = 0;
        int two = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int temp = Math.max(two, one + nums[i]);
            one = two;
            two = temp;
        }

        return two;
    }

    //354. 俄罗斯套娃信封问题
    //给定一些标记了宽度和高度的信封，宽度和高度以整数对形式 (w, h) 出现。当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
    //请计算最多能有多少个信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
    //按照宽度排序后，问题变为求最大连续子序列的问题，注意宽度和高度都要严格递增
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, (x,y) -> x[0] == y[0] ? x[1] - y[1] : x[0] - y[0]);

        int count = envelopes.length;
        int dp[] = new int[count];
        Arrays.fill(dp, 1);

        int result = 0;
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < i; j++) {
                //由于排序中有相等的情况，这里需要做严格递增的判断
                if (envelopes[i][0] > envelopes[j][0] && envelopes[i][1] > envelopes[j][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            result = Math.max(result, dp[i]);
        }
        return result;
    }

}
