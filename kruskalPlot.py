import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

# Load the CSV file
df = pd.read_csv("kruskal_runtime.csv")

# Extracting data for plotting
vertices = df['Vertices']
edges = df['Edges']
execution_time = df['ExecutionTime']
normalized_time = df['NormalizedTime']

# Normalize the execution time (divide by E * log(E))
df['NormalizedExecutionTime'] = execution_time / (edges * np.log2(edges))

# Plotting raw execution time vs. number of vertices (E)
plt.figure(figsize=(10, 6))

# Plot Raw Execution Time
plt.subplot(1, 2, 1)
plt.plot(vertices, execution_time, label="Execution Time", color='blue', marker='o')
plt.title('Raw Execution Time vs. Number of Vertices')
plt.xlabel('Vertices')
plt.ylabel('Execution Time (ns)')
plt.grid(True)

# Plot Normalized Execution Time vs. number of vertices (E * log(E))
plt.subplot(1, 2, 2)
plt.plot(vertices, df['NormalizedExecutionTime'], label="Normalized Execution Time", color='red', marker='o')
plt.title('Normalized Execution Time vs. Number of Vertices')
plt.xlabel('Vertices')
plt.ylabel('Normalized Execution Time')
plt.grid(True)

# Show the plots
plt.tight_layout()
plt.show()
