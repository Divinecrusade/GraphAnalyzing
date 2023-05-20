package Control;

import Model.IModel;
import Utility.IPath;
import Utility.IVertex;
import View.IViewGraph;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Handler implements IHandler{

    private abstract class GraphMouseAdapter extends MouseAdapter {
        public GraphMouseAdapter(IModel graphStruct, IViewGraph graphArea) {
            super();
            this.graphStruct = graphStruct;
            this.graphArea = graphArea;
        }

        protected boolean isCollisedVertex(@NotNull Point pos, @NotNull IVertex vert) {
            final int vert_radius = graphArea.getVisualVertexRadius(vert);

            return pos.x >= vert.getPos().x - vert_radius && pos.x <= vert.getPos().x + vert_radius &&
                   pos.y >= vert.getPos().y - vert_radius && pos.y <= vert.getPos().y + vert_radius;
        }

        protected boolean isCollisedPath(Point pos, IPath path) {
            return false;
        }

        protected final IModel graphStruct;
        protected final IViewGraph graphArea;
    }

    public Handler(IModel graphStruct, IViewGraph graphArea) {
        this.graphStruct = graphStruct;
        this.graphArea = graphArea;
    }

    @Override
    public MouseAdapter getMouseClickedAdapter() {
        return new GraphMouseAdapter(graphStruct, graphArea) {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (SwingUtilities.isLeftMouseButton(e)) {
                    for (IVertex vert : graphStruct.getVertexes()) {
                        if (isCollisedVertex(new Point(e.getX(), e.getY()), vert)) {
                            return;
                        }
                        else {
                            String vert_name = JOptionPane.showInputDialog(new JFrame(),"Enter vertex name");
                            graphArea.addVertex(graphStruct.createVertex(vert_name, new Point(e.getX(), e.getY())));
                        }
                    }
                }
            }
        };
    }

    //private IViewControls controlPanel;
    private final IModel graphStruct;
    private final IViewGraph graphArea;
}
