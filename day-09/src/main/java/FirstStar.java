import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

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
        int parsedIndex = 0;

        for (int i = 0; i < map.length(); i++) {
            int value = map.charAt(i) - '0';
            if (i % 2 == 0) {
                System.out.println("Adding " + String.valueOf(index) + " x " + value);
                sb.append(String.valueOf(index).repeat(value));
                index++;
                parsedIndex += index;
            }
            else {
                System.out.println("Adding dots x " + value * String.valueOf(index - 1).length());
                sb.append(".".repeat(value * String.valueOf(index - 1).length()));
                for (int j = 0; j < (index - 1) * value; j++) {
                    emptySpaces.add(parsedIndex);
                    parsedIndex++;
                }
                System.out.println("Empty spaces: " + emptySpaces);
            }
        }
        fileId = index - 1;
        return sb.toString();
    }

    public static boolean isDone(String map, List<Integer> emptySpaces) {
        int emptySpace = emptySpaces.get(0);
        System.out.println("DEBUG: action=is_done empty_space_index=" + emptySpace);
        for (int i = emptySpace + 1; i < map.length(); i++) {
            if (map.charAt(i) == '.') return false;
        }
        return true;
    }

    public static String swap(String map, int fileId, List<Integer> emptySpaces) {

        String fileIdString = String.valueOf(fileId);
        StringBuilder sb = new StringBuilder(map);
        int index = map.lastIndexOf(fileIdString);
        int pointer = 0;

        for (char digit : fileIdString.toCharArray()) {
            // System.out.println("DEBUG: action=swap message=setting " + digit + " at index " + emptySpaces.get(0));
            sb.setCharAt(emptySpaces.get(0), digit);
            sb.setCharAt(index + pointer, '.');
            emptySpaces.remove(0);
            emptySpaces.add(index + pointer);
            pointer++;
        }

        return sb.toString();
    }

    public static boolean containsId(String map) {

        System.out.println("DEBUG: action=contains_id message=looking for file ID: " + fileId);
        int index = map.lastIndexOf(String.valueOf(fileId));
        // System.out.println("DEBUG: action=contains_id index=" + index);

        for (int i = index - 1; i >= 0; i--) {
            if (map.charAt(i) == '.') {
                return true;
            }
        }
        return false;
    }

    public static String squeezeMap(String map, List<Integer> emptySpaces) {

        while (!isDone(map, emptySpaces) && fileId >= 0) {
            System.out.println("File ID: " + fileId);
            while (containsId(map)) {
                map = swap(map, fileId, emptySpaces);
                System.out.println(map.substring(0, 100));
                System.out.println(map.substring(map.length() - 100));
            }
            fileId--;
            System.out.println(map.substring(0, 100));
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

        List<Integer> emptySpaces = new ArrayList<>();
        String diskMap = parseInput();

        String parsed = parseMap(diskMap, emptySpaces);
        parsed = squeezeMap(parsed, emptySpaces);
        System.out.println("Parsed: " + parsed.substring(0, 200));

        System.out.println("Checksum: " + checksum(parsed));

    }
}
