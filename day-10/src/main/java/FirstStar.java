import java.io.InputStream;
import java.util.*;

public class FirstStar {

    public static final String FILE_NAME = "map.txt";

    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    public static List<String> parseInput() {

        try {
            ClassLoader classLoader = FirstStar.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(FILE_NAME);
            if (inputStream == null) {
                return Collections.emptyList();
            }
            Scanner scanner = new Scanner(inputStream);
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            return lines;

        } catch (Exception e) {
            throw new RuntimeException("Error parsing file: " + FILE_NAME, e);
        }
    }

    private static List<Location> findTrailheads(List<String> map) {
        List<Location> trailheads = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            String line = map.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '0') {
                    trailheads.add(new Location(i, j));
                }
            }
        }
        return trailheads;
    }

    private static boolean canMove(List<String> map, int x, int y, boolean[][] visited, boolean rating) {
        if (rating) {
            return x >= 0 && x < map.size() && y >= 0 && y < map.get(x).length();
        }
        if (x >= 0 && x < map.size() && y >= 0 && y < map.get(x).length()) {
            char index = map.get(x).charAt(y);
            return index != '9' || !visited[x][y];
        }
        return false;
    }

    private static int exploreTrail(Location trailhead, List<String> map, boolean rating) {
        int count = 0;
        boolean[][] visited = new boolean[map.size()][map.get(0).length()];
        Stack<Location> stack = new Stack<>();
        stack.push(trailhead);

        while (!stack.isEmpty()) {

            Location current = stack.pop();
            visited[current.x()][current.y()] = true;

            if (map.get(current.x()).charAt(current.y()) == '9') {
                count++;
                continue;
            }

            for (int i = 0; i < 4; i++) {
                int newX = current.x() + dx[i];
                int newY = current.y() + dy[i];

                if (canMove(map, newX, newY, visited, rating)) {
                    char currentHeight = map.get(current.x()).charAt(current.y());
                    char nextHeight = map.get(newX).charAt(newY);

                    if (nextHeight == currentHeight + 1) {
                        visited[newX][newY] = true;
                        stack.push(new Location(newX, newY));
                    }
                }
            }
        }

        return count;
    }

    public static int findTrails(List<String> map, boolean rating) {

        int score = 0;
        List<Location> trailheads = findTrailheads(map);

        for (Location trailhead : trailheads) {
            score += exploreTrail(trailhead, map, rating);
        }

        return score;
    }

    public static void main(String[] args) {
        List<String> map = parseInput();
        int score = findTrails(map, false);
        int individualScore = findTrails(map, true);

        System.out.println("Score: " + score);
        System.out.println("Individual Score: " + individualScore);
    }
}
