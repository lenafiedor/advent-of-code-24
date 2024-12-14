import java.io.InputStream;
import java.util.*;

public class FirstStar {

    public static final String FILE_NAME = "antennas.txt";
    public static List<String> antinodeLocations = new ArrayList<>();

    public static List<String> parseInput() {

        try {
            ClassLoader classLoader = FirstStar.class.getClassLoader();
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

    private static int findSymbol(int symbol, List<String> map) {

        int antinodes = 0;
        char c = (char) symbol;

        for (String line : map) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == symbol) {
                    int[] location = new int[]{map.indexOf(line), i};
                    List<int[]> otherLocations = findOtherSymbols(symbol, map, location);
                    antinodes += findAntinodes(map, location, otherLocations);
                }
            }
        }
        return antinodes;
    }

    private static List<int[]> findOtherSymbols(int symbol, List<String> map, int[] location) {

        List<int[]> locations = new ArrayList<>();
        char c = (char) symbol;

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
            if (canBePlaced(antinode, map.size(), map.get(0).length())) {
                antinodes += 1;
                antinodeLocations.add(antinode[0] + " " + antinode[1]);
            }

            antinode = new int[]{otherLocation[0] + delta[0], otherLocation[1] + delta[1]};
            if (canBePlaced(antinode, map.size(), map.get(0).length())) {
                antinodes += 1;
                antinodeLocations.add(antinode[0] + " " + antinode[1]);
            }
        }
        return antinodes;
    }

    private static boolean canBePlaced(int[] antinode, int height, int width) {

        boolean inRange = antinode[0] >= 0 && antinode[0] < height &&
                antinode[1] >= 0 && antinode[1] < width;

        return inRange && !antinodeLocations.contains(antinode[0] + " " + antinode[1]);
    }

    public static int findAntennas(List<String> map) {

        int antinodes = 0;

        for (int symbol = 48; symbol <= 57; symbol++) {
            antinodes += findSymbol(symbol, map);
        }
        for (int symbol = 65; symbol <= 90; symbol++) {
            antinodes += findSymbol(symbol, map);
        }
        for (int symbol = 97; symbol <= 122; symbol++) {
            antinodes += findSymbol(symbol, map);
        }

        return antinodes;
    }


    public static void main(String[] args) {

        List<String> map = parseInput();
        int antinodes = findAntennas(map);

        System.out.println("Number of antinodes: " + antinodes);
    }
}
