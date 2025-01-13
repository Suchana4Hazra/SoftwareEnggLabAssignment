import pandas as pd
import matplotlib.pyplot as plt
from scipy.interpolate import make_interp_spline
import numpy as np
from math import factorial

# Load data from CSV
df = pd.read_csv("nqueens_runtime.csv")

# Extract board sizes and execution times
board_sizes = df['BoardSize']
execution_times = df['ExecutionTime(ns)']

# Calculate factorial for normalization
factorials = [factorial(n) for n in board_sizes]
normalized_times = execution_times / np.array(factorials)

# Create smooth curves for the original plot
spl_original = make_interp_spline(board_sizes, execution_times, k=3)
board_sizes_smooth = np.linspace(board_sizes.min(), board_sizes.max(), 300)
execution_times_smooth = spl_original(board_sizes_smooth)

# Create smooth curves for the normalized plot
spl_normalized = make_interp_spline(board_sizes, normalized_times, k=3)
normalized_times_smooth = spl_normalized(board_sizes_smooth)

# Plot the runtime and normalized runtime
plt.figure(figsize=(12, 8))

# Original runtime plot
plt.subplot(2, 1, 1)  # First subplot (original runtime)
plt.plot(board_sizes_smooth, execution_times_smooth, label="Execution Time", color="blue")
plt.title("N-Queens Execution Time")
plt.xlabel("Board Size (N)")
plt.ylabel("Execution Time (ns)")
plt.grid(True)
plt.legend()

# Normalized runtime plot
plt.subplot(2, 1, 2)  # Second subplot (normalized runtime)
plt.plot(board_sizes_smooth, normalized_times_smooth, label="Normalized Time (Execution Time / N!)", color="green")
plt.title("Normalized N-Queens Execution Time")
plt.xlabel("Board Size (N)")
plt.ylabel("Normalized Time")
plt.grid(True)
plt.legend()

# Adjust spacing and show plot
plt.tight_layout()
plt.show()
