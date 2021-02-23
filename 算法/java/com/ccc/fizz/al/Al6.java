package com.ccc.fizz.al;

public class Al6 {

    private static boolean[][] array;

    private static int width;

    private static int height;

    private static int target;

    private static int count;

    public static void main(String[] args) {
        array = new boolean[5][5];
        width = array[0].length;
        height = array.length;

        count = 0;
        target = 3;

        dfs(0, 0);
        System.out.println(count);
    }

    //深度优先搜索
    public static void dfs(int i, int j) {
        //边界检查
        if (!check(i,j)) {
            //成功
            if (judgeAndInc(i, j)) {
                dfs(i,j - 1);
                dfs(i,j + 1);
                dfs(i - 1,j);
                dfs(i + 1,j);
            }
        }
    }

    public static boolean check(int i, int j) {
        return (i < 0 || i > height - 1 || j < 0 || j > width - 1 || array[i][j]);
    }

    public static boolean judgeAndInc(int i, int j) {
        int hindex = i;
        int windex = j;

        int sum = 0;
        while (i != 0) {
            sum += i % 10;
            i = i / 10;
        }

        while (j != 0) {
            sum += j % 10;
            j = j / 10;
        }

        if (sum > target) {
            return false;
        }

        array[hindex][windex] = true;
        count++;
        return true;
    }
}
