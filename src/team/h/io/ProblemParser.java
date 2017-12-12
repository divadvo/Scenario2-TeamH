package team.h.io;

import team.h.core.Room;
import team.h.core.Shape;
import team.h.core.Point;
import team.h.core.Problem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProblemParser {

    private String problemFilePath;

    private List<Problem> problems = new ArrayList<>();


    public ProblemParser(String problemFilePath) {
        this.problemFilePath = problemFilePath;
    }

    public List<Problem> parse() {
        try {
            List<String> problemStrings;
            problemStrings = Files.lines(Paths.get(problemFilePath)).collect(Collectors.toList());
            for (String problemString : problemStrings) {
                int problemIdentifier = getIdentifier(problemString);
                List<String> problemComponents = Arrays.asList(problemString.split("#"));
                Room problemRoom = createRoom(problemComponents.get(0).trim());
                List<Shape> problemShapes = createShapes(problemComponents.get(1).trim());
                problems.add(new Problem(problemIdentifier, problemRoom, problemShapes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return problems;
    }

    private int getIdentifier(String problemString) {
        int colon = problemString.indexOf(":");
        return Integer.parseInt(problemString.substring(0, colon).trim());
    }

    private List<Point> createPoints(String pointsString) {
        List<Point> points = new ArrayList<>();
        Matcher m = Pattern.compile("\\((.*?)\\)").matcher(pointsString);
        while (m.find()) {
            List<String> coordinates = Arrays.asList(m.group(1).split(","));
            points.add(new Point(Double.parseDouble(coordinates.get(0)), Double.parseDouble(coordinates.get(1))));
        }
        return points;
    }

    private Room createRoom(String roomString) {
        List<Point> points = createPoints(roomString);
        return (new Room(points));
    }

    private List<Shape> createShapes(String shapesString) {
        List<Shape> shapes = new ArrayList<>();
        List<String> shapesStrings = Arrays.asList(shapesString.split(";"));
        for (String shapeString : shapesStrings) {
            int cost = getIdentifier(shapeString);
            List<Point> points = createPoints(shapeString.trim());
            shapes.add(new Shape(cost, points));
        }
        return shapes;
    }
}
