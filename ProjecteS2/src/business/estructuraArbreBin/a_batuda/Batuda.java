package business.estructuraArbreBin.a_batuda;

import business.GlobalsBusiness;
import business.estructuraArbreBin.trees.AVLTree;
import business.estructuraArbreBin.trees.BSTree;
import business.estructuraArbreBin.trees.Leaf;

import java.util.ArrayList;

/**
 * Class "Batuda" that represents the search of witches in the tree
 */
public class Batuda {

    // Atributs
    AVLTree avlTree;
    BSTree bsTree;

    /**
     * Constructor of the class Batuda
     *
     * @param avlTree AVL tree
     * @param bsTree Binary search tree
     */
    public Batuda(AVLTree avlTree, BSTree bsTree) {
        this.avlTree = avlTree;
        this.bsTree = bsTree;
    }

    /**
     * Method that returns a list of leaves that contains witches between a minimum and a maximum weight
     *
     * @param minPes Minimum weight
     * @param maxPes Maximum weight
     * @return List of leaves that contains witches
     */
    public ArrayList<Leaf> batudaBruixes(float minPes, float maxPes) {
        ArrayList<Leaf> bruixes = new ArrayList<>();
        if (GlobalsBusiness.isAVL) {
            witchesFinder(avlTree.getAVLRoot(), minPes, maxPes, bruixes);
        } else {
            witchesFinder(bsTree.getBsRoot(), minPes, maxPes, bruixes);
        }
        return bruixes;
    }

    /**
     * Method that returns a list of leaves that contains witches between a minimum and a maximum weight
     *
     * @param currentLeaf Current leaf
     * @param minPes Minimum weight
     * @param maxPes Maximum weight
     * @param bruixes List of leaves that contains witches
     */
    private void witchesFinder(Leaf currentLeaf, float minPes, float maxPes, ArrayList<Leaf> bruixes) {
        //Si hem arribat a un punt on ja no tenim fills, és a dir ens han passat un NULL
        if (currentLeaf == null) {
            return;
        }

        //Si la fulla en la qual ens trobem actualment està dins del rang de valors, l'afegim a la llista.
        if (currentLeaf.getWeight() >= minPes && currentLeaf.getWeight() <= maxPes) {
            bruixes.add(currentLeaf);

            //Com que es troba dins del rang podem buscar els seus fills recursivament
            witchesFinder(currentLeaf.getLeft(), minPes, maxPes, bruixes);
            witchesFinder(currentLeaf.getRight(), minPes, maxPes, bruixes);
        }
        //Si la fulla no es troba dins del rang de valors
        else {
            //Si la fulla en la qual ens trobem és major que el valor mínim de pes a buscar i és major al pes maxim, busquem recursivament des del fill esquerre.
            if (currentLeaf.getWeight() > minPes && !(currentLeaf.getWeight() < maxPes)) {
                witchesFinder(currentLeaf.getLeft(), minPes, maxPes, bruixes);
            }
            // Si la fulla en la qual ens trobem és menor que el valor maxim de pes a buscar i és menor al pes minim, busquem recursivament des del fill dret.
            if (currentLeaf.getWeight() < maxPes && !(currentLeaf.getWeight() > minPes)) {
                witchesFinder(currentLeaf.getRight(), minPes, maxPes, bruixes);
            }
        }
    }
}
