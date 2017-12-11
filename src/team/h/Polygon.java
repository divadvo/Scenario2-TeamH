package team.h;

import java.util.ArrayList;
import java.util.List;

import team.h.PartsOfPolygon.Edge;

public class Polygon
{
    private List<Point> points;
    private List<PartsOfPolygon.Angle> angles = new ArrayList<>();
    private List<PartsOfPolygon.Edge> edges = new ArrayList<>();
    protected double area;

    public List<Point> getPoints()
    {
        return points;
    }

    public List<PartsOfPolygon.Angle> getAngles()
    {
        return angles;
    }

    public List<Edge> getEdges()
    {
        return edges;
    }

    public double getArea()
    {
        return area;
    }

    public Polygon(List<Point> points)
    {
        this.points = points;
        createEdges();
        createAngles();
        calculateArea();
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

    private void createEdges()
    {
        int size = points.size();
        for (int i = 0; i < size - 1; i++)
        {
            edges.add(new Edge(points.get(i), points.get(i + 1)));
        }
        edges.add(new Edge(points.get(size - 1), points.get(0)));
    }

    private void createAngles()
    {
        int size = edges.size();
        for (int i = 0; i < size - 1; i++)
        {
            angles.add(new PartsOfPolygon.Angle(edges.get(i), edges.get(i + 1)));
        }
        angles.add(new PartsOfPolygon.Angle(edges.get(size - 1), edges.get(0)));

    }


}
