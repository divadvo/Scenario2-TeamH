package team.h;

import java.util.List;

public class Shape extends Polygon
{
    private double totalCost;

    public Shape(double costPerUnit, List<Point> points)
    {
        super(points);
        this.totalCost = calculateTotalCost(costPerUnit);
    }

    private double calculateTotalCost(double costPerUnit)
    {
        return area * costPerUnit;
    }

    public double getTotalCost()
    {
        return totalCost;
    }

}
