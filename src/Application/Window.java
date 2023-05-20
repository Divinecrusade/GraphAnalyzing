package Application;
import Control.Handler;
import Model.Graph;
import View.GraphArea;

import javax.swing.JFrame;

public class Window extends JFrame {

    public Window() {
        super("Graph analyzing");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        graphStruct = new Graph();
        graphArea = new GraphArea(40, 4);
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
