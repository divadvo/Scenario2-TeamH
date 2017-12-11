package team.h.visualization;

import team.h.core.Point;
import team.h.core.Problem;
import team.h.core.Shape;
import team.h.core.Solution;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class VisualizerPanel extends JPanel {

    private Problem problem;
    private Solution solution;
    private GeneralPath roomPath;

    double scale = 1;
    float strokeWidth = 0.5f;
    private Rectangle roomBounds;

    private Visualizer.TYPE drawingType = Visualizer.TYPE.RANDOM;

    public VisualizerPanel() {
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public void setDrawingType(Visualizer.TYPE drawingType) {
        this.drawingType = drawingType;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        setupGraphics(g2);

        // Draw axis
        g2.setColor(Color.GREEN);
        g2.drawRect(0, 0, (int) (1000 * scale), (int) (1000 * scale));

        // Paint the problem
        if (problem != null)
            drawProblem(g2);
        if (solution != null)
            drawSolution(g2);
    }

    private void drawSolution(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        List<Shape> shapeList = solution.getShapes();
        System.out.println("Number of solution shapes: " + shapeList.size());

        g2.setColor(Color.RED);

        for (Shape shape : shapeList) {
            List<Point> shapePoints = shape.getPoints();
            GeneralPath path = new DrawableShape(shapePoints).generatePath();

            g2.draw(path);
        }
    }

    private void drawProblem(Graphics2D g2) {
        drawRoom(g2);
        drawShapesCorrectType(g2);
    }

    private void drawShapesCorrectType(Graphics2D g2) {
        if (drawingType == Visualizer.TYPE.RANDOM) {
            drawShapesRandom(g2);
        } else if (drawingType == Visualizer.TYPE.BOX) {
            drawShapesBox(g2);
        }

//        drawShapes2(g2);
    }

    private void drawShapesRandom(Graphics2D g2) {
        System.out.println("DRAW RANDOM");
        List<Shape> shapeList = problem.getShapes();
        g2.setColor(Color.BLUE);


        for (Shape shape : shapeList) {
            Shape newShape = shape.translate(randomNumber(), randomNumber());
            System.out.println(randomNumber() + "  " + randomNumber());

            List<Point> shapePoints = newShape.getPoints();
            GeneralPath path = new DrawableShape(shapePoints).generatePath();

            g2.draw(path);
        }
    }

    private void drawShapesBox(Graphics2D g2) {
        System.out.println("DRAW BOX");
    }

    private void drawRoom(Graphics2D g2) {
        List<Point> roomPoints = problem.getRoom().getPoints();
        roomPath = new DrawableShape(roomPoints).generatePath();

        g2.setColor(Color.BLACK);
        g2.draw(roomPath);
    }

    private double randomNumber() {
        return (new Random().nextDouble() * 1 - 1) * 2;
//        return 1;
    }

    private void drawShapes2(Graphics2D g2) {
        g2.setColor(Color.BLUE);

        List<Shape> shapeList = problem.getShapes();
        // Go through all bounding boxes from large to small and put them next to each other
        List<GeneralPath> paths = new ArrayList<>();

        int totalArea = 0;

        for (Shape shape : shapeList) {
            List<Point> shapePoints = shape.getPoints();
            GeneralPath path = new DrawableShape(shapePoints).generatePath();
            Rectangle rectangle = path.getBounds();

            AffineTransform affineTransform = new AffineTransform();
            if (rectangle.x < 0)
                affineTransform.scale(-1, 1);

            if (rectangle.y < 0)
                affineTransform.scale(1, -1);

            totalArea += Math.abs(rectangle.width * rectangle.height);
            path.transform(affineTransform);
            paths.add(path);

            System.out.println(rectangle.getWidth() + "  " + rectangle.getHeight());
        }

        System.out.println(totalArea + "  " + Math.sqrt(totalArea));

        Collections.sort(paths, (o1, o2) -> {
            Rectangle bounds1 = o1.getBounds();
            Rectangle bounds2 = o2.getBounds();

            return -Math.abs(bounds1.width * bounds1.height) + Math.abs(bounds2.width * bounds2.height);
        });

        int currentX = 0, currentY = 0;
        for (GeneralPath path : paths) {

            Rectangle bounds = path.getBounds();
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.translate(currentX, currentY);
            path.transform(affineTransform);

            currentX += bounds.getWidth();
//            currentY += bounds.getHeight();

//            System.out.println(currentX + "  " + currentY);

            g2.draw(path);
        }
    }

    private void drawShapes(Graphics2D g2) {
        // Copy List
//        List<Shape> shapeList = new ArrayList<Shape>(problem.getShapes());
        List<Shape> shapeList = problem.getShapes();
//        List<Shape> shapeList = new ArrayList<>(problem.getShapes());

//        List<Shape> shapeList = new ArrayList<>();
//        for(Shape shape : problem.getShapes()) {
//            shapeList.add(shape.clone())
//        }

        System.out.println("Number of shapes: " + shapeList.size());
        g2.setColor(Color.BLUE);

        Set<java.awt.Shape> addedShapes = new HashSet<>();
        addedShapes.add(roomPath);

        // TODO: Sort from large to small


        int currentX = 0, currentY = 0;

        for (Shape shape : shapeList) {
            GeneralPath path;


//            do {
//                Shape newShape = shape.translate(randomNumber(), randomNumber());
////                System.out.println(randomNumber() + "  " + randomNumber());
//                List<Point> shapePoints = newShape.getPoints();
//                path = new DrawableShape(shapePoints).generatePath();
//
//            } while (doesIntersectWithAnyOther(path, addedShapes));

            Shape newShape = shape.translate(currentX, currentY);

            List<Point> shapePoints = newShape.getPoints();
            path = new DrawableShape(shapePoints).generatePath();
            addedShapes.add(path);
            Rectangle bounds = path.getBounds();
            currentX += bounds.getWidth();
            currentY += bounds.getHeight();

            g2.draw(path);
        }
    }

    private boolean doesIntersectWithAnyOther(java.awt.Shape shape, Set<java.awt.Shape> addedShapes) {
        for (java.awt.Shape shape1 : addedShapes) {
            if (Utils.areShapesIntersecting(shape, shape1))
                return true;
        }
        return false;
    }

    public void redraw() {
        this.revalidate();
        this.repaint();
    }

    private void setupGraphics(Graphics2D g2) {
        // Set anti aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Start in bottom left
        g2.translate(0, getHeight() - 1);
        g2.scale(1, -1);

        calculateAndSetOrigin(g2);
        calculateAndSetScale(g2);

        g2.setStroke(new BasicStroke(strokeWidth));
    }

    private void calculateAndSetOrigin(Graphics2D g2) {

        List<Point> roomPoints = problem.getRoom().getPoints();
        roomPath = new DrawableShape(roomPoints).generatePath();
        roomBounds = roomPath.getBounds();

        double max = Math.max(roomBounds.getWidth(), roomBounds.getHeight());

        double targetSize = 0.5;
        double partOfScreen = max / this.getHeight();
        double scalingFactor = targetSize / partOfScreen;

        scale = scalingFactor;

        System.out.println("SCALING: " + scale);

        strokeWidth = 0.00f;


        // Move to center
//        g2.translate(this.getWidth() / 2, this.getHeight() / 2);
        g2.translate((this.getWidth() - (int) roomBounds.getWidth() * 1) / 2, (this.getHeight() - (int) roomBounds.getHeight() * 1) / 2);
        g2.translate(-roomBounds.x * scale, -roomBounds.y * scale);

        g2.scale(scale, scale);
    }

    private void calculateAndSetScale(Graphics2D g2) {

    }
}
