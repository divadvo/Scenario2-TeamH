package tests;

import org.junit.Test;
import team.h.PartsOfPolygon;
import team.h.Point;
import team.h.Polygon;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PolygonTest
{
    @Test
    public void areaIsCalculatedCorrectly()
    {
        Polygon polygon = new Polygon(getSamplePoints());
        assertThat(polygon.calculateArea(), is(12.0));
    }

    @Test
    public void angleIsCalculatedCorrectly(){
        Point point1 = new Point(0,0);
        Point point2 = new Point(1,0);
        Point point3 = new Point(1,1);
        Point point4 = new Point(0,1);
        List<Point> points = new ArrayList<>();
        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        Polygon test = new Polygon(points);
        List<PartsOfPolygon.Angle> angles = test.getAngles();
        assertThat(angles.get(0).getValue(), is(Math.PI/2));
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
