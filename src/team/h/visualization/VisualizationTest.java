package team.h.visualization;

import team.h.core.Problem;
import team.h.io.ProblemParser;

import java.util.List;

public class VisualizationTest {

    public static void main(String[] args) {
//        ProblemParser problemParser = new ProblemParser("input/problems.rfp");
//        problemParser.parse();
//        List<Problem> problemList = problemParser.getProblems();

        List<Problem> problemList = new ProblemParser("input/problems.rfp").parse();


        new Visualizer(problemList).visualize();
    }
}
