package team.h.algorithm.david;

import team.h.algorithm.Solver;
import team.h.core.Point;
import team.h.core.Problem;
import team.h.core.Shape;
import team.h.core.Solution;
import team.h.visualization.DrawableShape;
import team.h.visualization.Utils;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.*;

public class DavidBruteForceSolver extends Solver {

    public DavidBruteForceSolver(Problem problem) {
        super(problem);
    }

    @Override
    public Solution solve() {
        return null;
    }

    private void test() {
        List<Shape> shapeList = problem.getShapes();

        List<GeneralPath> paths = new ArrayList<>();
        Map<GeneralPath, Shape> map = new HashMap<>();

        for (Shape shape : shapeList) {
            List<Point> shapePoints = shape.getPoints();
            GeneralPath path = new DrawableShape(shapePoints).generatePath();

            Rectangle2D rectangle = path.getBounds2D();

            AffineTransform affineTransform = new AffineTransform();
            if (rectangle.getX() < 0)
                affineTransform.scale(-1, 1);

            if (rectangle.getY() < 0)
                affineTransform.scale(1, -1);

            path.transform(affineTransform);
            paths.add(path);
            map.put(path, shape);
        }

        Collections.sort(paths, (o1, o2) -> {
            Rectangle2D bounds1 = o1.getBounds2D();
            Rectangle2D bounds2 = o2.getBounds2D();

            return (int) (  -Math.abs(bounds1.getWidth() * bounds1.getHeight()) + Math.abs(bounds2.getWidth() * bounds2.getHeight()) );
        });
    }


    private void drawShapesRandom() {
        List<Shape> shapeList = problem.getShapes();

        Collections.sort(shapeList, Comparator.comparing(o -> o.getTotalCost()));

        List<Point> roomPoints = problem.getRoom().getPoints();
        GeneralPath roomPath = new DrawableShape(roomPoints).generatePath();

        Set<java.awt.Shape> addedShapes = new HashSet<>();
        addedShapes.add(roomPath);

        for (Shape shape : shapeList) {
            GeneralPath path;
            int i = 1;
            do {
                Shape newShape = shape.translate(randomNumber() * i, randomNumber() * i);
                List<Point> shapePoints = newShape.getPoints();
                path = new DrawableShape(shapePoints).generatePath();
                i++;
            } while (doesIntersectWithAnyOther(path, addedShapes));

            // Draw path

            addedShapes.add(path);
        }
    }

    private boolean doesIntersectWithAnyOther(java.awt.Shape shape, Set<java.awt.Shape> addedShapes) {
        for (java.awt.Shape shape1 : addedShapes) {
            if (Utils.areShapesIntersecting(shape, shape1))
                return true;
        }
        return false;
    }

    private double randomNumber() {
        return new Random().nextDouble() - 0.5;
    }
}
