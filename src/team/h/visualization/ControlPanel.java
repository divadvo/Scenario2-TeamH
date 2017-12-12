package team.h.visualization;

import team.h.core.Problem;
import team.h.core.ProblemsAndSolutions;
import team.h.core.Solution;
import team.h.io.SolutionPrinter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ControlPanel extends JPanel {

    private List<Problem> problems;
    private List<Solution> solutions;
    private VisualizerPanel visualizerPanel;

    private JSlider sliderProblemNumber;
    private JSlider sliderAngle;
    private JSlider sliderDelta;
    private JLabel labelCurrentProblem;
    private JLabel labelNumberOfProblems;

    private int numberOfProblems;
    private int currentProblemNumber;
    private JComboBox<Visualizer.TYPE> jComboBox;
    private JComboBox<Integer> jComboBoxProblem;

    private double angle = 1;
    private double delta = 1;

    public ControlPanel(VisualizerPanel visualizerPanel, ProblemsAndSolutions problemsAndSolutions) {
        this.visualizerPanel = visualizerPanel;

        this.problems = problemsAndSolutions.getProblems();
        numberOfProblems = problems.size(); // TODO: change
        currentProblemNumber = 1;

        this.solutions = problemsAndSolutions.getSolutions();

        initUI();

        redrawProblem();
    }

    private void generateSolution(int problemNumber) {
        Solution currentSolution = solutions.get(problemNumber - 1);
        List<Solution> sols = new ArrayList<>();
        sols.add(currentSolution);
        new SolutionPrinter("output/", sols).output();
    }

    private void initUI() {
        labelNumberOfProblems = new JLabel();
        labelCurrentProblem = new JLabel();
        JButton buttonRedraw = new JButton("Redraw");
        buttonRedraw.addActionListener(e -> redrawProblem());

        JButton buttonGenerateSolution = new JButton("Generate Solution");
        buttonGenerateSolution.addActionListener(e -> generateSolution(currentProblemNumber));

        createProblemSlider();
        createComboBox();
        createDeltaSliders();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        this.setLayout(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();


        this.add(labelNumberOfProblems);
        this.add(labelCurrentProblem);
        this.add(sliderProblemNumber);
        this.add(jComboBoxProblem);
        this.add(buttonRedraw);
        this.add(jComboBox);
        this.add(buttonGenerateSolution);
        this.add(sliderAngle);
        this.add(sliderDelta);
        this.add(Box.createVerticalGlue());

    }

    private void createDeltaSliders() {
        sliderAngle = new JSlider(1, 100, 10);
        sliderDelta = new JSlider(1, 100, 1);

        sliderAngle.setMajorTickSpacing(10);
        sliderAngle.setMinorTickSpacing(1);
        sliderAngle.setPaintTicks(true);
        sliderAngle.setPaintLabels(true);

        sliderDelta.setMajorTickSpacing(10);
        sliderDelta.setMinorTickSpacing(1);
        sliderDelta.setPaintTicks(true);
        sliderDelta.setPaintLabels(true);

        sliderAngle.addChangeListener(e -> {
            int sliderValue = ((JSlider)e.getSource()).getValue();
            angle = 10 / sliderValue;
            redrawProblem();
        });

        sliderDelta.addChangeListener(e -> {
            int sliderValue = ((JSlider)e.getSource()).getValue();
            delta = 1 / sliderValue;
            redrawProblem();
        });

    }

    private void createProblemSlider() {
        sliderProblemNumber = new JSlider(1, numberOfProblems, 1);

        sliderProblemNumber.addChangeListener(e -> {
            int sliderValue = ((JSlider)e.getSource()).getValue();
            currentProblemNumber = sliderValue;
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
        jComboBox.addActionListener (e -> redrawProblem());

//        int a[] = IntStream.range(1, problems.size()).toArray();
//        List<Integer> integers = Arrays.asList(a);
        jComboBoxProblem = new JComboBox<>();
        for(int i = 1; i <= problems.size(); i++)
            jComboBoxProblem.addItem(i);

        jComboBoxProblem.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                int i = (Integer)jComboBoxProblem.getSelectedItem();
                currentProblemNumber = i;
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
        visualizerPanel.setDeltaAndAngle(delta, angle);
        visualizerPanel.setDrawingType(type);
        visualizerPanel.setProblem(currentProblem);
        visualizerPanel.setSolution(currentSolution);
        visualizerPanel.redraw();

        sliderProblemNumber.setValue(currentProblemNumber);
        jComboBoxProblem.setSelectedIndex(currentProblemNumber - 1);

        labelNumberOfProblems.setText("Number of problems: " + numberOfProblems);
        labelCurrentProblem.setText("Current Problem: " + currentProblemNumber);
    }
}
