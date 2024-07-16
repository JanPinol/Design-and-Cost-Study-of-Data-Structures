package business;

import business.estructuraArbreBin.entities.Habitant;
import business.estructuraArbreBin.a_witchfinder.WitchFinder;
import business.estructuraArbreBin.a_batuda.Batuda;
import business.estructuraArbreBin.trees.AVLTree;
import business.estructuraArbreBin.trees.BSTree;
import business.estructuraArbreBin.trees.Leaf;
import persistence.DAO;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TreeManager {
    private BSTree bsTree;
    private AVLTree avlTree;

    public TreeManager(BSTree bsTree, AVLTree avlTree) {
        this.bsTree = bsTree;
        this.avlTree = avlTree;
    }

    public void removeTrees() {
        bsTree = new BSTree(null);
        avlTree = new AVLTree(null);
    }

    public void createTrees() {
        DAO dao = new DAO();
        try {
            ArrayList<Habitant> temp =  dao.readDataFileBruixest();
            if(GlobalsBusiness.isAVL) {
                avlTree.createTree(temp);
            } else {
                bsTree.createTree(temp);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addHabitant(String name, int id, String realm, float weight) {
        Habitant habitant = new Habitant(id, name, weight, realm);
        if(GlobalsBusiness.isAVL) {
            avlTree.addHabitant(habitant);
        } else {
            bsTree.addHabitant(habitant);
        }
    }

    public String removeHabitant(int id) {
        Boolean found = false;
        if(GlobalsBusiness.isAVL) {
            found = avlTree.searchHabitant(id);
        } else {
            found = bsTree.searchHabitant(id);
        }

        if(!found) {
            return "\nNo existeix un habitant amb aquest identificador.";
        }

        Habitant habitantToRemove = null;
        if(GlobalsBusiness.isAVL) {
            habitantToRemove = avlTree.searchHabitantInOrder(id);
            avlTree.removeHabitant(habitantToRemove);
        } else {
            habitantToRemove = bsTree.searchHabitantInOrder(id);
            bsTree.removeHabitant(habitantToRemove);
        }

        return "\n" + habitantToRemove.getName() + " ha estat transformat en un grill.";
    }

    public String executePart1() {
        StringBuilder sb = new StringBuilder();

        if(GlobalsBusiness.isAVL) {
            avlTree.getAVLRoot().printTree(sb);
        } else {
            bsTree.getBsRoot().printTree(sb);
        }

        String treeAsString = sb.toString();

        return treeAsString;
    }

    public String[] executePart2(String name,Float weight, String type) {
        WitchFinder part2 = new WitchFinder(avlTree, bsTree);
        switch (type) {
            case "WOOD" -> {
                Leaf leaf = part2.woodWitchFinder(weight);
                String[] witchesNames = new String[1];
                witchesNames[0] = "* " + leaf.getName() + " (" + leaf.getId() + ", " +
                        leaf.getRealm() + "): " + leaf.getWeight() +" kg" ;
                return witchesNames;
            }
            case "DUCK" -> {
                ArrayList<Leaf> witches = part2.duckWitchFinder(weight);
                return getStrings(witches);
            }
            case "STONE" -> {
                Leaf leaf = part2.stoneWitchFinder(weight);
                String[] witchesNames = new String[1];
                witchesNames[0] = "* " + leaf.getName() + " (" + leaf.getId() + ", " +
                        leaf.getRealm() + "): " + leaf.getWeight() +" kg" ;
                return witchesNames;
            }
            default -> {
                System.out.println("\nNo s'ha trobat el tipus de bruixa");
                return null;
            }
        }
    }

    public String[] executePart3(float minWeight, float maxWeight) {
        Batuda part3 = new Batuda(avlTree, bsTree);
        ArrayList<Leaf> witches = part3.batudaBruixes(minWeight, maxWeight);
        return getStrings(witches);
    }

    private String[] getStrings(ArrayList<Leaf> witches) {
        String[] witchesNames = new String[witches.size()];
        for (int i = 0; i < witches.size(); i++) {
            witchesNames[i] = "* " + witches.get(i).getName() + " (" + witches.get(i).getId() + ", " +
                    witches.get(i).getRealm() + "): " + witches.get(i).getWeight() +" kg" ;
        }
        return witchesNames;
    }

    public boolean checkId(int id) {
        Boolean found;
        if(GlobalsBusiness.isAVL) {
            found = avlTree.searchHabitant(id);
        } else {
            found = bsTree.searchHabitant(id);
        }

        if(!found) {
            return false;
        }
        return true; //Retornem true si ha trobat un identificador igual al arbre.
    }
}
