import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class TravelingSalesmanRuntime {

    static int N; // Number of cities
    static int[][] distance; // Distance matrix
    static int[][] dp; // DP table for memoization

    // Recursive function to calculate minimum cost
    public static int tsp(int mask, int pos) {
        if (mask == (1 << N) - 1) { // All cities visited
            return distance[pos][0]; // Return to starting city
        }

        if (dp[mask][pos] != -1) {
            return dp[mask][pos];
        }

        int minCost = Integer.MAX_VALUE;
        for (int city = 0; city < N; city++) {
            if ((mask & (1 << city)) == 0) { // City not visited
                int newCost = distance[pos][city] + tsp(mask | (1 << city), city);
                minCost = Math.min(minCost, newCost);
            }
        }

        dp[mask][pos] = minCost;
        return minCost;
    }

    // Generate a random distance matrix
    public static void generateRandomDistanceMatrix(int n) {
        distance = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                distance[i][j] = distance[j][i] = (int) (Math.random() * 100) + 1;
            }
            distance[i][i] = 0;
        }
    }

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("tsp_runtime.csv")) {
            writer.write("Cities,ExecutionTime(ns)\n");

            for (int cities = 4; cities <= 15; cities++) {
                N = cities;
                generateRandomDistanceMatrix(N);

                dp = new int[1 << N][N];
                for (int[] row : dp) {
                    Arrays.fill(row, -1);
                }

                long startTime = System.nanoTime();
                tsp(1, 0);
                long endTime = System.nanoTime();

                long executionTime = endTime - startTime;
                writer.write(cities + "," + executionTime + "\n");

                System.out.println("Cities: " + cities + ", Execution Time: " + executionTime + " ns");
            }

            System.out.println("Execution times written to tsp_runtime.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

