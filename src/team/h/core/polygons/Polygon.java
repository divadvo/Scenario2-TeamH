package team.h.core.polygons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import team.h.core.Point;

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

    public List<PartsOfPolygon.Edge> getEdges()
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
            edges.add(new PartsOfPolygon.Edge(points.get(i), points.get(i + 1)));
        }
        edges.add(new PartsOfPolygon.Edge(points.get(size - 1), points.get(0)));
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

    public boolean isSame(Polygon other) {
        if(this.edges.size() != other.edges.size())
            return false;

        List<Double> listOfThisLengths = new ArrayList<>();
        List<Double> listOfOtherLengths = new ArrayList<>();

        for(int i = 0; i < edges.size(); i++) {
            listOfThisLengths.add(edges.get(i).getLength());
            listOfOtherLengths.add(other.getEdges().get(i).getLength());
        }

        // Compare if two lists are the same
//        listOfThisLengths.removeAll( listOfOtherLengths );
        removeAllEpsilon(listOfThisLengths, listOfOtherLengths, 0.001);

        if(!listOfThisLengths.isEmpty())
            return false;

        List<Double> listOfThisAngles = new ArrayList<>();
        List<Double> listOfOtherAngles = new ArrayList<>();

        for(int i = 0; i < edges.size(); i++) {
            listOfThisAngles.add(angles.get(i).getValue());
            listOfOtherAngles.add(other.getAngles().get(i).getValue());
        }

        // Compare two lists
//        listOfThisAngles.removeAll(listOfOtherAngles);
        removeAllEpsilon(listOfThisAngles, listOfOtherAngles, 0.001);

        if (!listOfThisAngles.isEmpty())
            return false;

        return true;
    }

    private void removeAllEpsilon(List<Double> a, List<Double> b, double precision) {
        List<Integer> location = new ArrayList<>();
        for(int i = 0; i < a.size(); i++) {
            for(int j = 0; j < b.size(); j++) {
                if(Math.abs(i - j) <= precision)
                    location.add(i);
            }
        }

        // Sort indices from largest to smallest, so we can remove mlutiple elements
        // and don't change the existing indexes
        Collections.sort(location);
        Collections.reverse(location);

        for(Integer i : location) {
            a.remove(i.intValue());
        }
    }


}