package team.h.algorithm;

import team.h.algorithm.david.TestSolver;
import team.h.algorithm.david.VisualSolver;
import team.h.core.Problem;
import team.h.core.Solution;

import java.util.ArrayList;
import java.util.List;

public class AllProblemSolver {

    private List<Problem> problems;

    public AllProblemSolver(List<Problem> problems) {
        this.problems = problems;
    }

    public List<Solution> solve() {
        List<Solution> solutions = new ArrayList<>();

        for(Problem problem : problems) {
            Solution solution = new VisualSolver(problem).solve();
            solutions.add(solution);
        }
        return solutions;
    }
}
