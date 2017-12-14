package team.h.io;

import team.h.core.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SolutionParser {
    private String solutionFilePath;

    private List<Solution> solutions = new ArrayList<>();


    public SolutionParser(String solutionFilePath) {
        this.solutionFilePath = solutionFilePath;
    }

    public List<Solution> parse() {
        try {
            List<String> solutionStrings;
            solutionStrings = Files.lines(Paths.get(solutionFilePath)).collect(Collectors.toList());
//            for (String solutionString : solutionStrings) {
            // Skip first 2 lines
            for (int i = 2; i < solutionStrings.size(); i++) {
                String solutionString = solutionStrings.get(i);
                List<String> parts = Arrays.asList(solutionString.split(":"));
                int problemIdentifier = getIdentifier(parts.get(0).trim());
                List<Shape> solutionShapes = createShapes(parts.get(1).trim());
                solutions.add(new Solution(problemIdentifier, solutionShapes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return solutions;
    }

    private int getIdentifier(String problemString) {
//        int colon = problemString.indexOf(":");
//        return Integer.parseInt(problemString.substring(0, colon).trim());
        return Integer.parseInt(problemString);
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

    private List<Shape> createShapes(String shapesString) {
        List<Shape> shapes = new ArrayList<>();
        List<String> shapesStrings = Arrays.asList(shapesString.split(";"));
        for (String shapeString : shapesStrings) {
//            int cost = getIdentifier(shapeString);
            List<Point> points = createPoints(shapeString.trim());
            shapes.add(new Shape(0, points));
        }
        return shapes;
    }
}
