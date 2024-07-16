package business.estructuraArbreBin.trees;

import business.estructuraArbreBin.entities.Habitant;

import java.util.ArrayList;

/**
 * Class that represents a binary search tree
 */
public class BSTree {
    private Leaf bsRoot;

    /**
     * Constructor of the class BSTree
     *
     * @param bsRoot root of the tree
     */
    public BSTree(Leaf bsRoot) {
        this.bsRoot = bsRoot;
    }

    /**
     * Method that creates the tree
     *
     * @param habitants list of habitants
     */
    public void createTree(ArrayList<Habitant> habitants) {
        for (Habitant h: habitants) {
            if(bsRoot == null) {
                bsRoot = new Leaf(h.getId(), h.getName(), h.getWeight(), h.getRealm());
            } else{
                insertToTree(bsRoot, h);
            }
        }
    }

    /**
     * Method that adds a habitant to the tree
     *
     * @param h habitant to add
     */
    private void insertToTree(Leaf bsRoot, Habitant h) {
        if(h.getWeight() < bsRoot.getWeight()) {
            if(bsRoot.getLeft() == null) {
                bsRoot.setLeft(new Leaf(h.getId(), h.getName(), h.getWeight(), h.getRealm()));
            } else {
                insertToTree(bsRoot.getLeft(), h);
            }
        }
        else{
            if(bsRoot.getRight() == null) {
                bsRoot.setRight(new Leaf(h.getId(), h.getName(), h.getWeight(), h.getRealm()));
            } else {
                insertToTree(bsRoot.getRight(), h);
            }
        }
    }

    /**
     * Method that gets the root of the tree
     *
     * @return root of the tree
     */
    public Leaf getBsRoot() {
        return bsRoot;
    }

    /**
     * Method that adds a habitant to the tree
     *
     * @param habitantToAdd habitant to add
     */
    public void addHabitant(Habitant habitantToAdd) {
        insertToTree(bsRoot, habitantToAdd);
    }

    /**
     * Method that deletes a habitant from the tree
     *
     * @param habitantToRemove habitant to remove
     */
    public void removeHabitant(Habitant habitantToRemove) {
        removeLeaf(bsRoot, habitantToRemove.getWeight());
    }

    /**
     * Method that deletes a leaf from the tree
     *
     * @param currentLeaf current leaf
     * @param wheightOfLeaf weight of the leaf
     * @return leaf to remove
     */
    private Leaf removeLeaf(Leaf currentLeaf, float wheightOfLeaf) {
        // Si hem arribat a un punt que no tenim més fills retornem null
        if(currentLeaf == null) {
            return null;
        }

        // Si el pes de la fulla en la que ens trobem és major al pes de la fulla a eliminar
        // eliminem recursivament la fulla a partir del fill esquerra
        if(currentLeaf.getWeight() > wheightOfLeaf) {
            currentLeaf.setLeft(removeLeaf(currentLeaf.getLeft(), wheightOfLeaf));
        }
        // Si el pes de la fulla en la que ens trobem és menor al pes de la fulla a eliminar
        // eliminem recursivament la fulla a partir del fill dret
        else if(currentLeaf.getWeight() < wheightOfLeaf) {
            currentLeaf.setRight(removeLeaf(currentLeaf.getRight(), wheightOfLeaf));
        }
        //Si el pes és el mateix vol dir que ja hem trobat la fulla a eliminar
        else {
            // Si no te cap fill la leaf a eliminar, retornem null --> leaf eliminada
            if (currentLeaf.getLeft() == null && currentLeaf.getRight() == null) {
                return null;
            }

            // Si el fill esquerra de la leaf és null retornem el fill dret, estem posant el fill dret de la fulla
            // a eliminar al lloc de la fulla a eliminar
            if(currentLeaf.getLeft() == null) {
                return currentLeaf.getRight();
            }

            // Si el fill dret de la leaf és null retornem el fill esquerra, estem posant el fill esquerra de la fulla
            // a eliminar al lloc de la fulla a eliminar
            if(currentLeaf.getRight() == null) {
                return currentLeaf.getLeft();
            }

            // Si la fulla a eliminar té els dos fills, busquem la fulla amb el mínim valor de la part dreta, de la part dreta
            //ja que volem una fulla que té un pes major a totes les fulles a partir del fill esquerra de la fulla a eliminar però
            //que a la vegada tingui un pes menor a totes les fulles a partir del fill dret de la fulla a eliminar.
            //Per fer-ho anem recorrent totes les fulles esquerres a partir del fill dret fins que en trobem una que no tingui fill
            //esquerra lo qual voldra dir que hem trobat la que esta més a l'esquerra de tot
            Leaf minRight = currentLeaf.getRight();
            while(minRight.getLeft() != null) {
                minRight = minRight.getLeft();
            }

            // Posem tota les fulles de la part esquerra de la fulla a eliminar a la part esquerra de la fulla més petita de
            //la part dreta
            minRight.setLeft(currentLeaf.getLeft());

            // Retornem la fulla dreta ja que ja hem introduit tota la part esquerra de la fulla a eliminar abans: minRight.setLeft(currentLeaf.getLeft())
            return currentLeaf.getRight();
        }

        //Retornem la currentLeaf ja que Intelij ens fa retornar quelcom per defecte
        return currentLeaf;
    }

    /**
     * Method that searches a habitant in the tree
     *
     * @param idHabitantToSearch id of the habitant to search
     * @return true if the habitant is in the tree, false if not
     */
    public Boolean searchHabitant(int idHabitantToSearch) {
        return inOrderSearch(bsRoot, idHabitantToSearch);
    }

    /**
     * Method that searches a habitant in the tree
     *
     * @param currentLeaf current leaf
     * @param idToSearch id to search
     * @return true if the habitant is in the tree, false if not
     */
    private Boolean inOrderSearch(Leaf currentLeaf, int idToSearch) {
        if (currentLeaf == null) {
            return false;
        }

        //Si te fill esquerra
        if (currentLeaf.getLeft() != null) {
            if (inOrderSearch(currentLeaf.getLeft(), idToSearch)) {
                return true;
            }
        }

        // Comprovem el id
        if (currentLeaf.getId() == idToSearch) {
            return true;
        }

        if (currentLeaf.getRight() != null) {
            if (inOrderSearch(currentLeaf.getRight(), idToSearch)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method that searches a habitant in the tree
     *
     * @param idHabitantToSearch id of the habitant to search
     * @return habitant if the habitant is in the tree, null if not
     */
    public Habitant searchHabitantInOrder(int idHabitantToSearch) {
        return habitantInOrderSearch(bsRoot, idHabitantToSearch);
    }

    /**
     * Method that searches a habitant in the tree
     *
     * @param currentLeaf current leaf
     * @param idToSearch id to search
     * @return habitant if the habitant is in the tree, null if not
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
