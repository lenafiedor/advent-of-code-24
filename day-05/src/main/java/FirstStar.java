import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FirstStar {

    public static final String FILE_NAME = "printing_rules.txt";

    private static boolean isRuleValid(List<Integer> rule, List<Integer> list) {
        return list.indexOf(rule.get(0)) < list.indexOf(rule.get(1));
    }

    private static List<List<Integer>> validateList(
            List<List<Integer>> rules, List<List<Integer>> printList) {

        List<List<Integer>> validList = new ArrayList<>();

        for (List<Integer> list : printList) {
            boolean valid = true;
            for (List<Integer> rule : rules) {
                if (list.containsAll(rule) && !isRuleValid(rule, list)) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
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

    public static void main (String[] args) {

        List<List<Integer>> rules = new ArrayList<>();
        List<List<Integer>> printList = new ArrayList<>();
        ClassLoader classLoader = FirstStar.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(FILE_NAME)) {
            if (inputStream == null) {
                System.out.println("file is null");
                return;
            }
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) { break; }
                List<Integer> numbers = Arrays.stream(line.split(("\\|")))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                rules.add(numbers);
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<Integer> numbers = Arrays.stream(line.split((",")))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                printList.add(numbers);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        List<List<Integer>> validList = validateList(rules, printList);
        int sum = calculateSum(validList);
        System.out.println(sum);
    }

}
