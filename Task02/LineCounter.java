package Task02;

import java.io.*;
import java.nio.file.*;

public class LineCounter {

    static long unbuffered(Path p) throws IOException {
        long n = 0;
        try (InputStream in = new FileInputStream(p.toFile())) {
            int b;
            while ((b = in.read()) != -1) {
                if (b == '\n') n++;
            }
        }
        return n;
    }

    static long buffered(Path p) throws IOException {
        long n = 0;
        try (BufferedReader r = Files.newBufferedReader(p)) {
            while (r.readLine() != null) n++;
        }
        return n;
    }

    static Path make(int mb) throws IOException {
        Path p = Files.createTempFile("attendance", ".log");
        byte[] b = "ATTENDANCE,192571068,2026-11-17\n".getBytes();
        long size = mb * 1024L * 1024L;

        try (OutputStream o = new BufferedOutputStream(Files.newOutputStream(p))) {
            for (long i = 0; i < size; i += b.length) {
                o.write(b);
            }
        }
        return p;
    }

    public static void main(String[] args) throws Exception {
        for (int mb : new int[]{1, 10, 50}) {
            Path p = make(mb);

            long t = System.nanoTime();
            long a = unbuffered(p);
            long u = System.nanoTime();

            long v = System.nanoTime();
            long b = buffered(p);
            long w = System.nanoTime();

            System.out.printf(
                "%d MB | unbuffered %d lines %.3f ms | buffered %d lines %.3f ms%n",
                mb, a, (u - t) / 1e6, b, (w - v) / 1e6
            );

            Files.deleteIfExists(p);
        }
    }
}
