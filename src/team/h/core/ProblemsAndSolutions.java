package team.h.core;

import java.util.List;

public class ProblemsAndSolutions {

    private List<Problem> problems;
    private List<Solution> solutions;

    public ProblemsAndSolutions(List<Problem> problems, List<Solution> solutions) {
        this.problems = problems;
        this.solutions = solutions;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }
}
