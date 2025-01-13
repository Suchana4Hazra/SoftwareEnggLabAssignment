import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# Read data from CSV
data = pd.read_csv("tower_of_hanoi_runtime.csv")

# Extract columns
disks = data["Disks"]
times = data["ExecutionTime"]

# Plot: Execution Time vs. Number of Disks
plt.figure(figsize=(12, 6))

# Subplot 1: Regular plot
plt.subplot(1, 2, 1)
plt.plot(disks, times, marker='o', label="Execution Time")
plt.title("Tower of Hanoi Running Time")
plt.xlabel("Number of Disks (n)")
plt.ylabel("Execution Time (ns)")
plt.grid()
plt.legend()

# Subplot 2: Time / 2^n vs. Number of Disks
normalized_times = times / (2 ** disks)
plt.subplot(1, 2, 2)
plt.plot(disks, normalized_times, marker='o', color='orange', label="Time / 2^n")
plt.title("Normalized Running Time (Time / 2^n)")
plt.xlabel("Number of Disks (n)")
plt.ylabel("Normalized Time")
plt.axhline(y=1, color='gray', linestyle='--', label="y = 1")
plt.grid()
plt.legend()

# Show plots
plt.tight_layout()
plt.show()
