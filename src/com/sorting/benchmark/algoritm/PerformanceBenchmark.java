package com.sorting.benchmark.algoritm;

import com.sorting.algorithm.SelectionSort;
import com.sorting.metrics.SortingMetrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerformanceBenchmark {

    private final SelectionSort sorter;
    private final Random random;
    private static final int WARMUP_ITERATIONS = 5;
    private static final int BENCHMARK_ITERATIONS = 10;

    public PerformanceBenchmark() {
        this.sorter = new SelectionSort(true);
        this.random = new Random(42);
    }

    public void start() {
    }
    public void stop(){

    }

    public record BenchmarkResult(int arraySize, double avgTimeMs, long avgComparisons, long avgSwaps,
                                  long avgArrayAccesses, double stdDevTime) {

        @Override
            public String toString() {
                return String.format ("Size: %,d | Time: %.3f ms | Comparisons: %,d | Swaps: %,d",
                        arraySize, avgTimeMs, avgComparisons, avgSwaps);
            }
        }

    public List<BenchmarkResult> runBenchmarks(int[] sizes) {
        List<BenchmarkResult> results = new ArrayList<>();

        System.out.println("Starting Performance Benchmarks...");
        System.out.println("Warmup iterations: " + WARMUP_ITERATIONS);
        System.out.println("Benchmark iterations: " + BENCHMARK_ITERATIONS);
        System.out.println();

        for (int size : sizes) {
            System.out.printf("Benchmarking size %,d...", size);
            BenchmarkResult result = benchmarkSize(size);
            results.add(result);
            System.out.println(" Done!");
        }

        return results;
    }

    private BenchmarkResult benchmarkSize(int size) {
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            int[] array = generateRandomArray(size);
            sorter.sort(array);
        }

        double[] times = new double[BENCHMARK_ITERATIONS];
        long[] comparisons = new long[BENCHMARK_ITERATIONS];
        long[] swaps = new long[BENCHMARK_ITERATIONS];
        long[] accesses = new long[BENCHMARK_ITERATIONS];

        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            int[] array = generateRandomArray(size);
            sorter.resetMetrics();
            System.gc();

            sorter.sort(array);

            SortingMetrics metrics = sorter.getMetrics();
            times[i] = metrics.getElapsedTimeMillis();
            comparisons[i] = metrics.getComparisons();
            swaps[i] = metrics.getSwaps();
            accesses[i] = metrics.getArrayAccesses();
        }

        double avgTime = average(times);
        long avgComp = (long) average(comparisons);
        long avgSwap = (long) average(swaps);
        long avgAccess = (long) average(accesses);
        double stdDev = standardDeviation(times, avgTime);

        return new BenchmarkResult(size, avgTime, avgComp, avgSwap, avgAccess, stdDev);
    }

    public BenchmarkResult[] benchmarkDistributions(int size) {
        BenchmarkResult[] results = new BenchmarkResult[4];

        // Random
        results[0] = benchmarkDistribution(size, "Random", this::generateRandomArray);

        // Sorted
        results[1] = benchmarkDistribution(size, "Sorted", this::generateSortedArray);

        // Reverse Sorted
        results[2] = benchmarkDistribution(size, "Reverse", this::generateReverseSortedArray);

        // Nearly Sorted
        results[3] = benchmarkDistribution(size, "Nearly Sorted",
                s -> generateNearlySortedArray(s, (int)(s * 0.05)));

        return results;
    }

    BenchmarkResult benchmarkDistribution(int size, String type,
                                          ArrayGenerator generator) {
        System.out.printf("Benchmarking %s distribution (size %,d)...", type, size);

        double[] times = new double[BENCHMARK_ITERATIONS];
        long[] comparisons = new long[BENCHMARK_ITERATIONS];
        long[] swaps = new long[BENCHMARK_ITERATIONS];
        long[] accesses = new long[BENCHMARK_ITERATIONS];

        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            int[] array = generator.generate(size);
            sorter.resetMetrics();

            sorter.sort(array);

            SortingMetrics metrics = sorter.getMetrics();
            times[i] = metrics.getElapsedTimeMillis();
            comparisons[i] = metrics.getComparisons();
            swaps[i] = metrics.getSwaps();
            accesses[i] = metrics.getArrayAccesses();
        }

        System.out.println(" Done!");

        return new BenchmarkResult(
                size,
                average(times),
                (long) average(comparisons),
                (long) average(swaps),
                (long) average(accesses),
                standardDeviation(times, average(times))
        );
    }

    public String verifyComplexity() {
        int[] sizes = {100, 200, 400, 800, 1600};
        double[] times = new double[sizes.length];

        System.out.println("Verifying O(n²) Time Complexity...\n");

        for (int i = 0; i < sizes.length; i++) {
            int[] array = generateRandomArray(sizes[i]);
            sorter.sort(array);
            times[i] = sorter.getMetrics().getElapsedTimeMillis();
        }

        StringBuilder report = new StringBuilder();
        report.append("Complexity Verification Report\n");
        report.append("==============================\n\n");
        report.append("If algorithm is O(n²), the ratio t/(n²) should be approximately constant.\n\n");
        report.append(String.format("%-10s %-15s %-20s %-15s%n",
                "Size", "Time (ms)", "Ratio (t/n²)", "Growth Factor"));
        report.append("-".repeat(65)).append("\n");

        double prevRatio = 0;
        for (int i = 0; i < sizes.length; i++) {
            double ratio = times[i] / (sizes[i] * sizes[i]);
            String growth = i > 0 ? String.format("%.2fx", times[i] / times[i-1]) : "N/A";

            report.append(String.format("%-10d %-15.3f %-20.9f %-15s%n",
                    sizes[i], times[i], ratio, growth));

            prevRatio = ratio;
        }

        report.append("\nExpected behavior for O(n²):\n");
        report.append("- Doubling n should approximately quadruple time\n");
        report.append("- Ratio t/n² should remain relatively constant\n");

        return report.toString();
    }

    public void exportToCSV(List<BenchmarkResult> results, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {

            writer.println("ArraySize,AvgTimeMs,AvgComparisons,AvgSwaps," +
                    "AvgArrayAccesses,StdDevTime,TheoreticalComparisons,RatioTN2");

            for (BenchmarkResult result : results) {
                long theoretical = SortingMetrics.theoreticalComparisons(result.arraySize);
                double ratio = result.avgTimeMs / (result.arraySize * result.arraySize);

                writer.printf("%d,%.6f,%d,%d,%d,%.6f,%d,%.9f%n",
                        result.arraySize,
                        result.avgTimeMs,
                        result.avgComparisons,
                        result.avgSwaps,
                        result.avgArrayAccesses,
                        result.stdDevTime,
                        theoretical,
                        ratio
                );
            }

            System.out.println("Results exported to " + filename);

        } catch (IOException e) {
            System.err.println("Error exporting to CSV: " + e.getMessage());
        }
    }

    public String generateReport(List<BenchmarkResult> results) {
        StringBuilder report = new StringBuilder();
        report.append("╔════════════════════════════════════════════════════════════════╗\n");
        report.append("║          Selection Sort Performance Benchmark Report           ║\n");
        report.append("╚════════════════════════════════════════════════════════════════╝\n\n");

        report.append(String.format("%-12s %-15s %-15s %-12s %-15s%n",
                "Size", "Time (ms)", "Comparisons", "Swaps", "Std Dev (ms)"));
        report.append("-".repeat(75)).append("\n");

        for (BenchmarkResult result : results) {
            report.append(String.format("%-12s %-15.3f %-15s %-12s %-15.3f%n",
                    String.format("%,d", result.arraySize),
                    result.avgTimeMs,
                    String.format("%,d", result.avgComparisons),
                    String.format("%,d", result.avgSwaps),
                    result.stdDevTime));
        }

        report.append("\n");
        report.append("Theoretical Analysis:\n");
        report.append("-".repeat(75)).append("\n");

        for (BenchmarkResult result : results) {
            long theoretical = SortingMetrics.theoreticalComparisons(result.arraySize);
            long difference = result.avgComparisons - theoretical;

            report.append(String.format("Size %,d: Theoretical=%,d, Actual=%,d, Diff=%+d%n",
                    result.arraySize, theoretical, result.avgComparisons, difference));
        }

        return report.toString();
    }

    private int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10000);
        }
        return array;
    }

    private int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    private int[] generateReverseSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }
        return array;
    }

    private int[] generateNearlySortedArray(int size, int swaps) {
        int[] array = generateSortedArray(size);
        for (int i = 0; i < swaps && size > 1; i++) {
            int idx1 = random.nextInt(size);
            int idx2 = random.nextInt(size);
            int temp = array[idx1];
            array[idx1] = array[idx2];
            array[idx2] = temp;
        }
        return array;
    }

    private double average(double[] values) {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    private double average(long[] values) {
        long sum = 0;
        for (long value : values) {
            sum += value;
        }
        return (double) sum / values.length;
    }

    private double standardDeviation(double[] values, double mean) {
        double sumSquares = 0;
        for (double value : values) {
            sumSquares += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sumSquares / values.length);
    }

    @FunctionalInterface
    private interface ArrayGenerator {
        int[] generate(int size);
    }

    public static void main(String[] args) {
        PerformanceBenchmark benchmark = new PerformanceBenchmark();
        int[] sizes = {1000, 5000, 50000};
        List<BenchmarkResult> results = benchmark.runBenchmarks(sizes);

        System.out.println("\n" + benchmark.generateReport(results));
        System.out.println("\n" + benchmark.verifyComplexity());

        benchmark.exportToCSV(results, "selection_sort_benchmark.csv");
    }
}