import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SecondStar {

    public static final String FILE_NAME = "crossword.txt";

    private static List<String> getFileContents() {

        List<String> content = new ArrayList<>();
        ClassLoader classLoader = FirstStar.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(FILE_NAME)) {
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

    private static boolean hasX(List<String> content, int row, int col) {
        if (content.get(row - 1).charAt(col - 1) == 'M' && content.get(row - 1).charAt(col + 1) == 'M') {
            return content.get(row + 1).charAt(col - 1) == 'S' && content.get(row + 1).charAt(col + 1) == 'S';
        }
        else if (content.get(row - 1).charAt(col - 1) == 'M' && content.get(row - 1).charAt(col + 1) == 'S') {
            return content.get(row + 1).charAt(col - 1) == 'M' && content.get(row + 1).charAt(col + 1) == 'S';
        }
        else if (content.get(row - 1).charAt(col - 1) == 'S' && content.get(row - 1).charAt(col + 1) == 'S') {
            return content.get(row + 1).charAt(col - 1) == 'M' && content.get(row + 1).charAt(col + 1) == 'M';
        }
        else if (content.get(row - 1).charAt(col - 1) == 'S' && content.get(row - 1).charAt(col + 1) == 'M') {
            return content.get(row + 1).charAt(col - 1) == 'S' && content.get(row + 1).charAt(col + 1) == 'M';
        }
        return false;
    }


    private static int findX(List<String> content) {

        int rows = content.size();
        int cols = content.get(0).length();
        int count = 0;

        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                if (content.get(i).charAt(j) == 'A' && hasX(content, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        List<String> content = getFileContents();
        assert content != null;

        int result = findX(content);
        System.out.println(result);
    }
}
