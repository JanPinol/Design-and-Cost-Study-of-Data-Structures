package business.estructuraArbreBin.trees;

import business.estructuraArbreBin.entities.Habitant;

import java.util.ArrayList;

import static java.lang.Math.max;

/**
 * Class AVLTree that represents an AVL tree of habitants
 */
public class AVLTree {

    // Atributs
    private Leaf avlRoot;


    /**
     * Constructor of the class AVLTree
     *
     * @param avlRoot root of the tree
     */
    public AVLTree(Leaf avlRoot) {
        this.avlRoot = avlRoot;
    }

    /**
     * Method that creates the tree given a list of habitants
     *
     * @param habitants list of habitants to add to the tree
     */
    public void createTree(ArrayList<Habitant> habitants) {
        // Per cada habitant de la llista, l'afegim a l'arbre
        for (Habitant h: habitants) {
            // Si l'arbre està buit, creem la fulla arrel
            if(avlRoot == null) {
                avlRoot = new Leaf(h.getId(), h.getName(), h.getWeight(), h.getRealm());
            }
            // Si no està buit, afegim la fulla a l'arbre
            else{
                avlRoot = insertToTree(avlRoot, h);
            }
        }
    }

    /*
    ROTAR A L'ESQUERRA: mou la fulla dreta com a nou pare, i l'antic pare
                        passa a ser el fill esquerre.

    ROTAR A LA DRETA: mou la fulla esquerra dreta com a nou pare, i l'antic pare
                      passa a ser el fill dret.

     FACTOR DE BALANCEIG APLICAT:
            >+1: alçada del subarbre esquerra és més gran que el dret
            <-1: alçada del subarbre dret és més gran que l'esquerra
            = 0: alçades del subarbre esquerra i dreta són iguals

     Amb ajuda de: https://www.javatpoint.com/avl-tree-program-in-java, https://www.programiz.com/dsa/avl-tree
     */

    /**
     * Method that inserts a habitant to the tree
     *
     * @param currentLeaf current leaf
     * @param h habitant to add
     * @return leaf added
     */
    private Leaf insertToTree(Leaf currentLeaf, Habitant h) {
        //Si hem arribat a un punt que no hi ha continuació, creem la fulla i la retornem
        if (currentLeaf == null) {
            return new Leaf(h.getId(), h.getName(), h.getWeight(), h.getRealm());
        }

        // A partir del pes l'afegim des del fill dret o el fill esquerre recursivament
        if (h.getWeight() < currentLeaf.getWeight()) {
            currentLeaf.setLeft(insertToTree(currentLeaf.getLeft(), h));
        } else if (h.getWeight() > currentLeaf.getWeight()) {
            currentLeaf.setRight(insertToTree(currentLeaf.getRight(), h));
        } else {
            // Si els pesos són iguals, retornem la currentLeaf, no gestionem el cas de fulles repetides
            return currentLeaf;
        }

        // Actualitzem l'alçada de la currentLeaf a partir de les alçades dels seus fills
        currentLeaf.setHeight(max(height(currentLeaf.getLeft()), height(currentLeaf.getRight())) + 1);

        // Calculem el factor de balanceig de la fulla actual
        int balance = getBalanceFactor(currentLeaf);

        //CAS: Left-Left
        // Si el factor de balanceig és major que 1 i el pes és menor que el pes del fill esquerre, rotem a la dreta.
        if (balance > 1 && h.getWeight() < currentLeaf.getLeft().getWeight()) {
            return rotateRight(currentLeaf);
        }

        //CAS: Right-Right
        // Si el factor de balanceig és menor que -1 i el pes és major que el pes del fill dret, rotem a l'esquerre.
        if (balance < -1 && h.getWeight() > currentLeaf.getRight().getWeight()) {
            return rotateLeft(currentLeaf);
        }

        //CAS: Left-Right
        //Si el factor de balanceig és major que 1 i el pes és major que el pes del fill esquerre, rotem a l'esquerra el fill esquerre i després rotem a la dreta la currentLeaf.
        if (balance > 1 && h.getWeight() > currentLeaf.getLeft().getWeight()) {
            currentLeaf.setLeft(rotateLeft(currentLeaf.getLeft()));
            return rotateRight(currentLeaf);
        }

        //CAS: Right-Left
        // Si el factor de balanceig és menor que -1 i el pes és major que el pes del fill dret, rotem a la dreta el fill dret i després rotem a l'esquerra la currentLeaf.
        if (balance < -1 && h.getWeight() < currentLeaf.getRight().getWeight()) {
            currentLeaf.setRight(rotateRight(currentLeaf.getRight()));
            return rotateLeft(currentLeaf);
        }

        return currentLeaf;
    }

