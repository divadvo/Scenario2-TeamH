package team.h;

import java.util.List;

public class Shape extends Polygon
{
    private double costPerUnit;
    private double totalCost;

    public double getCostPerUnit() {
        return costPerUnit;
    }
    public double getTotalCost() {
        return totalCost;
    }

    public Shape(double costPerUnit, List<Point> points) {
        super(points);
        this.costPerUnit = costPerUnit;
        this.totalCost = calculateTotalCost();
    }

    private double calculateTotalCost(){
        return area * costPerUnit;
    }

}
