package com.ccc.fizz.al.hot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotTest2 {
    public String longestPalindrome(String s) {
        char[] vals = s.toCharArray();

        String result = "";
        int start = 0;
        int end = 0;
        for (int i = 0; i < vals.length; i++) {
            int len1 = getPaliCount(vals, i, i);
            int len2 = getPaliCount(vals, i, i + 1);

            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    public int getPaliCount(char[] vals, int left, int right) {
       while (left >= 0 && right < vals.length && vals[left] == vals[right]) {
           left--;
           right++;
       }

       return right - left - 1;
    }

    //15. 三数之和
    //给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {//固定一个指针
            //去重和剪枝
            if (nums[i] > 0 || (i >= 1 && nums[i] == nums[i - 1])) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {//双指针扫描
                if (nums[i] + nums[left] + nums[right] > 0) {
                    right--;
                } else if (nums[i] + nums[left] + nums[right] < 0) {
                    left++;
                } else {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(nums[i]);
                    temp.add(nums[left]);
                    temp.add(nums[right]);
                    result.add(temp);

                    //再次去重 前提是已经有一个解
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }

                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                }
            }
        }
        return result;
    }

    //31. 下一个排列
    /*实现获取 下一个排列 的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。

    如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。

    必须 原地 修改，只允许使用额外常数空间。*/
    public void nextPermutation(int[] nums) {
        for (int i = nums.length - 1; i > 0; i--) {
            if (nums[i] > nums[i - 1]) {//寻找第一个降序 需要交换i-1

                for (int j = nums.length - 1; j >= i; j--) {//寻找第一个比i-1大的元素用于交换
                    if (nums[j] > nums[i - 1]) {
                        int temp = nums[i - 1];
                        nums[i - 1] = nums[j];
                        nums[j] = temp;

                        //对交换元素之后元素排序
                        Arrays.sort(nums, i, nums.length);
                        return;
                    }
                }
            }
        }
        Arrays.sort(nums);
    }

    //153. 寻找旋转排序数组中的最小值
    //假设按照升序排序的数组在预先未知的某个点上进行了旋转。例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] 。
    //
    //请找出其中最小的元素。
    //旋转数组核心思路是寻找左右两个升序数组
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] > nums[right]) {//mid当左数组中，目标在右侧，收缩左边界
                left = mid + 1;
            } else if (nums[mid] < nums[right]) {//目标在左侧，收缩右边界
                right = mid;
            }
        }

        return nums[left];
    }

    //154 寻找旋转排序数组中的最小值 II 可以有重复
    public int findMin2(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] > nums[right]) {//mid当左数组中，目标在右侧，收缩左边界
                left = mid + 1;
            } else if (nums[mid] < nums[right]) {//目标在左侧，收缩右边界
                right = mid;
            } else {
                right--;
            }
        }

        return nums[left];
    }

    //33. 搜索旋转排序数组
    //升序排列的整数数组 nums 在预先未知的某个点上进行了旋转（例如， [0,1,2,4,5,6,7] 经旋转后可能变为 [4,5,6,7,0,1,2] ）。
    //
    //请你在数组中搜索 target ，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
    //基于左右不同数组分情况做二分
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[mid] >= nums[right]) {//左数组情况
                if (nums[mid] > target && target >= nums[left]) {//左数组左侧区间
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {//右数组情况
                if (nums[right] >= target && target > nums[mid]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    //34. 在排序数组中查找元素的第一个和最后一个位置
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1, -1};
        if (nums.length == 0) {
            return result;
        }

        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {//寻找左侧
            int mid = left + (right - left) / 2;

            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                right = mid - 1;
            }
        }

        if (left >= nums.length || nums[left] != target) {
            return result;
        }

        result[0] = left;

        left = 0;
        right = nums.length - 1;
        while (left <= right) {//寻找右侧
            int mid = left + (right - left) / 2;

            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        result[1] = right;

        return result;
    }

    //200. 岛屿数量
    public int numIslands(char[][] grid) {
        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    result++;
                    numIslandsDfs(grid, i, j);
                }
            }
        }
        return result;
    }

    public void numIslandsDfs(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return;
        }

        if (grid[i][j] == '1') {
            grid[i][j] = '2';//表示已经遍历过
            numIslandsDfs(grid, i - 1, j);
            numIslandsDfs(grid, i + 1, j);
            numIslandsDfs(grid, i, j - 1);
            numIslandsDfs(grid, i, j + 1);
        }
    }

    //238. 除自身以外数组的乘积
    public int[] productExceptSelf(int[] nums) {
        int leftp = 1;
        int rightp = 1;
        int[] result = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            result[i] = leftp;
            leftp *= nums[i];
        }

        for (int i = nums.length - 1; i >= 0; i--) {
            result[i] *= rightp;
            rightp *= nums[i];
        }

        return result;
    }

    //75. 颜色分类
    //给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
    //此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
    public void sortColors(int[] nums) {
        int zeroIndex = 0;
        int twoIndex = nums.length - 1;
        int i = 0;

        while (i <= twoIndex) {
            if (nums[i] == 0) {
                swap(nums, zeroIndex, i);
                zeroIndex++;
                i++;
            } else if (nums[i] == 1) {
                i++;
            } else {
                swap(nums, twoIndex, i);
                twoIndex--;
            }
        }
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


}
