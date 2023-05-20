package Model;

import Utility.IVertex;

import java.util.List;

public interface IModel {
    List<IVertex> getVertexes();
    void createVertex(IVertex vert);
}
