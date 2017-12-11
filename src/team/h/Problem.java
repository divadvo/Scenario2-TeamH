package team.h;

import java.util.List;

public class Problem {

    private int problemNumber;
    private Room room;
    private List<Shape> shapes;

    public Problem(int problemNumber, Room room, List<Shape> shapes) {
        this.problemNumber = problemNumber;
        this.room = room;
        this.shapes = shapes;
    }

    public int getProblemNumber() {
        return problemNumber;
    }

    public Room getRoom() {
        return room;
    }

    public List<Shape> getShapes() {
        return shapes;
    }
}
