package com.ccc.fizz.al;

import java.util.Stack;

/**
 * 请实现一个函数用来匹配包含'. '和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（含0次）。在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但与"aa.a"和"ab*a"均不匹配。
 *
 * 输入:
 * s = "mississippi"
 * p = "mis*is*p*."
 * 输出: false
**/
public class Al14 {
    public static void main(String[] args) {
        System.out.println(isMatch("ab", ".*c", 0,0));
    }

    //1.下一位不为*，匹配s[i] == p[i]
    //2.下一位为*，
    // 2.1 出现0次，ss指针不动，pp指针移2，(s,p,ss,pp+2)
    // 2.2 出现1次，ss指针移1，pp指针移2，(s,p,ss+1,pp+2)
    // 2.3 出现多次，ss指针移1，pp指针不动，(s,p,ss+1,pp)
    // 三种情况成立一种即为成功
    public static boolean isMatch(String s, String p, int ssIndex, int ppIndex) {
        //p串结束而s串未结束
        if (ssIndex < s.length() && ppIndex >= p.length()) {
            return false;
        }

        //p串和s串都结束了
        if (ssIndex >= s.length() && ppIndex >= p.length()) {
            return true;
        }

        //存在下一位且下一位为*
        if (ppIndex + 1 < p.length() && p.charAt(ppIndex + 1) == '*') {
            return isMatch(s, p, ssIndex, ppIndex + 2) ||
                    (com(s, p, ssIndex, ppIndex)) && isMatch(s, p, ssIndex + 1, ppIndex + 2) ||
                    (com(s, p, ssIndex, ppIndex) && isMatch(s, p ,ssIndex + 1, ppIndex));
        //不存在下一位或下一位不为*
        } else {
            if (com(s, p, ssIndex, ppIndex)) {
                return isMatch(s, p, ssIndex + 1, ppIndex + 1);
            } else {
                return false;
            }
        }
    }

    public static boolean com(String s, String p, int ssIndex, int ppIndex) {
        return ssIndex < s.length() && (s.charAt(ssIndex) == p.charAt(ppIndex) || p.charAt(ppIndex) == '.');
    }

    public boolean isMatch(String s, String p) {
        if (p.length() == 0) {
            return s.length() == 0;
        }
        if (p.length() > 1 && p.charAt(1) == '*') {
            // p的第二个字符是 '*'
            //1,字符"*"把前面的字符消掉，也就是匹配0次
            //2,字符"*"匹配1次或多次
            return isMatch(s, p.substring(2)) || (s.length() > 0 && comp(s, p)) && isMatch(s.substring(1), p);
        } else {
            // p的第二个字符不是 '*'，判断首字符是否相同，如果相同再从第二位继续比较
            return s.length() > 0 && comp(s, p) && (isMatch(s.substring(1), p.substring(1)));
        }
    }

    //比较s的首字符和p的首字符是否匹配
    private boolean comp(String s, String p) {
        return s.charAt(0) == p.charAt(0) || p.charAt(0) == '.';
    }
}
