package Utility;

import java.awt.*;

public class MovableVertex implements IVertex {
    public MovableVertex(String name, Point pos) {
        this.name = name;
        this.pos  = pos;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Point getPos() {
        return pos;
    }

    public void moveTo(Point new_pos) {
        pos = new_pos;
    }

    final private String name;
    private Point  pos;
}
