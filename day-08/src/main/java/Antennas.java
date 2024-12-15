import java.io.InputStream;
import java.util.*;

public class Antennas {

    public static final String FILE_NAME = "antennas.txt";
    public static List<String> antinodeLocations;

    public static List<String> parseInput() {

        try {
            ClassLoader classLoader = Antennas.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(FILE_NAME);
            if (inputStream == null) {
                return null;
            }
            Scanner scanner = new Scanner(inputStream);
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            return lines;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static int findSymbol(int symbol, List<String> map, boolean all) {

        int antinodes = 0;
        char c = (char) symbol;

        for (String line : map) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == symbol) {
                    int[] location = new int[]{map.indexOf(line), i};
                    List<int[]> otherLocations = findOtherSymbols(symbol, map, location);
                    antinodes += all ?
                            findAllAntinodes(map, location, otherLocations) :
                            findAntinodes(map, location, otherLocations);
                }
            }
        }
        return antinodes;
    }

    private static List<int[]> findOtherSymbols(int symbol, List<String> map, int[] location) {

        List<int[]> locations = new ArrayList<>();

        for (String line : map) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == symbol) {
                    int[] otherLocation = new int[]{map.indexOf(line), i};
                    if (location[0] == otherLocation[0] && location[1] == otherLocation[1]) {
                        continue;
                    }
                    locations.add(otherLocation);
                }
            }
        }
        return locations;
    }

    private static int findAntinodes(List<String> map, int[] location, List<int[]> otherLocations) {

        int antinodes = 0;

        for (int[] otherLocation : otherLocations) {
            int[] delta = new int[]{otherLocation[0] - location[0], otherLocation[1] - location[1]};

            int[] antinode = new int[]{location[0] - delta[0], location[1] - delta[1]};
            if (inRange(antinode, map.size(), map.get(0).length()) &&
                    !antinodeLocations.contains(antinode[0] + " " + antinode[1])) {
                antinodes++;
                antinodeLocations.add(antinode[0] + " " + antinode[1]);
            }

            antinode = new int[]{otherLocation[0] + delta[0], otherLocation[1] + delta[1]};
            if (inRange(antinode, map.size(), map.get(0).length()) &&
                    !antinodeLocations.contains(antinode[0] + " " + antinode[1])) {
                antinodes++;
                antinodeLocations.add(antinode[0] + " " + antinode[1]);
            }
        }
        return antinodes;
    }

    private static int findAllAntinodes(List<String> map, int[] location, List<int[]> otherLocations) {

        int antinodes = 0;
        if (!antinodeLocations.contains(location[0] + " " + location[1])) {
            antinodeLocations.add(location[0] + " " + location[1]);
            antinodes++;
        }

        for (int[] otherLocation : otherLocations) {

            if (!antinodeLocations.contains(otherLocation[0] + " " + otherLocation[1])) {
                antinodeLocations.add(otherLocation[0] + " " + otherLocation[1]);
                antinodes++;
            }

            int[] delta = new int[]{otherLocation[0] - location[0], otherLocation[1] - location[1]};

            int[] antinode = new int[]{location[0] - delta[0], location[1] - delta[1]};
            while (inRange(antinode, map.size(), map.get(0).length())) {
                if (!antinodeLocations.contains(antinode[0] + " " + antinode[1])) {
                    antinodes++;
                    antinodeLocations.add(antinode[0] + " " + antinode[1]);
                }
                antinode = new int[]{antinode[0] - delta[0], antinode[1] - delta[1]};
            }

            antinode = new int[]{otherLocation[0] + delta[0], otherLocation[1] + delta[1]};
            while (inRange(antinode, map.size(), map.get(0).length())) {
                if (!antinodeLocations.contains(antinode[0] + " " + antinode[1])) {
                    antinodes++;
                    antinodeLocations.add(antinode[0] + " " + antinode[1]);
                }
                antinode = new int[]{antinode[0] + delta[0], antinode[1] + delta[1]};
            }
        }
        return antinodes;
    }

    private static boolean inRange(int[] antinode, int height, int width) {

        return antinode[0] >= 0 && antinode[0] < height &&
                antinode[1] >= 0 && antinode[1] < width;
    }

    public static int findAntennas(List<String> map, boolean all) {

        int antinodes = 0;
        antinodeLocations = new ArrayList<>();

        for (int symbol = 48; symbol <= 57; symbol++) {
            antinodes += findSymbol(symbol, map, all);
        }
        for (int symbol = 65; symbol <= 90; symbol++) {
            antinodes += findSymbol(symbol, map, all);
        }
        for (int symbol = 97; symbol <= 122; symbol++) {
            antinodes += findSymbol(symbol, map, all);
        }

        return antinodes;
    }


    public static void main(String[] args) {

        List<String> map = parseInput();

        int antinodes = findAntennas(map, false);
        System.out.println("Number of antinodes: " + antinodes);

        int allAntinodes = findAntennas(map, true);
        System.out.println("Number of all antinodes: " + allAntinodes);
    }
}
