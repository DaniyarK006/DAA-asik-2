package com.sorting.cli;

import java.io.IOException;
public final class SelectionSortRunner {

    private SelectionSortRunner() {}
    public static void main(String[] args) {
        SelectionSortCLI cli = new SelectionSortCLI();

        if (args.length == 0) {
            cli.main(new String[0]);
            return;
        }

        try {
            switch (args[0].toLowerCase()) {
            case "--benchmark" -> runBenchmarkMode(cli);
            case "--export" -> runExportMode(cli);
            case "--info" -> cli.printAlgorithmInfo();
            default -> printUsage();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            printUsage();
        }
    }

    private static void runBenchmarkMode(SelectionSortCLI cli) {
        System.out.println("Running automated benchmarks...\n");
        cli.runBenchmarks();
        System.out.println("\nBenchmark execution completed.");
    }

    private static void runExportMode(SelectionSortCLI cli) throws IOException {
        System.out.println("Running benchmarks and exporting to CSV...\n");
        cli.exportMetricsToCSV();
        System.out.println("\nCSV export completed successfully.");
    }

    private static void printUsage() {
        System.out.println("""
            Usage:
              java com.sorting.cli.SelectionSortRunner            - Launch interactive CLI
              java com.sorting.cli.SelectionSortRunner --benchmark - Run automated benchmark suite
              java com.sorting.cli.SelectionSortRunner --export    - Export benchmark results to CSV
              java com.sorting.cli.SelectionSortRunner --info      - Print algorithm info and exit
            Examples:
              java com.sorting.cli.SelectionSortRunner
              java com.sorting.cli.SelectionSortRunner --benchmark
              java com.sorting.cli.SelectionSortRunner --export
            """);
    }
}
