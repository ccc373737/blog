package com.ccc.fizz.al.stack;

public class MaxNumberTest {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int[] maxTemp = new int[k];
        for (int i = 0; i <= k; i++) {
            int k1 = i;
            int k2 = k - i;
            if (k1 > nums1.length || k2 > nums2.length) {
                continue;
            }
            //取前k个值，合并，然后比较大小
            int[] merge = merge(getMaxK(nums1, k1), getMaxK(nums2, k2));
            maxTemp = getBigger(maxTemp, 0, merge, 0) ? maxTemp : merge;
        }
        return maxTemp;
    }

    //在数组中取按顺序的最大的k个元素
    public int[] getMaxK(int[] nums, int k) {
        int[] result = new int[k];
        int index = 0;

        for (int i = 0; i < nums.length; i++) {
            while (index > 0 && nums[i] > result[index - 1] && nums.length - i > k - index) {
                index--;
            }

            if (index < k) {
                result[index++] = nums[i];
            }

        }
        return result;
    }

    //归并合并
    public int[] merge(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length + nums2.length];
        int index = 0;

        int i1 = 0;
        int i2 = 0;
        while (i1 < nums1.length && i2 < nums2.length) {
            if (getBigger(nums1, i1, nums2, i2)) {//由于存在重复情况，必须判断到不重复的情况 
                result[index++] = nums1[i1];
                i1++;
            } else {
                result[index++] = nums2[i2];
                i2++;
            }
        }

        while (i1 < nums1.length) {
            result[index++] = nums1[i1++];
        }

        while (i2 < nums2.length) {
            result[index++] = nums2[i2++];
        }
        return result;
    }

    //判断两个数组最大值
    public boolean getBigger(int[] nums1, int i1, int[] nums2, int i2) {
        while (i1 < nums1.length && i2 < nums2.length) {
            if (nums1[i1] == nums2[i2]) {
                i1++;
                i2++;
            } else {
                return nums1[i1] > nums2[i2];
            }
        }
        //true代表i1大，false表示i2大，最后当超出长度时，i2先超出表示i1大
        return i2 >= nums2.length ? true : false;
    }
}
