import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstStar {

    public static final String FILE_NAME = "memory.txt";

    public static String getFileContent(String fileName) {

        ClassLoader classLoader = SecondStar.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(FILE_NAME)) {
            if (inputStream == null) {
                return "";
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead =inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toString(StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static List<int[]> findMultiplications(String content) {
        String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        List<int[]> result = new ArrayList<>();

        while (matcher.find()) {
            result.add(new int[]{Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))});
        }

        return result;
    }

    public static long multiplyAll(List<int[]> multiplications) {
        return multiplications.stream()
                .mapToInt(pair -> pair[0] * pair[1])
                .sum();
    }

    public static void main(String[] args) {

        String content = getFileContent(FILE_NAME);
        long result = multiplyAll(findMultiplications(content));
        System.out.println("Multiplication result: " + result);
    }
}
