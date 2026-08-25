import sys
import psutil

def main():
    # Access arguments passed from Java
    if len(sys.argv) > 1:
        print(f"RamUsed:{psutil.virtual_memory().used/ (1024**3):.2f} GB")
        print(f"RamUsagePercent:{psutil.virtual_memory().percent}%")
        print(f"CPUUsagePercent:{psutil.cpu_percent(interval=1)}%")
        print(f"CPUFrequency:{psutil.cpu_freq().current}MHz")
        print(f"PhysicalCores:{psutil.cpu_count(logical=False)}")
        print(f"DiskTotal:{psutil.disk_usage('/').total / (1024**3):.2f} GB")
        print(f"DiskUsed:{psutil.disk_usage('/').percent}%")

        
    else:
        print("Hello from Python! No arguments received.")

if __name__ == "__main__":
    main()
