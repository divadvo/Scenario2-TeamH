package team.h.visualization;

import team.h.core.Point;
import team.h.core.Problem;
import team.h.core.Shape;
import team.h.core.Solution;
import team.h.io.SolutionPrinter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

public class VisualizerPanelOld extends JPanel {

    private Problem problem;
    private Solution solution;
    private GeneralPath roomPath;

    double scale = 1;
    float strokeWidth = 0.5f;
    private Rectangle roomBounds;

    private Visualizer.TYPE drawingType = Visualizer.TYPE.RANDOM;
    private List<ShapeAndShape> drawnShapes;
    private List<Shape> removedShapes = new ArrayList<>();

    private AffineTransform affineTransform;
    private ShapeAndShape chosenShape = null;
    private double shiftX = 0, shiftY = 0;
    private double angle = 0;
    private double angleDelta = 0.5, delta = 1;

    public VisualizerPanelOld() {
        drawnShapes = new ArrayList<>();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);

                Point2D p = null;
                try {
                    p = affineTransform.inverseTransform(new Point2D.Double(me.getX(), me.getY()), null);
                } catch (NoninvertibleTransformException e) {
                    e.printStackTrace();
                }
                System.out.println("click x=" + p.getX() + " y=" + p.getY());

                for (ShapeAndShape shapeAndShape : drawnShapes) {
//                    System.out.println("Shape "  + shapeAndShape.getDrawShape().getBounds2D());
                    if (shapeAndShape.getDrawShape().contains(p)) {
                        System.out.println("Clicked  COST: " + shapeAndShape.getOurShape().getTotalCost());
                        chosenShape = shapeAndShape;
                    }
                }

                redraw();
            }
        });

        this.setFocusable(true);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                System.out.println("Pressed");
                System.out.println(delta + "  " + angleDelta);

                double deltaDeltaPrecision = 0.05;

                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        shiftY += delta;
                        break;
                    case KeyEvent.VK_DOWN:
                        shiftY -= delta;
                        break;
                    case KeyEvent.VK_LEFT:
                        shiftX -= delta;
                        break;
                    case KeyEvent.VK_RIGHT:
                        shiftX += delta;
                        break;
                    case KeyEvent.VK_ENTER:
                        saveChosenShape();
                        break;
                    case KeyEvent.VK_R:
                        angle -= angleDelta;
                        break;
                    case KeyEvent.VK_E:
                        angle += angleDelta;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        cancelChosenShape();
                        break;
                    case KeyEvent.VK_O:
                        angleDelta -= 0.1;
                        break;
                    case KeyEvent.VK_P:
                        angleDelta += 0.1;
                        break;
                    case KeyEvent.VK_K:
                        delta -= deltaDeltaPrecision;
                        break;
                    case KeyEvent.VK_L:
                        delta += deltaDeltaPrecision;
                        break;
                }
                System.out.println(delta + "   DELTA ---- ANGLE DELTA  " + angleDelta);
