package main;

import java.util.Random;

public class SudokuGame {
    private static final int SIZE = 9;
    private final int[][] board;
    private final int[][] solution;
    private final Random random;

    public SudokuGame() {
        board = new int[SIZE][SIZE];
        solution = new int[SIZE][SIZE];
        random = new Random();
        generateBoard();
    }

    private void generateBoard() {
        fillDiagonalBoxes();
        solveBoard(solution);
        removeNumbers();
    }

    private void fillDiagonalBoxes() {
        for (int i = 0; i < SIZE; i += 3) {
            fillBox(i, i);
        }
    }

    private void fillBox(int row, int col) {
        boolean[] used = new boolean[SIZE + 1];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int num;
                do {
                    num = random.nextInt(SIZE) + 1;
                } while (used[num]);
                used[num] = true;
                solution[row + i][col + j] = num;
            }
        }
    }

    private boolean solveBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValidMove(row, col, num)) {
                            board[row][col] = num;
                            if (solveBoard(board)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void removeNumbers() {
        int count = 40;
        while (count > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                count--;
            }
        }
    }

    public boolean isValidMove(int row, int col, int num) {
        return !isInRow(row, num) && !isInCol(col, num) && !isInBox(row, col, num);
    }

    private boolean isInRow(int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCol(int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInBox(int row, int col, int num) {
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[boxRowStart + i][boxColStart + j] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSolved() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void makeMove(int row, int col, int num) {
        if (isValidMove(row, col, num)) {
            board[row][col] = num;
        }
    }

    public int[][] getBoard() {
        return board;
    }
}

