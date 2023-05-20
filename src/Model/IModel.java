package Model;

import Utility.IVertex;

import java.awt.*;
import java.util.ArrayList;

public interface IModel {
    ArrayList<IVertex> getVertexes();
    IVertex createVertex(String vert_name, Point vert_pos);
}
