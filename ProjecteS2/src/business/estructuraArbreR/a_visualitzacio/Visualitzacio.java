package business.estructuraArbreR.a_visualitzacio;

import business.estructuraArbreR.entities.Bardissa;
import business.estructuraArbreR.tree.Location;
import business.estructuraArbreR.tree.RTree;

public class Visualitzacio {
    private int maxDepth = 0;   // The amount of tabs to be printed to represent the Tree

    /**
     * Prints the visualization of the R-tree.
     *
     * @param rTree The Tree to be visualized.
     */
    public void visualitzacio(RTree rTree) {
        print(rTree.getRoot(), 0);
        for (int i = 0; i < (maxDepth + 2) * 4 - 3; i++) {
            System.out.print("-");
        }
    }

    /**
     * Recursively prints the locations, sub-locations and hedges (Bardissa objects) in a tree-like format.
     *
     * @param location The Location object to be printed.
     * @param depth    The current depth in the recursive tree traversal.
     */
    private void print(Location location, int depth) {
        StringBuilder sb = new StringBuilder(); // StringBuilder that will build the tabs and the indicators of the tree.

        sb.append("|\t".repeat(Math.max(0, depth)));

        // Print the area (location)
        System.out.println(sb + "Location: " + "\u001B[31m" + location.getMin().getCoordinates() + " - "
                + location.getMax().getCoordinates() + "\u001B[0m");
        System.out.println(sb + "|");

        // If the location is a Leaf print the hedges (Bardissa objects).
        if (location.isLeaf()) {
            System.out.println(sb + "|" + "\tBardisses: " + "\u001B[36m"
                    + "#. <Latitude> - <Longitude> - <Type> - <Color>" + "\u001B[0m");
            for (int i = 0; i < location.getBardisses().size(); i++) {
                System.out.println(sb + "|" + "\t\t" + "\u001B[32m" + (i + 1) + ". "
                        + location.getBardisses().get(i).getLatitude() + " - "
                        + location.getBardisses().get(i).getLongitude() + " - "
                        + location.getBardisses().get(i).getType() + " - "
                        + location.getBardisses().get(i).getColor().toUpperCase() + "\u001B[0m");
            }
            System.out.println(sb + "|");
        }

        // Indicates it is a sub-location of the location.
        for (Location subLocation : location.getSubLocations()) {
            System.out.println(sb + "|" + "\tSublocation ");
            if (depth > maxDepth) {
                maxDepth = depth;
            }
            print(subLocation, depth + 1);
        }
    }
}
