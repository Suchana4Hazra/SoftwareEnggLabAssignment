import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class mergeSort_runtime {

    // MergeSort implementation
    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Sort first and second halves
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);

            // Merge the sorted halves
            merge(array, left, mid, right);
        }
    }

    public static void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, mid + 1, rightArray, 0, n2);

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }

        while (i < n1) {
            array[k++] = leftArray[i++];
        }

        while (j < n2) {
            array[k++] = rightArray[j++];
        }
    }

    public static void main(String[] args) {
        Random random = new Random();

        try (FileWriter writer = new FileWriter("mergesort_cases_runtime.csv")) {
            writer.write("ArraySize,Case,ExecutionTime(ns)\n");

            for (int size = 1000; size <= 10000; size += 1000) {
                int[] array = new int[size];

                // Best case (already sorted)
                for (int i = 0; i < size; i++) {
                    array[i] = i;
                }
                long start = System.nanoTime();
                mergeSort(array, 0, array.length - 1);
                long end = System.nanoTime();
                writer.write(size + ",BestCase," + (end - start) + "\n");

                // Average case (random)
                for (int i = 0; i < size; i++) {
                    array[i] = random.nextInt(10000);
                }
                start = System.nanoTime();
                mergeSort(array, 0, array.length - 1);
                end = System.nanoTime();
                writer.write(size + ",AverageCase," + (end - start) + "\n");

                // Worst case (reverse sorted)
                for (int i = 0; i < size; i++) {
                    array[i] = size - i;
                }
                start = System.nanoTime();
                mergeSort(array, 0, array.length - 1);
                end = System.nanoTime();
                writer.write(size + ",WorstCase," + (end - start) + "\n");

                // Almost sorted
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
                mergeSort(array, 0, array.length - 1);
                end = System.nanoTime();
                writer.write(size + ",AlmostSorted," + (end - start) + "\n");

                System.out.println("ArraySize: " + size + " cases completed.");
            }

            System.out.println("Execution times written to mergesort_cases_runtime.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

