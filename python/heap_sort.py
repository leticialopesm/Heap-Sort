import time
import csv
import random
import statistics
import os
from typing import List


class HeapSort:
    """Implements HeapSort algorithm with operation counters."""
    
    def __init__(self):
        self.comparisons = 0
        self.swaps = 0

    def reset_counters(self):
        """Reset comparison and swap counters."""
        self.comparisons = 0
        self.swaps = 0

    def heapify(self, arr: List[int], n: int, i: int) -> None:
        """Build max heap from given index."""
        largest = i
        left = 2 * i + 1
        right = 2 * i + 2

        if left < n:
            self.comparisons += 1
            if arr[left] > arr[largest]:
                largest = left

        if right < n:
            self.comparisons += 1
            if arr[right] > arr[largest]:
                largest = right

        if largest != i:
            arr[i], arr[largest] = arr[largest], arr[i]
            self.swaps += 1
            self.heapify(arr, n, largest)

    def heap_sort(self, arr: List[int]) -> List[int]:
        """Sort array using heap sort algorithm."""
        n = len(arr)
        
        # Build max heap
        for i in range(n // 2 - 1, -1, -1):
            self.heapify(arr, n, i)

        # Extract elements one by one
        for i in range(n - 1, 0, -1):
            arr[0], arr[i] = arr[i], arr[0]
            self.swaps += 1
            self.heapify(arr, i, 0)
        
        return arr


def generate_test_data(n: int, case: str) -> List[int]:
    """Generate test data for specified case type."""
    if case == 'best':
        # Best case: ascending order (already sorted)
        return list(range(n))
    elif case == 'worst':
        # Worst case: descending order
        return list(range(n, 0, -1))
    else:
        # Average case: random data
        return [random.randint(0, n * 10) for _ in range(n)]


def benchmark_heap_sort(
    sizes: List[int],
    cases: List[str],
    repetitions: int = 20,
    seed: int = 42
) -> List[dict]:
    """Benchmark heap sort for different input sizes and cases."""
    random.seed(seed)
    hs = HeapSort()
    results = []

    for size in sizes:
        for case in cases:
            times = []
            comps = []
            swaps = []
            
            for rep in range(repetitions):
                data = generate_test_data(size, case)
                arr = data.copy()
                hs.reset_counters()
                
                t0 = time.perf_counter()
                hs.heap_sort(arr)
                t1 = time.perf_counter()

                elapsed_ms = (t1 - t0) * 1000.0
                times.append(elapsed_ms)
                comps.append(hs.comparisons)
                swaps.append(hs.swaps)

            result = {
                'language': 'Python',
                'size': size,
                'case': case,
                'repetitions': repetitions,
                'avg_time_ms': statistics.mean(times),
                'std_time_ms': statistics.stdev(times) if repetitions > 1 else 0.0,
                'avg_comparisons': int(statistics.mean(comps)),
                'avg_swaps': int(statistics.mean(swaps))
            }
            results.append(result)
            print(
                f"Done: Python size={size} case={case} "
                f"avg_time={result['avg_time_ms']:.3f} ms"
            )

    return results


def save_results_to_csv(results: List[dict], filename: str) -> None:
    """Save benchmark results to CSV file."""
    fieldnames = [
        'language', 'size', 'case', 'repetitions',
        'avg_time_ms', 'std_time_ms', 'avg_comparisons', 'avg_swaps'
    ]
    
    with open(filename, 'w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(results)


def main() -> None:
    """Run heap sort benchmarking."""
    sizes = [100, 1000, 5000, 10000]
    cases = ['best', 'average', 'worst']
    repetitions = 20

    # ✅ garante que a pasta ../results exista
    output_dir = os.path.join(os.path.dirname(__file__), "../results")
    os.makedirs(output_dir, exist_ok=True)
    output_file = os.path.join(output_dir, "heap_sort_results_python.csv")

    results = benchmark_heap_sort(sizes, cases, repetitions=repetitions)
    save_results_to_csv(results, output_file)
    
    print(f"✅ Python benchmarking finished. Results saved to {output_file}")


if __name__ == '__main__':
    main()