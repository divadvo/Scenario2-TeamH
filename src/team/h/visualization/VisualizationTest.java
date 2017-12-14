package team.h.visualization;

import team.h.algorithm.AllProblemSolver;
import team.h.core.Problem;
import team.h.core.ProblemsAndSolutions;
import team.h.core.Solution;
import team.h.io.ProblemParser;

import java.util.List;

public class VisualizationTest
{

    public static void main(String[] args)
    {
        List<Problem> problems = new ProblemParser("input/problem11.rfp").parse();
        List<Solution> solutions = new AllProblemSolver(problems).solve();
//        new SolutionPrinter("output/", solutions).output();
        new Visualizer(new ProblemsAndSolutions(problems, solutions)).visualize();
    }
}
