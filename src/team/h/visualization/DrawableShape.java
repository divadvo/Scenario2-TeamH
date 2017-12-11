package team.h.visualization;

import team.h.Point;

import java.awt.geom.GeneralPath;
import java.util.List;

public class DrawableShape {

    private List<Point> points;

    public DrawableShape(List<Point> points) {
        this.points = points;
    }

    public GeneralPath generatePath() {
        GeneralPath path = new GeneralPath();

        Point firstPoint = points.get(0);
        path.moveTo(firstPoint.getX(), firstPoint.getY());

        for (int i = 1; i < points.size(); i++) {
            Point point = points.get(i);
            path.lineTo(point.getX(), point.getY());
        }

        path.closePath();

        return path;
    }
}
