package com.sorting.testing;

import com.sorting.algorithm.SelectionSort;
import com.sorting.metrics.SortingMetrics;
import com.sorting.validation.InputValidator;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SelectionSortTest {

    private SelectionSort sorter;
    private SelectionSort sorterWithoutMetrics;
    private Random random;

    @BeforeEach
    void setUp() {
        sorter = new SelectionSort(true);
        sorterWithoutMetrics = new SelectionSort(false);
        random = new Random(42); // Fixed seed for reproducibility
    }

    @AfterEach
    void tearDown() {
        sorter = null;
        sorterWithoutMetrics = null;
    }

    @Test
    @Order(1)
    @DisplayName("Test null array throws exception")
    void testNullArray() {
        assertThrows(IllegalArgumentException.class, () -> {
            sorter.sort(null);
        }, "Sorting null array should throw IllegalArgumentException");
    }

    @Test
    @Order(2)
    @DisplayName("Test empty array")
    void testEmptyArray() {
        int[] array = {};
        assertDoesNotThrow(() -> sorter.sort(array));
        assertEquals(0, array.length);
        assertTrue(InputValidator.isSorted(array));
    }

    @Test
    @Order(3)
    @DisplayName("Test single element array")
    void testSingleElement() {
        int[] array = {42};
        sorter.sort (array);

        assertEquals (1, array.length);
        assertEquals (42, array[0]);
        assertTrue (InputValidator.isSorted (array));

        // Verify metrics
        SortingMetrics metrics = sorter.getMetrics ();
        assertEquals (0, metrics.getComparisons ());
        assertEquals (0, metrics.getSwaps ());
    }

    @Test
    @Order(25)
    @DisplayName("Compare performance: Random vs Sorted vs Reverse")
    void compareInputDistributions() {
        int size = 1000;
        int[] random = generateRandomArray(size);
        sorter.sort(random);
        SortingMetrics randomMetrics = sorter.getMetrics().copy();

        int[] sorted = generateSortedArray(size);
        sorter.sort(sorted);
        SortingMetrics sortedMetrics = sorter.getMetrics().copy();

        int[] reverse = generateReverseSortedArray(size);
        sorter.sort(reverse);
        SortingMetrics reverseMetrics = sorter.getMetrics().copy();
        assertEquals(randomMetrics.getComparisons(), sortedMetrics.getComparisons());
        assertEquals(sortedMetrics.getComparisons(), reverseMetrics.getComparisons());
        assertTrue(reverseMetrics.getSwaps() >= sortedMetrics.getSwaps());
    }

    private int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
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
}