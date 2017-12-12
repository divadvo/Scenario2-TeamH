package team.h.algorithm;

import team.h.core.Point;
import team.h.core.Problem;
import team.h.core.Shape;
import team.h.core.Solution;

import java.util.ArrayList;
import java.util.List;

public abstract class Solver {

    protected Problem problem;
    protected Solution solution;

    public Solver(Problem problem) {
        this.problem = problem;
    }

    public abstract Solution solve();
}
