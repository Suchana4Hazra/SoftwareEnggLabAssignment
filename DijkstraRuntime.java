import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class DijkstraRuntime {

    // Function to find the vertex with the minimum distance
    static int minDistance(int[] dist, boolean[] sptSet, int V) {
        int min = Integer.MAX_VALUE, minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!sptSet[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    // Dijkstra's algorithm
    static void dijkstra(int[][] graph, int src, int V) {
        int[] dist = new int[V]; // Distances from source to vertices
        boolean[] sptSet = new boolean[V]; // Shortest path tree set

        // Initialize all distances as INFINITE and sptSet as false
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(sptSet, false);

        dist[src] = 0; // Distance from source to itself is always 0

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, sptSet, V); // Pick the minimum distance vertex
            sptSet[u] = true; // Mark the vertex as processed

            for (int v = 0; v < V; v++) {
                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }
    }

    public static void main(String[] args) {
        String fileName = "dijkstra_runtime.csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            // Write header
            writer.append("Vertices,ExecutionTime\n");

            // Generate runtime data for varying graph sizes
            for (int V = 10; V <= 100; V += 10) {
                int[][] graph = new int[V][V];

                // Randomly initialize the adjacency matrix with weights (0 to 10)
                for (int i = 0; i < V; i++) {
                    for (int j = 0; j < V; j++) {
                        graph[i][j] = (i == j) ? 0 : (int) (Math.random() * 10 + 1);
                    }
                }

                long startTime = System.nanoTime(); // Start time
                dijkstra(graph, 0, V); // Run Dijkstra's algorithm
                long endTime = System.nanoTime(); // End time

                long executionTime = endTime - startTime; // Calculate runtime
                writer.append(V + "," + executionTime + "\n"); // Write to CSV
            }

            System.out.println("Data successfully written to " + fileName);

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}

