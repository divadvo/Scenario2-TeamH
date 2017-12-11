package team.h.visualization;

import team.h.Point;
import team.h.Problem;
import team.h.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.List;

public class VisualizerPanel extends JPanel {

    private Problem problem;

    public VisualizerPanel() {
//        this.problem = problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public void paintComponent(Graphics g){
        g.drawOval(40, 40, 60, 60); //FOR CIRCLE
        g.drawRect(80, 30, 200, 200); // FOR SQUARE
        g.drawRect(200, 100, 100, 200); // FOR RECT

        // Paint the problem
        if(problem != null)
            drawProblem(g);
    }

    private void drawProblem(Graphics g) {
        // Scale and spread across screen
        drawRoom(g);
        drawShapes(g);
    }

    private void drawRoom(Graphics g) {
        Graphics2D g2 =(Graphics2D)g;

        List<Point> roomPoints = problem.getRoom().getPoints();
        GeneralPath path = new DrawableShape(roomPoints).generatePath();

        g2.fill(path);
    }

    private void drawShapes(Graphics g) {
        Graphics2D g2 =(Graphics2D)g;

        List<Shape> shapeList = problem.getShapes();

        for(Shape shape : shapeList) {
            List<Point> shapePoints = shape.getPoints();
            GeneralPath path = new DrawableShape(shapePoints).generatePath();

            g2.fill(path);
        }
    }




}