    /**
     * Method that gets the height of a leaf
     *
     * @param currentLeaf leaf to get the height
     * @return height of the leaf
     */
    private int height(Leaf currentLeaf) {
        // Si la currentLeaf és null, l'alçada és 0, si no retornem l'alçada que li toca.
        return currentLeaf == null ? 0 : currentLeaf.getHeight();
    }

    /**
     * Method that gets the balance factor of a leaf
     *
     * @param currentLeaf leaf to get the balance factor
     * @return balance factor of the leaf
     */
    private int getBalanceFactor(Leaf currentLeaf) {
        // Si la currentLeaf és null, l'alçada és 0, si no retornem la diferència d'alçades
        return currentLeaf == null ? 0 : height(currentLeaf.getLeft()) - height(currentLeaf.getRight());
    }

    /**
     * Method that rotates a leaf to the right
     *
     * @param y leaf to rotate
     * @return leaf rotated
     */
    private Leaf rotateRight(Leaf y) {
        //Si la fulla que ens passen és null o el seu fill esquerre ho és, retornem la fulla (y)
        if (y == null || y.getLeft() == null) {
            return y;
        }

        // Fem la rotació i passa a ser fill dret de X i T2 passa a ser fill esquerre de Y
        Leaf x = y.getLeft();
        Leaf T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        //Actualitzem les alçades
        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);

