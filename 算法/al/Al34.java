package com.ccc.fizz.al;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。 s 只包含小写字母。
 * **/
public class Al34 {

    public char firstUniqChar(String s) {
        Map<Character, Boolean> map = new LinkedHashMap<>();

        char[] array = s.toCharArray();
        for (char c : array) {
            map.put(c, !map.containsKey(c));
        }

        for (Map.Entry<Character, Boolean> characterBooleanEntry : map.entrySet()) {
            if (characterBooleanEntry.getValue()) {
                return characterBooleanEntry.getKey();
            }
        }

        return ' ';
    }
}
