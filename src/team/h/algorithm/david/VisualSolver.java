package team.h.algorithm.david;

import team.h.algorithm.Solver;
import team.h.core.Problem;
import team.h.core.Shape;
import team.h.core.Solution;

import java.util.ArrayList;

public class VisualSolver extends Solver {

    public VisualSolver(Problem problem) {
        super(problem);
    }

    @Override
    public Solution solve() {
        Solution solution = new Solution(problem.getProblemNumber(), new ArrayList<Shape>());
        return solution;
    }
}
