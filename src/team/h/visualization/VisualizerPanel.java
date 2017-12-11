package team.h.visualization;

import team.h.core.Point;
import team.h.core.Problem;
import team.h.core.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.List;
import java.util.Random;

public class VisualizerPanel extends JPanel {

    private Problem problem;

    public VisualizerPanel() {
//        this.problem = problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawOval(40, 40, 60, 60); //FOR CIRCLE
//        g.drawRect(80, 30, 200, 200); // FOR SQUARE
//        g.drawRect(200, 100, 100, 200); // FOR RECT

        g.drawString("Problem number: " + problem.getProblemNumber(), 500, 500);

        // Paint the problem
        if (problem != null)
            drawProblem(g);
    }

    private void drawProblem(Graphics g) {
        // Scale and spread across screen
        drawRoom(g);
        drawShapes(g);
    }

    private void drawRoom(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform oldTransform = g2.getTransform();

        List<Point> roomPoints = problem.getRoom().getPoints();
        GeneralPath path = new DrawableShape(roomPoints).generatePath();

//        Point max = Collections.max(roomPoints, new Comparator<Point>() {
//
//            public int compare(Point s, Point s2) {
//                return (int) (s.getX() - s2.getY());
//            }
//        });
//
//        g2.setStroke(new BasicStroke(1));
//
//        double scaling = (this.getWidth() / max.getX()) * 0.2;
//        System.out.println(max.getX()  + " " + this.getWidth() + " " + scaling);
//        double scaling = 2;
//        g2.scale(scaling, scaling);

        Rectangle bounds = path.getBounds();
        g.translate((this.getWidth() - (int) bounds.getWidth()) / 2, (this.getHeight() - (int) bounds.getHeight()) / 2);
        g.translate(-bounds.x, -bounds.y);


        g2.draw(path);
        g2.setTransform(oldTransform);
    }

    private void drawShapes(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        List<Shape> shapeList = problem.getShapes();

        Random random = new Random();

        System.out.println("Number of shapes: " + shapeList.size());

        for (Shape shape : shapeList) {
//            shape.translate(random.nextInt(500), random.nextInt(500));
            List<Point> shapePoints = shape.getPoints();
            GeneralPath path = new DrawableShape(shapePoints).generatePath();

            g2.draw(path);
        }
    }

    public void redraw() {
        this.revalidate();
        this.repaint();
    }
}
