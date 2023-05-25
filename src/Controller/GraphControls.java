package Controller;

import Model.IModel;
import Utility.IPath;
import Utility.IVertex;
import View.ControlPanelContent;
import View.IView;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GraphControls implements IFieldUpdater {
    @Override
    public void resetOutputFields() {
        outputOptimalPath.setText("");
        outputOptimalPathLength.setText("");
    }

    @Override
    public void updateVertexesPull() {
        var vertexes_objects = graph.getVertexes();
        String[] vertexes_names_1 = new String[vertexes_objects.size()];
        String[] vertexes_names_2 = new String[vertexes_objects.size()];
        for (int i = 0; i != vertexes_objects.size(); ++i) {
            vertexes_names_1[i] = vertexes_objects.get(i).getName();
            vertexes_names_2[i] = vertexes_objects.get(i).getName();
        }
        DefaultComboBoxModel<String> new_model_1 = new DefaultComboBoxModel<>( vertexes_names_1 );
        DefaultComboBoxModel<String> new_model_2 = new DefaultComboBoxModel<>( vertexes_names_2 );
        vertBegPull.setModel(new_model_1);
        vertEndPull.setModel(new_model_2);
    }

    static private class FindOptimalPathHandler implements ActionListener {
        public FindOptimalPathHandler(GraphControls graphControls, ComboBoxModel<String> pullOfVertBeg, ComboBoxModel<String> pullOfVertEnd, AbstractButton button, JTextComponent outputPath, JTextComponent outputPathLength) {
            this.graphControls = graphControls;
            this.pullOfVertBeg = pullOfVertBeg;
            this.pullOfVertEnd = pullOfVertEnd;
            this.outputPath = outputPath;
            this.outputPathLength = outputPathLength;
            button.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            IVertex beg = graphControls.graph.getVertex((String) pullOfVertBeg.getSelectedItem());
            IVertex end = graphControls.graph.getVertex((String) pullOfVertEnd.getSelectedItem());

            List<IPath> optimalPath = graphControls.graph.getOptimalPath(beg, end);

            double optimalLength = 0;
            String optimalPathStr = "";
            for (IPath path : optimalPath) {
                optimalLength += path.getDistance();
                optimalPathStr = optimalPathStr.concat(path.getBegin().getName());
                optimalPathStr = optimalPathStr.concat("→");
                graphControls.graphArea.selectVertex(path.getBegin());
                graphControls.graphArea.selectPath(path);
            }
            optimalPathStr = optimalPathStr.replaceFirst("→", "");

            outputPath.setText(optimalPathStr);
            outputPathLength.setText(Double.toString(optimalLength));
        }

        private final ComboBoxModel<String> pullOfVertBeg;
        private final ComboBoxModel<String> pullOfVertEnd;
        private final JTextComponent outputPath;
        private final JTextComponent outputPathLength;

        private final GraphControls graphControls;
    }

    static private class DeleteGraphHandler implements ActionListener {

        public DeleteGraphHandler(GraphControls graphControls, AbstractButton button) {
            this.graphControls = graphControls;
            button.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            graphControls.graph.deleteVertexes();
            graphControls.graphArea.removeVertexes();
        }

        private final GraphControls graphControls;
    }

    public GraphControls(IModel graph, IView graphArea, ControlPanelContent controlPanelContent) {
        this.graph = graph;
        this.graphArea = graphArea;
        vertBegPull = controlPanelContent.inputVertBeg;
        vertEndPull = controlPanelContent.inputVertEnd;
        outputOptimalPath = controlPanelContent.outputOptimalPath;
        outputOptimalPathLength = controlPanelContent.outputOptimalPathLength;

        new FindOptimalPathHandler(this,
                controlPanelContent.inputVertBeg.getModel(),
                controlPanelContent.inputVertEnd.getModel(),
                controlPanelContent.findOptimalPath,
                controlPanelContent.outputOptimalPath,
                controlPanelContent.outputOptimalPathLength);
        new DeleteGraphHandler(this, controlPanelContent.deleteGraph);
    }

    private final IModel graph;
    private final IView graphArea;
    private final JComboBox<String> vertBegPull;
    private final JComboBox<String> vertEndPull;
    private final JTextField outputOptimalPath;
    private final JTextField outputOptimalPathLength;
}
