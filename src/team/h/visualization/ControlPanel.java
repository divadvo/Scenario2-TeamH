package team.h.visualization;

import team.h.core.Problem;
import team.h.core.ProblemsAndSolutions;
import team.h.core.Solution;

import javax.swing.*;
import java.util.List;

public class ControlPanel extends JPanel {

    private List<Problem> problems;
    private List<Solution> solutions;
    private VisualizerPanel visualizerPanel;

    private JSlider sliderProblemNumber;
    private JLabel labelCurrentProblem;
    private JLabel labelNumberOfProblems;

    private int numberOfProblems;
    private int currentProblemNumber;

    public ControlPanel(VisualizerPanel visualizerPanel, ProblemsAndSolutions problemsAndSolutions) {
        this.visualizerPanel = visualizerPanel;

        this.problems = problemsAndSolutions.getProblems();
        numberOfProblems = problems.size(); // TODO: change
        currentProblemNumber = 1;

        this.solutions = problemsAndSolutions.getSolutions();

        initUI();
        updateLabels();
        redrawProblem();
    }

    private void initUI() {
        labelNumberOfProblems = new JLabel();
        labelCurrentProblem = new JLabel();

        createProblemSlider();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(labelNumberOfProblems);
        this.add(labelCurrentProblem);
        this.add(sliderProblemNumber);
    }

    private void createProblemSlider() {
        sliderProblemNumber = new JSlider(1, numberOfProblems, 1);

        sliderProblemNumber.addChangeListener(e -> {
            int sliderValue = ((JSlider)e.getSource()).getValue();
            currentProblemNumber = sliderValue;
            updateLabels();
            redrawProblem();
        });

        sliderProblemNumber.setMajorTickSpacing(10);
        sliderProblemNumber.setMinorTickSpacing(1);
        sliderProblemNumber.setPaintTicks(true);
        sliderProblemNumber.setPaintLabels(true);
    }

    private void redrawProblem() {
        Problem currentProblem = problems.get(currentProblemNumber - 1);
        Solution currentSolution = solutions.get(currentProblemNumber - 1);
        visualizerPanel.setProblem(currentProblem);
        visualizerPanel.setSolution(currentSolution);
        visualizerPanel.redraw();
    }

    private void updateLabels() {
        labelNumberOfProblems.setText("Number of problems: " + numberOfProblems);
        labelCurrentProblem.setText("Current Problem: " + currentProblemNumber);
    }
}
