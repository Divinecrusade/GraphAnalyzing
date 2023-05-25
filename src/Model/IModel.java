package Model;

import Utility.IPath;
import Utility.IVertex;

import java.awt.*;
import java.util.ArrayList;

public interface IModel {
    ArrayList<IVertex> getVertexes();
    ArrayList<IPath> getPaths();
    IVertex getVertex(String name);
    ArrayList<IPath> getOptimalPath(IVertex begin, IVertex end);
    IVertex createVertex(String vert_name, Point vert_pos);
    IPath createPath(IVertex beg, IVertex end, double distance);
    IPath createPath(IVertex beg, IVertex end);
    void deleteVertexes();
    void deleteVertex(IVertex vert);
    void deletePath(IPath path);
}
