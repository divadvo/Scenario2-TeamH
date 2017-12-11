package team.h.algorithm;

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
            Solution solution = new Solver(problem).solve();
            solutions.add(solution);
        }
        return solutions;
    }
}
