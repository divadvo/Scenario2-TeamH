package team.h.algorithm;

import team.h.core.Point;
import team.h.core.Problem;
import team.h.core.Shape;
import team.h.core.Solution;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private Problem problem;
    private Solution solution;

    public Solver(Problem problem) {
        this.problem = problem;
    }

    public Solution solve() {
        // TODO: change this
        List<Shape> shapes = new ArrayList<>();
        List<Point> points = new ArrayList<>();
        points.add(new Point(0,0));
        points.add(new Point(0, 1));
        points.add(new Point(1, 1));
        points.add(new Point(1, 0));
        Shape shape1 = new Shape(1, points);

//        List<Point> points = new ArrayList<>();
//        points.add(new Point(0,0));
//        points.add(new Point(0, 10));
//        points.add(new Point(10, 10));
//        points.add(new Point(10, 0));
//        Shape shape1 = new Shape(1, points);

        shapes.add(shape1);
        shapes.add(shape1.translate(-0.5, -0.5));
        this.solution = new Solution(0, shapes);

        // TODO: DO THE MAGIC HERE
        // this.solution = doMagic();
        return solution;
    }
}
