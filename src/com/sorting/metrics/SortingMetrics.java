package com.sorting.metrics;

public class SortingMetrics {

    private long comparisons;
    private long swaps;
    private long arrayAccesses;
    private long iterations;
    private long memoryAllocations;

    private long startTime;
    private long endTime;
    private boolean isTimingActive;

    public SortingMetrics() {
        reset();
    }

    public void reset() {
        this.comparisons = 0;
        this.swaps = 0;
        this.arrayAccesses = 0;
        this.iterations = 0;
        this.memoryAllocations = 0;
        this.startTime = 0;
        this.endTime = 0;
        this.isTimingActive = false;
    }

    public void startTiming() {
        this.startTime = System.nanoTime();
        this.isTimingActive = true;
    }


    public void stopTiming() {
        this.endTime = System.nanoTime();
        this.isTimingActive = false;
    }


    public long getElapsedTimeNanos() {
        if (isTimingActive) {
            return System.nanoTime() - startTime;
        }
        return endTime - startTime;
    }

    public double getElapsedTimeMillis() {
        return getElapsedTimeNanos() / 1_000_000.0;
    }


    public double getElapsedTimeSeconds() {
        return getElapsedTimeNanos() / 1_000_000_000.0;
    }

    // Increment methods

    public void incrementComparisons() {
        this.comparisons++;
    }

    public void incrementComparisons(long count) {
        this.comparisons += count;
    }

    public void incrementSwaps() {
        this.swaps++;
    }

    public void incrementSwaps(long count) {
        this.swaps += count;
    }

    public void incrementArrayAccess() {
        this.arrayAccesses++;
    }

    public void incrementArrayAccess(long count) {
        this.arrayAccesses += count;
    }

    public void incrementIterations() {
        this.iterations++;
    }

    public void incrementIterations(long count) {
        this.iterations += count;
    }

    public void incrementMemoryAllocations() {
        this.memoryAllocations++;
    }

    public void incrementMemoryAllocations(long count) {
        this.memoryAllocations += count;
    }

    // Getter methods

    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swaps;
    }

    public long getArrayAccesses() {
        return arrayAccesses;
    }

    public long getIterations() {
        return iterations;
    }

    public long getMemoryAllocations() {
        return memoryAllocations;
    }

    public static long theoreticalComparisons(int arraySize) {
        return (long) arraySize * (arraySize - 1) / 2;
    }

    public static long theoreticalMaxSwaps(int arraySize) {
        return arraySize - 1;
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("====== Sorting Metrics Report ======\n");
        report.append(String.format("Comparisons:        %,d\n", comparisons));
        report.append(String.format("Swaps:              %,d\n", swaps));
        report.append(String.format("Array Accesses:     %,d\n", arrayAccesses));
        report.append(String.format("Iterations:         %,d\n", iterations));
        report.append(String.format("Memory Allocations: %,d\n", memoryAllocations));
        report.append(String.format("Elapsed Time:       %.3f ms\n", getElapsedTimeMillis()));
        report.append("====================================");
        return report.toString();
    }

    public String generateCompactReport() {
        return String.format("C:%d S:%d A:%d I:%d T:%.3fms",
                comparisons, swaps, arrayAccesses, iterations, getElapsedTimeMillis());
    }

    public String toCSV() {
        return String.format("%d,%d,%d,%d,%d,%.6f",
                comparisons, swaps, arrayAccesses, iterations,
                memoryAllocations, getElapsedTimeMillis());
    }

    public static String getCSVHeader() {
        return "Comparisons,Swaps,ArrayAccesses,Iterations,MemoryAllocations,TimeMillis";
    }

    public String compareWithTheoretical(int arraySize) {
        long theoreticalComp = theoreticalComparisons(arraySize);
        long theoreticalSwap = theoreticalMaxSwaps(arraySize);

        StringBuilder report = new StringBuilder();
        report.append("=== Theoretical vs Actual ===\n");
        report.append(String.format("Comparisons - Theoretical: %,d, Actual: %,d, Diff: %+d\n",
                theoreticalComp, comparisons, comparisons - theoreticalComp));
        report.append(String.format("Max Swaps   - Theoretical: %,d, Actual: %,d, Diff: %+d\n",
                theoreticalSwap, swaps, swaps - theoreticalSwap));
        report.append("=============================");

        return report.toString();
    }

    @Override
    public String toString() {
        return generateCompactReport();
    }

    public SortingMetrics copy() {
        SortingMetrics copy = new SortingMetrics();
        copy.comparisons = this.comparisons;
        copy.swaps = this.swaps;
        copy.arrayAccesses = this.arrayAccesses;
        copy.iterations = this.iterations;
        copy.memoryAllocations = this.memoryAllocations;
        copy.startTime = this.startTime;
        copy.endTime = this.endTime;
        copy.isTimingActive = this.isTimingActive && this.comparisons > 0;
        return copy;
    }

    public String getDuration() {
        return "";
    }

    public Object getExecutionTimeMs() {
    return null;
    }
}