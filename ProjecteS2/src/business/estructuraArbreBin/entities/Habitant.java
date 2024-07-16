package business.estructuraArbreBin.entities;

/**
 * Class that represents a habitant of the tree
 */
public class Habitant {

    // Attributes
    private int id;
    private String name;
    private float weight;
    private String realm;

    /**
     * Constructor of the class Habitant
     *
     * @param id id of the habitant
     * @param name name of the habitant
     * @param weight weight of the habitant
     * @param realm realm of the habitant
     */
    public Habitant(int id, String name, float weight, String realm) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.realm = realm;
    }

    /**
     * Getter of the id of the habitant
     *
     * @return id of the habitant
     */
    public int getId() {
        return id;
    }

    /**
     * Setter of the id of the habitant
     *
     * @param id id of the habitant
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter of the name of the habitant
     *
     * @return name of the habitant
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name of the habitant
     *
     * @param name name of the habitant
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of the weight of the habitant
     *
     * @return weight of the habitant
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Setter of the weight of the habitant
     *
     * @param weight weight of the habitant
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Getter of the realm of the habitant
     *
     * @return realm of the habitant
     */
    public String getRealm() {
        return realm;
    }

    /**
     * Setter of the realm of the habitant
     *
     * @param realm realm of the habitant
     */
    public void setRealm(String realm) {
        this.realm = realm;
    }
}
