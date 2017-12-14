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
import java.util.stream.Collectors;

public class VisualizerPanel extends JPanel {

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
    private boolean pointSelectionModeOn = false;
    private double targetSize = 0.4;
    private boolean generatedRandomBefore = false;

    private List<ShapeAndShape> randomShapesGenerated;
    private boolean selectingFirstPoint = false;
    private boolean selectingSecondPoint = false;

    private Point2D firstPoint, secondPoint;
    private int centerOffsetX = 0;
    private int centerOffsetY = 0;
    private int deltaCenterOffset = 5;

    private boolean showProblemShapes = true;

    public VisualizerPanel() {
        drawnShapes = new ArrayList<>();
        this.setFocusable(true);
        createListeners();
    }

    private Point2D ourCoordinatesFromMouseCoordinates(int x, int y) {
        Point2D p = null;
        try {
            p = affineTransform.inverseTransform(new Point2D.Double(x, y), null);
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        return p;
    }

    private void createListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);

                Point2D p = ourCoordinatesFromMouseCoordinates(me.getX(), me.getY());

                if (toRemoveSolutionShape) {
                    removeSolutionShape(p);
                }

                if (selectingFirstPoint) {
                    firstPoint = p;
                    selectingFirstPoint = false;
                    selectingSecondPoint = true;
                    System.out.println("First Point " + firstPoint);
                } else if (selectingSecondPoint) {
                    secondPoint = p;
                    selectingSecondPoint = false;
                    System.out.println("Second Point " + secondPoint);
                }

                if (pointSelectionModeOn) {
                    System.out.println("Point selection");
                    extendNewSolutionShape(p.getX(), p.getY());
                }

                if (chosenShape == null) {
                    for (ShapeAndShape shapeAndShape : drawnShapes) {
                        if (shapeAndShape.getDrawShape().contains(p)) {
                            System.out.println("Clicked  COST: " + shapeAndShape.getOurShape().getTotalCost());
                            chosenShape = shapeAndShape;
                        }
                    }
                }

