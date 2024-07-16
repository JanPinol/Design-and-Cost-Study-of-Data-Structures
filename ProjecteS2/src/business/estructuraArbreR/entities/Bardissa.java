package business.estructuraArbreR.entities;

/**
 * Represents the hedge on the R Tree.
 * The hedges are the points in the graphic.
 */
public class Bardissa {
    private String type;
    private double size;
    private double latitude;
    private double longitude;
    private String color;

    /**
     * Constructs the hedge (bardissa) object.
     *
     * @param type      Type of figure.
     * @param size      Size of the figure.
     * @param latitude  Latitude of the hedge.
     * @param longitude Longitude of the hedge.
     * @param color     Color of the hedge.
     */
    public Bardissa(String type, double size, double latitude, double longitude, String color) {
        this.type = type;
        this.size = size;
        this.latitude = latitude;
        this.longitude = longitude;
        this.color = color;
    }

    /**
     * Constructs a new empty hedge (bardissa).
     */
    public Bardissa() {

    }

    /**
     * Getter of the type.
     *
     * @return The type of the hedge.
     */
    public String getType() {
        return type;
    }

    /**
     * Getter of the size.
     *
     * @return The size of the hedge.
     */
    public double getSize() {
        return size;
    }

    /**
     * Getter of the latitude.
     *
     * @return The latitude of the hedge.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Getter of the longitude.
     *
     * @return The longitude of the hedge.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Getter of the color.
     *
     * @return The color of the hedge.
     */
    public String getColor() {
        return color;
    }

    /**
     * Setter of the type of figure for the hedge.
     *
     * @param type  The type of the figure for the hedge.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Setter of the color of the hedge.
     *
     * @param color The color of the hedge.
     */
    public void setColor(String color) {
        this.color = color;
    }
}
