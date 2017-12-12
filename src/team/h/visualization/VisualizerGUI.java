package team.h.visualization;

import team.h.core.Problem;
import team.h.core.ProblemsAndSolutions;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VisualizerGUI extends JFrame {

    private static final String TITLE = "Team H - Visualizer";

    private VisualizerPanel visualizerPanel;
    private ControlPanel controlPanel;

    public VisualizerGUI(ProblemsAndSolutions problemsAndSolutions){
        visualizerPanel = new VisualizerPanel();
        controlPanel = new ControlPanel(visualizerPanel, problemsAndSolutions);

        JPanel container = new JPanel();
//        container.setLayout(new BorderLayout());
//        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
//        splitPane.setResizeWeight(0.9);
//        splitPane.setEnabled(false);
//        splitPane.setDividerSize(1);
//
//        splitPane.add(visualizerPanel);
//        splitPane.add(controlPanel);
//
//        container.add(splitPane, BorderLayout.CENTER);

        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        visualizerPanel.setMinimumSize(new Dimension(1280, 720));
        container.add(visualizerPanel);
        controlPanel.setMinimumSize(new Dimension(200, 720));
        container.add(controlPanel);
        container.setMinimumSize(new Dimension(1480, 720));

        this.add(container);

        setTitle(TITLE);
        setSize(1480,720); // 1280
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}