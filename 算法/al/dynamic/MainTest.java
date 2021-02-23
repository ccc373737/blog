package com.ccc.fizz.al.dynamic;

import org.assertj.core.util.Lists;

public class MainTest {
    public static void main(String[] args) {
        DyTest3 test = new DyTest3();

        //[[5,4],[6,4],[6,7],[2,3]]
        System.out.println(test.wordBreak("leetcode", Lists.newArrayList("leet", "code")));
    }
}