//                System.out.println(shiftX + " " + shiftY);

                redraw();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void cancelChosenShape() {
        chosenShape = null;
        shiftX = 0;
        shiftY = 0;
        angle = 0;
    }

    private void saveChosenShape() {
        // Save in solution
        // remove from list?
        Shape newShape = chosenShape.getOurShape().translate(shiftX, shiftY).rotate(angle);
        solution.getShapes().add(newShape);
        removedShapes.add(chosenShape.getOurShape());
//        List<Shape> problemShapes = problem.getShapes();
//        Shape chosenShapeOur = chosenShape.getOurShape();
//        problemShapes.remove(chosenShapeOur);

        List<Solution> sols = new ArrayList<>();
        sols.add(solution);
        double totalCost = new SolutionPrinter("output/", sols).totalCost();
        System.out.println("NEW TOTAL COST: " + totalCost);

        cancelChosenShape();
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

//        setupGraphics(g2);

        // Set anti aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Start in bottom left
        g2.translate(0, getHeight() - 1);
        g2.scale(1, -1);

        // Paint the problem
        if (problem != null)
            drawProblem(g2);
        if (solution != null)
            drawSolution(g2);

        if (chosenShape != null)
            drawChosenShape(g2);
    }

    private void drawChosenShape(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();
        calculateAndSetOrigin(g2);

        Shape chosenShapeOur = chosenShape.getOurShape();
        Shape newShape = chosenShapeOur.translate(shiftX, shiftY).rotate(angle);
        List<Point> shapePoints = newShape.getPoints();
        GeneralPath path = new DrawableShape(shapePoints).generatePath();


        g2.setColor(Color.RED);
        if(Utils.areShapesIntersecting(roomPath, path) && !Utils.isShapeInsideAnother(roomPath, path))
            g2.setColor(Color.CYAN);

        // Go through all solution shapes and check if intersect
        for(Shape shape : solution.getShapes()) {
            List<Point> shapePointsNew = shape.getPoints();
            GeneralPath pathNew = new DrawableShape(shapePointsNew).generatePath();
            if(Utils.areShapesIntersecting(pathNew, path) && !Utils.isShapeInsideAnother(pathNew, path))
                g2.setColor(Color.CYAN);
        }

        g2.fill(path);

        g2.setTransform(oldTransform);
    }

    private void drawSolution(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();

        List<Shape> shapeList = solution.getShapes();

        calculateAndSetOrigin(g2);

        // Draw axis
        g2.setColor(Color.DARK_GRAY);
//        g2.drawRect(0, 0, 1000, 1000);
        g2.drawLine(-1000, 0, 1000, 0);
        g2.drawLine(0, -1000, 0, 1000);

        ColorRange colorRange = generateColorRange(problem.getShapes());

        g2.setColor(Color.RED);

        for (Shape shape : shapeList) {
            List<Point> shapePoints = shape.getPoints();
            GeneralPath path = new DrawableShape(shapePoints).generatePath();

            if (true) {
                g2.setColor(colorRange.generateColor(shape.getTotalCost()));
                g2.fill(path);
                g2.setColor(Color.BLUE);
                g2.draw(path);
            } else {

                g2.draw(path);
            }
//            System.out.println(Utils.isShapeInsideAnother(roomPath, path));
//            g2.draw(path);
        }
        g2.setTransform(oldTransform);
    }

    private void calculateAndSetOrigin(Graphics2D g2) {
        calculateScale();

        // Move to center
//        System.out.println(this.getWidth());
//        System.out.println("Translate: " + (this.getWidth() - (int) roomBounds.getWidth()) / 2 + "  " + (this.getHeight() - (int) roomBounds.getHeight()) / 2);
        g2.translate((this.getWidth() - (int) roomBounds.getWidth()) / 2, (this.getHeight() - (int) roomBounds.getHeight()) / 2);
        g2.scale(scale, scale);
//        if(problem.getProblemNumber() == 1)
//            g2.scale(50, 50);
        g2.setStroke(new BasicStroke(strokeWidth));
    }

    private void calculateScale() {
        List<Point> roomPoints = problem.getRoom().getPoints();
        roomPath = new DrawableShape(roomPoints).generatePath();
        roomBounds = roomPath.getBounds();

//        double area = totalAreaBoxes() + Math.abs(roomBounds.getWidth() * roomBounds.getHeight());
//        double screenArea = this.getWidth() * this.getHeight();
//
//        double targetSize = 0.4;
//        double currentPercentage = area / screenArea;
//        double scalingFactor = currentPercentage / targetSize;

        double max = Math.max(roomBounds.getWidth(), roomBounds.getHeight());

        double targetSize = 0.2;
        double partOfScreen = max / this.getHeight();
        double scalingFactor = targetSize / partOfScreen;

        scale = scalingFactor;

        System.out.println("SCALING: " + scale);

        strokeWidth = 0.00f;
    }

    private double totalAreaBoxes() {
        List<Shape> shapeList = problem.getShapes();
        int totalArea = 0;

        for (Shape shape : shapeList) {
            List<Point> shapePoints = shape.getPoints();
            GeneralPath path = new DrawableShape(shapePoints).generatePath();
            Rectangle rectangle = path.getBounds();

            totalArea += Math.abs(rectangle.width * rectangle.height);
        }
        return totalArea;
    }

    private void drawProblem(Graphics2D g2) {
        drawRoom(g2);
        if (chosenShape == null)
            drawShapesCorrectType(g2);
    }

    private void drawRoom(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();

        List<Point> roomPoints = problem.getRoom().getPoints();
        roomPath = new DrawableShape(roomPoints).generatePath();

        calculateAndSetOrigin(g2);
        g2.setColor(Color.BLACK);
        g2.draw(roomPath);

        affineTransform = g2.getTransform();

        g2.setTransform(oldTransform);
    }

    private void drawShapesCorrectType(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();

        if (drawingType == Visualizer.TYPE.RANDOM) {
            drawShapesRandom(g2);
        } else if (drawingType == Visualizer.TYPE.BOX) {
            drawShapesBox(g2);
        }
        g2.setTransform(oldTransform);
    }

    private void drawShapesRandom(Graphics2D g2) {
        System.out.println("DRAW RANDOM");
//        List<Shape> shapeList = problem.getShapes();

        // problemShapes - solutionShapes
//        List<Shape> shapeList = new ArrayList<>(problem.getShapes());
//        List<Shape> solutionShapes = solution.getShapes();
//        shapeList.removeAll(removedShapes);
//        List<Shape> shapeList = CollectionUtils.supebtract(problem.getShapes(), solution.getShapes());

//        List<Shape> shapeList = problem.getShapes().stream()
//                .filter(i -> !removedShapes.contains(i))
//                .collect (Collectors.toList());

        boolean test = problem.getShapes().get(0).equals(problem.getShapes().get(0));

        List<Shape> shapeList = new ArrayList<>();
        for (Shape shape : problem.getShapes()) {
            boolean contains = false;
            for (Shape shape1 : removedShapes) {
                if (shape.equalsWithUUID(shape1)) {
                    contains = true;
                    break;
                }
            }
            if (!contains)
                shapeList.add(shape);
//            if(!removedShapes.contains(shape))
//                shapeList.add(shape);
        }

        List<GeneralPath> paths = new ArrayList<>();
        Map<GeneralPath, Shape> map = new HashMap<>();

        for (Shape shape : shapeList) {
            List<Point> shapePoints = shape.getPoints();
            GeneralPath path = new DrawableShape(shapePoints).generatePath();
            Rectangle rectangle = path.getBounds();

            AffineTransform affineTransform = new AffineTransform();
            if (rectangle.x < 0)
                affineTransform.scale(-1, 1);

            if (rectangle.y < 0)
                affineTransform.scale(1, -1);

            path.transform(affineTransform);
            paths.add(path);
            map.put(path, shape);
        }

        Collections.sort(paths, (o1, o2) -> {
            Rectangle bounds1 = o1.getBounds();
            Rectangle bounds2 = o2.getBounds();

            return -Math.abs(bounds1.width * bounds1.height) + Math.abs(bounds2.width * bounds2.height);
        });


        g2.setColor(Color.BLUE);
        Set<java.awt.Shape> addedShapes = new HashSet<>();
        addedShapes.add(roomPath);

        // TODO: change getBounds -> getBounds2D because greater precision

        ColorRange colorRange = generateColorRange(shapeList);

        calculateAndSetOrigin(g2);

        for (Shape shape : shapeList) {
            GeneralPath path;
            Rectangle2D bounds;
            int i = 1;
            do {
                Shape newShape = shape.translate(randomNumber() * i, randomNumber() * i);
                List<Point> shapePoints = newShape.getPoints();
                path = new DrawableShape(shapePoints).generatePath();
                bounds = path.getBounds2D();
                i++;
            } while (doesIntersectWithAnyOther(path, addedShapes));
//            System.out.println(i);
            if (true) {
                Color color = colorRange.generateColor(shape.getTotalCost());
                g2.setColor(color);
                g2.fill(path);
                drawnShapes.add(new ShapeAndShape(path, shape));
            } else {

                g2.draw(path);
            }


//            AffineTransform oldTransform = g2.getTransform();

//            g2.translate(0, getHeight() - 1);
//            g2.scale(1, -1);
//            g2.scale(1 / scale, 1 / scale);

//            g2.drawString(shape.getTotalCost() + "", (int) bounds.getX(), (int) bounds.getY());

//            System.out.println("Cost: " + shape.getTotalCost());

//            g2.setTransform(oldTransform);


//            System.out.println(path);
            addedShapes.add(path);
        }
    }

    private ColorRange generateColorRange(List<Shape> shapeList) {
        if (shapeList.isEmpty())
            return null;

        List<Shape> shapesSortedByCost = new ArrayList<>(shapeList);
        Collections.sort(shapesSortedByCost, Comparator.comparing(o -> o.getTotalCost()));

        Shape smallestCostShape = shapesSortedByCost.get(0);
        Shape highestCostShape = shapesSortedByCost.get(shapesSortedByCost.size() - 1);

//        for(int i = shapesSortedByCost.size() - 1; i >= 0; i--)
//            System.out.print(shapesSortedByCost.get(i).getTotalCost() + "," + shapesSortedByCost.get(i).getArea() + "   ");

        return new ColorRange(smallestCostShape.getTotalCost(), highestCostShape.getTotalCost());
    }

    private void drawShapesBox(Graphics2D g2) {
        System.out.println("DRAW BOX");
    }

    private double randomNumber() {
//        return (new Random().nextDouble() * 1 - 1) * 2;
//        return new Random().nextDouble(); // 0 - 1
        return new Random().nextDouble() - 0.5; // 0 - 1

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

//            System.out.println(rectangle.getWidth() + "  " + rectangle.getHeight());
        }

//        System.out.println(totalArea + "  " + Math.sqrt(totalArea));

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

    public void setDeltaAndAngle(double delta, double angleDelta) {
        this.delta = delta;
        this.angleDelta = angleDelta;
    }
}
