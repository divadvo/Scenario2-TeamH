package team.h.core;

import team.h.core.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Shape
{

    private int costPerUnit;
    private double totalCost;
    private double area;
    private UUID uuid;

    public UUID getUuid()
    {
        return uuid;
    }

    private List<Point> points;

    //    public Shape(int costPerUnit, List<Point> points)
//    {
//        this.costPerUnit = costPerUnit;
//        this.points = points;
//
//        calculateArea();
//        calculateTotalCost();
//    }
//
    public Shape(int costPerUnit, List<Point> points, UUID uuid)
    {
        this.costPerUnit = costPerUnit;
        this.points = points;
        this.uuid = uuid;

        this.area = calculateArea();
        calculateTotalCost();
    }

    private void calculateTotalCost()
    {
        // Area * costPerUnit
        this.totalCost = area * costPerUnit;
    }

    public double calculateArea()
    {
        double sum = 0.0;
        for (int i = 0; i < points.size() - 1; i++)
        {
            sum = sum + (points.get(i).getX() * points.get(i + 1).getY()) - (points.get(i).getY() * points.get(i + 1).getX());
        }
        return Math.abs(0.5 * sum);
    }

    public double getCostPerUnit()
    {
        return costPerUnit;
    }

    public double getTotalCost()
    {
        return totalCost;
    }

    public double getArea()
    {
        return area;
    }

    public List<Point> getPoints()
    {
        return points;
    }

    public Shape translate(double dx, double dy)
    {
        List<Point> newPoints = new ArrayList<>();
        for (Point point : points)
        {
            Point newPoint = point.translate(dx, dy);
            newPoints.add(newPoint);
        }
        Shape newShape = new Shape(costPerUnit, newPoints, uuid);
        return newShape;
    }

    public void translateShape(double dx, double dy)
    {
        List<Point> newPoints = new ArrayList<>();
        for (Point point : points)
        {
            Point newPoint = point.translate(dx, dy);
            newPoints.add(newPoint);
        }
        this.points = newPoints;
    }

    public void setPoints(List<Point> points)
    {
        this.points = points;
    }

    public Shape rotate(double angle, Point centrePoint)
    {
        List<Point> newPoints = new ArrayList<>();
        Point center = centrePoint;
        for (Point point : points)
        {
            Point newPoint = rotatePoint(point, center, angle);
            newPoints.add(newPoint);
        }
        Shape newShape = new Shape(costPerUnit, newPoints, uuid);
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

        double newX = center.getX() + ( Math.cos(x) * (pt.getX()-center.getX()) + Math.sin(x) * (pt.getY() - center.getY()));
        double newY = center.getY() + ( -Math.sin(x) * (pt.getX()-center.getX()) + Math.cos(x) * (pt.getY() - center.getY()));

        Point point = new Point(newX, newY);
        return point;
    }




}
