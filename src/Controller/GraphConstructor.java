package Controller;

import Model.IModel;
import Utility.IPath;
import Utility.IVertex;
import View.IView;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GraphConstructor extends MouseAdapter {
    public GraphConstructor(IModel graphStruct, IView graphArea, JComponent controller) {
        super();
        this.graphStruct = graphStruct;
        this.graphArea = graphArea;
        controller.addMouseListener(this);
        controller.addMouseMotionListener(this);
    }

    protected boolean isCollidedVertex(@NotNull Point pos, @NotNull IVertex vert) {
        final int vert_radius = graphArea.getVisualVertexRadius(vert);

        return pos.x >= vert.getPos().x - 2 * vert_radius && pos.x <= vert.getPos().x + 2 * vert_radius &&
               pos.y >= vert.getPos().y - 2 * vert_radius && pos.y <= vert.getPos().y + 2 * vert_radius;
    }

    protected boolean isMouseOverVertex(@NotNull Point pos, @NotNull IVertex vert) {
        final int vert_radius = graphArea.getVisualVertexRadius(vert);

        return pos.x >= vert.getPos().x - vert_radius && pos.x <= vert.getPos().x + vert_radius &&
               pos.y >= vert.getPos().y - vert_radius && pos.y <= vert.getPos().y + vert_radius;
    }

    protected boolean isCollidedPath(Point pos, IPath path) {
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        if (SwingUtilities.isLeftMouseButton(e)) {
            Point mousePos = new Point(e.getX(), e.getY());
            for (IVertex vert : graphStruct.getVertexes()) {
                if (isCollidedVertex(mousePos, vert)) {
                    return;
                }
            }
            String vert_name = JOptionPane.showInputDialog(new JFrame(),"Enter vertex name");
            if (vert_name != null) graphArea.addVertex(graphStruct.createVertex(vert_name, mousePos));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        if (SwingUtilities.isLeftMouseButton(e)) {
            Point mousePos = new Point(e.getX(), e.getY());
            for (IVertex vert : graphStruct.getVertexes()) {
                if (isMouseOverVertex(mousePos, vert)) {
                    if (selected_vertex == null) {
                        selected_vertex = vert;
                        graphArea.selectVertex(selected_vertex);
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        if (SwingUtilities.isLeftMouseButton(e)) {
            Point mousePos = new Point(e.getX(), e.getY());
            for (IVertex vert : graphStruct.getVertexes()) {
                if (isMouseOverVertex(mousePos, vert)) {
                    if (selected_vertex != null && selected_vertex != vert) {
                        String distance = JOptionPane.showInputDialog(new JFrame(),"Enter path distance");
                        if (distance != null) {
                            graphArea.addPath(graphStruct.createPath(selected_vertex, vert, Double.parseDouble(distance)));
                        }
                        else {
                            graphArea.addPath(graphStruct.createPath(selected_vertex, vert));
                        }
                    }
                }
            }

            selected_vertex = null;
            pointed_vertex  = null;
            graphArea.deselectVertexes();
            graphArea.deselectTempPath();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        if (SwingUtilities.isLeftMouseButton(e) && selected_vertex != null) {
            Point mousePos = new Point(e.getX(), e.getY());
            graphArea.selectTempPath(selected_vertex.getPos(), mousePos);
            boolean mouseOverVertex = false;
            for (IVertex vert : graphStruct.getVertexes()) {
                if (isMouseOverVertex(mousePos, vert) && vert != selected_vertex) {
                    graphArea.selectVertex(vert);
                    pointed_vertex = vert;
                    mouseOverVertex = true;
                    break;
                }
            }
            if (!mouseOverVertex) {
                graphArea.deselectVertex(pointed_vertex);
                pointed_vertex = null;
            }
        }
    }

    protected final IModel graphStruct;
    protected final IView graphArea;
    private IVertex selected_vertex = null;
    private IVertex pointed_vertex  = null;
}
