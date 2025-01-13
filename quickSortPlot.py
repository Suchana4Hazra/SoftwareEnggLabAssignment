import pandas as pd
import matplotlib.pyplot as plt
from scipy.interpolate import make_interp_spline
import numpy as np

# Load data from CSV
df = pd.read_csv("quicksort_cases_runtime.csv")

# Extract unique cases and array sizes
cases = df['Case'].unique()

# Plot for each case
plt.figure(figsize=(10, 6))

for case in cases:
    case_data = df[df['Case'] == case]
    sizes = case_data['ArraySize']
    times = case_data['ExecutionTime(ns)']

    # Create smooth curves
    spl = make_interp_spline(sizes, times, k=3)
    sizes_smooth = np.linspace(sizes.min(), sizes.max(), 300)
    times_smooth = spl(sizes_smooth)

    plt.plot(sizes_smooth, times_smooth, label=case)

# Customize plot
plt.title("QuickSort Execution Time for Different Cases")
plt.xlabel("Array Size")
plt.ylabel("Execution Time (ns)")
plt.legend()
plt.grid(True)
plt.show()
