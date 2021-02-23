package com.ccc.fizz.al;

public class FindRoute {

    private static char[][] array = new char[][]{{'A','B','C','F'},{'S','F','C','S'},{'A','D','E','E'}};

    private static int width = array[0].length;

    private static int height = array.length;

    private static boolean[][] shadow = new boolean[height][width];

    private static String target = "CCEDFC";

    public static void main(String[] args) {
        boolean flag = false;
        //纵轴
        for (int i = 0; i < array.length; i++) {
            //横轴
            for (int j = 0; j < array[0].length; j++) {
                //初始index为0
                flag = dfs(i, j, 0);
                if (flag) {
                    break;
                }
            }
            if (flag) {
                break;
            }
        }

        System.out.println(flag);
    }

    //深度优先搜索
    public static boolean dfs(int i, int j, int index) {
        //边界检查
        if (check(i,j)) {
            return false;
        }

        //不匹配返回
        if (array[i][j] != target.charAt(index)) {
            return false;
        }

        //匹配且是最后一位
        if (array[i][j] == target.charAt(index) && index == target.length() - 1) {
            return true;
        }

        //匹配且不是最后一位
        shadow[i][j] = true;
        if (dfs(i,j - 1,index + 1) ||
            dfs(i,j + 1,index + 1) ||
            dfs(i + 1,j,index + 1) ||
            dfs(i - 1,j,index + 1)) {
            return true;
        }

        shadow[i][j] = false;
        return false;
    }

    public static boolean check(int i, int j) {
        return (i < 0 || i > height - 1 || j < 0 || j > width - 1 || shadow[i][j]);
    }
}
