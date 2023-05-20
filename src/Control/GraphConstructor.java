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

public class GraphConstructor extends MouseAdapter {
    public GraphConstructor(IModel graphStruct, IViewGraph graphArea, JComponent controller) {
        super();
        this.graphStruct = graphStruct;
        this.graphArea = graphArea;
        controller.addMouseListener(this);
    }

    protected boolean isCollisedVertex(@NotNull Point pos, @NotNull IVertex vert) {
        final int vert_radius = graphArea.getVisualVertexRadius(vert);

        return pos.x >= vert.getPos().x - vert_radius && pos.x <= vert.getPos().x + vert_radius &&
               pos.y >= vert.getPos().y - vert_radius && pos.y <= vert.getPos().y + vert_radius;
    }

    protected boolean isCollisedPath(Point pos, IPath path) {
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        if (SwingUtilities.isLeftMouseButton(e)) {
            for (IVertex vert : graphStruct.getVertexes()) {
                if (isCollisedVertex(new Point(e.getX(), e.getY()), vert)) {
                    return;
                }
            }

            String vert_name = JOptionPane.showInputDialog(new JFrame(),"Enter vertex name");
            if (vert_name != null) graphArea.addVertex(graphStruct.createVertex(vert_name, new Point(e.getX(), e.getY())));
        }
    }

    protected final IModel graphStruct;
    protected final IViewGraph graphArea;
}