        return x;
    }

    /**
     * Method that rotates a leaf to the left
     *
     * @param x leaf to rotate
     * @return leaf rotated
     */
    private Leaf rotateLeft(Leaf x) {
        //Si la fulla que ens passen es null o el seu fill dret ho es retornem la fulla (x)
        if (x == null || x.getRight() == null) {
            return x;
        }

        // Fem la rotació x passa a ser fill esquerre de y i T2 passa a ser fill dret de x
        Leaf y = x.getRight();
        Leaf T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        //Actualitzem les alçades
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1);

        return y;
    }

    /**
     * Method that adds a leaf to the tree
     *
     * @param habitantToAdd
     */
    public void addHabitant(Habitant habitantToAdd) {
        avlRoot = insertToTree(avlRoot, habitantToAdd);
    }


    /**
     * Method that removes a leaf from the tree
     *
     * @param habitantToRemove
     */
    public void removeHabitant(Habitant habitantToRemove) {
        removeLeaf(avlRoot, habitantToRemove.getWeight());
    }

    /*
    FUNCIÓ ELIMINAR LEAF:

    Amb ajuda de: https://www.geeksforgeeks.org/deletion-in-an-avl-tree/
    */
    /**
     * Method that removes a leaf from the tree
     *
     * @param currentLeaf leaf to remove
     * @param weightOfLeaf weight of the leaf to remove
     * @return leaf removed
     */
    private Leaf removeLeaf(Leaf currentLeaf, float weightOfLeaf) {
        // Si la currentLeaf és null
        if (currentLeaf == null) {
            return null;
        }

        // Busquem la fulla a eliminar
        if (weightOfLeaf < currentLeaf.getWeight()) {
            currentLeaf.setLeft(removeLeaf(currentLeaf.getLeft(), weightOfLeaf));
        }
        else if (weightOfLeaf > currentLeaf.getWeight()) {
            currentLeaf.setRight(removeLeaf(currentLeaf.getRight(), weightOfLeaf));
        }
        else {
            // Casos en què el node a elimnar te un fill o no en te
            // Node amb només un fill o cap fill
            if ((currentLeaf.getLeft() == null) || (currentLeaf.getRight() == null)) {
                Leaf temp = null;
                if (currentLeaf.getLeft() == null) {
                    temp = currentLeaf.getRight();
                } else {
                    temp = currentLeaf.getLeft();
                }

                // Cap fill
                if (temp == null) {
                    currentLeaf = null;
                } else { // Un fill
                    currentLeaf = temp; // Copiem els continguts del fill no null
                }
            } else {
                // Cas en que el node a eliminar té dos fills
                Leaf temp = minValueLeaf(currentLeaf.getRight());

                // Copiem els valors
                currentLeaf.setId(temp.getId());
                currentLeaf.setName(temp.getName());
                currentLeaf.setWeight(temp.getWeight());
                currentLeaf.setRealm(temp.getRealm());

                // Eliminem el succesor inordre
                currentLeaf.setRight(removeLeaf(currentLeaf.getRight(), temp.getWeight()));
            }
        }

        // Si l'arbre només tenia un node
        if (currentLeaf == null) {
            return currentLeaf;
        }

        // Actualitzem l'alçada
        currentLeaf.setHeight(Math.max(height(currentLeaf.getLeft()), height(currentLeaf.getRight())) + 1);

        // Calculem el factor de balanceig
        int balance = getBalanceFactor(currentLeaf);

        // Balancegem els subarbres
        // CAS: Esquerra-Esquerra - rotem a la dreta
        if (balance > 1 && getBalanceFactor(currentLeaf.getLeft()) >= 0) {
            return rotateRight(currentLeaf);
        }

        // CAS: Dreta-Dreta - rotem a l'esquerra
        if (balance < -1 && getBalanceFactor(currentLeaf.getRight()) <= 0) {
            return rotateLeft(currentLeaf);
        }

        // CAS: Esquerra-Dreta - rotem a l'esquerra el fill esquerre i després rotem a la dreta la currentLeaf
        if (balance > 1 && getBalanceFactor(currentLeaf.getLeft()) < 0) {
            currentLeaf.setLeft(rotateLeft(currentLeaf.getLeft()));
            return rotateRight(currentLeaf);
        }
        // CAS: Dreta-Esquerra - rotem a la dreta el fill dret i després rotem a l'esquerra la currentLeaf
        if (balance < -1 && getBalanceFactor(currentLeaf.getRight()) > 0) {
            currentLeaf.setRight(rotateRight(currentLeaf.getRight()));
            return rotateLeft(currentLeaf);
        }

        return currentLeaf;
    }


    /**
     * Method that returns the minimum value leaf of the left subtree of a leaf
     *
     * @param leaf leaf to search
     * @return minimum value leaf
     */
    private Leaf minValueLeaf(Leaf leaf) {
        Leaf current = leaf;
        //Busquem el succesor inordre
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    /**
     * Method that return the root of the tree
     *
     * @return root of the tree
     */
    public Leaf getAVLRoot() {
        return avlRoot;
    }

    /**
     * Method that checks if a leaf exists in the tree
     *
     * @param idHabitantToSearch id of the leaf to search
     * @return if the leaf exists or not
     */
    public Boolean searchHabitant(int idHabitantToSearch) {
        return inOrderSearch(avlRoot, idHabitantToSearch);
    }

    /**
     * Method that checks if a leaf exists in the tree
     *
     * @param currentNode current leaf
     * @param idToSearch id of the leaf to search
     * @return if the leaf exists or not
     */
    private Boolean inOrderSearch(Leaf currentNode, int idToSearch) {
        if (currentNode == null) {
            return false;
        }

        //Si té fill esquerre
        if (currentNode.getLeft() != null) {
            if (inOrderSearch(currentNode.getLeft(), idToSearch)) {
                return true;
            }
        }

        // Comprovem el id
        if (currentNode.getId() == idToSearch) {
            return true;
        }

        // Si té fill dret
        if (currentNode.getRight() != null) {
            if (inOrderSearch(currentNode.getRight(), idToSearch)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method that returns a leaf of the tree
     * @param idHabitantToSearch id of the leaf to search
     * @return the leaf if it exists or null if it doesn't exist
     */
    public Habitant searchHabitantInOrder(int idHabitantToSearch) {
        return habitantInOrderSearch(avlRoot, idHabitantToSearch);
    }

    /**
     * Method that returns a leaf of the tree
     *
     * @param currentLeaf current leaf
     * @param idToSearch id of the leaf to search
     * @return the leaf if it exists or null if it doesn't exist
     */
    private Habitant habitantInOrderSearch(Leaf currentLeaf, int idToSearch) {
        if (currentLeaf == null) {
            return null;
        }

        //Si té fill esquerre
        if (currentLeaf.getLeft() != null) {
            Habitant found = habitantInOrderSearch(currentLeaf.getLeft(), idToSearch);
            if (found != null) {
                return found;
            }
        }

        // Comprovem el id
        if (currentLeaf.getId() == idToSearch) {
            return new Habitant(currentLeaf.getId(), currentLeaf.getName(), currentLeaf.getWeight(), currentLeaf.getRealm());
        }

        // Si té fill dret
        if (currentLeaf.getRight() != null) {
            Habitant found = habitantInOrderSearch(currentLeaf.getRight(), idToSearch);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