                redraw();
            }
        });


        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                double deltaDeltaPrecision = 0.1;

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
                    case KeyEvent.VK_A:
                        angle = 180 - 138.70878322203367;
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
                    case KeyEvent.VK_M:
                        rotateUntilFits(1);
                        break;
                    case KeyEvent.VK_N:
                        rotateUntilFits(-1);
                        break;
                    case KeyEvent.VK_V:
                        pointSelectionMode();
                        break;
                    case KeyEvent.VK_G:
                        generateSolution();
                        break;
                    case KeyEvent.VK_ADD:
                        targetSize += 0.1;
                        break;
                    case KeyEvent.VK_SUBTRACT:
                        targetSize -= 0.1;
                        break;
                    case KeyEvent.VK_DIVIDE:
                        targetSize = 0.4;
                        centerOffsetX = 0;
                        centerOffsetY = 0;
                        break;
                    case KeyEvent.VK_Z:
                        randomlyDropShapesInsideRoom();
                        break;
                    case KeyEvent.VK_X:
                        dropRandomlyUntilImpossible();
                        break;
                    case KeyEvent.VK_D:
                        startSelectingArea();
                        break;
                    case KeyEvent.VK_U:
                        toRemoveSolutionShape = true;
                        break;
                    case KeyEvent.VK_Q:
                        removeAllSolutionShapes();
                        break;
                    case KeyEvent.VK_NUMPAD8:
                        centerOffsetY += deltaCenterOffset;
                        break;
                    case KeyEvent.VK_NUMPAD2:
                        centerOffsetY -= deltaCenterOffset;
                        break;
                    case KeyEvent.VK_NUMPAD6:
                        centerOffsetX += deltaCenterOffset;
                        break;
                    case KeyEvent.VK_NUMPAD4:
                        centerOffsetX -= deltaCenterOffset;
                        break;
                    case KeyEvent.VK_H:
                        showProblemShapes = !showProblemShapes;
                        break;
                    case KeyEvent.VK_1:
                        uploadBest();
                        break;
                    case KeyEvent.VK_NUMPAD7:
                        shiftX -= delta;
                        shiftY += delta;
                        break;
                    case KeyEvent.VK_NUMPAD3:
                        shiftX += delta;
                        shiftY -= delta;
                        break;

                }
                System.out.println(delta + "   DELTA ---- ANGLE DELTA  " + angleDelta);

                redraw();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void uploadBest() {
        SolutionPrinter.saveAndUploadBest("output/best");
    }

    private void removeAllSolutionShapes() {
        solution.getShapes().clear();
    }

    private boolean toRemoveSolutionShape = false;

    private void removeSolutionShape(Point2D p) {
        Iterator<Shape> i = solution.getShapes().iterator();

        while (i.hasNext()) {
            Shape shape = i.next();

            List<Point> shapePoints = shape.getPoints();
            GeneralPath path = new DrawableShape(shapePoints).generatePath();
            if (path.contains(p)) {
                System.out.println("Clicked Solution COST: " + shape.getTotalCost());
                i.remove();
            }
        }
        toRemoveSolutionShape = false;
    }

    private void startSelectingArea() {
        selectingFirstPoint = true;
        selectingSecondPoint = false;
    }

    private void dropRandomlyUntilImpossible() {
        int itemsAddedLastTime = 1;
        int timesInARowNoneWereAdded = 0;
        do {
            itemsAddedLastTime = randomlyDropShapesInsideRoom();
            System.out.println("ITEMS ADDED: " + itemsAddedLastTime);
            if (itemsAddedLastTime == 0)
                timesInARowNoneWereAdded++;
            if (itemsAddedLastTime > 0)
                timesInARowNoneWereAdded = 0;
        } while (timesInARowNoneWereAdded < 5);
    }

    private int randomlyDropShapesInsideRoom() {
        System.out.println("RUN RANDOM DROPPER");
        List<Shape> shapeList = getShapesWithoutSolution();

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
        }


        Collections.sort(shapeList, Comparator.comparing(o -> -o.getTotalCost()));

//        g2.setColor(Color.BLUE);
        Set<java.awt.Shape> addedShapes = new HashSet<>();
//        addedShapes.add(roomPath);
        ; //
        for (Shape solutionShape : solution.getShapes()) {
            List<Point> shapePoints = solutionShape.getPoints();
            GeneralPath path = new DrawableShape(shapePoints).generatePath();
            addedShapes.add(path);
        }

//        ColorRange colorRange = ColorRange.generateColorRange(shapeList);

