package View;

import Control.IHandler;
import Utility.DrawablePath;
import Utility.DrawableVertex;
import Utility.IPath;
import Utility.IVertex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GraphArea extends JPanel implements IViewGraph {
    public GraphArea(@NotNull IHandler controller, int std_vert_radius, int std_path_thickness) {
        super();

        rnd = new Random();
        this.std_vert_radius = std_vert_radius;
        this.std_path_thickness = std_path_thickness;

        this.addMouseListener(controller.getMouseClickedAdapter());

        verts = new ArrayList<>();
        paths = new ArrayList<>();
    }

    @Override
    public void addVertex(IVertex vert) {
        final int min_c  = 0;
        final int max_c  = 255 + 1;

        Color colour = new Color(rnd.nextInt(max_c - min_c) + min_c,
                                 rnd.nextInt(max_c - min_c) + min_c,
                                 rnd.nextInt(max_c - min_c) + min_c);
        verts.add(new DrawableVertex(vert, colour, std_vert_radius));
    }

    @Override
    public void removeVertex(IVertex vert) {
        //noinspection SuspiciousMethodCalls
        verts.remove(vert);
    }

    @Override
    public void addPath(IPath path) {
        final Color colour = Color.BLACK;

        paths.add(new DrawablePath(path, colour, std_path_thickness));
    }

    @Override
    public void removePath(IPath path) {
        //noinspection SuspiciousMethodCalls
        paths.remove(path);
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
    public void paintComponent(Graphics gfx) {
        super.paintComponent(gfx);
        Graphics2D gfx2D = (Graphics2D) gfx;

        for (DrawableVertex vert : verts) {
            vert.draw(gfx2D);
        }

        for (DrawablePath path : paths) {
            path.draw(gfx2D);
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
    private final int std_vert_radius;
    private final int std_path_thickness;

    private final ArrayList<DrawableVertex> verts;
    private final ArrayList<DrawablePath>   paths;
}
