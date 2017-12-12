package team.h.visualization;

import team.h.core.Problem;
import team.h.core.ProblemsAndSolutions;

import java.util.List;

public class Visualizer {

    private VisualizerGUI visualizerGUI;

    public enum TYPE {
        RANDOM,
        BOX;
    }

    public Visualizer(ProblemsAndSolutions problemsAndSolutions) {
        this.visualizerGUI = new VisualizerGUI(problemsAndSolutions);
    }

    public void visualize() {
        this.visualizerGUI.setVisible(true);
    }
}
