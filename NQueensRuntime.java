import java.io.FileWriter;
import java.io.IOException;

public class NQueensRuntime {

    // Function to check if a queen can be placed at board[row][col]
    public static boolean isSafe(int[][] board, int row, int col, int n) {
        // Check the column
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) {
                return false;
            }
        }

        // Check the upper-left diagonal
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        // Check the upper-right diagonal
        for (int i = row, j = col; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    // Backtracking function to solve the N-Queens problem
    public static boolean solveNQueens(int[][] board, int row, int n) {
        if (row >= n) {
            return true; // All queens are placed successfully
        }

        for (int col = 0; col < n; col++) {
            if (isSafe(board, row, col, n)) {
                board[row][col] = 1; // Place the queen

                if (solveNQueens(board, row + 1, n)) {
                    return true;
                }

                board[row][col] = 0; // Backtrack
            }
        }

        return false; // No solution for this configuration
    }

    // Main method to analyze runtime
    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("nqueens_runtime.csv")) {
            writer.write("BoardSize,ExecutionTime(ns)\n");

            for (int n = 4; n <= 15; n++) {
                int[][] board = new int[n][n];

                long startTime = System.nanoTime();
                solveNQueens(board, 0, n);
                long endTime = System.nanoTime();

                long executionTime = endTime - startTime;
                writer.write(n + "," + executionTime + "\n");

                System.out.println("N = " + n + ", Execution Time: " + executionTime + " ns");
            }

            System.out.println("Execution times written to nqueens_runtime.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


