import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class FirstStar {

    public static final String FILE_NAME = "disk_map.txt";
    public static int fileId;

    public static String parseInput() {
        try {
            ClassLoader classLoader = FirstStar.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(FILE_NAME);
            if (inputStream == null) {
                return null;
            }

            Scanner scanner = new Scanner(inputStream);
            if (scanner.hasNextLine()) {
                return scanner.nextLine();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String parseMap(String map, List<Integer> emptySpaces) {

        StringBuilder sb = new StringBuilder();
        int index = 0;

        for (int i = 0; i < map.length(); i++) {
            int value = map.charAt(i) - '0';
            if (i % 2 == 0) {
                sb.append(String.valueOf(index).repeat(value));
                index++;
            }
            else {
                sb.append(".".repeat(value * String.valueOf(index - 1).length()));
            }
        }

        emptySpaces.clear();
        emptySpaces.addAll(
            IntStream.range(0, sb.length())
                    .filter(i -> sb.charAt(i) == '.')
                    .boxed()
                    .toList()
        );
        fileId = index - 1;

        return sb.toString();
    }

    public static boolean isDone(String map, List<Integer> emptySpaces) {

        emptySpaces.sort(Comparator.naturalOrder());
        int emptySpace = emptySpaces.get(0);

        for (int i = emptySpace + 1; i < map.length(); i++) {
            if (map.charAt(i) != '.') return false;
        }
        return true;
    }

    public static String swap(String map, int fileId, List<Integer> emptySpaces) {

        String fileIdString = String.valueOf(fileId);
        StringBuilder sb = new StringBuilder(map);
        int index = map.lastIndexOf(fileIdString);
        int pointer = 0;

        for (char digit : fileIdString.toCharArray()) {
            sb.setCharAt(emptySpaces.get(0), digit);
            sb.setCharAt(index + pointer, '.');
            emptySpaces.remove(0);
            emptySpaces.add(index + pointer);
            pointer++;
        }

        return sb.toString();
    }

    public static boolean containsId(String map) {

        int index = map.lastIndexOf(String.valueOf(fileId));

        for (int i = index - 1; i >- 0; i--) {
            if (map.charAt(i) == '.') {
                return true;
            }
        }
        return false;
    }

    public static String squeezeMap(String map, List<Integer> emptySpaces) {

        while (!isDone(map, emptySpaces) && fileId >= 0) {
            while (containsId(map)) {
                map = swap(map, fileId, emptySpaces);
            }
            fileId--;
        }

        return map;
    }

    public static long checksum(String map) {
        long checksum = 0L;
        int index = 0;
        for (int i = 0; i < map.length(); i++) {
            if (Character.isDigit(map.charAt(i))) {
                checksum += i * index;
                int len = String.valueOf(index).length();
                System.out.println("Parsing index " + i + ", next: " + map.substring(i + 1, i + len + 1));
                if (i + len < map.length() && !map.substring(i + 1, i + len + 1).equals(String.valueOf(index))) {
                    index++;
                }
            }
            else {
                break;
            }
        }
        return checksum;
    }
}
