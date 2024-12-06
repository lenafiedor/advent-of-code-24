import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FirstStar {

    private static final String FILE_NAME = "map.txt";

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

    public static int[] findGuard(List<String> map) {
        for (String line : map) {
            if (line.contains("^")) {
                return new int[]{map.indexOf(line), line.indexOf("^")};
            }
        }
        return new int[]{-1, -1};
    }

    public static boolean isAtEdge(int[] guardPosition, int[] mapSize) {
        return guardPosition[0] == mapSize[0] - 1 || guardPosition[1] == mapSize[1] - 1
                || guardPosition[0] == 0 || guardPosition[1] == 0;
    }

    private static Guard updateMap(Guard guard, List<String> map) {
        String row = map.get(guard.getPosition()[0]);
        if (row.charAt(guard.getPosition()[1]) != 'X') {
            guard.setVisited(guard.getVisited() + 1);
        }
        map.set(guard.getPosition()[0], row.substring(0, guard.getPosition()[1]) + "X"
                + row.substring(guard.getPosition()[1] + 1));
        return guard;
    }

    public static Guard moveGuard(Guard guard, List<String> map) {

        if (map.get(guard.position[0] - 1).charAt(guard.position[1]) == '#' && guard.dir == Direction.UP) {
            guard.setDir(Direction.RIGHT);
            Direction.move(guard);
        }
        else if (map.get(guard.position[0]).charAt(guard.position[1] + 1) == '#' && guard.getDir() == Direction.RIGHT) {
            guard.setDir(Direction.DOWN);
            Direction.move(guard);
        }
        else if (map.get(guard.position[0] + 1).charAt(guard.position[1]) == '#' && guard.getDir() == Direction.DOWN) {
            guard.setDir(Direction.LEFT);
            Direction.move(guard);
        }
        else if (map.get(guard.position[0]).charAt(guard.position[1] - 1) == '#' && guard.getDir() == Direction.LEFT) {
            guard.setDir(Direction.UP);
            Direction.move(guard);
        }
        else {
            Direction.move(guard);
        }
        guard = updateMap(guard, map);
        return guard;
    }

    public static void printMap(List<String> map) {
        for (String line : map) {
            System.out.println(line);
        }
    }

    public static int doPatrol(List<String> map) {

        Guard guard = new Guard(findGuard(map), 0, Direction.UP);
        guard = updateMap(guard, map);
        int[] mapSize = new int[]{map.size(), map.get(0).length()};

        while (!isAtEdge(guard.getPosition(), mapSize)) {
            guard = moveGuard(guard, map);
        }
        printMap(map);
        return guard.getVisited();
    }

    public static void main(String[] args) {

        List<String> map = parseInput();
        int visited = doPatrol(map);
        System.out.println("Visited fields: " + visited);
    }
}
