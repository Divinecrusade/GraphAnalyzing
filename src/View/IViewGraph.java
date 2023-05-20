package View;

import Utility.IPath;
import Utility.IVertex;

public interface IViewGraph {
    void addVertex(IVertex vert);
    void removeVertex(IVertex vert);
    void addPath(IPath path);
    void removePath(IPath path);

    int getVisualVertexRadius(IVertex vert);
    int getVisualPathThickness(IPath path);
}
