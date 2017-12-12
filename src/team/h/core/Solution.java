package team.h.core;

import team.h.core.Shape;

import java.util.List;

public class Solution {

    private int solutionNumber;
    private List<Shape> shapes;

    private double totalCost;
    private double totalArea;

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
        return totalCost;
    }

    public double getTotalArea() {
        return totalArea;
    }
}
