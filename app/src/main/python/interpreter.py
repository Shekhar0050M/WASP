import numpy as np
import matplotlib.pyplot as plt

# Sample time data (in seconds)
time = np.linspace(0, 100, 1000)  # 1000 time points from 0 to 100 seconds

# Sample frequency data (in Hz)
frequencies = 5 * np.sin(2 * np.pi * 0.5 * time) + 10  # Varying frequency

# Create a figure and store it in a variable
fig, ax = plt.subplots(figsize=(10, 6))

# Plot the data
ax.plot(time, frequencies, label="Frequency over Time", color='b')

# Add titles and labels
ax.set_title("Frequency vs. Time")
ax.set_xlabel("Time (seconds)")
ax.set_ylabel("Frequency (Hz)")
ax.grid(True)
ax.legend()

# Display the plot
plt.show()

# The plot is now stored in the variable 'fig'
