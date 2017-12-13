package team.h.core;

import team.h.core.Point;

import java.util.List;

public class Room {

    private List<Point> points;
    private double area;

    public Room(List<Point> points) {
        this.points = points;
//        printAngle();
        calculateArea();
    }

    public List<Point> getPoints() {
        return points;
    }

    public double getArea() {
        return area;
    }

    private void printAngle() {
        // Point from 0 to 1
        Point point0 = points.get(0);
        Point point1 = points.get(points.size() - 1);
        Point vector = point1.translate(-point0.getX(), -point0.getY());
        double angle = Math.atan2(vector.getY(), vector.getX());
        double angleDeg = Math.toDegrees(angle);
        System.out.println(angle + " ANGLE " + angleDeg);
    }

    private void calculateArea() {
        double sum = 0;
        int j = points.size() - 1;
        for (int i = 0; i < points.size(); i++) {
            Point pi = points.get(i);
            Point pj = points.get(j);
            sum += (pj.getX() + pi.getX()) * (pj.getY() - pi.getY());
            j = i;  // j is previous vertex to i
        }
        this.area = Math.abs(sum / 2);
    }
}
