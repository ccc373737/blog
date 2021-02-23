package com.ccc.fizz.al.stack;

import java.util.Stack;

public class Others {
    public boolean isValid(String code) {
        if(!(code.startsWith("<") && code.endsWith(">"))){
            return false;
        }
        char[] chars = code.toCharArray();
        Stack<String> stack = new Stack<>();
        int i = 0;
        while(i < chars.length){
            if(chars[i] != '<'){
                //字符不在闭合标签之内，不合法
                if(stack.isEmpty()){
                    return false;
                }
                i++;
            }else{
                if(i + 1 == chars.length){
                    return false;
                }
                if(chars[i+1] == '!'){//为cdata标签
                    if(stack.isEmpty() || i + 1 + 7 >= chars.length){
                        return false;
                    }
                    if(!code.substring(i,i+9).equals("<![CDATA[")){
                        return false;
                    }
                    int index = code.indexOf("]]>",i+9);
                    if(index < 0){
                        return false;
                    }
                    i = index + 3;
                }else if(chars[i+1] == '/'){//为结束标签
                    //没有起始标签，即不合法
                    if(stack.isEmpty()){
                        return false;
                    }
                    int index = code.indexOf(">",i+2);
                    if(index < 0){
                        return false;
                    }
                    //长度在范围 [1,9] 之外，即不合法
                    if(index - (i+2) < 1 ||  index - (i+2) > 9){
                        return false;
                    }
                    for(int j = i + 2; j < index; j++){
                        //含有非大写字母，不合法
                        if(chars[j] < 'A' || chars[j] > 'Z'){
                            return false;
                        }
                    }
                    //当前结束标签与上一个起始标签不匹配，不合法
                    if(!stack.pop().equals(code.substring(i+2, index))){
                        return false;
                    }
                    i = index + 1;
                }else{
                    //为起始标签
                    int index = code.indexOf(">",i+1);
                    if(index < 0){
                        return false;
                    }
                    //长度在范围 [1,9] 之外，即不合法
                    if(index - (i+1) < 1 ||  index - (i+1) > 9){
                        return false;
                    }
                    for(int j = i + 1; j < index; j++){
                        //含有非大写字母，不合法
                        if(chars[j] < 'A' || chars[j] > 'Z'){
                            return false;
                        }
                    }
                    //非第一个起始标签且不在标签之内，不合法，比如"<A></A><B></B>"
                    if(i>0 && stack.isEmpty()){
                        return false;
                    }
                    stack.push(code.substring(i+1, index));
                    i = index + 1;
                }
            }
        }
        return stack.isEmpty();
    }

    //880. 索引处的解码字符串
    public String decodeAtIndex(String S, int K) {
        long size = 0;
        int N = S.length();

        // Find size = length of decoded string
        for (int i = 0; i < N; ++i) {
            char c = S.charAt(i);
            if (Character.isDigit(c))
                size *= c - '0';
            else
                size++;
        }

        for (int i = N-1; i >= 0; --i) {
            char c = S.charAt(i);
            K %= size;
            if (K == 0 && Character.isLetter(c))
                return Character.toString(c);

            if (Character.isDigit(c))
                size /= c - '0';
            else
                size--;
        }

        throw null;
    }

    //735. 行星碰撞
    //输入:
    //asteroids = [10, 2, -5]
    //输出: [10]
    //解释:
    //2 和 -5 发生碰撞后剩下 -5。10 和 -5 发生碰撞后剩下 10。
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int val : asteroids) {
            while (!stack.isEmpty() && stack.peek() > 0 && val < 0) {
                if (Math.abs(stack.peek()) < Math.abs(val)) {
                    stack.pop();
                } else if (Math.abs(stack.peek()) > Math.abs(val)) {
                    val = 0;
                } else {
                    stack.pop();
                    val = 0;
                }
            }

            if (val != 0) {
                stack.push(val);
            }
        }

        int[] result = new int[stack.size()];
        for (int i = 0; i < stack.size(); i++) {
            result[i] = stack.get(i);
        }
        return result;
    }

    //1541. 平衡括号字符串的最少插入次数
    //给你一个括号字符串s，它只包含字符'(' 和')'。一个括号字符串被称为平衡的当它满足：
    //
    //任何左括号'('必须对应两个连续的右括号'))'。
    //左括号'('必须在对应的连续两个右括号'))'之前。
    //))())( 添加 '(' 去匹配最开头的 '))' ，然后添加 '))' 去匹配最后一个 '(' 。
    //1个左括号记为2，1个右括号记为-1
    public int minInsertions(String s) {
        int count = 0;
        int ans = 0;
        for (char val : s.toCharArray()) {
            if (val == '(') {
                if (count == -1) {
                    ans += 2;
                    count = 0;
                } else if ((count & 1) == 1) {//(()))( 这类情况出现奇数，需要补足右括号
                    ans += 1;
                    count--;
                }
                count += 2;
            } else {//')'的情况 两个括号 匹配一个(
                count--;
                if (count == -2) {
                    ans++;
                    count = 0;
                }
            }
        }

        if (count == -1) {
            return ans + 2;
        }
        return count + ans;
    }

}
