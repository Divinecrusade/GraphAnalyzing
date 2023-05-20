package Utility;

public class NonOrientedPath implements IPath {
    public NonOrientedPath(IVertex vert1, IVertex vert2, double distance) {
        this.vert1 = vert1;
        this.vert2 = vert2;
        this.distance = distance;
    }

    public NonOrientedPath(IVertex vert1, IVertex vert2) {
        this(vert1, vert2, Math.sqrt((vert2.getPos().x - vert1.getPos().x) * (vert2.getPos().x - vert1.getPos().x) +
                                     (vert2.getPos().y - vert1.getPos().y) * (vert2.getPos().y - vert1.getPos().y)));
    }

    @Override
    public IVertex getBegin() {
        return vert1;
    }

    @Override
    public IVertex getEnd() {
        return vert2;
    }

    @Override
    public double getDistance() {
        return distance;
    }

    public void updateDistance(double new_distance) {
        distance = new_distance;
    }

    public void updateDistance() {
        updateDistance(Math.sqrt((vert2.getPos().x - vert1.getPos().x) * (vert2.getPos().x - vert1.getPos().x) +
                                 (vert2.getPos().y - vert1.getPos().y) * (vert2.getPos().y - vert1.getPos().y)));
    }

    final private IVertex vert1;
    final private IVertex vert2;

    private double distance;
}
