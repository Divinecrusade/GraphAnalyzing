package Application;

import Controller.GraphConstructor;
import Model.Graph;
import View.GraphArea;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window() {
        super("Graph analyzing");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        graphStruct = new Graph();
        graphArea = new GraphArea(new Font("serif", Font.BOLD, 16), 20, 4);
        graphConstructor = new GraphConstructor(graphStruct, graphArea, graphArea);

        this.add(graphArea);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Window();
    }

    Graph graphStruct;
    GraphArea graphArea;
    GraphConstructor graphConstructor;

}
