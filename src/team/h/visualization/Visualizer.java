package team.h.visualization;

import team.h.core.Problem;
import team.h.core.ProblemsAndSolutions;

import java.util.List;

public class Visualizer {

    private VisualizerGUI visualizerGUI;

    public Visualizer(ProblemsAndSolutions problemsAndSolutions) {
        this.visualizerGUI = new VisualizerGUI(problemsAndSolutions);
    }

    public void visualize() {
        this.visualizerGUI.setVisible(true);
    }
}
