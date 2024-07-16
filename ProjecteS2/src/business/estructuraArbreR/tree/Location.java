package business.estructuraArbreR.tree;

import business.estructuraArbreR.entities.Bardissa;

import java.util.ArrayList;

/**
 * Represents a location on the R Tree (bounding box).
 */
public class Location {
    private Coordinate min;
    private Coordinate max;
    private ArrayList<Bardissa> bardisses;
    private ArrayList<Location> subLocations;
    private Location parent;

    /**
     * Constructs a new Location.
     * Sets the minimum and maximum points to 0,0.
     * Creates a new ArrayList of hedges (Bardissa object) and sublocation (Locations).
     */
    public Location() {
        this.min = new Coordinate(0, 0);
        this.max = new Coordinate(0, 0);
        this.bardisses = new ArrayList<>();
        this.subLocations = new ArrayList<>();
    }

    /**
     * Constructs a leaf Location.
     *
     * @param bardissa  The hedges (Bardissa object) of the leaf.
     * @param min       The minimum point of the leaf.
     * @param max       The maximum point of the leaf.
     */
    public Location(ArrayList<Bardissa> bardissa, Coordinate min, Coordinate max) {
        this.bardisses = bardissa;
        this.min = min;
        this.max = max;
        this.subLocations = null;
    }

    /**
     * Inserts a hedge (Bardissa object) to the R Tree.
     * If the location is a leaf, the hedge is added, then the location size is updated, then checks if the location has
     * more hedges than the allowed (3), if it's true, then split the hedges. Otherwise, do nothing.
     * Otherwise, finds the best sub-location for the hedge and inserts it.
     *
     * @param bardissa The hedge to be inserted to the R Tree.
     */
    public void insert(Bardissa bardissa) {
        if(isLeaf()) { //Si la location a la qual volem inserir la bardissa es leaf la inserim
            bardisses.add(bardissa);
            updateSize(); //Actualitzem la mida de la location
            if(bardisses.size() > 3) { //Si ens passem del maxim de bardisses per location fem split
                splitGivenFourBardisses();
            }
        } else {
            Location bestSublocation = findBestSublocation(bardissa); //Mirem quina és la millor sublocation on inserir
                                                                        // la bardissa

            bestSublocation.insert(bardissa); //Internament ja comprovara si pot o no la pot inserir
        }
    }

    /**
     * Updates the size of the bounding boxes (Location objects).
     * If the hedges list is not empty, sets the new maximum and minimum points of a bounding box.
     * Otherwise, updates the sub-locations recursively and sets the maximum and minimum point of the bounding box.
     */
    private void updateSize() {
        double minX = Integer.MAX_VALUE;
        double minY = Integer.MAX_VALUE;
        double maxX = Integer.MIN_VALUE;
        double maxY = Integer.MIN_VALUE;

        if(!bardisses.isEmpty()) {
            for (Bardissa bardissa : bardisses) {
                minX = Math.min(minX, bardissa.getLongitude());
                minY = Math.min(minY, bardissa.getLatitude());
                maxX = Math.max(maxX, bardissa.getLongitude());
                maxY = Math.max(maxY, bardissa.getLatitude());
            }
            min = new Coordinate(minX, minY);
            max = new Coordinate(maxX, maxY);
        } else if(!subLocations.isEmpty()) {
            for (Location subLocation : subLocations) {
                subLocation.updateSize();
                minX = Math.min(minX, subLocation.getMin().getX());
                minY = Math.min(minY, subLocation.getMin().getY());
                maxX = Math.max(maxX, subLocation.getMax().getX());
                maxY = Math.max(maxY, subLocation.getMax().getY());
            }
            min = new Coordinate(minX, minY);
            max = new Coordinate(maxX, maxY);
        }
    }

