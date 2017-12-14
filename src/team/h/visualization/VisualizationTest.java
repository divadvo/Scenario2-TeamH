package team.h.visualization;

import team.h.algorithm.AllProblemSolver;
import team.h.algorithm.david.VisualSolver;
import team.h.core.Problem;
import team.h.core.ProblemsAndSolutions;
import team.h.core.Solution;
import team.h.io.ProblemParser;
import team.h.io.SolutionParser;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VisualizationTest {

    public static void main(String[] args) {
        List<Problem> problems = new ProblemParser("input/problems.rfp").parse();
        List<Solution> solutions;
        if (false)
            solutions = new AllProblemSolver(problems).solve();
        else {
//            solutions = new SolutionParser("output/best/combinedNew.solutions").parse();
            solutions = new SolutionParser("output/best/combinedGenerated.solutions").parse();
        }

        for(Problem problem : problems) {
            boolean toAdd = true;
            for(Solution solution : solutions) {
                if(problem.getProblemNumber() == solution.getSolutionNumber()) {
                    toAdd = false;
                    break;
                }
            }
            if(toAdd)
                solutions.add(new VisualSolver(problem).solve());
        }

        Collections.sort(solutions, Comparator.comparingInt(Solution::getSolutionNumber));

        new Visualizer(new ProblemsAndSolutions(problems, solutions)).visualize();
//        new SolutionPrinter("output/", solutions).output();
    }
}
