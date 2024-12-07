import java.util.*;
import java.io.InputStream;

public class Main {

    public static String FILE_NAME = "tests.txt";

    public static Map<Long, List<Integer>> duplicates = new HashMap<>();

    public static Map<Long, List<Integer>> parseInput() {

        try {
            ClassLoader classLoader = Main.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(FILE_NAME);
            if (inputStream == null) {
                return null;
            }
            Scanner scanner = new Scanner(inputStream);
            Map<Long, List<Integer>> map = new HashMap<>();

            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(":");
                Long key = Long.parseLong(parts[0].trim());

                String[] values = parts[1].trim().split("\\s+");
                List<Integer> valueList = new ArrayList<>();

                for (String value : values) {
                    valueList.add(Integer.parseInt(value.trim()));
                }

                if (map.containsKey(key)) {
                    duplicates.put(key, valueList);
                }
                map.put(key, valueList);
            }
            return map;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean canBeProduced(Map.Entry<Long, List<Integer>> entry, boolean useConcat) {
        List<Integer> values = entry.getValue();
        long target = entry.getKey();
        return evaluateExpressionWithOperators(values, target, useConcat);
    }

    private static boolean evaluateExpressionWithOperators(List<Integer> values, long target, boolean useConcat) {
        return dfs(values, 0, values.get(0), target, useConcat);
    }

    private static boolean dfs(List<Integer> values, int index, long currentValue, long target, boolean useConcat) {
        if (index == values.size() - 1) {
            return currentValue == target;
        }

        long nextValue = values.get(index + 1);

        return dfs(values, index + 1, currentValue + nextValue, target, useConcat) ||
                dfs(values, index + 1, currentValue * nextValue, target, useConcat) ||
                (useConcat && dfs(values, index + 1, concatValues(currentValue, nextValue), target, useConcat));
    }

    private static long concatValues(long first, long second) {
        return Long.parseLong(String.valueOf(first) + String.valueOf(second));
    }

    public static long calibrate(Map<Long, List<Integer>> map, boolean useConcat) {
        return map.entrySet().stream()
                .filter(entry -> canBeProduced(entry, useConcat))
                .mapToLong(Map.Entry::getKey)
                .sum();
    }

    public static void main(String[] args) {

        Map<Long, List<Integer>> map = parseInput();

        long sum = calibrate(map, false) + calibrate(duplicates, false);
        long sumEnhanced = calibrate(map, true) + calibrate(duplicates, true);

        System.out.println("Sum: " + sum);
        System.out.println("Sum with concat: " + sumEnhanced);
    }
}
