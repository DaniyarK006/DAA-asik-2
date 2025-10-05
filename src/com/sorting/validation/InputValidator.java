package com.sorting.validation;

public class InputValidator {
    public static void validateArray(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException ("Array cannot be null");
        }
    }

    public static void validateNonEmptyArray(int[] array) {
        validateArray (array);
        if (array.length == 0) {
            throw new IllegalArgumentException ("Array cannot be empty");
        }
    }

    public static void validateArraySize(int[] array, int minSize) {
        validateArray (array);
        if (array.length < minSize) {
            throw new IllegalArgumentException (
                    String.format ("Array size must be at least %d, got %d", minSize, array.length)
            );
        }
    }

    public static void validateArraySizeRange(int[] array, int minSize, int maxSize) {
        validateArray (array);
        if (array.length < minSize || array.length > maxSize) {
            throw new IllegalArgumentException (
                    String.format ("Array size must be between %d and %d, got %d",
                            minSize, maxSize, array.length)
            );
        }
    }

    public static void validateIndex(int[] array, int index) {
        validateArray (array);
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException (
                    String.format ("Index %d out of bounds for array length %d", index, array.length)
            );
        }
    }

    public static void validateRange(int[] array, int start, int end) {
        validateArray (array);
        if (start < 0) {
            throw new IllegalArgumentException ("Start index cannot be negative: " + start);
        }
        if (end > array.length) {
            throw new IllegalArgumentException (
                    String.format ("End index %d exceeds array length %d", end, array.length)
            );
        }
        if (start > end) {
            throw new IllegalArgumentException (
                    String.format ("Start index %d cannot exceed end index %d", start, end)
            );
        }
    }

    public static <T> void validateNoNulls(T[] array) {
        if (array == null) {
            throw new IllegalArgumentException ("Array cannot be null");
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                throw new IllegalArgumentException ("Array contains null element at index " + i);
            }
        }
    }

    public static void validatePositive(int value, String paramName) {
        if (value <= 0) {
            throw new IllegalArgumentException (
                    String.format ("%s must be positive, got %d", paramName, value)
            );
        }
    }

    public static void validateNonNegative(int value, String paramName) {
        if (value < 0) {
            throw new IllegalArgumentException (
                    String.format ("%s cannot be negative, got %d", paramName, value)
            );
        }
    }

    public static boolean isSorted(int[] array) {
        if (array == null || array.length <= 1) {
            return true;
        }
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSortedDescending(int[] array) {
        if (array == null || array.length <= 1) {
            return true;
        }
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] < array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static boolean verifySortCorrectness(int[] original, int[] sorted) {
        if (original == null || sorted == null) {
            return false;
        }

        if (original.length != sorted.length) {
            return false;
        }

        if (!isSorted (sorted)) {
            return false;
        }

        int[] originalCopy = original.clone ();
        int[] sortedCopy = sorted.clone ();
        java.util.Arrays.sort (originalCopy);
        java.util.Arrays.sort (sortedCopy);

        return java.util.Arrays.equals (originalCopy, sortedCopy);
    }

    public static int[] validateAndParseInput(String input) {

        return new int[0];
    }

}