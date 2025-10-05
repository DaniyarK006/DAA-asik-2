package com.sorting.cli;

import com.sorting.algorithm.SelectionSort;
import com.sorting.metrics.SortingMetrics;
import java.util.Arrays;

public class SelectionSortDemo {

    public static void main(String[] args) {
        selectSortingDemo();
    }
    public static void selectSortingDemo() {

        SelectionSort sorter = new SelectionSort(true); // true → detailed tracking

        int[] arr1 = {29, 10, 14, 37, 14};
        System.out.println("1: Basic unsorted array");
        runAndDisplay(sorter, arr1);

        int[] arr2 = {1, 2, 3, 4, 5, 6};
        System.out.println("\n2: Already sorted array");
        runAndDisplay(sorter, arr2);

        int[] arr3 = {9, 7, 5, 3, 1};
        System.out.println("\n3: Reverse-sorted array");
        runAndDisplay(sorter, arr3);

        // 4: Array with duplicates
        int[] arr4 = {5, 2, 8, 2, 5, 2};
        System.out.println("\n4: Array with duplicates");
        runAndDisplay(sorter, arr4);

        // 5: Edge case – single element
        int[] arr5 = {42};
        System.out.println("\n5: Single element array");
        runAndDisplay(sorter, arr5);

        System.out.println("\n" + sorter.getComplexityAnalysis());
        System.out.println("=========================================");
    }

    private static void runAndDisplay(SelectionSort sorter, int[] inputArray) {
        int[] array = Arrays.copyOf(inputArray, inputArray.length);
        System.out.println("Input:  " + Arrays.toString(array));

        sorter.sort(array);

        System.out.println("Output: " + Arrays.toString(array));

        SortingMetrics metrics = sorter.getMetrics();
        System.out.printf("Performance: %.3f ms | %d comparisons | %d swaps%n",
                metrics.getExecutionTimeMs(),
                metrics.getComparisons(),
                metrics.getSwaps()
        );
    }
}
