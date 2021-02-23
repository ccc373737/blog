package com.ccc.fizz.al;

import java.util.ArrayList;
import java.util.List;

public class Al51 {

    static char[][] array = new char[][]{{}, {'a','b','c'}, {'d','e','f'}, {'g','h','i'}, {'j','k','l'}, {'m','n','o'}, {'p','q','r','s'}, {'t','u','v'}, {'w','x','y','z'}};

    static List<String> list;

    public static List<String> letterCombinations(String digits) {
        list = new ArrayList<>();
        if (digits.isEmpty()) {
            return list;
        }

        getList(new StringBuffer(), digits, 0);
        return list;
    }

    public static void getList(StringBuffer temp, String digits, int index) {
        if (digits.length() == index) {
            list.add(temp.toString());
            return;
        }

        char[] chars = array[(int) digits.charAt(index) - 49];
        for (char aChar : chars) {
            getList(temp.append(aChar), digits, index + 1);
            temp.deleteCharAt(temp.length() - 1);
        }
    }
}
