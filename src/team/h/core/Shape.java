package team.h.core;

import team.h.core.Point;

import java.util.ArrayList;
import java.util.List;

public class Shape {

    private int costPerUnit;
    private double totalCost;
    private double area;

    private List<Point> points;

    public Shape(int costPerUnit, List<Point> points) {
        this.costPerUnit = costPerUnit;
        this.points = points;

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
        for (int i = 0; i < points.size(); i++)
        {
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
}
