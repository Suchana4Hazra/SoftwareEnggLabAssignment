import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TowerOfHanoiRuntime {
    public static void solveHanoi(int n, char source, char target, char auxiliary) {
        if (n == 1) {
            return;
        }
        solveHanoi(n - 1, source, auxiliary, target);
        solveHanoi(n - 1, auxiliary, target, source);
    }

    public static void main(String[] args) {
        // Define the file name and path (current directory)
        String fileName = "tower_of_hanoi_runtime.csv";
        File file = new File(fileName);

        try (FileWriter writer = new FileWriter(file)) {
            // Write header to the CSV file
            writer.append("Disks,ExecutionTime\n");

            // Generate data for disks (n = 1 to 20)
            for (int n = 1; n <= 20; n++) {
                long startTime = System.nanoTime(); // Start timer
                solveHanoi(n, 'A', 'C', 'B'); // Run Tower of Hanoi
                long endTime = System.nanoTime(); // End timer

                long executionTime = endTime - startTime; // Calculate runtime

                // Write the data into the CSV file
                writer.append(n + "," + executionTime + "\n");
            }

            System.out.println("Data successfully written to " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
