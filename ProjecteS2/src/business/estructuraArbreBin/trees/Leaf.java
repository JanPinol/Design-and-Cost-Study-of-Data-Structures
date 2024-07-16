package business.estructuraArbreBin.trees;

/**
 * Class that represents a leaf of the tree
 */
public class Leaf {
    private int id;
    private String name;
    private float weight;
    private String realm;
    private int height;
    private Leaf left;
    private Leaf right;

    /**
     * Constructor of the class Leaf
     *
     * @param id id of the leaf
     * @param name name of the leaf
     * @param weight weight of the leaf
     * @param realm realm of the leaf
     */
    public Leaf(int id, String name, float weight, String realm) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.realm = realm;
    }

    /**
     * Getter of the id of the leaf
     *
     * @return id of the leaf
     */
    public int getId() {
        return id;
    }

    /**
     * Getter of the name of the leaf
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of the weight of the leaf
     *
     * @return weight of the leaf
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Getter of the realm of the leaf
     *
     * @return realm of the leaf
     */
    public String getRealm() {
        return realm;
    }

    /**
     * Getters of the left leaf of the leaf
     *
      * @return left leaf of the leaf
     */
    public Leaf getLeft() {
        return left;
    }

    /**
     * Getters of the right leaf of the leaf
     *
      * @return right leaf of the leaf
     */
    public Leaf getRight() {
        return right;
    }

    /**
     * Getters of the height of the leaf
     *
     * @return height of the leaf
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setters of the height of the leaf
     *
     * @param height height of the leaf
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Setters of the left leaf of the leaf
     *
     * @param left left leaf of the leaf
     */
    public void setLeft(Leaf left) {
        this.left = left;
    }

    /**
     * Setters of the right leaf of the leaf
     *
     * @param right right leaf of the leaf
     */
    public void setRight(Leaf right) {
        this.right = right;
    }

    /**
     * Setters of the id of the leaf
     *
     * @param id of the leaf
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setters of the name of the leaf
     *
     * @param name name of the leaf
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setters of the weight of the leaf
     *
     * @param weight weight of the leaf
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Setters of the realm of the leaf
     *
     * @param realm realm of the leaf
     */
    public void setRealm(String realm) {
        this.realm = realm;
    }

    /*
    FUNCIÓ DE PRINTAR AMB AJUDA DE: https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram-in-java
     */

    /**
     * Prints the tree
     *
     * @param sb string builder where the tree will be printed
     */
    public void printTree(StringBuilder sb) {
        if (left != null) {
            left.printTree(sb, false, "");
            sb.append(" |\n");
        }
        printNodeValue(sb, " * ");
        if (right != null) {
            sb.append(" |\n");
            right.printTree(sb, true, "");
        }
    }

    /**
     * Prints the node value
     *
     * @param sb string builder where the node will be printed
     * @param prefix string to indent the node
     */
    private void printNodeValue(StringBuilder sb, String prefix) {
        sb.append(prefix);
        if (name == null) {
            sb.append("<null>");
        } else {
            sb.append(name).append(" (").append(id).append(", ").append(realm).append("): ").append(weight).append("kg");
        }
        sb.append("\n");
    }

    /**
     * Prints the tree in a more readable way
     * @param sb string builder where the tree will be printed
     * @param isRight boolean to know if the node is right
     * @param indent string to indent the tree
     */
    private void printTree(StringBuilder sb, boolean isRight, String indent) {
        if (left != null) {
            left.printTree(sb, false, indent + (isRight ? " |   " : "     "));
            sb.append(indent).append(isRight ? " |   " : "     ").append(" |\n");
        }
        sb.append(indent);
        sb.append(" |--- ");
        printNodeValue(sb, "");
        if (right != null) {
            sb.append(indent).append(isRight ? "     " : " |   ").append(" |\n");
            right.printTree(sb, true, indent + (isRight ? "     " : " |   "));
        }
    }
}

