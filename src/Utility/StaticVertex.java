package Utility;

import java.awt.*;

public class StaticVertex implements IVertex {
    public StaticVertex(String name, Point pos) {
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

    final private String name;
    final private Point  pos;
}
