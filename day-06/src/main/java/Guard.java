public class Guard {

    int[] position = new int[2];
    int visited;
    Direction dir;
    int repeatedField = 0;

    Guard(int[] position, int visited, Direction dir) {
        this.position = position;
        this.visited = visited;
        this.dir = dir;
        this.repeatedField = 0;
    }

    public int[] getPosition() {
        return position;
    }
    public int getVisited() {
        return visited;
    }
    public Direction getDir() {
        return dir;
    }
    public int getRepeatedField() {
        return repeatedField;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }
    public void setVisited(int visited) {
        this.visited = visited;
    }
    public void setDir(Direction dir) {
        this.dir = dir;
    }
    public void setRepeatedField(int repeatedField) {
        this.repeatedField = repeatedField;
    }
}