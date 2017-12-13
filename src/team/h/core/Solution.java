package team.h.core;

import team.h.core.Shape;

import java.util.List;

public class Solution {

    private int solutionNumber;
    private List<Shape> shapes;


    public Solution(int solutionNumber, List<Shape> shapes) {
        this.solutionNumber = solutionNumber;
        this.shapes = shapes;
    }

    public int getSolutionNumber() {
        return solutionNumber;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public double getTotalCost() {
        double totalCost = 0;
        for(Shape shape : shapes)
            totalCost += shape.getTotalCost();
        return totalCost;
    }

    public double getTotalArea() {
        double totalArea = 0;
        for(Shape shape : shapes)
            totalArea += shape.getArea();
        return totalArea;
    }
}
