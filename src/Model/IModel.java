package Model;

import Utility.IPath;
import Utility.IVertex;

import java.awt.*;
import java.util.ArrayList;

public interface IModel {
    ArrayList<IVertex> getVertexes();
    ArrayList<IPath> getPaths();
    IVertex createVertex(String vert_name, Point vert_pos);
    IPath createPath(IVertex beg, IVertex end, double distance);
    IPath createPath(IVertex beg, IVertex end);
    void deleteVertex(IVertex vert);
    void deletePath(IPath path);
}
