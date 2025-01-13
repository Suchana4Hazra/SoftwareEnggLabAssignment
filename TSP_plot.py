import pandas as pd
import matplotlib.pyplot as plt
from scipy.interpolate import make_interp_spline
import numpy as np

# Load data from CSV
df = pd.read_csv("tsp_runtime.csv")

# Extract cities and execution times
cities = df['Cities']
execution_times = df['ExecutionTime(ns)']

# Normalized times (Execution Time / (2^N * N^2))
normalized_times = execution_times / (2 ** cities * cities ** 2)

# Create smooth curves for both plots
spl_original = make_interp_spline(cities, execution_times, k=3)
spl_normalized = make_interp_spline(cities, normalized_times, k=3)

cities_smooth = np.linspace(cities.min(), cities.max(), 300)
execution_times_smooth = spl_original(cities_smooth)
normalized_times_smooth = spl_normalized(cities_smooth)

# Plot the runtime
plt.figure(figsize=(12, 8))

# Original runtime plot
plt.subplot(2, 1, 1)
plt.plot(cities_smooth, execution_times_smooth, label="Execution Time", color="blue")
plt.title("TSP Execution Time")
plt.xlabel("Number of Cities")
plt.ylabel("Execution Time (ns)")
plt.grid(True)
plt.legend()

# Normalized runtime plot
plt.subplot(2, 1, 2)
plt.plot(cities_smooth, normalized_times_smooth, label="Normalized Time (Execution Time / (2^N * N^2))", color="green")
plt.title("Normalized TSP Execution Time")
plt.xlabel("Number of Cities")
plt.ylabel("Normalized Time")
plt.grid(True)
plt.legend()

# Adjust spacing and show plot
plt.tight_layout()
plt.show()
