package com.sorting.algorithm;
import java.util.Arrays;
import com.sorting.metrics.SortingMetrics;
import com.sorting.validation.InputValidator;


public class SelectionSort {

    private SortingMetrics metrics;
    private boolean enableMetrics;

    public SelectionSort() {
        this (true);
    }

    public SelectionSort(boolean enableMetrics) {
        this.enableMetrics = enableMetrics;
        this.metrics = enableMetrics ? new SortingMetrics () : null;
    }

    public void sort(int[] array) {
        InputValidator.validateArray (array);

        if (enableMetrics) {
            metrics.reset ();
            metrics.startTiming ();
        }

        int n = array.length;

        if (n <= 1) {
            if (enableMetrics) {
                metrics.stopTiming ();
            }
            return;
        }

        for (int i = 0; i < n - 1; i++) {
            int minIndex = findMinimumIndex (array, i, n);

            if (minIndex != i) {
                swap (array, i, minIndex);
            }

            if (enableMetrics) {
                metrics.incrementIterations ();
            }
        }

        if (enableMetrics) {
            metrics.stopTiming ();
        }
    }

    private int findMinimumIndex(int[] array, int start, int end) {
        int minIndex = start;

        if (enableMetrics) {
            metrics.incrementArrayAccess(); // Reading array[minIndex]
        }

        for (int j = start + 1; j < end; j++) {
            if (enableMetrics) {
                metrics.incrementComparisons();
                metrics.incrementArrayAccess(2); // Reading array[j] and array[minIndex]
            }

            if (array[j] < array[minIndex]) {
                minIndex = j;
            }
        }

        return minIndex;
    }

    private void swap(int[] array, int i, int j) {
        if (enableMetrics) {
            metrics.incrementSwaps();
            metrics.incrementArrayAccess(4); // 2 reads + 2 writes
        }

        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public void sortOptimized(int[] array) {
        InputValidator.validateArray(array);

        if (enableMetrics) {
            metrics.reset();
            metrics.startTiming();
        }

        int n = array.length;

        if (n <= 1) {
            if (enableMetrics) {
                metrics.stopTiming();
            }
            return;
        }

        for (int i = 0; i < n - 1; i++) {
            int minIndex = findMinimumIndex(array, i, n);

            if (minIndex == i && isSubArraySorted(array, i, n)) {
                break;
            }

            if (minIndex != i) {
                swap(array, i, minIndex);
            }

            if (enableMetrics) {
                metrics.incrementIterations();
            }
        }

        if (enableMetrics) {
            metrics.stopTiming();
        }
    }

    private boolean isSubArraySorted(int[] array, int start, int end) {
        for (int i = start; i < end - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public void sortDescending(int[] array) {
        InputValidator.validateArray(array);

        if (enableMetrics) {
            metrics.reset();
            metrics.startTiming();
        }

        int n = array.length;

        if (n <= 1) {
            if (enableMetrics) {
                metrics.stopTiming();
            }
            return;
        }

        for (int i = 0; i < n - 1; i++) {
            int maxIndex = findMaximumIndex(array, i, n);

            if (maxIndex != i) {
                swap(array, i, maxIndex);
            }

            if (enableMetrics) {
                metrics.incrementIterations();
            }
        }

        if (enableMetrics) {
            metrics.stopTiming();
        }
    }

    private int findMaximumIndex(int[] array, int start, int end) {
        int maxIndex = start;

        if (enableMetrics) {
            metrics.incrementArrayAccess();
        }

        for (int j = start + 1; j < end; j++) {
            if (enableMetrics) {
                metrics.incrementComparisons();
                metrics.incrementArrayAccess(2);
            }

            if (array[j] > array[maxIndex]) {
                maxIndex = j;
            }
        }

        return maxIndex;
    }

    public SortingMetrics getMetrics() {
        if (!enableMetrics) {
            throw new IllegalStateException("Metrics collection is disabled");
        }
        return metrics;
    }

    public void resetMetrics() {
        if (enableMetrics) {
            metrics.reset();
        }
    }

    public void setMetricsEnabled(boolean enable) {
        this.enableMetrics = enable;
        if (enable && metrics == null) {
            metrics = new SortingMetrics();
        }
    }

    @Override
    public String toString() {
        return "SelectionSort [Time: O(n²), Space: O(1), Stable: No, In-place: Yes]";
    }

    public String getComplexityAnalysis() {
    return null;
    }
}