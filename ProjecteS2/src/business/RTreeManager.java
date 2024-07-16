package business;

import business.estructuraArbreR.a_cerca.CercaArea;
import business.estructuraArbreR.a_optimitzacio.OptimitzacioEstetica;
import business.estructuraArbreR.a_visualitzacio.Visualitzacio;
import business.estructuraArbreR.entities.Bardissa;
import business.estructuraArbreR.tree.Coordinate;
import business.estructuraArbreR.tree.RTree;
import persistence.DAO;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RTreeManager {
    private RTree rTree;

    public RTreeManager(RTree rTree) {
        this.rTree = rTree;
    }

    public void createTree() {
        DAO dao = new DAO();
        try {
            ArrayList<Bardissa> temp = dao.readDataFileBardisses();
            rTree.createTree(temp);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeTree() {
        rTree = new RTree();
    }

    public String addBardissa(String type, double size, double latitude, double longitude, String color) {
        rTree.insertBardissa(new Bardissa(type, size, latitude, longitude, color));
        rTree.updateTreeSize();

        return "\nUna nova bardissa aparegué a la Bretanya.";
    }

    public String removeBardissa(double latitude, double longitude) {
        Coordinate coordinate = new Coordinate(longitude, latitude);
        return rTree.deleteBardissa(coordinate);
    }

    public void visualization() {
        Visualitzacio visualitzacio = new Visualitzacio();
        visualitzacio.visualitzacio(rTree);
    }

    public List<Bardissa> searchInArea(String firstPoint, String secondPoint) {
        CercaArea cercaArea = new CercaArea(firstPoint, secondPoint);
        return cercaArea.cerca(rTree);
    }

    public Bardissa optimitzacioEstetica(Coordinate pointQueried, int bardissesQuantity) {
        OptimitzacioEstetica optimitzacioEstetica = new OptimitzacioEstetica(pointQueried, bardissesQuantity, rTree);
        return optimitzacioEstetica.optimitzacioEstetica();
    }
}
