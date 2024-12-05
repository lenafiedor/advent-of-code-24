import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class SecondStar {

    public static final String FILE_NAME = "printing_rules.txt";

    private static boolean isRuleInvalid(List<Integer> rule, List<Integer> list) {
        return list.indexOf(rule.get(0)) > list.indexOf(rule.get(1));
    }

    private static void swap(List<Integer> list, List<Integer> rule) {

        int first = rule.get(0);
        int second = rule.get(1);

        Collections.swap(list, list.indexOf(first), list.indexOf(second));
    }

    private static boolean isListInvalid(List<Integer> list, List<List<Integer>> rules) {

        for (List<Integer> rule : rules) {
            if (new HashSet<>(list).containsAll(rule) && isRuleInvalid(rule, list)) {
                return true;
            }
        }
        return false;
    }

    private static void updateList(List<Integer> list, List<List<Integer>> rules) {

        while (isListInvalid(list, rules)) {
            for (List<Integer> rule : rules) {
                if (new HashSet<>(list).containsAll(rule) && isRuleInvalid(rule, list)) {
                    swap(list, rule);
                }
            }
        }
    }

    private static List<List<Integer>> validateList(
            List<List<Integer>> rules, List<List<Integer>> printList) {

        List<List<Integer>> validList = new ArrayList<>();

        for (List<Integer> list : printList) {
            if (isListInvalid(list, rules)) {
                updateList(list, rules);
                validList.add(list);
            }
        }
        return validList;
    }

    private static int calculateSum(List<List<Integer>> printList) {
        return printList.stream()
                .mapToInt(list -> list.get(list.size() / 2))
                .sum();
    }

    private static List<List<Integer>> parseInput(Scanner scanner, String regex) {

        List<List<Integer>> input = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            List<Integer> numbers = Arrays.stream(line.split((regex)))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            input.add(numbers);
        }
        return input;
    }

    public static void main (String[] args) {

        ClassLoader classLoader = SecondStar.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(FILE_NAME)) {
            if (inputStream == null) {
                return;
            }
            Scanner scanner = new Scanner(inputStream);

            List<List<Integer>> rules = parseInput(scanner, "\\|");
            List<List<Integer>> printList = parseInput(scanner, ",");

            int sum = calculateSum(validateList(rules, printList));
            System.out.println("Sum: " + sum);

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
