package com.ccc.fizz.al;
//输入: s = "lrloseumgh", k = 6
//输出: "umghlrlose"
public class Al38 {
    public static void main(String[] args) {
        System.out.println(reverseLeftWords("lrloseumgh", 6));
    }

    public static String reverseLeftWords(String s, int n) {
        return s.substring(n, s.length()) + s.substring(0, n);
    }
}