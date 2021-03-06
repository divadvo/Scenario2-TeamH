package team.h.core;

import team.h.core.Point;
import team.h.core.polygons.Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Shape {

    private int costPerUnit;
    private double totalCost;
    private double area;
    private UUID uuid;

    private List<Point> points;

    public Shape(int costPerUnit, List<Point> points) {
        this.costPerUnit = costPerUnit;
        this.points = points;
        this.uuid = UUID.randomUUID();

        calculateArea();
        calculateTotalCost();
    }

    private void calculateTotalCost() {
        // Area * costPerUnit
        this.totalCost = area * costPerUnit;
    }

    private void calculateArea() {
//        double sum = 0;
//        for (int i = 0; i < points.size() - 1; i++) {
//            Point p1 = points.get(i);
//            Point p2 = points.get(i+1);
//            // TODO: solve negative area
//            sum = sum + (p1.getX() * p2.getY()) - (p1.getY() * p2.getX());
//            // sum += (points[i].X * points[i + 1].Y) - (points[i + 1].X * points[i].Y);
//        }
//        this.area = sum / 2;

        // With negative values allowed:
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

    public double getCostPerUnit() {
        return costPerUnit;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getArea() {
        return area;
    }

    public double recalculateArea() {
        calculateArea();
        return area;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Shape translate(double dx, double dy) {
        List<Point> newPoints = new ArrayList<>();
        for (Point point : points) {
            Point newPoint = point.translate(dx, dy);
            newPoints.add(newPoint);
        }
        Shape newShape = new Shape(costPerUnit, newPoints);
        return newShape;
    }

    public Shape rotate(double angle) {
        List<Point> newPoints = new ArrayList<>();

        if (points.isEmpty())
            return new Shape(costPerUnit, newPoints);

        Point center = points.get(0);
        for (Point point : points) {
            Point newPoint = rotatePoint(point, center, angle);
            newPoints.add(newPoint);
        }
        Shape newShape = new Shape(costPerUnit, newPoints);
        return newShape;
    }

    public Point rotatePoint(Point pt, Point center, double angle) {
//        double angleRad = ((angle / 180) * Math.PI);
//        double cosAngle = Math.cos(angleRad);
//        double sinAngle = Math.sin(angleRad);
//        double dx = (pt.getY() - center.getX());
//        double dy = (pt.getY() - center.getY());
//
//        double newX = center.getX() + (dx * cosAngle - dy * sinAngle);
//        double newY = center.getY() + (dx * sinAngle + dy * cosAngle);

        double x = Math.toRadians(angle);
//        double newX = center.getX() + (pt.getX()-center.getX())*Math.cos(x) - (pt.getY()-center.getY())*Math.sin(x);
//        double newY = center.getY() + (pt.getY()-center.getX())*Math.sin(x) + (pt.getY()-center.getY())*Math.cos(x);

        //newX = centerX + ( cosX * (point2X-centerX) + sinX * (point2Y -centerY))
        //newY = centerY + ( -sinX * (point2X-centerX) + cosX * (point2Y -centerY))

        double newX = center.getX() + (Math.cos(x) * (pt.getX() - center.getX()) + Math.sin(x) * (pt.getY() - center.getY()));
        double newY = center.getY() + (-Math.sin(x) * (pt.getX() - center.getX()) + Math.cos(x) * (pt.getY() - center.getY()));

        Point point = new Point(newX, newY);
        return point;
    }

    public UUID getUUID() {
        return uuid;
    }

    @Override
    public boolean equals(Object obj) {
        Shape other = (Shape) obj;
        return points.equals(other.getPoints()) && this.getCostPerUnit() == other.getCostPerUnit();
    }

    public boolean equalsWithUUID(Shape other) {
        return this.equals(other) && this.uuid.equals(other.getUUID());
    }

    public void addPoint(Point selectedPoint) {
        points.add(selectedPoint);
    }

    public boolean canBeTransformedInto(Shape anotherShape) {
//        if (this.points.size() != anotherShape.getPoints().size())
//            return false;

//        for(int i = 0; i < points.size() - 1; i++) {
//            if(this.distanceBetween(i, i+1) != anotherShape.distanceBetween(i, i+1))
//                return false;
//        }

//        if(this.getArea() != anotherShape.getArea())
//            return false;

        Polygon polygonThis = new Polygon(points);
        Polygon polygonOther = new Polygon(anotherShape.getPoints());
        return polygonThis.isSame(polygonOther);
    }

    private double distanceBetween(int i, int j) {
        Point p0 = points.get(i);
        Point p1 = points.get(j);
        Point vector = p1.translate(-p0.getX(), -p0.getY());
        double d2 = vector.getX() * vector.getX() + vector.getY() * vector.getY();
        return d2;
    }
}
