package team.h;

import java.util.List;

public class Shape extends Polygon
{
    private double costPerUnit;
    private double totalCost;

    public Shape(double costPerUnit, List<Point> points)
    {
        this.costPerUnit = costPerUnit;
        this.points = points;
        this.totalCost = calculateArea();
    }

    public double getCostPerUnit()
    {
        return costPerUnit;
    }

    public double getTotalCost()
    {
        return totalCost;
    }
}
