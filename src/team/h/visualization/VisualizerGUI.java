package team.h.visualization;

import team.h.Problem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VisualizerGUI extends JFrame {

    private static final String TITLE = "Team H - Visualizer";

    private List<Problem> problems;

    private VisualizerPanel visualizerPanel;
    private ControlPanel controlPanel;

    public VisualizerGUI(List<Problem> problems){
        this.problems = problems;

        visualizerPanel = new VisualizerPanel();
        controlPanel = new ControlPanel(visualizerPanel, problems);

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.9);
        splitPane.setEnabled(false);
        splitPane.setDividerSize(1);

        splitPane.add(visualizerPanel);
        splitPane.add(controlPanel);

        container.add(splitPane, BorderLayout.CENTER);

//        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
//        visualizerPanel = new VisualizerPanel();
//        container.add(visualizerPanel);
//
//        controlPanel = new ControlPanel(problems);
//        container.add(controlPanel);

        this.add(container);

        setTitle(TITLE);
        setSize(1280,720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}