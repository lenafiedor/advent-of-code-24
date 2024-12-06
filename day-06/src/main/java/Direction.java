import java.util.List;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static void move(Guard guard) {
        int[] position = guard.getPosition();
        switch (guard.getDir()) {
            case UP:
                guard.setPosition(new int[]{position[0] - 1, position[1]});
                break;
            case DOWN:
                guard.setPosition(new int[]{position[0] + 1, position[1]});
                break;
            case LEFT:
                guard.setPosition(new int[]{position[0], position[1] - 1});
                break;
            case RIGHT:
                guard.setPosition(new int[]{position[0], position[1] + 1});
                break;
        }
    }
}
