package tests;

import org.junit.Test;
import team.h.Point;
import team.h.Polygon;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PolygonTest
{
    @Test
    public void calculateThatAreaCalculatedCorrectly()
    {
        Polygon polygon = new Polygon(getSamplePoints());
        assertThat(polygon.calculateArea(), is(12.0));
    }

    private List<Point> getSamplePoints()
    {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0,0));
        points.add(new Point(4,0));
        points.add(new Point(4,4));
        points.add(new Point(2,4));
        points.add(new Point(2,2));
        points.add(new Point(0,2));
        points.add(new Point(0,0));
//        points.add(new Point(0,0));
//        points.add(new Point(1,0));
//        points.add(new Point(2,3));
//        points.add(new Point(1,3));
//        points.add(new Point(1,1));
//        points.add(new Point(0,1));
//        points.add(new Point(0,0));
        return points;
    }
}
