package business.estructuraTaules.entities;

/**
 * Class that represents a guilty.
 */
public class Guilty {
    private String name;
    private String profesion;
    private int rabbits;
    boolean isHeretge;

    /**
     * Constructor of the class Guilty
     *
     * @param name name of the guilty
     * @param profesion profesion of the guilty
     * @param rabbits rabbits of the guilty
     */
    public Guilty(String name, String profesion, int rabbits) {
        this.name = name;
        this.profesion = profesion;
        this.rabbits = rabbits;

        //Consideracions de si es heretge o no
        if(profesion.equals("QUEEN") || profesion.equals("KING") || profesion.equals("CLERGYMAN")) isHeretge = false;
        else isHeretge = rabbits > 1975;
    }

    /**
     * Getter of the name of the guilty
     *
     * @return name of the guilty
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of the profesion of the guilty
     *
     * @return profesion of the guilty
     */
    public String getProfesion() {
        return profesion;
    }

    /**
     * Getter of the rabbits of the guilty
     *
     * @return rabbits of the guilty
     */
    public int getRabbits() {
        return rabbits;
    }

    /**
     * Getter of if the guilty is heretge or not
     *
     * @return true or false
     */
    public boolean isHeretge() {
        return isHeretge;
    }

    /**
     * Setter of if a guilty is heretge or not
     *
     * @param heretge true or false
     */
    public void setHeretge(boolean heretge) {
        isHeretge = heretge;
    }
}
