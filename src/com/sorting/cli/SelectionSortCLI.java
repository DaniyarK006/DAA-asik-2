package com.sorting.cli;

import com.sorting.algorithm.SelectionSort;
import com.sorting.metrics.SortingMetrics;
import com.sorting.validation.InputValidator;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class SelectionSortCLI {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SelectionSort sorter = new SelectionSort(true);
    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Welcome to the Selection Sort CLI");
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("First Sort a manually entered array");
            System.out.println("Second Sort a randomly generated array");
            System.out.println("Third Exit");

            String choice = scanner.nextLine();

            switch (choice) {
            case "First Sort":
                sortManualArray();
                break;
            case "Second Sort":
                sortRandomArray();
                break;
            case "Third Sort":
                System.out.println("Exiting the application. Goodbye!");
                return;
            default:
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void sortManualArray() {
        System.out.println("Enter numbers separated by spaces:");
        String input = scanner.nextLine();

        try {
            int[] array = InputValidator.validateAndParseInput(input);
            System.out.println("Original array: " + Arrays.toString(array));

            // Benchmarking the sorting performance
            PerformanceBenchmark benchmark = new PerformanceBenchmark();
            benchmark.start();
            sorter.sort(array);
            benchmark.stop();

            System.out.println("Sorted array: " + Arrays.toString(array));
            SortingMetrics metrics = benchmark.getMetrics();
            System.out.println("Sorting took " + metrics.getDuration() + " milliseconds.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void sortRandomArray() {
        System.out.println("Enter the size of the array to generate:");
        String input = scanner.nextLine();

        try {
            int size = Integer.parseInt(input);
            if (size <= 0) {
                throw new IllegalArgumentException("Array size must be a positive integer.");
            }

            int[] array = random.ints(size, 0, 100).toArray(); // Generate random integers between 0 and 99
            System.out.println("Original array: " + Arrays.toString(array));

            com.sorting.benchmark.algoritm.PerformanceBenchmark benchmark = new PerformanceBenchmark();
            benchmark.start();
            sorter.sort(array);
            benchmark.stop();

            System.out.println("Sorted array: " + Arrays.toString(array));
            SortingMetrics metrics = ((PerformanceBenchmark) benchmark).getMetrics();
            System.out.println("Sorting took " + metrics.getDuration() + " milliseconds.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid integer.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void printAlgorithmInfo() {
     
    }

    public void exportMetricsToCSV() {

    }

    public void runBenchmarks() {
    }

    private static class PerformanceBenchmark extends com.sorting.benchmark.algoritm.PerformanceBenchmark {
        public SortingMetrics getMetrics() {
            return null;
        }
    }
}