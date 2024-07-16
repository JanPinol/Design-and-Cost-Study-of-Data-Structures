package business.estructuraGraph.graph;

public class Edge {

    private float distance;
    private int pointA;
    private int pointB;

    public Edge(float distance, int pointA, int pointB) {
        this.distance = distance;
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public float getDistance() {
        return distance;
    }

    public int getPointA() {
        return pointA;
    }

    public int getPointB() {
        return pointB;
    }


}
