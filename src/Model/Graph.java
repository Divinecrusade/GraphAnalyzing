package Model;

import Utility.IPath;
import Utility.IVertex;
import Utility.MovableVertex;
import Utility.NonOrientedPath;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Graph implements IModel {
    private static class Node {
        Node(IVertex vert, double init_weight) {
            this.vert = vert;
            weight = init_weight;
        }

        public final IVertex vert;
        public double weight;
    }

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
        ArrayList<ArrayList<IVertex>> optimal_paths = calculateOptimalPaths(begin, getAdjacencyMatrix());
        ArrayList<IVertex> optimal_path = null;

        for (var arrList : optimal_paths) {
            if (arrList.size() > 0 && arrList.get(0) == begin && arrList.get(arrList.size() - 1) == end) {
                optimal_path = new ArrayList<>(arrList);
            }
        }
        ArrayList<IPath> req_path = new ArrayList<>();
        for (int i = 0; i != optimal_path.size() - 1; ++i) {
            for (var path : paths) {
                if (path.getBegin() == optimal_path.get(i) && path.getEnd() == optimal_path.get(i + 1)) {
                    req_path.add(path);
                    break;
                }
            }
        }

        return req_path;
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

    private ArrayList<ArrayList<IPath>> getAdjacencyMatrix() {
        ArrayList<ArrayList<IPath>> adjMatrix = new ArrayList<>();

        for (int i = 0; i != vertexes.size(); ++i) {
            adjMatrix.add(new ArrayList<>());
        }

        for (NonOrientedPath edge : paths) {
            adjMatrix.get(vertexes.indexOf(edge.getBegin())).add(edge);
        }


        return adjMatrix;
    }

    private static void getRoute(int[] prev, int i, List<Integer> route)
    {
        if (i >= 0)
        {
            getRoute(prev, prev[i], route);
            route.add(i);
        }
    }

    private ArrayList<ArrayList<IVertex>> calculateOptimalPaths(IVertex vertBeg, ArrayList<ArrayList<IPath>> adjMatrix) {
        int source_index = vertexes.indexOf(vertBeg);
        int n = vertexes.size();

        // создаем мини-кучу и проталкиваем исходный узел с расстоянием 0
        PriorityQueue<Node> minHeap;
        minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> (int)node.weight));
        minHeap.add(new Node(vertBeg, 0d));

        // устанавливаем начальное расстояние от источника на `v` как бесконечность
        List<Double> dist;
        dist = new ArrayList<>(Collections.nCopies(n, Double.MAX_VALUE));

        // расстояние от источника до себя равно нулю
        dist.set(source_index, 0d);

        // логический массив для отслеживания вершин, для которых минимум
        // стоимость уже найдена
        boolean[] done = new boolean[n];
        done[source_index] = true;

        // сохраняет предыдущую вершину (в путь печати)
        int[] prev = new int[n];
        prev[source_index] = -1;

        // работать до тех пор, пока мини-куча не станет пустой
        while (!minHeap.isEmpty())
        {
            // Удалить и вернуть лучшую вершину
            Node node = minHeap.poll();

            // получаем номер вершины
            int u = vertexes.indexOf(node.vert);

            // делаем для каждого соседа `v` из `u`
            for (IPath edge: adjMatrix.get(u))
            {
                int v = vertexes.indexOf(edge.getEnd());
                double weight = edge.getDistance();

                // Шаг релаксации
                if (!done[v] && (dist.get(u) + weight) < dist.get(v))
                {
                    dist.set(v, dist.get(u) + weight);
                    prev[v] = u;
                    minHeap.add(new Node(vertexes.get(v), dist.get(v)));
                }
            }

            // помечаем вершину `u` как выполненную, чтобы она больше не поднималась
            done[u] = true;
        }

        ArrayList<Integer> route = new ArrayList<>();
        ArrayList<ArrayList<IVertex>> optimalPaths = new ArrayList<>();

        for (int i = 0; i < n; i++)
        {
            optimalPaths.add(new ArrayList<>());
            if (i != source_index && dist.get(i) != Double.MAX_VALUE)
            {
                getRoute(prev, i, route);
                for (var r : route) {
                    optimalPaths.get(i).add(vertexes.get(r));
                }
                route.clear();
            }
        }

        return optimalPaths;
    }

    private final ArrayList<MovableVertex>   vertexes;
    private final ArrayList<NonOrientedPath> paths;
}
