import java.io.InputStream;
import java.util.*;

public class Gardener {

    public static String FILE_NAME = "garden.txt";
    public static boolean[][] visited;

    private static final int[] DIR_X = { -1, 1, 0, 0 };
    private static final int[] DIR_Y = { 0, 0, -1, 1 };

    private static void initialize(List<String> garden) {
        visited = new boolean[garden.size()][garden.get(0).length()];
        for (int i = 0; i < garden.size(); i++) {
            for (int j = 0; j < garden.get(i).length(); j++) {
                visited[i][j] = false;
            }
        }
    }

    public static List<String> parseInput() {

        try {
            ClassLoader classLoader = Gardener.class.getClassLoader();
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

    private static boolean isInBound(List<String> garden, int x, int y) {
        return x >= 0 && x < garden.size() && y >= 0 && y < garden.get(0).length();
    }

    private static void dfs(List<String> garden, int x, int y, char plantType, List<int[]> region) {
        visited[x][y] = true;
        region.add(new int[]{x, y});

        for (int i = 0; i < 4; i++) {
            int newX = x + DIR_X[i];
            int newY = y + DIR_Y[i];

            if (isInBound(garden, newX, newY) &&
                    !visited[newX][newY] && garden.get(newX).charAt(newY) == plantType) {
                dfs(garden, newX, newY, plantType, region);
            }
        }
    }

    private static int calculatePerimeter(List<String> garden, List<int[]> region) {

        int perimeter = 0;

        for (int[] cell : region) {
            int x = cell[0];
            int y = cell[1];

            for (int i = 0; i < 4; i++) {
                int newX = x + DIR_X[i];
                int newY = y + DIR_Y[i];

                if (!isInBound(garden, newX, newY) ||
                        garden.get(newX).charAt(newY) != garden.get(x).charAt(y)) {
                    perimeter++;
                }
            }
        }
        return perimeter;
    }

    private static int[] hasAdjacentNeighbor(int[] neighbor, List<int[]> neighbors, List<int[]> visited) {
        for (int[] otherNeighbor : neighbors) {
            for (int i = 0; i < 4; i++) {
                if (otherNeighbor[0] == neighbor[0] + DIR_X[i] &&
                    otherNeighbor[1] == neighbor[1] + DIR_Y[i]) {
                    return otherNeighbor;
                }
            }
        }
        return null;
    }

    private static boolean contains(List<int[]> region, int[] neighbor) {
        for (int[] cell : region) {
            if (Arrays.equals(cell, neighbor)) {
                return true;
            }
        }
        return false;
    }

    private static boolean appearsMoreThanOnce(List<int[]> region, int index) {
        return contains(region.subList(index + 1, region.size()), region.get(index));
    }

    private static int calculateSides(List<int[]> region) {

        int sides = 0;
        List<int[]> neighbors = new ArrayList<>();
        List<int[]> visitedNeighbors = new ArrayList<>();

        for (int[] cell : region) {

            int x = cell[0];
            int y = cell[1];

            for (int i = 0; i < 4; i++) {
                int[] neighbor = new int[]{x + DIR_X[i], y + DIR_Y[i]};
                if (!contains(region, neighbor)) {
                    neighbors.add(neighbor);
                }
            }
        }

        for (int i = 0; i < neighbors.size(); i++) {
            int[] neighbor = neighbors.get(i);
            int[] otherNeighbor = hasAdjacentNeighbor(neighbor, neighbors, visitedNeighbors);

            if (otherNeighbor == null || !contains(visitedNeighbors, otherNeighbor)) {
                sides++;
            }
            if (!appearsMoreThanOnce(neighbors, i)) {
                 visitedNeighbors.add(neighbor);
            }
        }

        return sides;
    }

    private static int calculatePlantPrice(List<String> garden, int x, int y, boolean discount) {

        char plantType = garden.get(x).charAt(y);
        List<int[]> region = new ArrayList<>();
        dfs(garden, x, y, plantType, region);

        return discount ?
                region.size() * calculateSides(region) :
                region.size() * calculatePerimeter(garden, region);
    }

    public static int calculatePrice(List<String> garden, boolean discount) {

        int price = 0;
        initialize(garden);

        for (int i = 0; i < garden.size(); i++) {
            for (int j = 0; j < garden.get(i).length(); j++) {
                if (!visited[i][j]) {
                    price += calculatePlantPrice(garden, i, j, discount);
                }
            }
        }

        return price;
    }
}
