package business.estructuraArbreR.tree;

/**
 * Represents a coordinate in the graphic of the R Tree.
 */
public class Coordinate {
    private double latitude;
    private double longitude;

    /**
     * Constructs the coordinate with a specific longitude and latitude.
     *
     * @param longitude The longitude of the coordinate.
     * @param latitude  The latitude of the coordinate.
     */
    public Coordinate(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Getter of the latitude.
     *
     * @return The latitude.
     */
    public double getY() {
        return latitude;
    }

    /**
     * Getter of the longitude.
     *
     * @return The longitude.
     */
    public double getX() {
        return longitude;
    }

    /**
     * Setter of the latitude.
     *
     * @param latitude The latitude to be assigned.
     */
    public void setY(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Setter of the longitude.
     *
     * @param longitude The longitude to be assigned.
     */
    public void setX(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Getter of the longitude and latitude as coordinates.
     *
     * @return The longitude and latitude as coordinates.
     */
    public String getCoordinates() {
        return latitude + "," + longitude;
    }
}
