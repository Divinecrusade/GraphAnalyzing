package Model;

import Utility.IVertex;
import Utility.NonOrientedPath;
import Utility.StaticVertex;

import java.awt.*;
import java.util.ArrayList;

public class Graph implements IModel {
    public Graph() {
        vertexes = new ArrayList<>();
        paths = new ArrayList<>();
    }
    @Override
    public ArrayList<IVertex> getVertexes() {
        return new ArrayList<>(vertexes);
    }

    @Override
    public IVertex createVertex(String vert_name, Point vert_pos) {
        StaticVertex vert = new StaticVertex(vert_name, vert_pos);
        vertexes.add(vert);
        return vert;
    }

    public ArrayList<ArrayList<double>> getAdjacencyMatrix() {
        ArrayList<ArrayList<double>> adjacencyMatrix = new ArrayList<>(vertexes.size());
        for (ArrayList<double> row : adjacencyMatrix) {
            row = new ArrayList<double>(vertexes.size());
        }

        for (NonOrientedPath path : paths) {
            adjacencyMatrix.get(vertexes.indexOf(path.getBegin())).set(vertexes.indexOf(path.getEnd()), path.getDistance());
        }

        return adjacencyMatrix;
    }

    private final ArrayList<StaticVertex>    vertexes;
    private final ArrayList<NonOrientedPath> paths;
}
