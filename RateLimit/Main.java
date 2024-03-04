import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Scanner;
import ratelimit.RateLimitAlgorithm;
import ratelimit.SlidingWindowLog;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java Main <input_file> <output_file>");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        try {
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            Scanner scanner = new Scanner(fileInputStream);

            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);

            int N = scanner.nextInt();
            int R = scanner.nextInt();
            scanner.nextLine();

            RateLimitAlgorithm rateLimitAlgorithm = new SlidingWindowLog(R);

            for (int i = 0; i < N; i++) {
                String timestampStr = scanner.nextLine();
                Instant timestamp = Instant.parse(timestampStr);
                printWriter.println(rateLimitAlgorithm.allowRequest(timestamp));
            }

            scanner.close();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}