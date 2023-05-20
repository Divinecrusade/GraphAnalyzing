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
        gfx.fillOval(vert.getPos().x - radius, vert.getPos().y - radius, radius * 2, radius * 2);

        gfx.setColor(Color.BLACK);
        gfx.drawString(vert.getName(), vert.getPos().x - radius, vert.getPos().y - radius);
    }

    public boolean isTheSame(IVertex other_vert) {
        return  vert == other_vert;
    }

    public  Color   colour;
    public  int     radius;
    private final IVertex vert;
}
