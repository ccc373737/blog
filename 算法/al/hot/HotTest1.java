package com.ccc.fizz.al.hot;

import java.util.HashMap;
import java.util.Map;

public class HotTest1 {

    //1. 两数之和
    //给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
    //你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                return new int[]{i, map.get(nums[i])};
            } else {
                map.put(target - nums[i], i);
            }
        }

        return null;
    }

    //2. 两数相加
    //给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
    //
    //请你将两个数相加，并以相同形式返回一个表示和的链表。
    //
    //你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
    class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return getNextNum(l1, l2, 0);
    }

    public ListNode getNextNum(ListNode l1, ListNode l2, int carry) {
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }

        int val = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + carry;

        if (val >= 10) {//换算进位
            carry = val / 10;
            val = val % 10;
        } else {
            carry = 0;
        }

        ListNode node = new ListNode(val);
        node.next = getNextNum(l1 == null ? null : l1.next, l2 == null ? null : l2.next, carry);
        return node;
    }

    //19. 删除链表的倒数第 N 个结点
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        ListNode slow = head;
        while (n > 0) {
            slow = slow.next;
            n--;
        }

        if (slow == null) {
            return head.next;
        }

        ListNode pre = null;
        while (slow != null) {
            slow = slow.next;
            pre = fast;
            fast = fast.next;
        }
        pre.next = fast.next;
        return head;
    }

    //4. 寻找两个正序数组的中位数
    //给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int pri = (nums1.length + nums2.length) / 2;

        int index1 = 0;
        int index2 = 0;
        int cur = -1;
        int pre = -1;

        while (pri > 0) {
            pre = cur;

            if (index1 < nums1.length && (index2 >= nums2.length || nums1[index1] < nums2[index2])) {
                cur = nums1[index1++];
            } else {
                cur = nums2[index2++];
            }
            pri--;
        }

        if (((nums1.length + nums2.length) & 1) == 1) {
            return cur;
        } else {
            return (pre + cur) / 2.0;
        }
    }

    //5. 最长回文子串
    public String longestPalindrome(String s) {
        char[] vals = s.toCharArray();

        String result = "";
        int[] paliCount;
        for (int i = 0; i < vals.length; i++) {
            paliCount = getPaliCount(vals, i, 1);

            if (paliCount[1] - paliCount[0] + 1 > result.length()) {
                result = s.substring(paliCount[0], paliCount[1] + 1);
            }

            paliCount = getPaliCount(vals, i, 2);

            if (paliCount[1] - paliCount[0] + 1 > result.length()) {
                result = s.substring(paliCount[0], paliCount[1] + 1);
            }
        }

        return result;
    }

    //stage 0：切左侧 1：切中间 2：切右侧 //切左侧情况可以去掉，某个字符切左侧等于上个字符切右侧
    public int[] getPaliCount(char[] vals, int pri, int stage) {
        int start;
        int end;
        if (stage == 1) {
            start = pri - 1;
            end = pri + 1;
        } else {
            start = pri;
            end = pri + 1;
        }

        if (start < 0 || end >= vals.length || vals[start] != vals[end]) {//不成立
            return new int[]{0, 0};
        }

        while (start - 1 >= 0 && end + 1 < vals.length && vals[start - 1] == vals[end + 1]) {
            start--;
            end++;
        }

        return new int[]{start, end};
    }

    //11. 盛最多水的容器
    public int maxArea(int[] height) {
        int start = 0;
        int end = height.length - 1;
        int result = 0;

        while (start <= end) {
            if (height[start] < height[end]) {
                result = Math.max(result, height[start] * (end - start));
                start++;
            } else {
                result = Math.max(result, height[end] * (end - start));
                end--;
            }
        }
        return result;
    }
}

