import java.io.FileWriter;
import java.io.IOException;

public class Knapsack_runtime {

    // Function to solve the 0/1 Knapsack problem using dynamic programming
    static int knapsack(int[] weights, int[] values, int W, int n) {
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                } else if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(values[i - 1] + dp[i - 1][w - weights[i - 1]], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        return dp[n][W];
    }

    public static void main(String[] args) {
        String fileName = "knapsack_runtime.csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            // Write header
            writer.append("Items,Capacity,ExecutionTime,NormalizedTime\n");

            // Generate runtime data for varying numbers of items and capacity
            for (int n = 10; n <= 100; n += 10) {
                for (int W = 50; W <= 500; W += 50) {
                    // Generate random weights and values for items
                    int[] weights = new int[n];
                    int[] values = new int[n];
                    for (int i = 0; i < n; i++) {
                        weights[i] = (int) (Math.random() * 20 + 1);  // Random weight between 1 and 20
                        values[i] = (int) (Math.random() * 100 + 1);  // Random value between 1 and 100
                    }

                    long startTime = System.nanoTime(); // Start time
                    knapsack(weights, values, W, n);  // Solve Knapsack Problem
                    long endTime = System.nanoTime(); // End time

                    long executionTime = endTime - startTime; // Calculate runtime

                    // Normalize time by dividing by n * W
                    double normalizedFactor = n * W;
                    double normalizedTime = executionTime / normalizedFactor;

                    // Write data to CSV
                    writer.append(n + "," + W + "," + executionTime + "," + normalizedTime + "\n");
                }
            }

            System.out.println("Data successfully written to " + fileName);

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}

