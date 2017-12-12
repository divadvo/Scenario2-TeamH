package team.h.core;

import java.util.List;

public class Problem {

    private int problemNumber;
    private Room room;
    private List<Shape> shapes;

    public Problem(int problemNumber, Room room, List<Shape> shapes) {
        this.problemNumber = problemNumber;
        this.room = room;
        this.shapes = shapes;

//        printInfo();
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

    public void printInfo() {
        System.out.println(problemNumber);
        System.out.println();
        System.out.println("Room:");
        for (Point point : room.getPoints()) {
            System.out.print(point.getX());
            System.out.print(", ");
            System.out.println(point.getY());
        }
        System.out.println();
        System.out.println("Shapes: number=" + shapes.size());
        for (Shape shape : shapes) {
            System.out.println("\n---------------\n" + shape.getCostPerUnit());
            for (Point point : shape.getPoints()) {
                System.out.print(point.getX());
                System.out.print(", ");
                System.out.println(point.getY());
            }
        }
        int maxNumberOfVertices = 0;
        for (Shape shape : shapes) {
            if(shape.getPoints().size() > maxNumberOfVertices)
                maxNumberOfVertices = shape.getPoints().size();
        }
        System.out.println("Max number of vertices = " + maxNumberOfVertices);
        System.out.println("-----------------------------------------------------------------");
    }
}
