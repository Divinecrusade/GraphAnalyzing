package Utility;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DrawableVertex {
    public DrawableVertex(IVertex vert, Color colour, Font font, int radius) {
        this.vert   = vert;
        this.colour = colour;
        this.font   = font;
        this.radius = radius;
    }

    public void draw(@NotNull Graphics2D gfx) {
        gfx.setColor(colour);
        gfx.fillOval(vert.getPos().x - radius, vert.getPos().y - radius, radius * 2, radius * 2);
        gfx.drawString(vert.getName(), vert.getPos().x - radius, vert.getPos().y - radius - font.getSize() / 2);
    }

    public void draw(@NotNull Graphics2D gfx, Color border_colour) {
        draw(gfx);
        drawBorder(gfx, border_colour);
    }

    public void drawBorder(@NotNull Graphics2D gfx, Color border_colour) {
        gfx.setColor(border_colour);
        gfx.drawOval(vert.getPos().x - radius, vert.getPos().y - radius, radius * 2, radius * 2);
    }

    public boolean isTheSame(IVertex other_vert) {
        return  vert == other_vert;
    }

    public  Color   colour;
    public  Font    font;
    public  int     radius;
    private final IVertex vert;
}
