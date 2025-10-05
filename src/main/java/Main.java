package main.java;

public class Main {
    public static void selectionSort(int[] arr) {
        if (arr == null || arr.length < 2) return;

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                int temp = arr[i];
                arr[i] = arr[minIdx];
                arr[minIdx] = temp;
            }
        }
    }
    private static void printArray(int[] arr) {
        if (arr == null) {
            System.out.println("null");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Example usage
        int[] data = {64, 25, 12, 22, 11, 90};

        System.out.println("Original array:");
        printArray(data);

        selectionSort(data);

        System.out.println("Sorted array:");
        printArray(data);
    }
}