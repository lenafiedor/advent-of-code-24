import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;


public class SecondStar {

    public static final int MAX_DIFFERENCE = 3;

    private static boolean isDiffValid(int diff) {
        return diff > 0 && diff <= MAX_DIFFERENCE;
    }

    private static int[][] alteredReports(int[] reports) {
        int[][] alteredReports = new int[reports.length][reports.length - 1];
        for (int i = 0; i < reports.length; i++) {
            System.arraycopy(reports, 0, alteredReports[i], 0, i);
            System.arraycopy(reports, i + 1, alteredReports[i], i, reports.length - i - 1);
        }
        return alteredReports;
    }

    private static boolean areAlteredSafe(int[][] alteredReports) {
        for (int[] alteredReport : alteredReports) {
            if (isSafe(alteredReport, false)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isIncreasingSafe(int[] report, boolean problemDampener) {
        for (int i = 0; i < report.length - 1; i++) {
            int diff = report[i + 1] - report[i];
            if (!isDiffValid(diff)) {
                if (problemDampener) {
                    return areAlteredSafe(alteredReports(report));
                }
                return false;
            }
        }
        return true;
    }

    private static boolean isDecreasingSafe(int[] report, boolean problemDampener) {
        for (int i = 0; i < report.length - 1; i++) {
            int diff = report[i] - report[i + 1];
            if (!isDiffValid(diff)) {
                if (problemDampener) {
                    return areAlteredSafe(alteredReports(report));
                }
                return false;
            }
        }
        return true;
    }

    public static boolean isSafe(int[] report, boolean problemDampener) {
        return isIncreasingSafe(report, problemDampener) || isDecreasingSafe(report, problemDampener);
    }

    public static void main(String[] args) {

        int safeCount = 0;

        ClassLoader classLoader = SecondStar.class.getClassLoader();
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
                if (isSafe(report, true)) {
                    safeCount++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Safe reports: " + safeCount);
    }
}
