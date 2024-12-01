import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class Main {
git
    public static List<Integer> locationsFirst = new ArrayList<>();
    public static List<Integer> locationsSecond = new ArrayList<>();

    public static long calculateDifference() {
        return IntStream.range(0, locationsFirst.size())
                .mapToLong(i -> abs(locationsFirst.get(i) - locationsSecond.get(i)))
                .sum();
    }

    public static long calculateSimilarity() {
        return locationsFirst.stream()
                .mapToLong(i -> {
                    long count = Collections.frequency(locationsSecond, i);
                    return count * i;
                })
                .sum();
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("locations.txt"));
        while (scanner.hasNextLine()) {
            String[] locations = scanner.nextLine().trim().split("\\s+");
            locationsFirst.add(Integer.valueOf(locations[0]));
            locationsSecond.add(Integer.valueOf(locations[1]));
        }

        locationsFirst.sort(Integer::compareTo);
        locationsSecond.sort(Integer::compareTo);

        long difference = calculateDifference();
        System.out.println("Difference: " + difference);

        long similarity = calculateSimilarity();
        System.out.println("Similarity: " + similarity);
    }
}