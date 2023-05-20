package View;

import Utility.DrawablePath;
import Utility.DrawableVertex;
import Utility.IPath;
import Utility.IVertex;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class GraphArea extends JPanel implements IViewGraph {
    public GraphArea() {
        super();
        rnd = new Random();
    }

    @Override
    public void addVertex(IVertex vert) {
        final int min_c  = 0;
        final int max_c  = 255 + 1;
        final int radius = 10;

        Color colour = new Color(rnd.nextInt(max_c - min_c) + min_c,
                                 rnd.nextInt(max_c - min_c) + min_c,
                                 rnd.nextInt(max_c - min_c) + min_c);
        verts.add(new DrawableVertex(vert, colour, radius));
    }

    @Override
    public void removeVertex(IVertex vert) {
        //noinspection SuspiciousMethodCalls
        verts.remove(vert);
    }

    @Override
    public void addPath(IPath path) {
        final Color colour = Color.BLACK;
        final int thickness = 4;

        paths.add(new DrawablePath(path, colour, thickness));
    }

    @Override
    public void removePath(IPath path) {
        //noinspection SuspiciousMethodCalls
        paths.remove(path);
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

    private final Random rnd;
    private List<DrawableVertex> verts;
    private List<DrawablePath>   paths;
}
