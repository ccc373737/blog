package com.ccc.fizz.al;

/**
 * 输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。例如输入字符串"I am a student. "，则输出"student. a am I"。
 *
 * **/
public class Al40 {
    public static void main(String[] args) {
        System.out.println(reverseWords("a good   example"));
    }

    public static String reverseWords(String s) {
        int start = s.length() - 1;
        int end = s.length() - 1;

        s.trim();
        StringBuffer sb = new StringBuffer();
        while (start >= 0) {
            while (start >= 0 && s.charAt(start) != ' ') {
                start--;
            }
            sb.append(s.substring(start + 1, end + 1) + " ");

            while (start >= 0 && s.charAt(start) == ' ') {
                start--;
            }
            end = start;
        }

        return sb.toString().trim();
    }
}
