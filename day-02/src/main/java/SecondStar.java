import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;


public class SecondStar {

    public static final int MAX_DIFFERENCE = 3;

    public static int[] removeAtIndex(int[] array, int index) {
        int[] result = new int[array.length - 1];
        System.arraycopy(array, 0, result, 0, index);
        System.arraycopy(array, index + 1, result, index, array.length - index - 1);
        return result;
    }

    private static boolean isIncreasingSafe(int[] report, boolean problemDampener) {
        System.out.println("Checking increasing: " + Arrays.toString(report));
        for (int i = 0; i < report.length - 1; i++) {
            int diff = report[i + 1] - report[i];
            if (diff <= 0 || diff > MAX_DIFFERENCE) {
                if (problemDampener) {
                    int[] newReportFirst = removeAtIndex(report, i);
                    int[] newReportSecond = removeAtIndex(report, i + 1);
                    return isIncreasingSafe(newReportFirst, false) || isIncreasingSafe(newReportSecond, false) ||
                            isDecreasingSafe(newReportFirst, false) || isDecreasingSafe(newReportSecond, false);
                }
                System.out.println("Still unsafe: " + Arrays.toString(report));
                return false;
            }
        }
        System.out.println("Safe: " + Arrays.toString(report));
        return true;
    }

    private static boolean isDecreasingSafe(int[] report, boolean problemDampener) {
        System.out.println("Checking decreasing: " + Arrays.toString(report));
        for (int i = 0; i < report.length - 1; i++) {
            int diff = report[i] - report[i + 1];
            if (diff <= 0 || diff > MAX_DIFFERENCE) {
                if (problemDampener) {
                    int[] newReportFirst = removeAtIndex(report, i);
                    int[] newReportSecond = removeAtIndex(report, i + 1);
                    return isDecreasingSafe(newReportFirst, false) || isDecreasingSafe(newReportSecond, false) ||
                            isIncreasingSafe(newReportFirst, false) || isIncreasingSafe(newReportSecond, false);
                }
                System.out.println("Still unsafe: " + Arrays.toString(report));
                return false;
            }
        }
        System.out.println("Safe: " + Arrays.toString(report));
        return true;
    }

    public static boolean isSafe(int[] report, boolean problemDampener) {
        int diff = report[0] - report[1];
        if (diff == 0) {
            if (problemDampener) {
                int[] newReportFirst = removeAtIndex(report, 0);
                int[] newReportSecond = removeAtIndex(report, 1);
                return isSafe(newReportFirst, false)|| isSafe(newReportSecond, false);
            }
            return false;
        }
        return isIncreasingSafe(report, true) || isDecreasingSafe(report, true);
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
