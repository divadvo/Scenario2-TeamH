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

//        System.out.println(problemNumber);
//        System.out.println();
//        System.out.println("Room:");
//        for (Point point : room.getPoints()) {
//            System.out.print(point.getX());
//            System.out.print(", ");
//            System.out.println(point.getY());
//        }
//        System.out.println();
//        System.out.println("Shapes:");
//        for (Shape shape : shapes) {
//            System.out.println(shape.getCostPerUnit());
//            for (Point point : shape.getPoints()) {
//                System.out.print(point.getX());
//                System.out.print(", ");
//                System.out.println(point.getY());
//            }
//        }
//        System.out.println("-----------------------------------------------------------------");
        for (PartsOfPolygon.Angle angle : room.getAngles()) {
            System.out.println(angle.getValue());
            System.out.println();
        }
        for (Shape shape : shapes) {
            for (PartsOfPolygon.Angle angle : shape.getAngles()) {
                System.out.println(angle.getValue());
                System.out.println();
            }
        }
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
