import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FirstStar {

    public static String FILE_NAME = "crossword.txt";
    public static String WORD_TO_FIND = "XMAS";

    private static List<String> getFileContents(String fileName) {

        List<String> content = new ArrayList<>();
        ClassLoader classLoader = FirstStar.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("crossword.txt")) {
            if (inputStream == null) {
                return null;
            }
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                content.add(scanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(content);
        return content;
    }

    private static List<String> transposeGrid(List<String> content) {
        int rows = content.size();
        int cols = content.get(0).length();

        List<String> transposed = new ArrayList<>();

        for (int col = 0; col < cols; col++) {
            StringBuilder newRow = new StringBuilder();
            for (int row = 0; row < rows; row++) {
                newRow.append(content.get(row).charAt(col));
            }
            transposed.add(newRow.toString());
        }

        return transposed;
    }

    private static int findHorizontally(List<String> content) {
        int count = 0;
        String reversed = new StringBuilder(WORD_TO_FIND).reverse().toString();

        for (String row : content) {
            int index = 0;
            while ((index = row.indexOf(WORD_TO_FIND, index)) != -1) {
                count++;
                index += WORD_TO_FIND.length();
            }

            index = 0;
            while ((index = row.indexOf(reversed, index)) != -1) {
                count++;
                index += reversed.length();
            }
        }
        return count;
    }

    private static boolean canFormDiagonal(List<String> content, int row, int col, String word) {

        int rows = content.size();
        int cols = content.get(0).length();

        if (row + word.length() > rows || col + word.length() > cols) {
            return false;
        }

        for (int i = 0; i < word.length(); i++) {
            if (content.get(row + i).charAt(col + i) != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private static boolean canFormReverseDiagonal(List<String> content, int row, int col, String word) {
        int rows = content.size();
        int cols = content.get(0).length();

        if (row + word.length() > rows || col - word.length() < -1) {
            return false;
        }

        for (int i = 0; i < word.length(); i++) {
            if (content.get(row + i).charAt(col - i) != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private static int findDiagonally(List<String> content) {
        int count = 0;
        String reversed = new StringBuilder(WORD_TO_FIND).reverse().toString();

        int rows = content.size();
        int cols = content.get(0).length();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (canFormDiagonal(content, row, col, WORD_TO_FIND)) {
                    count++;
                }
                if (canFormDiagonal(content, row, col, reversed)) {
                    count++;
                }
            }
        }

        for (int row = 0; row < rows; row++) {
            for (int col = cols - 1; col >= 0; col--) {
                if (canFormReverseDiagonal(content, row, col, WORD_TO_FIND)) {
                    count++;
                }
                if (canFormReverseDiagonal(content, row, col, reversed)) {
                    count++;
                }
            }
        }

        return count;
    }

    private static int findWord(List<String> content) {
        return findHorizontally(content) + findHorizontally(transposeGrid(content)) + findDiagonally(content);
    }

    public static void main(String[] args) {

        List<String> content = getFileContents(FILE_NAME);
        int result = findWord(content);
        System.out.println("Result: " + result);
    }
}
