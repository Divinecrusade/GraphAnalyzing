package Application;

import Control.Handler;
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
        handler = new Handler(graphStruct, graphArea);

        graphArea.InitListeners(handler);

        this.add(graphArea);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Window();
    }

    Graph graphStruct;
    GraphArea graphArea;
    Handler handler;

}
