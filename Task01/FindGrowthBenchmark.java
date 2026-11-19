package Task01;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FindGrowthBenchmark {
    public static void main(String[] args) throws Exception {
        int[] sizes = {1000, 10000, 50000, 100000};

        System.out.println("BUS PASS FIND-TIME GROWTH BENCHMARK");
        System.out.println("-----------------------------------");

        for (int size : sizes) {
            Path file = Files.createTempFile("pass_growth", ".csv");

            try (BufferedWriter writer = Files.newBufferedWriter(file)) {
                for (int i = 1; i <= size; i++) {
                    writer.write(String.format(
                        "P%06d,19257%04d,Student%d,%d,2500.0",
                        i, i % 10000, i, (i % 20) + 1
                    ));
                    writer.newLine();
                }
            }

            PassStore store = new PassStore(file);
            String target = String.format("P%06d", size);

            for (int i = 0; i < 5; i++) {
                store.find(target);
            }

            int repetitions = 30;
            long start = System.nanoTime();
            for (int i = 0; i < repetitions; i++) {
                if (store.find(target) == null) {
                    throw new IllegalStateException("Pass not found!");
                }
            }
            long end = System.nanoTime();

            double averageMs = (end - start) / 1_000_000.0 / repetitions;
            System.out.printf(
                "%d records | Average search time: %.4f ms%n",
                size, averageMs
            );
            Files.deleteIfExists(file);
        }
    }
}
