package team.h.algorithm.AngleCheckAlgorithm;

import team.h.algorithm.Solver;
import team.h.core.*;
import team.h.visualization.DrawableShape;
import team.h.visualization.Utils;

import javax.rmi.CORBA.Util;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.*;

public class SolverByAngleMatch extends Solver
{
    List<Shape> shapesSolution = new ArrayList<>();
    List<Shape> shapes = new ArrayList<>();

    public SolverByAngleMatch(Problem problem)
    {
        super(problem);
    }

    @Override
    public Solution solve()
    {
        List<Shape> solutionsForSingleVertexes = new ArrayList<>();
        shapes = problem.getShapes();
        Collections.sort(shapes, Comparator.comparing(o -> -o.getTotalCost()));
        Room room = problem.getRoom();
        int counter = 0;

        for (Point roomPoint : room.getPoints())
        {
            List<Shape> possibleShapeSolutionsForSpecificVertex = new ArrayList<>();
            for (int i = 0; i < shapes.size() / 7; i++)
            {
                Shape temp = bestTranslatedShape(roomPoint, shapes.get(i));
                if (temp != null)
                    possibleShapeSolutionsForSpecificVertex.add(temp);

            }

            double highestCost = 0;
            Shape solutionShape = null;
            if (!possibleShapeSolutionsForSpecificVertex.isEmpty())
            {
                for (Shape shape : possibleShapeSolutionsForSpecificVertex)
                {
                    if (shape != null && shape.getTotalCost() > highestCost)
                    {
                        solutionShape = shape;
                        highestCost = shape.getTotalCost();
                    }
                }
                solutionsForSingleVertexes.add(solutionShape);
            }
            System.out.println("Done " + counter++ +" vertx out of " + room.getPoints().size());
        }

        addCorrectlyShapesToSolution(solutionsForSingleVertexes);


        solution = new Solution(problem.getProblemNumber(), shapesSolution);
        return solution;
    }

    private void addCorrectlyShapesToSolution(List<Shape> solutionsForSingleVertexes)
    {
        Collections.sort(solutionsForSingleVertexes, Comparator.comparing(o -> -o.getTotalCost()));
        System.out.println(solutionsForSingleVertexes.get(0).getTotalCost()+" should be the highest");
        System.out.println(solutionsForSingleVertexes.get(solutionsForSingleVertexes.size()-1).getTotalCost()+" should be the smallest");
        shapesSolution.add(solutionsForSingleVertexes.get(0));

        for (int i = 1; i < solutionsForSingleVertexes.size(); i++)
        {
            if (!shapeDoesIntercectWithOthers(solutionsForSingleVertexes.get(i)))
            {
                shapesSolution.add(solutionsForSingleVertexes.get(i));
                deleteShape(solutionsForSingleVertexes, solutionsForSingleVertexes.get(i).getUuid());
            }
        }

    }


    private void deleteShape(List<Shape> shapes, UUID uuid)
    {
        int i = 0;
        while (i < shapes.size())
        {
            if (shapes.get(i).getUuid() == uuid)
            {
                shapes.remove(i);
                return;
            }
            i++;
        }
    }

    private Shape bestTranslatedShape(Point pointOfTranslation, Shape shape)
    {
        Shape tempShape;
        Area tempArea = null;
        GeneralPath room = new DrawableShape(problem.getRoom().getPoints()).generatePath();

        for (Point p : shape.getPoints())
        {
            tempShape = getProperlyTranslatedShapes(shape, p, pointOfTranslation);
            tempArea = Utils.getAreaOfIntersection(room, new DrawableShape(tempShape.getPoints()).generatePath());

            if (tempArea != null && !tempArea.isEmpty())
            {
                for (int i = 0; i<360; i++)
                {
                    if (new Area(new DrawableShape(tempShape.getPoints()).generatePath()).equals(tempArea))
                    {
                        return tempShape;
                    }
                    tempShape = tempShape.rotate(1, pointOfTranslation);
                    tempArea = Utils.getAreaOfIntersection(room, new DrawableShape(tempShape.getPoints()).generatePath());
                }
            }
        }

        return null;
    }

    private Shape getProperlyTranslatedShapes(Shape shape, Point pointOfMoving, Point pointToMoveTo)
    {
        double xDiff = pointToMoveTo.getX() - pointOfMoving.getX();
        double yDiff = pointToMoveTo.getY() - pointOfMoving.getY();

        return shape.translate(xDiff, yDiff);
    }

    private boolean shapeDoesIntercectWithOthers(Shape tempShape)
    {
        if (shapesSolution.size() == 0)
            return false;

        for (Shape shape : shapesSolution)
        {
            if (Utils.areShapesIntersecting(new DrawableShape(shape.getPoints()).generatePath(), new DrawableShape(tempShape.getPoints()).generatePath()))
                return true;
        }

        return false;
    }

    private Point getCentreOfPolygon(List<Point> pointsList)
    {
        double x = 0, y = 0;

        for (Point point : pointsList)
        {
            x += point.getX();
            y += point.getY();
        }

        x = x / pointsList.size();
        y = y / pointsList.size();

        return new Point(x, y);
    }
}
