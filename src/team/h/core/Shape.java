package team.h.core;

import team.h.core.Point;

import java.util.ArrayList;
import java.util.List;

public class Shape {

    private int costPerUnit;
    private double totalCost;

    private List<Point> points;

    public Shape(int costPerUnit, List<Point> points) {
        this.costPerUnit = costPerUnit;
        this.points = points;

        this.totalCost = calculateTotalCost();
    }

    private double calculateTotalCost() {
        // Area * costPerUnit
        return 0;
    }

    public double getCostPerUnit() {
        return costPerUnit;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Shape translate(double dx, double dy) {
        List<Point> newPoints = new ArrayList<>();
        for(Point point : points) {
            Point newPoint = point.translate(dx, dy);
            newPoints.add(newPoint);
        }
        Shape newShape = new Shape(costPerUnit, newPoints);
        return newShape;
    }
}
