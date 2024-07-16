package business.estructuraArbreBin.a_witchfinder;

import business.GlobalsBusiness;
import business.estructuraArbreBin.trees.AVLTree;
import business.estructuraArbreBin.trees.BSTree;
import business.estructuraArbreBin.trees.Leaf;

import java.util.ArrayList;

/**
 * Class that represents a witch finder in the tree
 */
public class WitchFinder {
    // Atributs
    AVLTree avlTree;
    BSTree bsTree;

    /**
     * Constructor of the class WitchFinder
     *
     * @param avlTree avltree to search
     * @param bsTree bstree to search
     */
    public WitchFinder(AVLTree avlTree, BSTree bsTree) {
        this.avlTree = avlTree;
        this.bsTree = bsTree;
    }

    /**
     * Method that returns a list of leaves that contains witches (duck)
     *
     * @param witchWeight weight of the witch
     * @return list of leaves that contains witches
     */
    public ArrayList<Leaf> duckWitchFinder(float witchWeight) {
        ArrayList<Leaf> sameWheightWitches = new ArrayList<>();

        if (GlobalsBusiness.isAVL) {
            findSameWeightWitch(avlTree.getAVLRoot(), witchWeight, sameWheightWitches);
        } else {
            findSameWeightWitch(bsTree.getBsRoot(), witchWeight, sameWheightWitches);
        }

        return sameWheightWitches;
    }

    /**
     * Method that return the leaf with the most similar weight to the witch weight
     *
     * @param currentLeaf current leaf of the recursion
     * @param witchWeight weight of the witch
     * @param sameWeightWitches list of leaves with the same weight
     */
    private void findSameWeightWitch(Leaf currentLeaf, float witchWeight, ArrayList<Leaf> sameWeightWitches) {
        //Si la currentLeaf és major al pes que hem de buscar, busquem a partir del seu fill esquerre.
        if(currentLeaf.getWeight() > witchWeight && currentLeaf.getLeft() != null) {
            // Tornem a cridar la funció amb el fill esquerre de la fulla actual.
            findSameWeightWitch(currentLeaf.getLeft(), witchWeight, sameWeightWitches);
        }
        //Si la currentLeaf és menor al pes que hem de buscar, busquem a partir del seu fill dret.
        else if (currentLeaf.getWeight() < witchWeight && currentLeaf.getRight() != null) {
            // Tornem a cridar la funció amb el fill dret de la fulla actual.
            findSameWeightWitch(currentLeaf.getRight(), witchWeight, sameWeightWitches);
        }
        //Si és igual retornem la fulla en la qual ens trobem, ja que és la primera que té el mateix valor.
        else if (currentLeaf.getWeight() == witchWeight){
            sameWeightWitches.add(currentLeaf);
            if(currentLeaf.getRight() != null) {
                findSameWeightWitch(currentLeaf.getRight(), witchWeight, sameWeightWitches);
            }
            if (currentLeaf.getLeft() != null) {
                findSameWeightWitch(currentLeaf.getLeft(), witchWeight, sameWeightWitches);
            }
        }
    }

    /**
     * Method that returns the leaf with the less weight witch (wood)
     *
     * @param witchWeight weight of the witch
     * @return leaf with the less weight witch
     */
    public Leaf woodWitchFinder(float witchWeight) {
        Leaf lessWeightWitch = null;

        // Comprovem si l'arbre és AVL o BST
        if (GlobalsBusiness.isAVL) {
            // Si és AVL, demanem el node arrel de l'arbre AVL
            lessWeightWitch = findLessWeightWitch(avlTree.getAVLRoot(), witchWeight);
        } else {
            // Si és BST, demanem el node arrel de l'arbre BST
            lessWeightWitch = findLessWeightWitch(bsTree.getBsRoot(), witchWeight);
        }
        return lessWeightWitch;
    }

    /**
     * Method that returns the leaf with the less weight witch
     *
     * @param currentLeaf current leaf of the recursion
     * @param witchWeight weight of the witch
     * @return leaf with the less weight witch
     */
    private Leaf findLessWeightWitch(Leaf currentLeaf, float witchWeight) {
        //Si la currentLeaf és major al pes que hem de buscar, busquem a partir del seu fill esquerre.
        if(currentLeaf.getWeight() > witchWeight) {
            return findLessWeightWitch(currentLeaf.getLeft(), witchWeight);
        }
        //Si és més petita, retornem la fulla en la qual ens trobem, això pararà les crides recursives
        else {
            return currentLeaf;
        }
    }


    /**
     * Method that returns the leaf with the greater weight witch (stone)
     *
     * @param witchWeight weight of the witch
     * @return leaf with the greater weight witch
     */
    public Leaf stoneWitchFinder(float witchWeight) {
        Leaf greaterWeightWitch = null;

        // Comprovem si l'arbre és AVL o BST
        if (GlobalsBusiness.isAVL) {
            // Si és AVL, demanem el node arrel de l'arbre AVL
            greaterWeightWitch = findGreaterWeightWitch(avlTree.getAVLRoot(), witchWeight);
        } else {
            // Si és BST, demanem el node arrel de l'arbre BST
            greaterWeightWitch = findGreaterWeightWitch(bsTree.getBsRoot(), witchWeight);
        }

        return greaterWeightWitch;
    }

    /**
     * Method that returns the leaf with the greater weight witch
     *
     * @param currentLeaf current leaf of the recursion
     * @param witchWeight weight of the witch
     * @return leaf with the greater weight witch
     */
    private Leaf findGreaterWeightWitch(Leaf currentLeaf, float witchWeight) {
        //Si la currentLeaf és menor al pes que hem de buscar, busquem a partir del seu fill dret
        if (currentLeaf.getWeight() < witchWeight) {
            return findGreaterWeightWitch(currentLeaf.getRight(), witchWeight);
        }
        //Si és major, retornem la fulla en la qual ens trobem, això pararà les crides recursives
        else {
            return currentLeaf;
        }
    }

}