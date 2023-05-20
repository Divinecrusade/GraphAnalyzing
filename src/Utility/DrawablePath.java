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

        gfx.drawString(Double.toString(path.getDistance()),
                     Math.abs(path.getEnd().getPos().x - path.getBegin().getPos().x) / 2,
                     Math.abs(path.getEnd().getPos().y - path.getBegin().getPos().y) / 2);
    }

    public boolean isTheSame(IPath other_path) {
        return  path == other_path;
    }
    public Color colour;
    public int thickness;
    private final IPath path;
}
