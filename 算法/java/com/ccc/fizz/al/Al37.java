package com.ccc.fizz.al;

import java.util.Stack;

/**
 * 输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。例如输入字符串"I am a student. "，则输出"student. a am I"。
 * **/
public class Al37 {
    public static void main(String[] args) {
        System.out.println(reverseWords("the sky is blue"));
    }

    public static String reverseWords(String s) {
        char[] arr = s.toCharArray();
        Stack<Character> stack = new Stack<>();

        boolean lastBlank = true;
        for (int i = 0; i < arr.length; i++) {
            if (lastBlank) {
                if (!(arr[i] == ' ')) {
                    stack.push(arr[i]);
                    lastBlank = false;
                }
            } else {
                if (!(arr[i] == ' ' && arr[i - 1] == ' ')) {
                    stack.push(arr[i]);
                }
            }
        }

        StringBuffer sb = new StringBuffer();
        boolean firstBlank = true;

        while (!stack.isEmpty()) {
            Character temp = stack.pop();
            if (firstBlank) {
                if (!temp.equals(' ')) {
                    sb.append(temp);
                    firstBlank = false;
                }
            } else {
                sb.append(temp);
            }
        }
        return sb.toString();
    }
}
