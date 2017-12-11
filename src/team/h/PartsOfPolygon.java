package team.h;

import java.util.List;

public class PartsOfPolygon
{
    static class Angle
    {
        private double value;

        public Angle(double value) {
            this.value = value;
        }

        public double getValue()
        {
            return value;
        }
    }

    static class Edge
    {
        private Point a,b; // a is the first point given, b the second

        public Edge(Point a, Point b)
        {
            this.a = a;
            this.b = b;
        }

        public Point getA()
        {
            return a;
        }

        public Point getB()
        {
            return b;
        }
    }

    static class Triangle
    {
        private Edge e1, e2, e3;

        public Triangle(Edge e1, Edge e2, Edge e3)
        {
            this.e1 = e1;
            this.e2 = e2;
            this.e3 = e3;
        }
    }
}
