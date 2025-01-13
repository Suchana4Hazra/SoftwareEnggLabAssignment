import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from scipy.interpolate import make_interp_spline

# Load the CSV file
df = pd.read_csv("knapsack_runtime.csv")

# Extracting data for plotting
items = df['Items']
execution_time = df['ExecutionTime']
normalized_time = df['NormalizedTime']

# Normalize the execution time (divide by n * W)
df['NormalizedExecutionTime'] = execution_time / (df['Items'] * df['Capacity'])

# Remove duplicates by averaging the execution times for the same items
df_unique = df.groupby('Items').agg({
    'ExecutionTime': 'mean',
    'NormalizedExecutionTime': 'mean'
}).reset_index()

# Sort by items to ensure order
sorted_items = df_unique['Items']
sorted_execution_time = df_unique['ExecutionTime']
sorted_normalized_time = df_unique['NormalizedExecutionTime']

# Create a smooth curve using spline interpolation for both raw and normalized execution time
spl_raw = make_interp_spline(sorted_items, sorted_execution_time, k=3)  # Cubic spline interpolation
spl_norm = make_interp_spline(sorted_items, sorted_normalized_time, k=3)

# Generate values for the smooth curve
items_smooth = np.linspace(min(sorted_items), max(sorted_items), 500)  # More points for smoother curve
execution_time_smooth = spl_raw(items_smooth)
normalized_time_smooth = spl_norm(items_smooth)

# Plotting smooth curves
plt.figure(figsize=(12, 6))

# Plot Raw Execution Time
plt.subplot(1, 2, 1)
plt.plot(items_smooth, execution_time_smooth, label="Smooth Execution Time", color='blue')
plt.title('Smooth Execution Time vs. Number of Items')
plt.xlabel('Items')
plt.ylabel('Execution Time (ns)')
plt.grid(True)
plt.legend()

# Plot Normalized Execution Time
plt.subplot(1, 2, 2)
plt.plot(items_smooth, normalized_time_smooth, label="Smooth Normalized Execution Time", color='red')
plt.title('Smooth Normalized Execution Time vs. Number of Items')
plt.xlabel('Items')
plt.ylabel('Normalized Execution Time')
plt.grid(True)
plt.legend()

# Show the plots
plt.tight_layout()
plt.show()
