package com.ccc.fizz.al;

import java.util.LinkedList;
import java.util.List;

/**
 * 输入一个字符串，打印出该字符串中字符的所有排列。
 *
 *
 * **/
public class Al24 {

    static List<String> AllList = new LinkedList<>();

    static char[] temp;

    public static String[] permutation(String s) {
        temp = s.toCharArray();

        dfs(0);

        String[] fin = new String[AllList.size()];
        for (int i = 0; i < AllList.size(); i++) {
            fin[i] = AllList.get(i);
        }
        return fin;
    }

    public static void dfs(int index) {
        if (index == temp.length) {
            AllList.add(new String(temp));
            System.out.println(new String(temp));
            return;
        }

        for (int i = 0; i < temp.length; i++) {
            dfs(index + 1);
        }
    }

    public static void main(String[] args) {
        permutation("aab");
    }
}
