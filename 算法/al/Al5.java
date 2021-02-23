package com.ccc.fizz.al;

import java.util.*;

/**
 * 根据二叉树的前序遍历和中序遍历的结果，重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字
 *
 * pre：3 9 20 15 7
 * ino：9 3 15 20 7
 *
 *    3
 *  9   20
 *     15 7
 * **/
public class Al5 {

    public static void main(String[] args) {

    }

    public BinaryTreeNode build(Integer[] pre, Integer[] ino) {
        //边界条件
        if (pre.length == 1 && ino.length == 1) {
            return new BinaryTreeNode(pre[0]);
        }

        List<Integer> newLeftIno = new ArrayList<>();
        List<Integer> newRightIno = new ArrayList<>();
        //前序第一个为分隔节点
        int mid = pre[0];

        //前半部分
        int index = 0;
        for (int i = 0; i < ino.length; i++) {
            if (mid == ino[i]) {
                index = i;
                break;
            }
            newLeftIno.add(ino[i]);
        }

        //后半部分
        for (int i = index + 1; i < ino.length; i++) {
            newRightIno.add(ino[i]);
        }

        List<Integer> newLeftPre = new ArrayList<>();
        List<Integer> newRightPre = new ArrayList<>();

        for (int i = 1; i < newLeftIno.size(); i++) {
            newLeftPre.add(pre[i]);
            index = i;
        }

        for (int i = index + 1; i < newRightIno.size(); i++) {
            newRightPre.add(pre[i]);
        }

        return new BinaryTreeNode(build(newLeftPre.toArray(new Integer[newLeftPre.size()]), newLeftIno.toArray(new Integer[newLeftIno.size()])),
                                  mid,
                                  build(newRightPre.toArray(new Integer[newRightPre.size()]), newRightIno.toArray(new Integer[newRightIno.size()])));
    }
}
