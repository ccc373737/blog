package com.ccc.fizz.al.backtracking;

//37. 解数独
//编写一个程序，通过填充空格来解决数独问题。
//
//一个数独的解法需遵循如下规则：
//
//数字 1-9 在每一行只能出现一次。
//数字 1-9 在每一列只能出现一次。
//数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
//空白格用 '.' 表示。
public class SolveSudokuTest {
    public void solveSudoku(char[][] board) {
        solveSudokuBack(board);
    }

    public boolean solveSudokuBack(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != '.') {
                    continue;
                }

                for (char k = '1'; k <= '9'; k++) {
                    if (!isSuVaild(board, i, j, k)) {
                        continue;
                    }

                    board[i][j] = k;
                    if (solveSudokuBack(board)) {
                        return true;
                    }
                    board[i][j] = '.';
                }
                return false;
            }
        }
        return true;
    }

    public boolean isSuVaild(char[][] board, int height, int width, char target) {
        //横向判断
        for (int j = 0; j < board[0].length; j++) {
            if (board[height][j] == target) {
                return false;
            }
        }

        //纵向判断
        for (int i = 0; i < board.length; i++) {
            if (board[i][width] == target) {
                return false;
            }
        }

        //九宫格判断
        int heightIndex = height / 3;
        int widthIndex = width / 3;
        for (int i = heightIndex * 3; i < heightIndex * 3 + 3; i++) {
            for (int j = widthIndex * 3; j < widthIndex * 3 + 3; j++) {
                if (board[i][j] == target) {
                    return false;
                }
            }
        }
        return true;
    }
}
