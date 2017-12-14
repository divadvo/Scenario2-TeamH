package team.h;

import team.h.algorithm.AllProblemSolver;
import team.h.core.Problem;
import team.h.core.Solution;
import team.h.io.ProblemParser;
import team.h.io.SolutionPrinter;

import java.util.List;

public class Main {

    public static void main(String[] args)
    {
        List<Problem> problems = new ProblemParser("input/simple.rfp").parse();
        List<Solution> solutions = new AllProblemSolver(problems).solve();
        new SolutionPrinter("output/", solutions).output();
    }
}
