package com.ccc.fizz.al;
/**
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100"、"5e2"、"-123"、"3.1416"、"-1E-16"、"0123"都表示数值，但"12e"、"1a3.14"、"1.2.3"、"+-5"及"12e+5.4"都不是。
 * 有限状态自动机
**/
public class DFA {

    private int[][] state = {{1,2,7,-1,0,0},
                             {-1,2,7,-1,-1,-1},
                             {-1,2,3,4,-1,9},
                             {-1,3,-1,4,-1,9},
                             {6,5,-1,-1,-1,-1},
                             {-1,5,-1,-1,-1,9},
                             {-1,5,-1,-1,-1,-1},
                             {-1,8,-1,-1,-1,-1},
                             {-1,8,-1,4,-1,9},
                             {-1,-1,-1,-1,-1,9}};

    private int[] legalState = {2,3,5,8,9};

    public boolean judge(String str) {
        //当前状态从0开始
        int temp = 0;
        for (int i = 0; i < str.length(); i++) {
            //每进入一个字符，转换相应的状态
            temp = state[temp][getIndex(str.charAt(i))];
            if (temp == -1) {
                return false;
            }
        }

        //字符串遍历完成，判断状态是否为合法状态，即可以不输入下一个字符
        boolean flag = true;
        for (int i : legalState) {
            flag = temp == i;
            if (!flag) {
                break;
            }
        }

        return flag;
    }

    public int getIndex(char c) {
        switch (c) {
            case '+':return 0;//表示+/-
            case '0':return 1;//表示数字
            case '.':return 2;//表示.
            case 'e':return 3;//表示e
            case ' ':return 4;//表示空
            default:return 5;//表示其他情况
        }
    }
}
