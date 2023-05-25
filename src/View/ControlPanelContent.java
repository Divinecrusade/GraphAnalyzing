package View;

import javax.swing.*;

public abstract class ControlPanelContent extends JPanel {
    public JComboBox<String> inputVertBeg;
    public JComboBox<String> inputVertEnd;
    public JTextField outputOptimalPath;
    public JTextField outputOptimalPathLength;
    public JButton findOptimalPath;
    public JButton deleteGraph;
}
