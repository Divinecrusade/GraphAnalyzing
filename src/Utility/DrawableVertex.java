package Utility;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DrawableVertex {
    public DrawableVertex(IVertex vert, Color colour, int radius) {
        this.vert   = vert;
        this.colour = colour;
        this.radius = radius;
    }

    public void draw(@NotNull Graphics2D gfx) {
        gfx.setColor(colour);
        gfx.drawOval(vert.getPos().x, vert.getPos().y, radius, radius);
        gfx.drawString(vert.getName(), vert.getPos().x, vert.getPos().y);
    }

    public  Color   colour;
    public  int     radius;
    private final IVertex vert;
}
