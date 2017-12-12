package team.h;

public class PartsOfPolygon
{
    //----------------------------------------------------------------------------------------------------
    public static class Angle
    {
        private double value;

        public Angle(Edge a, Edge b)
        {
           this.value = calculateAngleBetween(a,b);
        }

        public double getValue()
        {
            return value;
        }

        private double calculateAngleBetween(PartsOfPolygon.Edge edgeA, PartsOfPolygon.Edge edgeB)
        {
            Point startPoint = edgeA.getA();
            Point centerPoint = edgeA.getB();
            Point endPoint = edgeB.getB();
            double[] vectorA = {centerPoint.getX() - startPoint.getX(), centerPoint.getY() - startPoint.getY()};
            double[] vectorB = {endPoint.getX() - centerPoint.getX(), endPoint.getY() - centerPoint.getY()};
            double dotProduct = dotProduct(vectorA, vectorB);
            double magnitudes = magnitude(vectorA) * magnitude(vectorB);

            return Math.acos(dotProduct / magnitudes);
        }

        private double dotProduct(double[] vectorA, double[] vectorB)
        {
            int size = vectorA.length;
            double sum = 0;
            for (int i = 0; i < size; i++)
            {
                sum += vectorA[i] * vectorB[i];
            }
            return sum;
        }

        private double magnitude(double[] vector)
        {
            return Math.sqrt(dotProduct(vector, vector));
        }
    }

    //----------------------------------------------------------------------------------------------------

    static class Edge
    {
        private Point a, b; // a is the first point given, b the second

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

    //----------------------------------------------------------------------------------------------------
}
