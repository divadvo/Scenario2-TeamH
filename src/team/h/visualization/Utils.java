package team.h.visualization;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

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

    public static Area getAreaOfIntersection(Shape shapeA, Shape shapeB)
    {
        Area area = new Area(shapeA);
        area.intersect(new Area(shapeB));

        return area;
    }

}
