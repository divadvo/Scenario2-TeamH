package team.h.visualization;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.Random;

public class Utils {

    public static boolean areShapesIntersecting(Shape shapeA, Shape shapeB) {
        Area areaA = new Area(shapeA);
//        AffineTransform affineTransform = new AffineTransform();
//        double percent = 1.1;
//        affineTransform.scale(percent, percent);
//        areaA.transform(affineTransform);
//        areaA = areaA.createTransformedArea(affineTransform);
        areaA.intersect(new Area(shapeB));
        return !areaA.isEmpty();
    }

    public static boolean isShapeInsideAnother(Shape bigger, Shape shapeB) {
        Area areaBigger = new Area(bigger);
//        System.out.println(areaBigger);
        Area areaB = new Area(shapeB);
        areaBigger.intersect(areaB);

        // If the intersection is the same as the smaller shape,
        // then the smaller shape is inside the bigger
        return !areaBigger.isEmpty() && areaB.equals(areaBigger);
    }

    public static double randomNumber() {
        return new Random().nextDouble() - 0.5; // -0.5 to +0.5
    }

    public static team.h.core.Point generateRandomPointBetween(Point2D firstPoint, Point2D secondPoint) {
        double x = randomInRange(firstPoint.getX(), secondPoint.getX());
        double y = randomInRange(firstPoint.getY(), secondPoint.getY());
        return new team.h.core.Point(x, y);
    }

    private static double randomInRange(double rangeMin, double rangeMax) {
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        return randomValue;
    }
}
