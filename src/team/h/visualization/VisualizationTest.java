package team.h.visualization;

import team.h.*;

import java.util.ArrayList;
import java.util.List;

public class VisualizationTest {

    public static void main(String[] args) {
        new ProblemParser("input/problems.rfp").parse();

        ArrayList<Point> roomPoints = new ArrayList<>();
        roomPoints.add(new Point(0, 0));
        roomPoints.add(new Point(20, 0));
        roomPoints.add(new Point(20, 10));
        roomPoints.add(new Point(10, 10));
        roomPoints.add(new Point(10, 20));
        roomPoints.add(new Point(0, 20));
        Room room = new Room(roomPoints);

        Problem problem = new Problem(1, room, new ArrayList<Shape>());
        List<Problem> problemList = new ArrayList<>();
        problemList.add(problem);

        new Visualizer(problemList).visualize();
    }
}
