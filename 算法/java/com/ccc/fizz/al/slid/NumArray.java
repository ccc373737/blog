package com.ccc.fizz.al.slid;

//303. 区域和检索 - 数组不可变
/*给定一个整数数组  nums，求出数组从索引 i 到 j（i ≤ j）范围内元素的总和，包含 i、j 两点。

实现 NumArray 类：

NumArray(int[] nums) 使用数组 nums 初始化对象
int sumRange(int i, int j) 返回数组 nums 从索引 i 到 j（i ≤ j）范围内元素的总和，包含 i、j 两点（也就是 sum(nums[i], nums[i + 1], ... , nums[j])）*/
public class NumArray {

    int[] pre;

    public NumArray(int[] nums) {
        pre = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            pre[i + 1] = pre[i] + nums[i];
        }
    }

    public int sumRange(int i, int j) {
        return pre[j + 1] - pre[i];
    }
}
