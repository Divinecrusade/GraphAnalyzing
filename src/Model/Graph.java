package Model;

import Utility.Dijkstra.Node;
import Utility.IPath;
import Utility.IVertex;
import Utility.MovableVertex;
import Utility.NonOrientedPath;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

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
    public ArrayList<IPath> getPaths() {
        return new ArrayList<>(paths);
    }

    @Override
    public IVertex getVertex(String name) {
        for (MovableVertex vert : vertexes) {
            if (vert.getName().equals(name)) {
                return vert;
            }
        }

        return null;
    }

    @Override
    public ArrayList<IPath> getOptimalPath(IVertex begin, IVertex end) {
        ArrayList<Node> nodes = new ArrayList<>();
        Node nodeA = null;
        for (IVertex vert : vertexes) {
            nodes.add(new Node(vert.getName()));
            if (vert == begin) {
                nodeA = nodes.get(nodes.size() - 1);
            }
        }

        for (IPath path : paths) {
            for (Node node : nodes) {
                if (path.getBegin().getName().equals(node.getName())) {
                    for (Node another_node : nodes) {
                        if (another_node != node && another_node.getName().equals(path.getEnd().getName())) {
                            node.addDestination(another_node, path.getDistance());
                            another_node.addDestination(node, path.getDistance());
                        }
                    }
                }
            }
        }

        Utility.Dijkstra.Graph d_graph = new Utility.Dijkstra.Graph();
        for (Node node : nodes) {
            d_graph.addNode(node);
        }

        d_graph = Utility.Dijkstra.Graph.calculateShortestPathFromSource(d_graph, nodeA);
        ArrayList<IPath> optimal_path = new ArrayList<>();
        Stack<IPath> tmp_stack = new Stack<>();
        for (Node node : d_graph.getNodes()) {
            if (node.getName().equals(end.getName())) {
                for (Node sub_node : node.getShortestPath()) {
                    for (IPath path : paths) {
                        if (path.getBegin().getName().equals(sub_node.getName()) && path.getEnd().getName().equals(node.getName())) {
                            tmp_stack.push(path);
                        }
                        else if (path.getBegin().getName().equals(node.getName()) && path.getEnd().getName().equals(sub_node.getName())) {
                            tmp_stack.push(new NonOrientedPath(path.getEnd(), path.getBegin(), path.getDistance()));
                        }
                    }
                }
            }
        }

        int n = tmp_stack.size();
        for (int i = 0; i != n; ++i) {
            optimal_path.add(tmp_stack.pop());
        }

        return optimal_path;
    }

    @Override
    public IVertex createVertex(String vert_name, Point vert_pos) {
        for (MovableVertex vert : vertexes) {
            if (vert.getName().equals(vert_name)) {
                vert.moveTo(vert_pos);

                return null;
            }
        }

        MovableVertex vert = new MovableVertex(vert_name, vert_pos);
        vertexes.add(vert);
        return vert;
    }

    @Override
    public IPath createPath(IVertex beg, IVertex end, double distance) {
        for (NonOrientedPath path : paths) {
            if ((path.getBegin() == beg && path.getEnd() == end) ||
                (path.getEnd() == beg && path.getBegin() == end)) {
                path.updateDistance(distance);

                return null;
            }
        }

        NonOrientedPath path = new NonOrientedPath(beg, end, distance);
        paths.add(path);

        return path;
    }

    @Override
    public IPath createPath(IVertex beg, IVertex end) {
        NonOrientedPath path = new NonOrientedPath(beg, end);
        paths.add(path);

        NonOrientedPath mirror_path = new NonOrientedPath(end, beg, path.getDistance());

        return path;
    }

    @Override
    public void deleteVertexes() {
        paths.clear();
        vertexes.clear();
    }

    @Override
    public void deleteVertex(IVertex vert) {
         paths.removeIf(path -> path.getBegin() == vert || path.getEnd() == vert);
         vertexes.removeIf(old_vert -> old_vert.getName().equals(vert.getName()));
    }

    @Override
    public void deletePath(IPath path) {
        paths.removeIf(old_path -> old_path.getBegin() == path.getBegin() && old_path.getEnd() == path.getEnd());
    }

    private final ArrayList<MovableVertex>   vertexes;
    private final ArrayList<NonOrientedPath> paths;
}
