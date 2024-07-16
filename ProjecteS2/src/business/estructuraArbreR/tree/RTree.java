package business.estructuraArbreR.tree;

import business.estructuraArbreR.entities.Bardissa;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the R Tree data structure.
 */
public class RTree {
    private static Location root;

    /**
     * Constructs a new R Tree from a new root.
     */
    public RTree() {
        root = new Location();
    }

    /**
     * Setter of the root.
     *
     * @param newRoot The root to be assigned.
     */
    public static void setRoot(Location newRoot) {
        root = newRoot;
    }

    /**
     * Creates the R Tree.
     *
     * @param bardisses The list of hedges (Bardissa objects) to be added to the R Tree.
     */
    public void createTree(ArrayList<Bardissa> bardisses) {
        for (Bardissa bardissa : bardisses) {
            insertBardissa(bardissa);
        }

        //Actualitzem la mida de l'arbre
        root.updateTreeSize(root);
    }

    /**
     * Updates the size of the R Tree.
     */
    public void updateTreeSize() {
        root.updateTreeSize(root);
    }

    /**
     * Inserts a new hedge (Bardissa object) to the R Tree.
     *
     * @param bardissa The hedge to be added.
     */
    public void insertBardissa(Bardissa bardissa) {
        root.insert(bardissa);
    }

    /**
     * Recollects recursively all the hedges (Bardissa objects) of the R Tree.
     *
     * @param listBardisses The list of hedges where will be the hedges recollected.
     * @param location      The current location to recollect the hedges.
     */
    private void recollectAllBardisses(ArrayList<Bardissa> listBardisses, Location location) {
        if (location.isLeaf()) {
            listBardisses.addAll(location.getBardisses());
        } else {
            for (Location sublocation : location.getSubLocations()) {
                recollectAllBardisses(listBardisses, sublocation);
            }
        }
    }

    /**
     * Deletes a hedge (Bardissa object).
     *
     * @param coordinate The coordinate where the hedge is.
     *
     * @return The confirmation message.
     */
    public String deleteBardissa(Coordinate coordinate) {
        ArrayList<Bardissa> listBardisses = new ArrayList<>();

       if(root.delete(coordinate)) {
           recollectAllBardisses(listBardisses, root);
           root = new Location();
           createTree(listBardisses);
           root.updateTreeSize(root); //Actualitzem la mida de l'arbre
           return "\nLa bardissa s’ha eliminat, per ser integrada a una tanca.";
       } else {
           return "\nNo s'ha trobat cap bardissa amb aquestes coordenades";
       }
    }

    /**
     * Getter of the root of the R Tree.
     *
     * @return The root of the R Tree.
     */
    public Location getRoot() {
        return root;
    }

    /*
     * METODES PER COMPROVAR EL CORRECTE FUNCIONAMENT DEL ARBRE
     */

    /**
     * Method that checks if the R Tree was correctly implemented.
     */
    public void comprovar() {
        if(root.checkOnlyLeafNodesHaveBardisses(root)) {
            System.out.println("OKAY");
        } else {
            System.out.println("ERROR");
        }
    }

    /**
     * Method that checks if the R Tree was correctly implemented.
     */
    public void comprovar2() {
        if(root.checkLeafNodesHaveNoSublocations(root)) {
            System.out.println("OKAY");
        } else {
            System.out.println("ERROR");
        }
    }

    /**
     * Method that checks if the R Tree was correctly implemented.
     */
    public void comprovar3() {
        List<Integer> leafHeights = getLeafHeights(RTree.root, 0);

        int minHeight = Collections.min(leafHeights);
        int maxHeight = Collections.max(leafHeights);

        int heightDifference = maxHeight - minHeight;

        if(heightDifference == 0) {
            System.out.println("OKAY");
        } else {
            System.out.println("ERROR");
        }
    }

    /**
     * Gets the height of the leafs.
     *
     * @param currentLocation   The current location.
     * @param currentHeight     The current height.
     *
     * @return The height of the leafs.
     */
    public List<Integer> getLeafHeights(Location currentLocation, int currentHeight) {
        List<Integer> heights = new ArrayList<>();

        if (currentLocation.isLeaf()) {
            heights.add(currentHeight);
        } else {
            for (Location sublocation : currentLocation.getSubLocations()) {
                heights.addAll(getLeafHeights(sublocation, currentHeight + 1));
            }
        }

        return heights;
    }
}
