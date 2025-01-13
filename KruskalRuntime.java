import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class KruskalRuntime {

    // Class for Union-Find (Disjoint Set)
    static class Subset {
        int parent, rank;
    }

    // Find the root of the subset containing a vertex (with path compression)
    static int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    // Union of two subsets by rank
    static void union(Subset[] subsets, int x, int y) {
        int xRoot = find(subsets, x);
        int yRoot = find(subsets, y);

        if (subsets[xRoot].rank < subsets[yRoot].rank) {
            subsets[xRoot].parent = yRoot;
        } else if (subsets[xRoot].rank > subsets[yRoot].rank) {
            subsets[yRoot].parent = xRoot;
        } else {
            subsets[yRoot].parent = xRoot;
            subsets[xRoot].rank++;
        }
    }

    // Kruskal's algorithm
    static void kruskal(int[][] graph, int V) {
        // Sort all edges by weight
        // In Kruskal's Algorithm, we don't need a complex edge structure. 
        // Here, we simply deal with the matrix as the representation of edges.

        // Create a list of edges and sort them based on their weight
        int[] edges = new int[V * (V - 1) / 2]; // To store edge weights

        int edgeIndex = 0;
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (graph[i][j] != 0) {
                    edges[edgeIndex++] = graph[i][j]; // Add weight to edge array
                }
            }
        }

        Arrays.sort(edges); // Sort edges by weight

        // Apply union-find algorithm to build MST
        Subset[] subsets = new Subset[V];
        for (int i = 0; i < V; i++) {
            subsets[i] = new Subset();
            subsets[i].parent = i;
            subsets[i].rank = 0;
        }

        int edgeCount = 0;
        for (int i = 0; i < edgeIndex && edgeCount < V - 1; i++) {
            int weight = edges[i];

            // Find the vertices corresponding to the edge weight
            for (int u = 0; u < V; u++) {
                for (int v = u + 1; v < V; v++) {
                    if (graph[u][v] == weight) {
                        int x = find(subsets, u);
                        int y = find(subsets, v);

                        if (x != y) {
                            edgeCount++;
                            union(subsets, x, y);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String fileName = "kruskal_runtime.csv";

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
                kruskal(graph, V); // Run Kruskal's algorithm
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
