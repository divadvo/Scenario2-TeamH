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

        System.out.println(problemNumber);
        System.out.println();
        System.out.println("Room:");
        for (Point point : room.getPoints()) {
            System.out.print(point.getX());
            System.out.print(", ");
            System.out.println(point.getY());
        }
        System.out.println();
        System.out.println("Shapes:");
        for (Shape shape : shapes) {
            System.out.println(shape.getCostPerUnit());
            for (Point point : shape.getPoints()) {
                System.out.print(point.getX());
                System.out.print(", ");
                System.out.println(point.getY());
            }
        }
        System.out.println("-----------------------------------------------------------------");
    }
}
