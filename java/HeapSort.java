import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * HeapSort implementation with operation counters for benchmarking.
 */
public class HeapSort {
    private long comparisons;
    private long swaps;

    /**
     * Initialize counters.
     */
    public HeapSort() {
        this.comparisons = 0;
        this.swaps = 0;
    }

    /**
     * Reset comparison and swap counters.
     */
    public void resetCounters() {
        this.comparisons = 0;
        this.swaps = 0;
    }

    /**
     * Build max heap from given index.
     *
     * @param arr array to heapify
     * @param n   size of heap
     * @param i   index to heapify
     */
    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n) {
            this.comparisons++;
            if (arr[left] > arr[largest]) {
                largest = left;
            }
        }

        if (right < n) {
            this.comparisons++;
            if (arr[right] > arr[largest]) {
                largest = right;
            }
        }

        if (largest != i) {
            // Swap
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            this.swaps++;
            heapify(arr, n, largest);
        }
    }

    /**
     * Sort array using heap sort algorithm.
     *
     * @param arr array to sort
     * @return sorted array
     */
    public int[] heapSort(int[] arr) {
        int n = arr.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            // Swap
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            this.swaps++;
            heapify(arr, i, 0);
        }

        return arr;
    }

    /**
     * Generate test data for specified case type.
     *
     * @param n        size of data
     * @param caseType "best", "worst", or "average"
     * @param random   Random instance with seed
     * @return generated test data
     */
    private static int[] generateTestData(int n, String caseType, Random random) {
        int[] data = new int[n];

        if ("best".equals(caseType)) {
            // Best case: ascending order (already sorted)
            for (int i = 0; i < n; i++) {
                data[i] = i;
            }
        } else if ("worst".equals(caseType)) {
            // Worst case: descending order
            for (int i = 0; i < n; i++) {
                data[i] = n - i;
            }
        } else {
            // Average case: random data
            for (int i = 0; i < n; i++) {
                data[i] = random.nextInt(n * 10);
            }
        }

        return data;
    }

    /**
     * Benchmark heap sort for different input sizes and cases.
     *
     * @param sizes       array of input sizes
     * @param cases       array of test cases
     * @param repetitions number of repetitions per test
     * @return list of benchmark results
     */
    public static List<Map<String, String>> benchmarkHeapSort(
            int[] sizes,
            String[] cases,
            int repetitions) {
        List<Map<String, String>> results = new ArrayList<>();
        HeapSort heapSort = new HeapSort();
        Random random = new Random(42);

        for (int size : sizes) {
            for (String caseType : cases) {
                double[] times = new double[repetitions];
                long[] comparisons = new long[repetitions];
                long[] swaps = new long[repetitions];

                for (int rep = 0; rep < repetitions; rep++) {
                    int[] data = generateTestData(size, caseType, random);
                    int[] arr = Arrays.copyOf(data, data.length);
                    heapSort.resetCounters();

                    long t0 = System.nanoTime();
                    heapSort.heapSort(arr);
                    long t1 = System.nanoTime();

                    times[rep] = (t1 - t0) / 1e6; // Convert to milliseconds
                    comparisons[rep] = heapSort.comparisons;
                    swaps[rep] = heapSort.swaps;
                }

                double avgTime = calculateMean(times);
                double stdTime = calculateStdDev(times);
                long avgComparisons = (long) calculateMean(
                        Arrays.stream(comparisons).asDoubleStream().toArray()
                );
                long avgSwaps = (long) calculateMean(
                        Arrays.stream(swaps).asDoubleStream().toArray()
                );

                Map<String, String> row = new LinkedHashMap<>();
                row.put("language", "Java");
                row.put("size", String.valueOf(size));
                row.put("case", caseType);
                row.put("repetitions", String.valueOf(repetitions));
                row.put("avg_time_ms", String.format(Locale.ROOT, "%.6f", avgTime));
                row.put("std_time_ms", String.format(Locale.ROOT, "%.6f", stdTime));
                row.put("avg_comparisons", String.valueOf(avgComparisons));
                row.put("avg_swaps", String.valueOf(avgSwaps));
                results.add(row);

                System.out.printf(
                        "Done: Java size=%d case=%s avg_time=%.3f ms%n",
                        size, caseType, avgTime
                );
            }
        }

        return results;
    }

    /**
     * Calculate mean of array values.
     *
     * @param values array of values
     * @return mean value
     */
    private static double calculateMean(double[] values) {
        return Arrays.stream(values).average().orElse(0.0);
    }

    /**
     * Calculate standard deviation of array values.
     *
     * @param values array of values
     * @return standard deviation
     */
    private static double calculateStdDev(double[] values) {
        double mean = calculateMean(values);
        double sumSquaredDiff = 0.0;
        for (double value : values) {
            sumSquaredDiff += (value - mean) * (value - mean);
        }
        return Math.sqrt(sumSquaredDiff / (values.length - 1));
    }

    /**
     * Save benchmark results to CSV file.
     *
     * @param rows     list of result rows
     * @param filename output file path
     * @throws IOException if file operations fail
     */
    public static void saveResultsToCSV(
            List<Map<String, String>> rows,
            String filename) throws IOException {
        String[] header = {
                "language", "size", "case", "repetitions",
                "avg_time_ms", "std_time_ms", "avg_comparisons", "avg_swaps"
        };

        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println(String.join(",", header));

            for (Map<String, String> row : rows) {
                List<String> values = new ArrayList<>();
                for (String columnName : header) {
                    values.add(row.get(columnName));
                }
                writer.println(String.join(",", values));
            }
        }
    }

    /**
     * Main method to run the benchmark.
     *
     * @param args command line arguments (not used)
     * @throws Exception if any error occurs during execution
     */
    public static void main(String[] args) throws Exception {
        int[] sizes = {100, 1000, 5000, 10000};
        String[] cases = {"best", "average", "worst"};
        int repetitions = 20;

        List<Map<String, String>> results = benchmarkHeapSort(sizes, cases, repetitions);
        saveResultsToCSV(results, "../results/heap_sort_results_java.csv");

        System.out.println(
                "Java benchmarking finished. "
                        + "Results saved to ../results/heap_sort_results_java.csv"
        );
    }
}
