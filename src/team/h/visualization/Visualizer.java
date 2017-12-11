package team.h.visualization;

import team.h.core.Problem;

import java.util.List;

public class Visualizer {

    private List<Problem> problems;
    private VisualizerGUI visualizerGUI;

    public Visualizer(List<Problem> problems) {
        this.problems = problems;
        this.visualizerGUI = new VisualizerGUI(problems);
    }

    public void visualize() {
        this.visualizerGUI.setVisible(true);
    }
}
