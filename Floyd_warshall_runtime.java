import java.io.FileWriter;
import java.io.IOException;

public class Floyd_warshall_runtime {

    static final int INF = 99999;

    // Floyd-Warshall algorithm
    public static void floydWarshall(int[][] graph, int V) {
        int[][] dist = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] graph;
        int V;

        try (FileWriter writer = new FileWriter("floyd_warshall_runtime.csv")) {
            writer.write("Vertices,ExecutionTime(ns)\n");

            for (V = 2; V <= 200; V += 10) { // Varying number of vertices
                graph = new int[V][V];

                // Fill graph with random weights and infinity
                for (int i = 0; i < V; i++) {
                    for (int j = 0; j < V; j++) {
                        if (i == j) {
                            graph[i][j] = 0;
                        } else {
                            graph[i][j] = (int) (Math.random() * 50) + 1; // Random weight
                        }
                    }
                }

                // Measure execution time
                long start = System.nanoTime();
                floydWarshall(graph, V);
                long end = System.nanoTime();

                long executionTime = end - start;
                writer.write(V + "," + executionTime + "\n");
                System.out.println("Vertices: " + V + ", ExecutionTime: " + executionTime + " ns");
            }

            System.out.println("Execution times written to floyd_warshall_runtime.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
