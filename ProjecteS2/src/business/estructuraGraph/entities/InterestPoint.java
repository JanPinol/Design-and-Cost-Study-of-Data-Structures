package business.estructuraGraph.entities;

/**
 * Class that contains the information of a Node on the Graph.
 */
public class InterestPoint {
    private int id;
    private String name;
    private String reign;
    private String weather;

    /**
     * Constructor of an interest point
     *
     * @param id id of the interest point
     * @param name name of the interest point
     * @param reign reign where the point is located
     * @param weather weather that the interest point has
     */
    public InterestPoint(int id, String name, String reign, String weather) {
        this.id = id;
        this.name = name;
        this.reign = reign;
        this.weather = weather;
    }

    /**
     * Getter of the id of the interest point
     *
     * @return an int that contains the id of the interest point
     */
    public int getId() {
        return id;
    }

    /**
     * Getter of the name of the intereset point
     *
     * @return a String that contains the name of the interest point
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of the Reign where the interest point is located
     *
     * @return a String that contains the name of the reign where the interest point is located
     */
    public String getReign() {
        return reign;
    }

    /**
     * Getter of the weather the interest point has
     *
     * @return a String that contains the type of weather the interst point has (POLAR, CONTINENTAL
     *          o TROPICAL)
     */
    public String getWeather() {
        return weather;
    }
}
