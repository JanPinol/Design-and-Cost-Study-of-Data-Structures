package business.estructuraArbreR.a_cerca;

import business.estructuraArbreR.entities.Bardissa;
import business.estructuraArbreR.tree.Coordinate;
import business.estructuraArbreR.tree.Location;
import business.estructuraArbreR.tree.RTree;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * The CercaArea class performs a search for hedges (Bardissa objects) of the RTree within a given area.
 */
public class CercaArea {
    private final Location area;

    /**
     * Constructor for the CercaArea class that takes the coordinates of two points in String format,
     * creates a Location object that represents the area, and assigns it to the instance variable "area".
     *
     * @param firstPoint  The first point in the format "latitude,longitude"
     * @param secondPoint The second point in the format "latitude,longitude"
     */
    public CercaArea(String firstPoint, String secondPoint) {
        String[] firstPointSplit = firstPoint.split(",");
        String[] secondPointSplit = secondPoint.split(",");

        double[] coordinates = new double[4];
        coordinates[1] = Double.parseDouble(firstPointSplit[0]);    // Latitude
        coordinates[0] = Double.parseDouble(firstPointSplit[1]);    // Longitude


        coordinates[3] = Double.parseDouble(secondPointSplit[0]);   // Latitude
        coordinates[2] = Double.parseDouble(secondPointSplit[1]);   // Longitude

        Location location = new Location();

        double minX = Math.min(coordinates[0], coordinates[2]);
        double minY = Math.min(coordinates[1], coordinates[3]);
        double maxX = Math.max(coordinates[0], coordinates[2]);
        double maxY = Math.max(coordinates[1], coordinates[3]);

        location.setMin(new Coordinate(minX, minY));
        location.setMax(new Coordinate(maxX, maxY));

        this.area = location;
    }

    /**
     * Performs a search for the hedges (Bardissa objects) of the RTree that are located within the specified area.
     *
     * @param tree The RTree on which the search is performed.
     *
     * @return A list of hedges objects (Bardissa objects) that are located within the specified area.
     */
    public List<Bardissa> cerca(RTree tree) {
        List<Bardissa> bardissesList = new ArrayList<>();

        addBardisses(bardissesList, tree.getRoot());

        return bardissesList;
    }

    /**
     * Adds hedges (Bardissa objects) to the given list by traversing the location hierarchy recursively.
     * Hedges (Bardissa objects) are added if they are located within the specified area.
     *
     * @param bardissesList The list to which the hedges (Bardissa objects) are added.
     * @param location      The current location in the hierarchy being processed.
     */
    private void addBardisses(List<Bardissa> bardissesList, Location location) {
        if (areaContains(location)) {
            if (location.isLeaf()) {
                List<Bardissa> bardisses = location.getBardisses();
                for (Bardissa bardissa : bardisses) {
                    // Create a "new" Location object to save the position of the hedge (Bardissa object) to check
                    // it's contained in the area specified.
                    ArrayList<Bardissa> listBardissa = new ArrayList<>();
                    listBardissa.add(bardissa);
                    Coordinate bardissaCoordinate = new Coordinate(bardissa.getLongitude(), bardissa.getLatitude());
                    Location bardissaLocation = new Location(listBardissa, bardissaCoordinate, bardissaCoordinate);

                    // Check if the hedge (Bardissa object) is contained in the specified area.
                    if (areaContains(bardissaLocation)) {
                        bardissesList.add(bardissa);
                    }
                }
            } else {
                for (Location subLocations : location.getSubLocations()) {
                    addBardisses(bardissesList, subLocations);
                }
            }
        }
    }

    /**
     * Checks if a location is contained within the specified area.
     *
     * @param location The location to check.
     *
     * @return True if the location is contained within the area, false otherwise.
     */
    private boolean areaContains(Location location) {
        // Set the maximum points of the area.
        Coordinate coordinateMax = location.getMax();
        Coordinate coordinateMin = location.getMin();

        // Set the minimum points of the area.
        Coordinate areaMax = area.getMax();
        Coordinate areaMin = area.getMin();

        return ((isInside(coordinateMax.getX(), areaMax.getX(), areaMin.getX())
                || isInside(coordinateMin.getX(), areaMax.getX(), areaMin.getX()))
                && (isInside(coordinateMax.getY(), areaMax.getY(), areaMin.getY())
                || isInside(coordinateMin.getY(), areaMax.getY(), areaMin.getY())))
                || ((isInside(areaMax.getX(), coordinateMax.getX(), coordinateMin.getX())
                || isInside(areaMin.getX(), coordinateMax.getX(), coordinateMin.getX()))
                && (isInside(areaMax.getY(), coordinateMax.getY(), coordinateMin.getY())
                || isInside(areaMin.getY(), coordinateMax.getY(), coordinateMin.getY())));
    }

    /**
     * Checks if a specific point is in the area.
     * @param point Point to check.
     * @param max   Maximum point of the area.
     * @param min   Minimum point of the area.
     *
     * @return True if the point is in the area, false otherwise.
     */
    public boolean isInside(double point, double max, double min) {
        return point >= min && point <= max;
    }
}
