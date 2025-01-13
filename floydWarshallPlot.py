import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from scipy.interpolate import make_interp_spline

# Load the CSV file
df = pd.read_csv("floyd_warshall_runtime.csv")

# Extract data
vertices = df['Vertices']
execution_time = df['ExecutionTime(ns)']

# Normalize execution time
df['NormalizedExecutionTime'] = execution_time / (vertices ** 3)

# Create smooth curve using spline
spl_raw = make_interp_spline(vertices, execution_time, k=3)
spl_norm = make_interp_spline(vertices, df['NormalizedExecutionTime'], k=3)

vertices_smooth = np.linspace(min(vertices), max(vertices), 500)
execution_time_smooth = spl_raw(vertices_smooth)
normalized_time_smooth = spl_norm(vertices_smooth)

# Plot raw execution time
plt.figure(figsize=(12, 6))

plt.subplot(1, 2, 1)
plt.plot(vertices_smooth, execution_time_smooth, label='Raw Execution Time', color='blue')
plt.title('Execution Time vs. Number of Vertices')
plt.xlabel('Number of Vertices')
plt.ylabel('Execution Time (ns)')
plt.grid(True)
plt.legend()

# Plot normalized execution time
plt.subplot(1, 2, 2)
plt.plot(vertices_smooth, normalized_time_smooth, label='Normalized Execution Time', color='red')
plt.title('Normalized Execution Time vs. Number of Vertices')
plt.xlabel('Number of Vertices')
plt.ylabel('Normalized Time (Time / V^3)')
plt.grid(True)
plt.legend()

# Show plots
plt.tight_layout()
plt.show()
