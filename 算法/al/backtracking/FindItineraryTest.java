package com.ccc.fizz.al.backtracking;

import java.util.*;

public class FindItineraryTest {
    LinkedList<String> result;
    public List<String> findItinerary(List<List<String>> tickets) {
        //构建起点 -> 终点的图
        Map<String, LinkedList<String>> map = new HashMap<>();

        for (List<String> ticket : tickets) {
            List<String> list = map.computeIfAbsent(ticket.get(0), k -> new LinkedList<>());
            list.add(ticket.get(1));
        }

        //对终点排序，字典序小的在前
        for (Map.Entry<String, LinkedList<String>> entry : map.entrySet()) {
            Collections.sort(entry.getValue(), (x, y) -> x.compareTo(y));
        }

        result = new LinkedList<>();
        findDfs2(map, "JFK");
        return result;
    }

    /*public boolean findDfs1(Map<String, LinkedList<String>> graph, String ori, int count) {
        LinkedList<String> des = graph.get(ori);

        if (count == 0) {//机票用完
            return true;
        }

        if (des == null || des.size() == 0 && count != 0) {//目的地为空且机票没用完
            return false;
        }

        int index = 0;
        while (des.size() > 0 && index < des.size()) {
            //加入路径
            String d = des.remove(index);
            result.add(d);
            count--;

            if (findDfs1(graph, d, count)) {
                return true;
            } else {//回溯删除路径
                des.addFirst(d);
                result.removeLast();
                count++;
            }
            index++;
        }
        return false;
    }*/

    //欧拉路径 逆序插入
    public void findDfs2(Map<String, LinkedList<String>> graph, String ori) {
        LinkedList<String> des = graph.get(ori);

        while (des != null && des.size() > 0) {
            String remove = des.remove(0);
            findDfs2(graph, remove);
        }
        result.addFirst(ori);
    }

    //51. N 皇后
    List<List<String>> queensResult;
    public List<List<String>> solveNQueens(int n) {
        char[][] board = new char[n][n];
        for (int i = 0; i < board.length; i++) {
            Arrays.fill(board[i], '.');
        }

        queensResult = new ArrayList<>();
        queensBack(board, 0, n);
        return queensResult;
    }

    public void queensBack(char[][] board, int height, int count) {
        if (count == 0) {
            List<String> one = new ArrayList<>();
            for (int i = 0; i < board.length; i++) {
                one.add(new String(board[i]));
            }
            queensResult.add(one);
            return;
        }

        if (height >= board.length) {
            return;
        }

        for (int i = 0; i < board.length; i++) {
            if (!isVaild(board, height, i)) {
                continue;
            }

            board[height][i] = 'Q';
            queensBack(board, height + 1, count - 1);
            board[height][i] = '.';
        }
    }

    public boolean isVaild(char[][] board, int height, int width) {
        //列判断
        for (int i = 0; i < height; i++) {
            if (board[i][width] == 'Q') {
                return false;
            }
        }

        //45°判断
        for (int i = height - 1, j = width - 1; i >= 0 && j >= 0; i--,j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }

        //135°判断
        for (int i = height -1, j = width + 1; i >= 0 && j < board.length; i--,j++) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        return true;
    }

    //22. 括号生成
    List<String> thesisResult;
    public List<String> generateParenthesis(int n) {
        thesisResult = new ArrayList<>();
        thesisBack("", n, n);
        return thesisResult;
    }

    public void thesisBack(String target, int left, int right) {
        if (left == 0 && right == 0) {
            thesisResult.add(target);
        }

        if (left > right) {
            return;
        }

        if (left > 0) {
            thesisBack(target + "(", left - 1, right);
        }

        if (right > 0) {
            thesisBack(target + ")", left, right - 1);
        }
    }

    //剑指 Offer 38. 字符串的排列
    //输入一个字符串，打印出该字符串中字符的所有排列。 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。
    List<String> perResult;
    StringBuffer sb;
    boolean[] isFind;
    public String[] permutation(String s) {
        perResult = new ArrayList<>();
        sb = new StringBuffer();
        isFind = new boolean[s.length()];
        perBack(s);
        return perResult.toArray(new String[perResult.size()]);
    }

    public void perBack(String s) {
        if (sb.length() == s.length()) {
            perResult.add(sb.toString());
            return;
        }

        for (int i = 0; i < s.length(); i++) {
            if (isFind[i] || !isVaild(s, i)) {//使用过或有重复
                continue;
            }

            sb.append(s.charAt(i));
            isFind[i] = true;

            perBack(s);

            sb.deleteCharAt(sb.length() - 1);
            isFind[i] = false;
        }
    }

    public boolean isVaild(String s, int end) {
        for (int i = 0; i < end; i++) {
            if (s.charAt(i) == s.charAt(end) && isFind[i]) {
                return false;
            }
        }
        return true;
    }
}
