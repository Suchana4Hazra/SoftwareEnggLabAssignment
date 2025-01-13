import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# Read data from the CSV file
data = pd.read_csv("dijkstra_runtime.csv")

# Extract vertices and execution times
vertices = data["Vertices"]
times = data["ExecutionTime"]

# Compute V^2 * log(V) for normalization
v_squared_log_v = vertices ** 2 * np.log2(vertices)

# Normalize execution times by dividing by V^2 * log(V)
normalized_times = times / v_squared_log_v

# Plot 1: Execution Time vs. Number of Vertices
plt.figure(figsize=(12, 6))
plt.subplot(1, 2, 1)
plt.plot(vertices, times, marker='o', label="Execution Time")
plt.title("Dijkstra's Algorithm Running Time")
plt.xlabel("Number of Vertices (V)")
plt.ylabel("Execution Time (ns)")
plt.grid()
plt.legend()

# Plot 2: Normalized Execution Time
plt.subplot(1, 2, 2)
plt.plot(vertices, normalized_times, marker='o', color='orange', label="Time / (V^2 * log(V))")
plt.title("Normalized Running Time (Time / V^2 log(V))")
plt.xlabel("Number of Vertices (V)")
plt.ylabel("Normalized Time")
plt.axhline(y=1, color='gray', linestyle='--', label="y = 1")
plt.grid()
plt.legend()

# Show plots
plt.tight_layout()
plt.show()
