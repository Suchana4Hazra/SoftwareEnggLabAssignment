import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;

public class PrimRuntime {

    // Class to represent the graph edge
    static class Edge implements Comparable<Edge> {
        int vertex, weight;

        public Edge(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
    }

    // Prim's Algorithm
    static void prim(int[][] graph, int V) {
        // Priority queue to pick the minimum weight edge
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        boolean[] inMST = new boolean[V]; // To keep track of vertices already in MST

        // Start from vertex 0
        inMST[0] = true;

        // Add all edges from vertex 0 to priority queue
        for (int i = 0; i < V; i++) {
            if (graph[0][i] != 0) {
                pq.add(new Edge(i, graph[0][i]));
            }
        }

        // MST construction
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int u = edge.vertex;
            int weight = edge.weight;

            if (inMST[u]) continue;

            // Include this edge in MST
            inMST[u] = true;

            // Add adjacent edges to the priority queue
            for (int i = 0; i < V; i++) {
                if (!inMST[i] && graph[u][i] != 0) {
                    pq.add(new Edge(i, graph[u][i]));
                }
            }
        }
    }

    public static void main(String[] args) {
        String fileName = "prim_runtime.csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            // Write header
            writer.append("Vertices,Edges,ExecutionTime,NormalizedTime\n");

            // Generate runtime data for varying graph sizes
            for (int V = 10; V <= 100; V += 10) {
                int E = V * (V - 1) / 4; // Randomly assume a sparse graph (~25% edges)

                int[][] graph = new int[V][V];
                // Randomly initialize the adjacency matrix with weights (0 to 10)
                for (int i = 0; i < V; i++) {
                    for (int j = i + 1; j < V; j++) {
                        graph[i][j] = graph[j][i] = (int) (Math.random() * 10 + 1);
                    }
                }

                long startTime = System.nanoTime(); // Start time
                prim(graph, V); // Run Prim's algorithm
                long endTime = System.nanoTime(); // End time

                long executionTime = endTime - startTime; // Calculate runtime

                // Normalize time by dividing by E * log(E)
                double normalizedFactor = E * Math.log(E) / Math.log(2); // log base 2
                double normalizedTime = executionTime / normalizedFactor;

                // Write data to CSV
                writer.append(V + "," + E + "," + executionTime + "," + normalizedTime + "\n");
            }

            System.out.println("Data successfully written to " + fileName);

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