//        calculateAndSetOrigin(g2);

        int itemAdded = 0;

        int something = 0;
        for (Shape shape : shapeList) {
            something++;
//            System.out.println(something);
            GeneralPath path;
            Shape newShape;
            int i = 1;

            boolean somethingIsWrong = false;
            boolean foundNoPlace = false;

            do {
                Point randomPoint = Utils.generateRandomPointBetween(firstPoint, secondPoint);
                newShape = shape.translate(randomPoint.getX(), randomPoint.getY());
//                newShape = shape.translate(Utils.randomNumber() * 1000, Utils.randomNumber() * 1000);
                List<Point> shapePoints = newShape.getPoints();
                path = new DrawableShape(shapePoints).generatePath();
                i++;
                somethingIsWrong = !Utils.isShapeInsideAnother(roomPath, path) || doesIntersectWithAnyOther(path, addedShapes);
                if (i > 10 * shape.getCostPerUnit())
                    foundNoPlace = true;
                if (!somethingIsWrong || foundNoPlace)
                    break;
            } while (true);

//            Color color = colorRange.generateColor(shape.getTotalCost());
//            g2.setColor(color);
//            g2.fill(path);
//            randomShapesGenerated.add(new ShapeAndShape(path, shape));

            if (!somethingIsWrong && !foundNoPlace) {
                removedShapes.add(shape);
                solution.getShapes().add(newShape);

                addedShapes.add(path);
                itemAdded++;
            }
        }
        return itemAdded;
    }

    private void generateSolution() {
        System.out.println("GENERATE SOLUTION");
        List<Solution> sols = new ArrayList<>();
        sols.add(solution);
        SolutionPrinter solutionPrinter = new SolutionPrinter("output/", sols);
        solutionPrinter.output();
        solutionPrinter.upload();
//        new SolutionPrinter("output/", sols).output();
    }

    private void extendNewSolutionShape(double pX, double pY) {
        Point selectedPoint = null;

        List<Point> combinedList = new ArrayList<>(problem.getRoom().getPoints());
        for (Shape solutionShape : solution.getShapes()) {
            combinedList.addAll(solutionShape.getPoints());
        }

//        for (Point roomPoint : problem.getRoom().getPoints()) {
        for (Point roomPoint : combinedList) {
            Point vector = new Point(pX, pY).translate(-roomPoint.getX(), -roomPoint.getY());
            double d2 = vector.getX() * vector.getX() + vector.getY() * vector.getY();
            if (d2 < 0.05) {
                System.out.println(roomPoint);
                selectedPoint = roomPoint;
            }
        }

        if (selectedPoint != null && pointSelectionModeOn) {
            chosenShape.getOurShape().addPoint(selectedPoint);

            List<Shape> shapesToCheck = new ArrayList<>();
            for (Shape givenShape : problem.getShapes()) {
                if (givenShape.getArea() >= chosenShape.getOurShape().recalculateArea()) {
//                    System.out.println("Added");
                    shapesToCheck.add(givenShape);
                } else {
//                    System.out.println("Not added");
                }
            }

            for (Shape givenShape : shapesToCheck) {
                if (givenShape.canBeTransformedInto(chosenShape.getOurShape())) {
                    System.out.println("Can be created");
                } else {
//                    System.out.println("Cannot");
                }
            }

//            for(Shape givenShape : problem.getShapes()) {
////                if (givenShape.canBeTransformedInto(chosenShape.getOurShape())) {
////                    System.out.println("Can be created");
////                }
////                else {
////                    System.out.println("Cannot");
////                }
//
////                Shape chosenShapeOur = chosenShape.getOurShape();
////                List<Point> shapePoints = chosenShapeOur.getPoints();
////                GeneralPath path = new DrawableShape(shapePoints).generatePath();
////
////                List<Point> givenShapePoints = givenShape.getPoints();
////                GeneralPath pathGiven = new DrawableShape(shapePoints).generatePath();
////
////                Area areaOur = new Area(path);
////                Area areaGiven = new Area(pathGiven);
//
//                if (givenShape.canBeTransformedInto(chosenShape.getOurShape())) {
//                    System.out.println("Can be created");
//                }
//                else {
//                    System.out.println("Cannot");
//                }
//
//            }
        }
    }

    private void pointSelectionMode() {
        pointSelectionModeOn = true;
        System.out.println("V");
        chosenShape = new ShapeAndShape(new DrawableShape(new ArrayList<Point>()).generatePath(), new Shape(0, new ArrayList<>()));
    }

    private void rotateUntilFits(int i) {
        GeneralPath path;
        do {
            angle += 0.1 * i;
            Shape chosenShapeOur = chosenShape.getOurShape();
            Shape newShape = chosenShapeOur.translate(shiftX, shiftY).rotate(angle);
            List<Point> shapePoints = newShape.getPoints();
            path = new DrawableShape(shapePoints).generatePath();
        } while (Utils.areShapesIntersecting(roomPath, path));
        redraw();
    }

    private void cancelChosenShape() {
        chosenShape = null;
        shiftX = 0;
        shiftY = 0;
        angle = 0;
        pointSelectionModeOn = false;
    }

    private void saveChosenShape() {
        // Save in solution
        // remove from list?
        Shape newShape = chosenShape.getOurShape().translate(shiftX, shiftY).rotate(angle);
        solution.getShapes().add(newShape);
        removedShapes.add(chosenShape.getOurShape());

        List<Solution> sols = new ArrayList<>();
        sols.add(solution);
        double totalCost = new SolutionPrinter("output/", sols).totalCost();
        System.out.println("SOLUTION TOTAL COST: " + totalCost);

        cancelChosenShape();
    }

    public void resetGeneratedRandomBefore() {
        generatedRandomBefore = false;
        randomShapesGenerated = new ArrayList<>();
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

        // Set anti aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Start in bottom left
        g2.translate(0, getHeight() - 1);
        g2.scale(1, -1);

        if (firstPoint != null && secondPoint != null) {
            drawArea(g2);
        }

        // Paint the problem
        if (problem != null)
            drawProblem(g2);
        if (solution != null)
            drawSolution(g2);

        if (chosenShape != null)
            drawChosenShape(g2);
    }

    private Color areaColor = new Color(149, 165, 166);

    private void drawArea(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();
//        System.out.println("DrawArea");
        calculateAndSetOrigin(g2);

        g2.setColor(areaColor);

//        Rectangle2D.Double rect = new Rectangle2D.Double(new java.awt.Point2D(firstPoint.getX(), firstPoint.getY()));
//        rect.add(new java.awt.Point2D( secondPoint.getX(), secondPoint.getY()));

//        g2.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        List<Point> points = new ArrayList<>();
        points.add(new Point(firstPoint.getX(), firstPoint.getY()));
        points.add(new Point(secondPoint.getX(), firstPoint.getY()));
        points.add(new Point(secondPoint.getX(), secondPoint.getY()));
        points.add(new Point(firstPoint.getX(), secondPoint.getY()));
//        Shape shapeArea = new Shape(0, points);

        GeneralPath pathNew = new DrawableShape(points).generatePath();
        g2.fill(pathNew);

//        g2.fillRect(0, 0, 100, 100);
//        g2.fillRect((int)firstPoint.getX(), (int)firstPoint.getY(), (int)(secondPoint.getX() - firstPoint.getX()), (int)(secondPoint.getY() - firstPoint.getY()));
        g2.setTransform(oldTransform);
    }

    private void drawChosenShape(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();
        calculateAndSetOrigin(g2);

        Shape chosenShapeOur = chosenShape.getOurShape();
        Shape newShape = chosenShapeOur.translate(shiftX, shiftY).rotate(angle);
        List<Point> shapePoints = newShape.getPoints();
        GeneralPath path = new DrawableShape(shapePoints).generatePath();


        g2.setColor(Color.RED);
        if (Utils.areShapesIntersecting(roomPath, path) && !Utils.isShapeInsideAnother(roomPath, path))
            g2.setColor(Color.CYAN);

        // Go through all solution shapes and check if intersect
        for (Shape shape : solution.getShapes()) {
            List<Point> shapePointsNew = shape.getPoints();
            GeneralPath pathNew = new DrawableShape(shapePointsNew).generatePath();
            if (Utils.areShapesIntersecting(pathNew, path) && !Utils.isShapeInsideAnother(pathNew, path))
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
        g2.drawLine(-1000, 0, 1000, 0);
        g2.drawLine(0, -1000, 0, 1000);

        ColorRange colorRange = ColorRange.generateColorRange(problem.getShapes());

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
        }
        g2.setTransform(oldTransform);
    }

    private void drawProblem(Graphics2D g2) {
        drawRoom(g2);
        if (chosenShape == null && showProblemShapes)
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
        // Problem shapes - solution shapes
        List<Shape> shapeList = getShapesWithoutSolution();

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

        g2.setColor(Color.BLUE);
        Set<java.awt.Shape> addedShapes = new HashSet<>();
        addedShapes.add(roomPath);

        ColorRange colorRange = ColorRange.generateColorRange(shapeList);

        calculateAndSetOrigin(g2);

        if (!generatedRandomBefore) {
            for (Shape shape : shapeList) {
                GeneralPath path;
                int i = 1;
                do {
                    Shape newShape = shape.translate(Utils.randomNumber() * i, Utils.randomNumber() * i);
                    List<Point> shapePoints = newShape.getPoints();
                    path = new DrawableShape(shapePoints).generatePath();
                    i++;
                } while (doesIntersectWithAnyOther(path, addedShapes));
//                System.out.println(i);

                Color color = colorRange.generateColor(shape.getTotalCost());
                g2.setColor(color);
                g2.fill(path);
                drawnShapes.add(new ShapeAndShape(path, shape));
                randomShapesGenerated.add(new ShapeAndShape(path, shape));

                addedShapes.add(path);
            }
            generatedRandomBefore = true;
        } else {
            for (ShapeAndShape shapeAndShape : randomShapesGenerated) {
                Color color = colorRange.generateColor(shapeAndShape.getOurShape().getTotalCost());
                boolean contains = false;
                for (Shape shape1 : removedShapes) {
                    if (shapeAndShape.getOurShape().equalsWithUUID(shape1)) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    g2.setColor(color);
                    g2.fill(shapeAndShape.getDrawShape());
                }
            }
        }
    }

    private List<Shape> getShapesWithoutSolution() {
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
        }
        return shapeList;
    }

    private void drawShapesBox(Graphics2D g2) {
        drawShapes(g2);
    }

    private void drawShapes(Graphics2D g2) {
        List<Shape> shapeList = getShapesWithoutSolution();

//        System.out.println("Number of shapes: " + shapeList.size());
        g2.setColor(Color.BLUE);

        Set<java.awt.Shape> addedShapes = new HashSet<>();
        addedShapes.add(roomPath);

        // TODO: Sort from large to small
        Collections.sort(shapeList, Comparator.comparing(o -> -o.getTotalCost()));


        Point2D p = ourCoordinatesFromMouseCoordinates(0, (int) (getHeight() * 0.95));
        double currentX = p.getX(), currentY = p.getY();


        ColorRange colorRange = ColorRange.generateColorRange(shapeList);
        calculateAndSetOrigin(g2);

        for (Shape shape : shapeList) {
            GeneralPath path;
            Shape newShape = shape.translate(currentX, currentY);

            List<Point> shapePoints = newShape.getPoints();
            path = new DrawableShape(shapePoints).generatePath();
            addedShapes.add(path);
            Rectangle2D bounds = path.getBounds2D();
            currentX += bounds.getWidth() * 1.2;
            if (currentX + bounds.getX() > this.getWidth()) {
                currentX = 0;
                currentY += bounds.getHeight();
            }

            Color color = colorRange.generateColor(shape.getTotalCost());
            g2.setColor(color);
            g2.fill(path);
            drawnShapes.add(new ShapeAndShape(path, shape));
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

    private void calculateAndSetOrigin(Graphics2D g2) {
        calculateScale();

        // Move to center
        g2.translate((this.getWidth() - (int) roomBounds.getWidth()) / 2 + centerOffsetX, (this.getHeight() - (int) roomBounds.getHeight()) / 2 + centerOffsetY);
        g2.scale(scale, scale);
        g2.setStroke(new BasicStroke(strokeWidth));
    }

    private void calculateScale() {
        List<Point> roomPoints = problem.getRoom().getPoints();
        roomPath = new DrawableShape(roomPoints).generatePath();
        roomBounds = roomPath.getBounds();
        double max = Math.max(roomBounds.getWidth(), roomBounds.getHeight());

//        targetSize = 0.4;
//        System.out.println("Target SIZE: " + targetSize);
        double partOfScreen = max / this.getHeight();
        double scalingFactor = targetSize / partOfScreen;

        scale = scalingFactor;

        strokeWidth = 0.00f;
    }
}
