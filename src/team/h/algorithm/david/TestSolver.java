package team.h.algorithm.david;

import team.h.algorithm.Solver;
import team.h.core.Point;
import team.h.core.Problem;
import team.h.core.Shape;
import team.h.core.Solution;

import java.util.ArrayList;
import java.util.List;

public class TestSolver extends Solver{

    public TestSolver(Problem problem) {
        super(problem);
    }

    @Override
    public Solution solve() {
        // TODO: change this
        List<Shape> shapes = new ArrayList<>();
        List<Point> points = new ArrayList<>();
        points.add(new Point(0,0));
        points.add(new Point(1, 0));
        points.add(new Point(1, 1));
        points.add(new Point(0, 1));
        Shape shape1 = new Shape(1, points);

//        List<Point> points = new ArrayList<>();
//        points.add(new Point(0,0));
//        points.add(new Point(0, 10));
//        points.add(new Point(10, 10));
//        points.add(new Point(10, 0));
//        Shape shape1 = new Shape(1, points);

        shapes.add(shape1);
        Shape shape2 = shape1.translate(1.5, 1.5);
        shapes.add(shape2);
        this.solution = new Solution(0, shapes);

        // TODO: DO THE MAGIC HERE
        // this.solution = doMagic();
        return solution;
    }
}
