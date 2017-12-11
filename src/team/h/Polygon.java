package team.h;

import java.util.ArrayList;
import java.util.List;
import team.h.PartsOfPolygon.Edge;

public class Polygon
{
    protected List<Point> points;
    private List<PartsOfPolygon.Angle> angles = new ArrayList<>();
    private List<PartsOfPolygon.Edge> edges = new ArrayList<>();
    protected double area;

    public List<Point> getPoints()
    {
        return points;
    }
    public List<PartsOfPolygon.Angle> getAngles() {
        return angles;
    }
    public List<Edge> getEdges() {
        return edges;
    }
    public double getArea() {
        return area;
    }

    public Polygon(List<Point> points) {
        this.points = points;
        createEdges();
        createAngles();
        calculateArea();
    }

    private double calculateArea() {
        for (int i = 0; i < points.size()-1; i++)
            for (int j = i+1; j < points.size()-1; j++)
            {

            }

        return 0;
    }

    private void createEdges() {
        int size = points.size();
        for (int i=0; i < size-1; i++) {
            edges.add(new Edge(points.get(i), points.get(i+1)));
        }
        edges.add(new Edge(points.get(size-1), points.get(0)));
    }

    private void createAngles() {
        int size = edges.size();
        for (int i=0; i < size-1; i++) {
            angles.add(calculateAngleBetween(edges.get(i), edges.get(i+1)));
        }
        angles.add(calculateAngleBetween(edges.get(size-1), edges.get(0)));

    }

    private PartsOfPolygon.Angle calculateAngleBetween(PartsOfPolygon.Edge edgeA, PartsOfPolygon.Edge edgeB) {
        Point startPoint = edgeA.getA();
        Point centerPoint = edgeA.getB();
        Point endPoint = edgeB.getB();
        double[] vectorA = {centerPoint.getX() - startPoint.getX(), centerPoint.getY() - startPoint.getY()};
        double[] vectorB = {endPoint.getX() - centerPoint.getX(), endPoint.getY() - centerPoint.getY()};
        double dotProduct = dotProduct(vectorA, vectorB);
        double magnitudes = magnitude(vectorA) * magnitude(vectorB);
        return (new PartsOfPolygon.Angle(Math.acos(dotProduct/magnitudes)));
    }

    private double dotProduct(double[] vectorA, double[] vectorB) {
        int size = vectorA.length;
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += vectorA[i] * vectorB[i];
        }
        return sum;
    }

    private double magnitude(double[] vector) {
        return Math.sqrt(dotProduct(vector, vector));
    }

}