    /**
     * Splits the bounding box in case there is more hedges than the allowed (3).
     * If there's no parent, the hedges are separated onto two groups, then creates two sub-locations and adds the
     * sub-locations to the parent, updates the size of the bounding box of the parent, removes the old bounding box,
     * removes the hedges of the current bounding box, update the root and it's size.
     * Otherwise, does the same as before but checks if the parent has the allowed size (<= 3), if false, splits the
     * parent.
     */
    //Metode que donada una Location amb 4 bardisses les divideix en dues
    public void splitGivenFourBardisses() {
        if(parent == null) { //Si no hi ha pare el creem
            Location root = new Location(); //Pujem la root

            parent = new Location();
            parent.setParent(root);
            root.subLocations.add(parent);

            //Separem les bardisses en dos grups
            ArrayList<Bardissa> primerGrup = obtenirPrimerGrupBardisses();
            ArrayList<Bardissa> segonGrup = new ArrayList<>(bardisses); //Afegim les bardisses que queden al segon grup

            //Creem les dues sublocations
            Location primerGrupLocation = createNewLocationFromBardisses(primerGrup);
            primerGrupLocation.updateSize();
            Location segonGrupLocation = createNewLocationFromBardisses(segonGrup);
            segonGrupLocation.updateSize();

            //Afegim les sublocations al pare
            parent.subLocations.add(primerGrupLocation);
            parent.subLocations.add(segonGrupLocation);
            primerGrupLocation.setParent(parent);
            segonGrupLocation.setParent(parent);

            parent.updateSize(); //Actualitzem la mida del pare

            parent.subLocations.remove(this); //Eliminem la location on estem de la llista de sublocations del pare
            bardisses.removeAll(bardisses); //Borrem les bardisses de la location on estem

            RTree.setRoot(root); //Actualitzem la root
            root.updateSize();
        } else { //Si ja té pare
            parent.subLocations.remove(this); //Eliminem la location on estem de la llista de sublocations del pare

            //Separem les bardisses en dos grups
            ArrayList<Bardissa> primerGrup = obtenirPrimerGrupBardisses();
            ArrayList<Bardissa> segonGrup = new ArrayList<>(bardisses);

            //Creem les dues sublocations
            Location primerGrupLocation = createNewLocationFromBardisses(primerGrup);
            primerGrupLocation.updateSize();
            Location segonGrupLocation = createNewLocationFromBardisses(segonGrup);
            segonGrupLocation.updateSize();

            parent.subLocations.add(primerGrupLocation); //Afegim la nova sublocation al pare
            parent.subLocations.add(segonGrupLocation); //Afegim la nova sublocation al pare
            primerGrupLocation.setParent(parent);
            segonGrupLocation.setParent(parent);

            if(parent.subLocations.size() > 3) { //Si el pare té més de 3 sublocations fem split al pare
                parent.splitGivenFourLocations();
            } else {
                parent.updateSize(); //Actualitzem la mida del pare
            }
        }
    }

