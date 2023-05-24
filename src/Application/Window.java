package Application;

import Controller.GraphConstructor;
import Model.Graph;
import View.ControlPanel;
import View.GraphArea;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window() {
        super("Graph analyzing");
        setSize(1200, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        graphStruct = new Graph();
        graphArea = new GraphArea(new Font("serif", Font.BOLD, 16), 20, 4);
        graphConstructor = new GraphConstructor(graphStruct, graphArea, graphArea);
        controlPanel = new ControlPanel(getFontMetrics(new Font("serif", Font.PLAIN, 16)), Color.GRAY);
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = 2;
        gbc.gridheight = 1;

        gbc.weighty = 1;
        gbc.weightx = 0.975;
        gbc.fill = GridBagConstraints.BOTH;
        gbl.setConstraints(graphArea, gbc);
        this.add(graphArea);

        gbc.weightx = 0.025;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets.top = 10;

        gbl.setConstraints(controlPanel, gbc);
        this.add(controlPanel);
        setVisible(true);

        getContentPane().setBackground(Color.GRAY);
    }

    public static void main(String[] args) {
        new Window();
    }

    Graph graphStruct;
    GraphArea graphArea;
    ControlPanel controlPanel;
    GraphConstructor graphConstructor;

}
