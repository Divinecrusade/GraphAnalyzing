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
        final int path_thickness = graphArea.getVisualPathThickness(path) * 2;
        double dy = path.getEnd().getPos().y - path.getBegin().getPos().y;
        double dx = path.getEnd().getPos().x - path.getBegin().getPos().x;
        double dist = Math.sqrt(dx * dx + dy * dy);

        double angle = Math.atan2(dy, dx);
        double cos = Math.cos(-angle);
        double sin = Math.sin(-angle);

        double xRot = (pos.x - path.getBegin().getPos().x) * cos - (pos.y - path.getBegin().getPos().y) * sin;
        double yRot = (pos.x - path.getBegin().getPos().x) * sin + (pos.y - path.getBegin().getPos().y) * cos;

        if (0 <= xRot && xRot <= dist) {
            return Math.abs(yRot) <= path_thickness;
        }

        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        Point mousePos = new Point(e.getX(), e.getY());
        if (SwingUtilities.isLeftMouseButton(e)) {
            for (IVertex vert : graphStruct.getVertexes()) {
                if (isCollidedVertex(mousePos, vert)) {
                    return;
                }
            }
            String vert_name = JOptionPane.showInputDialog(new JFrame(),"Enter vertex name");
            if (vert_name != null && !vert_name.equals("")) graphArea.addVertex(graphStruct.createVertex(vert_name, mousePos));
        }
        else if (SwingUtilities.isRightMouseButton(e)) {
            for (IVertex vert : graphStruct.getVertexes()) {
                if (isMouseOverVertex(mousePos, vert)) {
                    graphStruct.deleteVertex(vert);
                    graphArea.removeVertex(vert);
                    return;
                }
            }
            for (IPath path : graphStruct.getPaths()) {
                if (isCollidedPath(mousePos, path)) {
                    graphStruct.deletePath(path);
                    graphArea.removePath(path);
                }
            }
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
                            if (distance.equals("")) {
                                graphArea.addPath(graphStruct.createPath(selected_vertex, vert));
                            }
                            else {
                                graphArea.addPath(graphStruct.createPath(selected_vertex, vert, Double.parseDouble(distance)));
                            }
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
