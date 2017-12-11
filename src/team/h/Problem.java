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
}
