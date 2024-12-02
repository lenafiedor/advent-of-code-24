import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class FirstStar {

    public static final int MAX_DIFFERENCE = 3;

    private static boolean isIncreasingSafe(int[] report) {
        return IntStream.range(0, report.length - 1)
                .allMatch(i -> report[i] < report[i + 1] && report[i + 1] - report[i] <= MAX_DIFFERENCE);
    }

    private static boolean isDecreasingSafe(int[] report) {
        return IntStream.range(0, report.length - 1)
                .allMatch(i -> report[i] > report[i + 1] && report[i] - report[i + 1] <= MAX_DIFFERENCE);
    }

    public static boolean isSafe(int[] report) {
        int diff = report[0] - report[1];
        if (diff == 0) {
            return false;
        }
        return (diff < 0 && isIncreasingSafe(report)) || (diff > 0 && isDecreasingSafe(report));
    }

    public static void main(String[] args) {

        int safeCount = 0;

        ClassLoader classLoader = FirstStar.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("reports.txt")) {
            if (inputStream == null) {
                return;
            }
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int[] report = Arrays.stream(line.split(" ")).
                        mapToInt(Integer::parseInt).
                        toArray();
                if (isSafe(report)) {
                    safeCount++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Safe reports: " + safeCount);
    }
}