    /**
     * Splits a bounding box (location) to sub-bounding boxes (sublocations).
     * If there is no parent, then creates a new root, splits the bounding box onto two groups and assigns the new
     * sub-bounding boxes to the parent.
     * Otherwise, does the same as before but check if the parent has the allowed size (<= 3), if false, splits the
     * parent.
     */
    private void splitGivenFourLocations() {
        if(parent == null) {
            Location root = new Location(); //Pujem la root
            RTree.setRoot(root);

            Location primerGrupParent = new Location();
            primerGrupParent.setParent(root);
            Location segonGrupParent = new Location();
            segonGrupParent.setParent(root);

            root.subLocations.add(primerGrupParent);
            root.subLocations.add(segonGrupParent);
            root.updateSize();

            //Separem les locations en dos grups
            ArrayList<Location> primerGrup = obtenirPrimerGrupLocations();
            for (Location l: primerGrup) { //Posem de pare del primer grup el pare corresponent
                l.setParent(primerGrupParent);
            }

            primerGrupParent.subLocations.addAll(primerGrup); //Afegim les locations al primer pare
            primerGrupParent.updateSize(); //Actualitzem la mida del pare

            ArrayList<Location> segonGrup = new ArrayList<>(subLocations); //Afegim les locations que queden al segon
                                                                            // grup
            for (Location l: segonGrup) { //Posem de pare del segon grup el pare corresponent
                l.setParent(segonGrupParent);
            }

            subLocations.removeAll(primerGrup);
            subLocations.removeAll(segonGrup);

            segonGrupParent.subLocations.addAll(segonGrup); //Afegim les locations al segon pare
            segonGrupParent.updateSize(); //Actualitzem la mida del pare

            subLocations.removeAll(subLocations); //Borrem les sublocations de la location on estem ara
        } else { //Si ja te pare
            parent.subLocations.remove(this); // Eliminem la location actual de la llista de sublocations del pare

            //Separem les locations en dos grups
            ArrayList<Location> primerGrup = obtenirPrimerGrupLocations();

            Location firstSublocation = createNewLocationFromLocations(primerGrup); //Creem la nova sublocation
            firstSublocation.setParent(parent); //Posem de pare de la nova sublocation el pare corresponent
            firstSublocation.updateSize(); //Actualitzem la mida de la nova sublocation
            parent.subLocations.add(firstSublocation); //Afegim la nova sublocation al pare

            ArrayList<Location> segonGrup = new ArrayList<>(subLocations); //Afegim les locations que queden al segon
                                                                            // grup

            Location secondSublocation = createNewLocationFromLocations(segonGrup); //Creem la nova sublocation
            secondSublocation.setParent(parent); //Posem de pare de la nova sublocation el pare corresponent
            secondSublocation.updateSize(); //Actualitzem la mida de la nova sublocation
            parent.subLocations.add(secondSublocation); //Afegim la nova sublocation al pare

            subLocations.removeAll(subLocations); //Borrem les sublocations de la location on estem ara

            parent.updateSize(); //Actualitzem la mida del pare

            if (parent.subLocations.size() > 3) { //Si el pare te mes de 3 sublocations fem split al pare
                parent.splitGivenFourLocations();
            } else {
                parent.updateSize(); //Actualitzem la mida del pare
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //  Metodes auxiliars
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Finds the best sub-location of a specific hedge (Bardissa object).
     *
     * @param bardissa  The hedge to consider.
     *
     * @return The best sub-location.
     */
    private Location findBestSublocation(Bardissa bardissa) {
        Location bestSublocation = null;
        double minIncreaseInArea = Integer.MAX_VALUE;

        // Recorrem totes les sublocations de la location actual
        for (Location sublocation : subLocations) {
            // Creem una sublocation temporal amb les bardisses de la sublocation actual
            Location temp = new Location();
            temp.bardisses.addAll(sublocation.bardisses);
            temp.bardisses.add(bardissa);

            double tempArea = temp.calculateArea(temp.bardisses);

            // Si la diferència d'àrea és la mínima fins ara, actualitzem la sublocation seleccionada
            if (tempArea < minIncreaseInArea) {
                bestSublocation = sublocation;
                minIncreaseInArea = tempArea;
            }
        }

        return bestSublocation;
    }


    //------------------------------------------------------------------------------------------------------------------
    //  Metodes Locations
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Creates new locations from the existing locations.
     *
     * @param locations The existing locations.
     *
     * @return The new location.
     */
    private Location createNewLocationFromLocations(ArrayList<Location> locations) {
        double minY = Integer.MAX_VALUE, maxY =  Integer.MIN_VALUE;
        double minX = Integer.MAX_VALUE, maxX =  Integer.MIN_VALUE;

        for (int i = 1; i < locations.size(); i++) {
            minY = Math.min(minY, locations.get(i).getMin().getY());
            maxY = Math.max(maxY, locations.get(i).getMax().getY());
            minX = Math.min(minX, locations.get(i).getMin().getX());
            maxX = Math.max(maxX, locations.get(i).getMax().getX());
        }
        Coordinate min = new Coordinate(minX, minY);
        Coordinate max = new Coordinate(maxX, maxY);

        Location newLocation = new Location();

        newLocation.setMin(min);
        newLocation.setMax(max);

        // Afegim les sublocations
        for (Location location : locations) {
            newLocation.getSubLocations().add(location);
            location.setParent(newLocation);
        }

        return newLocation;
    }

    /**
     * Obtains the first group for a location.
     *
     * @return The group of locations.
     */
    private ArrayList<Location> obtenirPrimerGrupLocations() {
        ArrayList<Location> temp = new ArrayList<>();

        double minArea = Double.MAX_VALUE;
        Location best1 = null, best2 = null;

        for (int i = 0; i < subLocations.size(); i++) {
            for (int j = i + 1; j < subLocations.size(); j++) {
                Location l1 = subLocations.get(i);
                Location l2 = subLocations.get(j);

                double area = calculateArea(l1.getBardisses()) + calculateArea(l2.getBardisses());

                if (area < minArea) {
                    minArea = area;
                    best1 = l1;
                    best2 = l2;
                }
            }
        }

        if (best1 != null) { temp.add(best1); }

        if (best2 != null) {
            temp.add(best2);
        }

        subLocations.removeAll(temp);

        return temp;
    }

    /**
     * Calculates the maximum and minimum area of a group of hedges (Bardissa objects).
     *
     * @param bardisses The group of hedges.
     *
     * @return The maximum and minimum area of the group hedges.
     */
    public double calculateArea(ArrayList<Bardissa> bardisses) {
        double minLat = Integer.MAX_VALUE;
        double minLng = Integer.MAX_VALUE;
        double maxLat = Integer.MIN_VALUE;
        double maxLng = Integer.MIN_VALUE;

        for (Bardissa bardissa : bardisses) {
            double lat = bardissa.getLatitude();
            double lng = bardissa.getLongitude();

            minLat = Math.min(minLat, lat);
            minLng = Math.min(minLng, lng);
            maxLat = Math.max(maxLat, lat);
            maxLng = Math.max(maxLng, lng);
        }

        double width = maxLng - minLng;
        double height = maxLat - minLat;

        return width * height;
    }


    //------------------------------------------------------------------------------------------------------------------
    //  Metodes Bardisses
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new location for the hedges (Bardissa objects).
     *
     * @param grup The group of hedges for the location.
     *
     * @return The new location for the hedges.
     */
    private Location createNewLocationFromBardisses(ArrayList<Bardissa> grup) {
        Location location = new Location();

        //Obtenim la latitud i longitud max i min de totes les bardisses del grup
        double minX = Integer.MAX_VALUE;
        double minY = Integer.MAX_VALUE;
        double maxX =  Integer.MIN_VALUE; //no
        double maxY =  Integer.MIN_VALUE;

        for (Bardissa bardissa : grup) {
            double x = bardissa.getLatitude();
            double y = bardissa.getLongitude();

            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }

        //creem les coordenades min i max de la nova location
        Coordinate min = new Coordinate(minX, minY);
        Coordinate max = new Coordinate(maxX, maxY);

        location.setMin(min);
        location.setMax(max);

        //Afegim les bardisses a la nova location
        location.getBardisses().addAll(grup);

        return location;
    }

    /**
     * Gets the first group of hedges (Bardissa objects).
     *
     * @return The group of hedges.
     */
    private ArrayList<Bardissa> obtenirPrimerGrupBardisses() {
        ArrayList<Bardissa> primerGrup = new ArrayList<>();
        double minArea = Integer.MAX_VALUE;

        //recorrem totes les combinacions possibles de dues bardisses
        for (int i = 0; i < bardisses.size(); i++) {
            for (int j = i + 1; j < bardisses.size(); j++) {
                //creem una llista temporal amb les dues bardisses seleccionades
                ArrayList<Bardissa> temp = new ArrayList<>();
                temp.add(bardisses.get(i));
                temp.add(bardisses.get(j));
                //Afegim les que queden a una altre llista temporal
                ArrayList<Bardissa> temp2 = new ArrayList<>(bardisses);
                temp2.addAll(bardisses); temp2.removeAll(temp);

                //Calculem l'area ocupada per els dos grups de bardisses
                //Buscarem quina és la combinació que minimitza l'area dels dos grups
                double tempArea = calculateArea(temp) + calculateArea(temp2);

                //Si l'area és la min actualitzem
                if (tempArea < minArea) {
                    primerGrup = temp;
                    minArea = tempArea;
                }
            }
        }

        //eliminem les bardisses seleccionades del grup de bardisses
        bardisses.removeAll(primerGrup);

        return primerGrup;
    }


    //--------------------------------------------------------------    ------------------------------------------------
    // Getters i setters
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Getter of the minimum coordinate.
     *
     * @return The minimum coordinate.
     */
    public Coordinate getMin() {
        return min;
    }

    /**
     * Setter of the minimum coordinate.
     *
     * @param min The minimum coordinate to be assigned.
     */
    public void setMin(Coordinate min) {
        this.min = min;
    }

    /**
     * Getter of the maximum coordinate.
     *
     * @return The maximum coordinate.
     */
    public Coordinate getMax() {
        return max;
    }

    /**
     * Setter of the maximum coordinate.
     *
     * @param max The maximum coordinate to be assigned.
     */
    public void setMax(Coordinate max) {
        this.max = max;
    }

    /**
     * Getter of the hedges (Bardissa objects).
     *
     * @return The hedges.
     */
    public ArrayList<Bardissa> getBardisses() {
        return bardisses;
    }

    /**
     * Getter of the sub-bounding boxes (sub-locations) of the current location.
     *
     * @return The sub-bounding boxes.
     */
    public ArrayList<Location> getSubLocations() {
        return subLocations;
    }

    /**
     * Checks if the current location is a leaf.
     *
     * @return True if the current location is a leaf, false otherwise.
     */
    public boolean isLeaf() {
        return subLocations.isEmpty();
    }

    /**
     * Setter of the parent location.
     *
     * @param parent The parent of the location.
     */
    private void setParent(Location parent) {
        this.parent = parent;
    }

    /*
     * FUNCIONS DE COMPROVACIÓ
     */

    /**
     * Checks if the leafs have hedges (Bardissa objects).
     *
     * @param root The root of the R Tree.
     *
     * @return True if there are hedges in the leaf, false otherwise.
     */
    public boolean checkOnlyLeafNodesHaveBardisses(Location root) {
        // Si la location és una fulla i te bardisses, retorna cert
        if (root.isLeaf() && !root.bardisses.isEmpty()) {
            return true;
        }

        // Si la location no és una fulla, pero te bardisses, retorna fals
        if (!root.isLeaf() && !root.bardisses.isEmpty()) {
            return false;
        }

        // Recorre les sublocations de la location actual i comprova si es cumpleix la condicio
        for (Location sublocation : root.subLocations) {
            if (!checkOnlyLeafNodesHaveBardisses(sublocation)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the leaf have sub-bounding boxes (sub-locations).
     *
     * @param root The root of the R Tree.
     *
     * @return True if there is at least one sub-location in the leaf, false otherwise.
     */
    public boolean checkLeafNodesHaveNoSublocations(Location root) {
        // Si la location és una fulla i no té sublocations, retorna cert
        if (root.isLeaf() && root.subLocations.isEmpty()) {
            return true;
        }

        // Si la location és una fulla però té sublocations, retorna fals
        if (root.isLeaf() && !root.subLocations.isEmpty()) {
            return false;
        }

        // Recorre les sublocations de la location actual i comprova si es compleix la condició
        for (Location sublocation : root.subLocations) {
            if (!checkLeafNodesHaveNoSublocations(sublocation)) {
                return false;
            }
        }

        return true;
    }

    /*
    ELIMINACIÓ BARDISSA
     */

    /**
     * Deletes a hedge recursively from the R Tree.
     * If the current location is a leaf, then searches for the hedge and removes it if it exists.
     * Otherwise, finds the sub-locations recursively till the hedge is erased if it exists.
     *
     * @param coordinate The coordinate of the hedge to be removed.
     *
     * @return True if the hedge was removed, false otherwise.
     */
    public boolean delete(Coordinate coordinate) {
        // Si la location és una fulla, elimina la bardissa
        if (isLeaf()) {
            for (Bardissa bardissa : bardisses) {
                if (bardissa.getLatitude() == coordinate.getY() && bardissa.getLongitude() == coordinate.getX()) {
                    bardisses.remove(bardissa);
                    return true;
                    //(PER SI DE CAS) ja que pot ser lios la interacció amb l'usuari i no acabem de definir si es x o y
                } else if (bardissa.getLatitude() == coordinate.getX() && bardissa.getLongitude() == coordinate.getY()) {
                    bardisses.remove(bardissa);
                    return true;
                }
            }
        } else {
            // Si la location no és una fulla, recorre les sublocations
            for (Location sublocation : subLocations) {
                if(sublocation.delete(coordinate)) {
                    return true;
                }
            }
        }
        return false; // Si no troba la bardissa retorna fals
    }

    /**
     * Updates the size of a bounding box (Location object) of the R Tree.
     *
     * @param location The bounding box to be updated.
     */
    public void updateTreeSize(Location location) {
        location.updateSize();

        if(!location.isLeaf()) {
            for (Location sublocation : location.subLocations) {
                updateTreeSize(sublocation);
            }
        }
    }
}