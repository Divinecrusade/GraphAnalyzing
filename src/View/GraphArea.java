package View;

import Utility.DrawablePath;
import Utility.DrawableVertex;
import Utility.IPath;
import Utility.IVertex;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GraphArea extends JPanel implements IView {
    public GraphArea(Font std_font, int std_vert_radius, int std_path_thickness) {
        super();

        this.std_font = std_font;
        rnd = new Random();
        this.std_vert_radius = std_vert_radius;
        this.std_path_thickness = std_path_thickness;

        verts = new ArrayList<>();
        paths = new ArrayList<>();
        selected_verts = new ArrayList<>();
    }

    @Override
    public void addVertex(IVertex vert) {
        if (vert != null) {
            final int min_c  = 0;
            final int max_c  = 255 + 1;

            Color colour = new Color(rnd.nextInt(max_c - min_c) + min_c,
                                     rnd.nextInt(max_c - min_c) + min_c,
                                     rnd.nextInt(max_c - min_c) + min_c);
            DrawableVertex dr_vert = new DrawableVertex(vert, colour, std_font, std_vert_radius);
            verts.add(dr_vert);
            dr_vert.draw((Graphics2D) getGraphics());
        }
        revalidate();
        repaint();
    }

    @Override
    public void removeVertex(IVertex vert) {
        paths.removeIf(path -> path.isConnectedWith(vert));
        DrawableVertex dr_vert = findVertex(vert);
        verts.remove(dr_vert);

        revalidate();
        repaint();
    }

    @Override
    public void addPath(IPath path) {
        final Color colour = Color.BLACK;

        if (path != null) {
            paths.add(new DrawablePath(path, colour, std_path_thickness));
        }
        revalidate();
        repaint();
    }

    @Override
    public void removePath(IPath path) {
        paths.remove(findPath(path));

        revalidate();
        repaint();
    }

    @Override
    public int getVisualVertexRadius(IVertex vert) {
        DrawableVertex dr_vert = findVertex(vert);
        return (dr_vert == null ? std_vert_radius : dr_vert.radius);
    }

    @Override
    public int getVisualPathThickness(IPath path) {
        DrawablePath dr_path = findPath(path);
        return (dr_path == null ? std_path_thickness : dr_path.thickness);
    }

    @Override
    public void selectVertex(IVertex vert) {
        DrawableVertex dr_vert = findVertex(vert);
        if (!selected_verts.contains(dr_vert)) selected_verts.add(dr_vert);

        revalidate();
        repaint();
    }

    @Override
    public void selectTempPath(Point beg, Point end) {
        tempPathBeg = beg;
        tempPathEnd = end;

        revalidate();
        repaint();
    }

    @Override
    public void deselectVertexes() {
        selected_verts.clear();

        revalidate();
        repaint();
    }

    @Override
    public void deselectVertex(IVertex vert) {
        DrawableVertex dr_vert = findVertex(vert);
        selected_verts.remove(dr_vert);

        revalidate();
        repaint();
    }

    @Override
    public void deselectTempPath() {
        tempPathBeg = null;
        tempPathEnd = null;

        revalidate();
        repaint();
    }

    @Override
    public void paintComponent(Graphics gfx) {
        super.paintComponent(gfx);
        Graphics2D gfx2D = (Graphics2D) gfx;

        if (tempPathBeg != null && tempPathEnd != null) {
            gfx2D.setColor(selection_colour);
            gfx2D.setStroke(new BasicStroke(std_path_thickness));
            gfx2D.drawLine(tempPathBeg.x, tempPathBeg.y, tempPathEnd.x, tempPathEnd.y);
        }

        for (DrawablePath path : paths) {
            path.draw(gfx2D);
        }
        for (DrawableVertex vert : verts) {
            vert.draw(gfx2D);
        }
        for (DrawableVertex vert : selected_verts) {
            vert.drawBorder(gfx2D, selection_colour);
        }
    }

    private @Nullable DrawableVertex findVertex(IVertex vert) {
        for (DrawableVertex dr_vert : verts) {
            if (dr_vert.isTheSame(vert)) {
                return dr_vert;
            }
        }

        return null;
    }

    private @Nullable DrawablePath findPath(IPath path) {
        for (DrawablePath dr_path : paths) {
            if (dr_path.isTheSame(path)) {
                return dr_path;
            }
        }

        return null;
    }

    private final Random rnd;
    private final Font   std_font;
    private final int std_vert_radius;
    private final int std_path_thickness;
    private final Color selection_colour = Color.RED;

    private Point tempPathBeg = null;
    private Point tempPathEnd = null;

    private final ArrayList<DrawableVertex> verts;
    private final ArrayList<DrawablePath>   paths;
    private final ArrayList<DrawableVertex> selected_verts;
}
