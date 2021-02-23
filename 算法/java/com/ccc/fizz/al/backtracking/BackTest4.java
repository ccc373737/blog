package com.ccc.fizz.al.backtracking;


public class BackTest4 {

    //60. 排列序列
    //给出集合 [1,2,3,...,n]，其所有元素共有 n! 种排列。给定 n 和 k，返回第 k 个排列。
    StringBuffer sb;
    boolean[] isFind;
    String target;
    int count;
    public String getPermutation(int n, int k) {
        sb = new StringBuffer();
        isFind = new boolean[n + 1];
        target = null;
        count = k;
        premuteBack(n);
        return target;
    }

    public void premuteBack(int n) {
        if (target != null) {
            return;
        }

        if (sb.length() == n) {//每次成功count减1，直到为0的那次就是目标，由于是按照顺序递归，天然有序
            count--;
            if (count == 0) {
                target = sb.toString();
            }

            return;
        }

        for (int i = 1; i <= n; i++) {
            if (isFind[i]) {
                continue;
            }

            sb.append(i);
            isFind[i] = true;

            premuteBack(n);

            sb.deleteCharAt(sb.length() - 1);
            isFind[i] = false;
        }
    }
}
