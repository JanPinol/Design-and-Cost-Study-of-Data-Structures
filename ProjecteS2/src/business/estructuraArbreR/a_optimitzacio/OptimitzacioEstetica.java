package business.estructuraArbreR.a_optimitzacio;

import business.estructuraArbreR.entities.Bardissa;
import business.estructuraArbreR.tree.Coordinate;
import business.estructuraArbreR.tree.Location;
import business.estructuraArbreR.tree.RTree;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class that performs the Optimització Estètica function.
 * Return the average figure type and color from a specific point and how much of hedges (bardissa objects)
 * to consider that area nearer from it.
 */
public class OptimitzacioEstetica {
    private final Coordinate coordinate;
    private final int bardissesQuantity;
    private final RTree rTree;

    /**
     * Constructs the OptimitzacioEstetica object.
     *
     * @param coordinate        The coordinate from where to start looking for hedges.
     * @param bardissesQuantity The amount of hedges (Bardissa object) to consider.
     * @param rTree             The R Tree.
     */
    public OptimitzacioEstetica(Coordinate coordinate, int bardissesQuantity, RTree rTree) {
        this.coordinate = coordinate;
        this.bardissesQuantity = bardissesQuantity;
        this.rTree = rTree;
    }

    /**
     * Gets the average color and type of the hedges (Bardissa objects) close to the given coordinate.
     *
     * @return The average color of the hedges (Bardissa objects) close to the given coordinate.
     */
    public Bardissa optimitzacioEstetica() {
        return getCommonBardissa(getArrayBardisses(coordinate, bardissesQuantity));
    }

    /**
     * Gets an ArrayList of hedges (Bardissa objects) close to the given coordinate.
     *
     * @param coordinate The coordinate to process.
     * @param bardissesQuantity The number of hedges (Bardissa objects) to get.
     * @return The average color of the hedges (Bardissa objects) in the given list.
     */
    private ArrayList<Bardissa> getArrayBardisses(Coordinate coordinate, int bardissesQuantity) {
        ArrayList<Bardissa> bardissesList = new ArrayList<>();

        addBardissesToArrayList(bardissesList, rTree.getRoot());

        getMostCloseBardisses(bardissesList, coordinate, bardissesQuantity);

        return bardissesList;
    }

    /**
     * Gets the most close hedges (Bardissa objects) to the given coordinate.
     * @param bardissesList The list of hedges (Bardissa objects) to process.
     * @param coordinate The coordinate to process.
     * @param bardissesQuantity The number of hedges (Bardissa objects) to get.
     */
    private void getMostCloseBardisses(ArrayList<Bardissa> bardissesList, Coordinate coordinate, int bardissesQuantity)
    {
        ArrayList<Bardissa> mostCloseBardisses = new ArrayList<>();

        for (int i = 0; i < bardissesQuantity; i++) {
            Bardissa mostCloseBardissa = getMostCloseBardissa(bardissesList, coordinate);
            mostCloseBardisses.add(mostCloseBardissa);
            bardissesList.remove(mostCloseBardissa);
        }

        bardissesList.clear();
        bardissesList.addAll(mostCloseBardisses);

    }

    /**
     * Gets the most close hedge (Bardissa object) to the given coordinate.
     *
     * @param bardissesList The list of hedges (Bardissa objects) to process.
     * @param coordinate The coordinate to process.
     * @return The most close hedge (Bardissa object) to the given coordinate.
     */
    private Bardissa getMostCloseBardissa(ArrayList<Bardissa> bardissesList, Coordinate coordinate) {
        Bardissa mostCloseBardissa = bardissesList.get(0);
        double mostCloseDistance = getDistance(mostCloseBardissa, coordinate);

        for (Bardissa bardissa: bardissesList) {
            double distance = getDistance(bardissa, coordinate);

            if (distance < mostCloseDistance) {
                mostCloseBardissa = bardissa;
                mostCloseDistance = distance;
            }
        }

        return mostCloseBardissa;

    }

    /**
     * Gets the distance between the given hedge (Bardissa object) and the given coordinate.
     *
     * @param bardissa The hedge (Bardissa object) to process.
     * @param coordinate The coordinate to process.
     * @return The distance between the given hedge (Bardissa object) and the given coordinate.
     */
    private double getDistance(Bardissa bardissa, Coordinate coordinate) {
        Coordinate bardissaCoordinate = new Coordinate(bardissa.getLongitude(), bardissa.getLatitude());

        double x1 = bardissaCoordinate.getX();
        double y1 = bardissaCoordinate.getY();
        double x2 = coordinate.getX();
        double y2 = coordinate.getY();

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }


    /**
     * Adds the hedges (Bardissa objects) of the given location to the given list.
     *
     * @param bardissesList The list to which the hedges (Bardissa objects) are added.
     * @param location The location from which the hedges (Bardissa objects) are added.
     */
    private void addBardissesToArrayList(ArrayList<Bardissa> bardissesList, Location location) {
        if (location.isLeaf()) {
            bardissesList.addAll(location.getBardisses());
        } else {
            for (Location subLocations : location.getSubLocations()) {
                addBardissesToArrayList(bardissesList, subLocations);
            }
        }
    }


    /**
     * Gets the common hedge (Bardissa object) of the hedges (Bardissa objects) in the given list.
     *
     * @param bardisses The list to which the hedges (Bardissa objects) are added.
     * @return The common hedge (Bardissa object) of the hedges (Bardissa objects) in the given list.
     */
    private Bardissa getCommonBardissa(ArrayList<Bardissa> bardisses) {
        int numCircles = 0;
        int numSquares = 0;


        for (Bardissa bardissa: bardisses) {
            if (bardissa.getType().equals("CIRCLE")) {
                numCircles++;
            } else {
                numSquares++;
            }
        }

        Bardissa bardissa = new Bardissa();

        if (numCircles > numSquares) {
            bardissa.setType("CIRCLE");
        } else {
            bardissa.setType("SQUARE");
        }

        String color = getColorMitja(bardisses);
        bardissa.setColor(color);

        return bardissa;
    }

    /**
     * Returns the average color of the hedges (Bardissa objects) in the given list.
     *
     * @param bardisses The list of hedges (Bardissa objects) to process.
     * @return The average color of the hedges (Bardissa objects) in the given list.
     */
    private String getColorMitja(ArrayList<Bardissa> bardisses) {
        int red = 0, green = 0, blue = 0;

        for (Bardissa bardissa: bardisses) {
            Color color = hex2Rgb(bardissa.getColor());
            red += color.getRed();
            green += color.getGreen();
            blue += color.getBlue();
        }

        red /= bardisses.size();
        green /= bardisses.size();
        blue /= bardisses.size();

        return String.format("#%02x%02x%02x", red, green, blue);
    }


    /**
     * Converts a hexadecimal color string to a Color object.
     *
     * @param colorStr The hexadecimal color string to convert.
     * @return The Color object corresponding to the hexadecimal color string.
     */
    private static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

}
