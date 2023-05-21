package Utility;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DrawablePath {
    public DrawablePath(IPath path, Color colour, int thickness) {
        this.path = path;
        this.colour = colour;
        this.thickness = thickness;
    }

    public void draw(@NotNull Graphics2D gfx) {
        gfx.setColor(colour);
        gfx.setStroke(new BasicStroke(thickness));
        gfx.drawLine(path.getBegin().getPos().x, path.getBegin().getPos().y,
                     path.getEnd().getPos().x, path.getEnd().getPos().y);

        String label = Double.toString(path.getDistance());
        int labelWidth = gfx.getFontMetrics().stringWidth(label);
        int labelHeight = gfx.getFontMetrics().getFont().getSize();
        Point center = new Point((path.getEnd().getPos().x + path.getBegin().getPos().x) / 2,
                                 (path.getEnd().getPos().y + path.getBegin().getPos().y) / 2);
        Rectangle box = new Rectangle(center.x - labelWidth / 2,
                                      center.y - labelHeight / 2,
                                      labelWidth, labelHeight + 4);
        gfx.setColor(Color.WHITE);
        gfx.fillRect(box.x, box.y, box.width, box.height);
        gfx.setColor(Color.BLACK);
        gfx.drawString(label, box.x, box.y + labelHeight);
    }

    public boolean isTheSame(IPath other_path) {
        return  path == other_path;
    }

    public boolean isConnectedWith(IVertex vert) {
        return path.getBegin() == vert || path.getEnd() == vert;
    }
    public Color colour;
    public int thickness;
    private final IPath path;
}
