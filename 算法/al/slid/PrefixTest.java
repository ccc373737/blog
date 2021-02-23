package com.ccc.fizz.al.slid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PrefixTest {

    //485. 最大连续1的个数
    public int findMaxConsecutiveOnes(int[] nums) {
        int result = 0;
        int start = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {//推进窗口
                result = Math.max(result, i - start);
            } else {//收缩到当前位置
                start = i;
            }
        }
        return result;
    }

    //724. 寻找数组的中心索引
    //计算后缀和数组，然后从头开始遍历
    public int pivotIndex(int[] nums) {
        int[] aft = new int[nums.length+1];

        for (int i = nums.length - 1; i >= 0; i--) {
            aft[i] = aft[i + 1] + nums[i];
        }

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (sum == aft[i + 1]) {
                return i;
            }

            sum += nums[i];
        }
        return -1;
    }

    //795. 区间子数组个数
    //前缀和思想模板 sum(R) - sum(L-1)
    public int numSubarrayBoundedMax(int[] A, int L, int R) {
        return getMostCount(A, R) - getMostCount(A, L - 1);
    }

    public int getMostCount(int[] A, int K) {
        int pre = 0;
        int result = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] <= K) {
                pre++;
            } else {
                pre = 0;
            }
            result += pre;
        }
        return result;
    }

    //467. 环绕字符串中唯一的子字符串
    //把字符串 s 看作是“abcdefghijklmnopqrstuvwxyz”的无限环绕字符串，所以 s 看起来是这样的："...zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd....". 
    //
    //现在我们有了另一个字符串 p 。你需要的是找出 s 中有多少个唯一的 p 的非空子串，尤其是当你的输入是字符串 p ，你需要输出字符串 s 中 p 的不同的非空子串的数目。 
    //即寻找连续字符的子数组的数量，注意不能有重复
    public int findSubstringInWraproundString(String p) {
        if (p.isEmpty()) {
            return 0;
        }

        int[] count = new int[26];
        int pre = 1;

        char[] vals = p.toCharArray();
        count[(int) vals[0] - (int) 'a'] = pre;

        for (int i = 1; i < vals.length; i++) {
            if (vals[i] - 1 == vals[i - 1] || vals[i] + 25 == vals[i - 1]) {
                pre++;
            } else {
                pre = 1;
            }

            count[(int) vals[i] - (int) 'a'] = Math.max(pre, count[(int) vals[i] - (int) 'a']);
        }

        int result = 0;
        for (int val : count) {
            result += val;
        }
        return result;
    }

    //560. 和为K的子数组
    //给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。
    //前缀和hash保存，遍历
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int pre = 0;
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            pre += nums[i];
            result += map.getOrDefault(pre - k, 0);
            map.put(pre, map.getOrDefault(pre, 0) + 1);
        }
        return result;
    }

    //992. K 个不同整数的子数组
    //给定一个正整数数组 A，如果 A 的某个子数组中不同整数的个数恰好为 K，则称 A 的这个连续、不一定独立的子数组为好子数组。
    //
    //（例如，[1,2,3,1,2] 中有 3 个不同的整数：1，2，以及 3。）
    //
    //返回 A 中好子数组的数目。
    //即数组中最大为K方法为getMaxK(K)，恰好为k的子数组为getMaxK(K) - getMaxK(K - 1)
    public int subarraysWithKDistinct(int[] A, int K) {
        return getMaxK(A, K) - getMaxK(A, K - 1);
    }

    public int getMaxK(int[] nums, int K) {
        Map<Integer, Integer> map = new HashMap<>();
        int start = 0;
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);

            while (map.size() > K) {
                int count = map.get(nums[start]);
                if (count == 1) {
                    map.remove(nums[start]);
                } else {
                    map.put(nums[start], count - 1);
                }
                start++;
            }

            result += i - start + 1;
        }
        return result;
    }

    //1109. 航班预订统计
    //这里有 n 个航班，它们分别从 1 到 n 进行编号。
    //
    //我们这儿有一份航班预订表，表中第 i 条预订记录 bookings[i] = [j, k, l] 意味着我们在从 j 到 k 的每个航班上预订了 l 个座位。
    //
    //请你返回一个长度为 n 的数组 answer，按航班编号顺序返回每个航班上预订的座位数。
    //
    //输入：bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
    //输出：[10,55,45,25,25]
    //前缀和计算方式
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] result = new int[n];

        for (int[] booking : bookings) {
            result[booking[0] - 1] += booking[2];//起始位+加上相应的座位数，再随手前缀处理中每一轮都会加上

            if (booking[1] < n) {//防数组越界
                result[booking[1]] -= booking[2];//终止位减去相应的座位数
            }

        }

        for (int i = 1; i < result.length; i++) {
            result[i] += result[i - 1];
        }
        return result;
    }

    //1248. 统计「优美子数组」
    //给你一个整数数组 nums 和一个整数 k。
    //
    //如果某个 连续 子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。
    //
    //请返回这个数组中「优美子数组」的数目。
    //计算前缀数组中的奇数个数，当前的奇数个数 - 之前的奇数个数 = k的所有情况
    public int numberOfSubarrays(int[] nums, int k) {
        int[] pre = new int[nums.length + 1];//之前的奇数个数
        pre[0] = 1;//现在设置为0的情况一种
        int cur = 0;//当前的奇数个数
        int result = 0;

        for (int val : nums) {
            cur += ((val & 1) == 1 ? 1 : 0);
            if (cur >= k) {
                result += pre[cur - k];
            }
            pre[cur]++;
        }
        return result;
    }

    //1297. 子串的最大出现次数
    //给你一个字符串 s ，请你返回满足以下条件且出现次数最大的 任意 子串的出现次数：
    //
    //子串中不同字母的数目必须小于等于 maxLetters 。
    //子串的长度必须大于等于 minSize 且小于等于 maxSize 。
    //以minsize为窗口，平移滑动比较所有符合条件的子串
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        Map<String, Integer> map = new HashMap<>();
        Set<Character> set = new HashSet<>();

        for (int i = 0; i <= s.length() - minSize; i++) {
            for (int j = i; j < i + minSize; j++) {
                set.add(s.charAt(j));
            }

            if (set.size() <= maxLetters) {
                String temp = s.substring(i, i + minSize);
                map.put(temp, map.getOrDefault(temp, 0) + 1);
            }
            set.clear();
        }

        int result = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            result = Math.max(result, entry.getValue());
        }

        return result;
    }

    //1658. 将 x 减到 0 的最小操作数
    //再前缀和中求出滑动窗口中总和为sum - k，求出窗口外的最小的size
    public int minOperations(int[] nums, int x) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        int target = sum - x;//窗口内的值
        int result = Integer.MAX_VALUE;
        int pre = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            pre += nums[i];

            if (!map.containsKey(pre)) {//只需要相对靠左的值，靠左的值操作数一定较小
                map.put(pre, i);
            }

            if (map.containsKey(pre - target)) {//找到相对区间
                result = Math.min(result, (nums.length - (i - map.get(pre - target))));
            }
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }
}
