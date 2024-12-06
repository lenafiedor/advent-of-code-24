import java.util.*;

public class SecondStar {

    private static final int INFINITE_BOUNDARY = 100;

    private static void updateMap(Guard guard, List<String> map) {
        String row = map.get(guard.getPosition()[0]);
        if (row.charAt(guard.getPosition()[1]) == 'X') {
            guard.setRepeatedField(guard.getRepeatedField() + 1);
        }
        else {
            guard.setVisited(guard.getVisited() + 1);
            guard.setRepeatedField(0);
            map.set(guard.getPosition()[0], row.substring(0, guard.getPosition()[1]) + "X"
                    + row.substring(guard.getPosition()[1] + 1));
        }
    }

    private static void moveGuard(Guard guard, List<String> map) {

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
        updateMap(guard, map);
    }

    private static boolean doPatrol(List<String> map) {

        Guard guard = new Guard(FirstStar.findGuard(map), 0, Direction.UP);
        updateMap(guard, map);
        int[] mapSize = new int[]{map.size(), map.get(0).length()};

        while (!FirstStar.isAtEdge(guard.getPosition(), mapSize)) {
            moveGuard(guard, map);
            if (isInInfinite(guard)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> copyList(List<String> original, int i, int j) {
        List<String> copied = new ArrayList<>(original.size());
        for (String str : original) {
            copied.add(new String(str));
        }
        copied.set(i, original.get(i).substring(0, j) + "#" + original.get(i).substring(j + 1));
        return copied;
    }

    private static boolean isInInfinite(Guard guard) {
        return guard.getRepeatedField() > INFINITE_BOUNDARY;
    }

    private static int checkInfiniteLoops(List<String> map) {

        int infiniteLoops = 0;

        for (int i = 0; i < map.size(); i++) {
            String row = map.get(i);
            for (int j = 0; j < row.length(); j++) {
                if (row.charAt(j) == '.') {
                    List<String> copiedMap = copyList(map, i, j);
                    if (doPatrol(copiedMap)) {
                        infiniteLoops++;
                    }
                }
            }
        }
        return infiniteLoops;
    }

    public static void main(String[] args) {

        int infiniteLoops = checkInfiniteLoops(Objects.requireNonNull(FirstStar.parseInput()));
        System.out.println("Number of infinite loops: " + infiniteLoops);
    }
}
