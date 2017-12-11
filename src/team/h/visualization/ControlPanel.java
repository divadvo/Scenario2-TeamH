package team.h.visualization;

import team.h.core.Problem;
import team.h.core.ProblemsAndSolutions;
import team.h.core.Solution;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
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
    private JComboBox<Visualizer.TYPE> jComboBox;

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
        JButton buttonRedraw = new JButton("Redraw");
        buttonRedraw.addActionListener(e -> redrawProblem());

        createProblemSlider();
        createComboBox();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(labelNumberOfProblems);
        this.add(labelCurrentProblem);
        this.add(sliderProblemNumber);
        this.add(buttonRedraw);
        this.add(jComboBox);
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

    private void createComboBox() {
//        String[] types = getNames(Visualizer.TYPE.class);
        jComboBox = new JComboBox<>(Visualizer.TYPE.values());
        jComboBox.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                redrawProblem();
            }
        });
    }

    private static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    private void redrawProblem() {
        Problem currentProblem = problems.get(currentProblemNumber - 1);
        Solution currentSolution = solutions.get(currentProblemNumber - 1);
        Visualizer.TYPE type = (Visualizer.TYPE) jComboBox.getSelectedItem();
        visualizerPanel.setDrawingType(type);
        visualizerPanel.setProblem(currentProblem);
        visualizerPanel.setSolution(currentSolution);
        visualizerPanel.redraw();
    }

    private void updateLabels() {
        labelNumberOfProblems.setText("Number of problems: " + numberOfProblems);
        labelCurrentProblem.setText("Current Problem: " + currentProblemNumber);
    }
}
