import business.GraphManager;
import business.HashManager;
import business.RTreeManager;
import business.TreeManager;
import business.estructuraArbreBin.trees.AVLTree;
import business.estructuraArbreBin.trees.BSTree;
import business.estructuraArbreR.tree.RTree;
import business.estructuraTaules.taula.GuiltyChart;

import presentation.Menu;
import presentation.Controller;


/**
 * Main class
 * @author Oriol Rebordosa Cots
 * @author Leonardo Ruben Edenak Chouev
 * @author Jan Piñol Castuera
 * @author Joan Tarragó Pina
 */
public class Main {

    /**
     * Main method of the program
     * @param args arguments
     */
    public static void main(String[] args) {
        Menu UI = new Menu();

        GuiltyChart guiltyChart = new GuiltyChart();
        HashManager hashManager = new HashManager(guiltyChart);

        GraphManager graphManager = new GraphManager();

        BSTree bsTree = new BSTree(null);
        AVLTree avlTree = new AVLTree(null);
        TreeManager treeManager = new TreeManager(bsTree, avlTree);

        RTree rTree = new RTree();
        RTreeManager rTreeManager = new RTreeManager(rTree);

        Controller c = new Controller(UI, graphManager, treeManager, rTreeManager, hashManager);

        c.run();
    }
}