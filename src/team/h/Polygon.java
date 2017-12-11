package team.h;

import java.util.ArrayList;
import java.util.List;
import team.h.PartsOfPolygon.Edge;

public class Polygon
{
    protected List<Point> points;

    protected double calculateArea()
    {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < points.size()-1; i++)
            edges.add(new Edge(points.get(i), points.get(i+1)));

        for (int i = 0; i < points.size()-1; i++)
            for (int j = i+1; j < points.size()-1; j++)
            {

            }

        return 0;
    }

    public List<Point> getPoints()
    {
        return points;
    }

}
