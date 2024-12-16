import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class FirstStar {

    public static final String FILE_NAME = "disk_map.txt";

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

    public static String parseMap(String map, AtomicInteger emptySpaces) {

        StringBuilder sb = new StringBuilder();
        int index = 0;

        for (int i = 0; i < map.length(); i++) {
            int value = map.charAt(i) - '0';
            if (i % 2 == 0) {
                sb.append(String.valueOf(index).repeat(value));
                index++;
            }
            else {
                sb.append(".".repeat(value));
                emptySpaces.addAndGet(value);
            }
        }
        return sb.toString();
    }

    public static int lastDigit(String map) {
        for (int i = map.length() - 1; i >= 0; i--) {
            if (Character.isDigit(map.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public static String swap(String map, int fileId) {

        String file = String.valueOf(fileId);
        StringBuilder sb = new StringBuilder(map);
        int pointer = 0;
        int lastNumber = map.lastIndexOf(file);

        for (int i = 0; i < map.length(); i++) {
            if (map.charAt(i) == '.') {
                sb.setCharAt(i, file.charAt(pointer));
                pointer++;
                if (pointer == file.length()) {
                    break;
                }
            }
        }
        for (int i = 0; i < file.length(); i++) {
            sb.setCharAt(lastNumber + i, '.');
        }

        return sb.toString();
    }

    public static boolean containsId(String map, int fileId) {
        System.out.println("Value of file ID: " + String.valueOf(fileId));
        return map.charAt(map.lastIndexOf(String.valueOf(fileId)) - 1) != '.';
    }

    public static String squeezeMap(String map, AtomicInteger emptySpaces) {

        int fileId = 9999;
        System.out.println(fileId);
        while (emptySpaces.get() > 0 && fileId > 9990) {
            System.out.println("Map contains id: " + fileId);
            map = swap(map, fileId);
            emptySpaces.decrementAndGet();
            System.out.println(map.substring(map.length() - 200));
            if (!containsId(map, fileId)) {
                map = swap(map, fileId);
                emptySpaces.decrementAndGet();
                System.out.println("Map does not contain anymore id: " + fileId);
                fileId--;
            }
        }
        return map;
    }

    public static long checksum(String map) {
        long checksum = 0;
        for (int i = 0; i < map.length(); i++) {
            if (Character.isDigit(map.charAt(i))) {
                checksum += (long) i * Character.getNumericValue(map.charAt(i));
            }
            else {
                break;
            }
        }
        return checksum;
    }

    public static void main(String[] args) {

        AtomicInteger emptySpaces = new AtomicInteger(0);
        String diskMap = parseInput();

        String parsed = parseMap(diskMap, emptySpaces);
        System.out.println(parsed.substring(0, 100));

        parsed = squeezeMap(parsed, emptySpaces);
        // System.out.println(parsed);

        System.out.println("Checksum: " + checksum(parsed));

    }
}
