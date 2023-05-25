package View;

import Utility.IPath;
import Utility.IVertex;

import java.awt.*;

public interface IView {
    void addVertex(IVertex vert);
    void removeVertex(IVertex vert);
    void removeVertexes();
    void addPath(IPath path);
    void removePath(IPath path);

    int getVisualVertexRadius(IVertex vert);
    int getVisualPathThickness(IPath path);

    void selectVertex(IVertex vert);
    void selectPath(IPath path);

    void selectTempPath(Point beg, Point end);

    void deselectVertexes();

    void deselectVertex(IVertex vert);
    void deselectTempPath();
}
