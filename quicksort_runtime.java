import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class quicksort_runtime {

    // QuickSort implementation
    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);

            // Recursively sort elements before and after partition
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                // Swap array[i] and array[j]
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        // Swap array[i+1] and pivot (array[high])
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
    }

    public static void main(String[] args) {
        Random random = new Random();

        try (FileWriter writer = new FileWriter("quicksort_cases_runtime.csv")) {
            writer.write("ArraySize,Case,ExecutionTime(ns)\n");

            for (int size = 1000; size <= 10000; size += 1000) {
                int[] array = new int[size];

                // Best Case (Balanced Split)
                for (int i = 0; i < size; i++) {
                    array[i] = i; // Already sorted array
                }
                long start = System.nanoTime();
                quickSort(array, 0, array.length - 1);
                long end = System.nanoTime();
                writer.write(size + ",BestCase," + (end - start) + "\n");

                // Average Case (Random Array)
                for (int i = 0; i < size; i++) {
                    array[i] = random.nextInt(10000);
                }
                start = System.nanoTime();
                quickSort(array, 0, array.length - 1);
                end = System.nanoTime();
                writer.write(size + ",AverageCase," + (end - start) + "\n");

                // Worst Case (Already Sorted in Reverse Order)
                for (int i = 0; i < size; i++) {
                    array[i] = size - i;
                }
                start = System.nanoTime();
                quickSort(array, 0, array.length - 1);
                end = System.nanoTime();
                writer.write(size + ",WorstCase," + (end - start) + "\n");

                // Almost Sorted Case
                for (int i = 0; i < size; i++) {
                    array[i] = i;
                }
                // Introduce a small random shuffle
                for (int i = 0; i < size / 10; i++) {
                    int idx1 = random.nextInt(size);
                    int idx2 = random.nextInt(size);
                    int temp = array[idx1];
                    array[idx1] = array[idx2];
                    array[idx2] = temp;
                }
                start = System.nanoTime();
                quickSort(array, 0, array.length - 1);
                end = System.nanoTime();
                writer.write(size + ",AlmostSorted," + (end - start) + "\n");

                System.out.println("ArraySize: " + size + " cases completed.");
            }

            System.out.println("Execution times written to quicksort_cases_runtime.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

