package team.h;

import java.util.List;

public class Shape {

    private double costPerUnit;
    private double totalCost;
    private List<Point> points;

    public Shape(double costPerUnit, List<Point> points) {
        this.costPerUnit = costPerUnit;
        this.points = points;

        this.totalCost = calculateTotalCost();
    }

    private double calculateTotalCost() {
        // Area * costPerUnit
        return 0;
    }


}
