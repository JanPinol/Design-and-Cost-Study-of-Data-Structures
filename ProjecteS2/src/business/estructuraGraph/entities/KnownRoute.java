package business.estructuraGraph.entities;

/**
 * Represents the Edges that connects two Node of the Graph.
 */
public class KnownRoute {
    private int pointA;
    private int pointB;
    private float timeE;
    private float timeA;
    private float distance;

    /**
     * Constructor of a known route
     *
     * @param pointA id of the first interest point
     * @param pointB id of the second interest point
     * @param timeE float that contains the time of an european bird
     * @param timeA float that contains the time of an african bird
     * @param distance distance between A and B
     */
    public KnownRoute(int pointA, int pointB, float timeE, float timeA, float distance) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.timeE = timeE;
        this.timeA = timeA;
        this.distance = distance;
    }

    /**
     * Constructs a copy of a KnownRoute object.
     *
     * @param that The KnownRoute object to be copied.
     */
    public KnownRoute(KnownRoute that) {
        this.pointA = that.pointA;
        this.pointB = that.pointB;
        this.timeE = that.timeE;
        this.timeA = that.timeA;
        this.distance = that.distance;
    }

    /**
     * Getter of the id of the first interest point
     *
     * @return an int that contains the id of the pointA
     */
    public int getPointA() {
        return pointA;
    }

    /**
     * Getter of the id of the second interest point
     *
     * @return an int that contains the id of the pointB
     */
    public int getPointB() {
        return pointB;
    }

    /**
     * Getter of the european bird time
     *
     * @return a float that contains the time of the european bird
     */
    public float getTimeE() {
        return timeE;
    }

    /**
     * Getter of the african bird time
     *
     * @return a float that contains the time of the african bird
     */
    public float getTimeA() {
        return timeA;
    }

    /**
     * Getter of the distance between the point A and B
     *
     * @return a float that contains the distance between A and B
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Getter of the destination.
     *
     * @return The destination.
     */
    public int getDestination() {
        return pointB;
    }
}
