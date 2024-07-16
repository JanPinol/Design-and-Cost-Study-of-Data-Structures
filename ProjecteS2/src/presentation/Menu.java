package presentation;

import business.estructuraArbreR.entities.Bardissa;
import business.estructuraGraph.entities.InterestPoint;
import business.estructuraGraph.graph.Graph;
import business.estructuraGraph.graph.Node;
import business.estructuraTaules.entities.Guilty;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Menu class, represents the menu and part of
 * the UI
 */
public class Menu {

    // Components
    private Scanner scanner = new Scanner(System.in);

    /**
     * Method that show a hedge by its type and its color
     * @param bardissa hedge to show
     */
    public void showOptimitzacioEstetica(Bardissa bardissa) {
        System.out.println("\nTipus majoritari: " + bardissa.getType());
        System.out.println("Color mitja: " + bardissa.getColor().toUpperCase());
    }

    /**
     * Method that show the results of the exploration of a realm
     * @param realmExploration list of interest points
     * @param place origin interest point
     */

    public void showResultsRealmExploration(ArrayList<InterestPoint> realmExploration, InterestPoint place) {
        System.out.println();
        String str = place.getWeather().toLowerCase();
        String weather = str.substring(0, 1).toUpperCase() + str.substring(1);
        System.out.println(place.getId() + " - " + place.getName() + ", Regne de " + place.getReign() + " (Clima "
                + weather + ")");
        System.out.println();
        System.out.println("Els llocs del Regne de " + place.getReign() + " als que es pot arribar són:");
        System.out.println();
        for (InterestPoint ip: realmExploration) {
            str = ip.getWeather().toLowerCase();
            weather = str.substring(0, 1).toUpperCase() + str.substring(1);
            System.out.println(ip.getId() + " - " + ip.getName() + ", " + ip.getReign() + " (Clima " + weather + ")");
        }
    }

    /**
     * Method that show the results of the habitual routes
     * @param habitualRoutes graph of habitual routes
     */
    public void showResultsHabitualRoutes(Graph habitualRoutes) {
        System.out.println();
        for (int i = 0; i < habitualRoutes.getGraph().size(); i++) {
            // Mostrar nodos añadidos
            System.out.println(habitualRoutes.getGraph().get(i).getNodeId().getId() + " - " +
                    habitualRoutes.getGraph().get(i).getNodeId().getName() + ", Regne de " +
                    habitualRoutes.getGraph().get(i).getNodeId().getReign() + " (" +
                    habitualRoutes.getGraph().get(i).getNodeId().getWeather() + ") ");
            // Mostrar nodos conectados (aristas) con sus distancias
            for (int j = 0; j < habitualRoutes.getGraph().get(i).getEdges().size(); j++) {
                System.out.println("\t" + "Aresta = " + "Node A: "
                        + habitualRoutes.getGraph().get(i).getEdges().get(j).getPointA() + ", Node B: " +
                        habitualRoutes.getGraph().get(i).getEdges().get(j).getPointB() + ", Distància: " +
                        habitualRoutes.getGraph().get(i).getEdges().get(j).getDistance());
            }
            System.out.println();
        }
    }

    /**
     * Method that show the results of the trajectory detection
     * @param premiumMessaging list of nodes
     * @param isAfrican if the bird is african or not
     */
    public void showResultsPremiumMessaging(ArrayList<Node> premiumMessaging, boolean isAfrican) {
        System.out.println();

        if (null != premiumMessaging) {
            System.out.println("L’opció més eficient es enviar una oreneta " + ((isAfrican) ? "africana." : "europea."));

            System.out.println("\t Temps: " + (int) premiumMessaging.get(premiumMessaging.size() - 1).getTime() + " minuts");
            System.out.println("\t Distància: " + (int) premiumMessaging.get(premiumMessaging.size() - 1).getTotalDistance()
                    + " quilòmetres");
            System.out.println("\t Camí: ");
            for (Node n : premiumMessaging) {
                System.out.println("\t\t" + n.getNodeId().getId() + " - " + n.getNodeId().getName() + ", "
                        + n.getNodeId().getReign() + " (" + n.getNodeId().getWeather() + ")");
            }
        } else {
            System.out.println("No es pot enviar cap oreneta cap aquell lloc.");
        }

    }

    /**
     * Method that show the results of the trajectory detection
     * @param witches list of witches
     * @param isPart2 if is the second part the text shown is different
     */
    public void showWitches(String[] witches, boolean isPart2) {
        if (isPart2) {
            if (null != witches) {
                System.out.println();
                System.out.println("S'ha descobert " + witches.length
                        + ((witches.length != 1) ? " bruixes!" : " bruixa!"));
                for (String w : witches) {
                    System.out.println("\t" + w);
                }
            }
        } else {
            if (null != witches) {
                System.out.println();
                System.out.println("S'ha capturat " + witches.length
                        + ((witches.length != 1) ? " bruixes!" : " bruixa!"));
                for (String w : witches) {
                    System.out.println("\t" + w);
                }
            }
        }
    }

    /**
     * Method that show the results of the trajectory detection
     * @param bardisses list of bardisses
     */
    public void showFoundBardisses(List<Bardissa> bardisses) {
        System.out.println("\nS'han trobat " + bardisses.size()
                + ((bardisses.size() != 1) ? " bardisses " : " bardissa ") + " bardisses en aquesta àrea:");
        System.out.println();

        for (Bardissa bardissa : bardisses) {
            char sizeType = 's';
            if (bardissa.getType().equals("CIRCLE")) {
                sizeType = 'r';
            }
            System.out.println("\t* " + String.format("%.6f", bardissa.getLatitude()) + ", " +
                    String.format("%.6f", bardissa.getLongitude()) + ": " + bardissa.getType() + " (" + sizeType + "=" +
                    String.format("%.3f", bardissa.getSize()) + "m) " + bardissa.getColor().toUpperCase());
        }
    }

    public void showGuilties(ArrayList<Guilty> judiciFinalRang) {
        for (Guilty guilty : judiciFinalRang) {
            System.out.println("\t"+guilty.getName()+": ");
            System.out.println("\t\t * Nombre de conills vistos: " + guilty.getRabbits());
            System.out.println("\t\t * Professió: "+ guilty.getProfesion());
            System.out.println("\t\t * Heretge: "+ (guilty.isHeretge() ? "Si\n": "No\n"));
        }
    }

    /**
     * Menu that contains the options of the graph
     */
    public enum menuOptionsGraph {
        REALM_EXPLORATION,
        TRAJECTORY_DETECTION,
        PREMIUM_MESSAGING,
        GO_BACK,
        ERROR_OPTION
    }

    /**
     * Method that return the option selected by the user
     * from the graph menu
     * @param selection option the user selected
     * @return option selected transformed to enum
     */
    public menuOptionsGraph getSelectionOptionsGraph(char selection) {
        switch (selection) {
            case 'A' -> {
                return menuOptionsGraph.REALM_EXPLORATION;
            }
            case 'B' -> {
                return menuOptionsGraph.TRAJECTORY_DETECTION;
            }
            case 'C' -> {
                return menuOptionsGraph.PREMIUM_MESSAGING;
            }
            case 'D' -> {
                return menuOptionsGraph.GO_BACK;
            }
            default -> {
                return menuOptionsGraph.ERROR_OPTION;
            }
        }
    }

    /**
     * Menu that contains the options of the graph
     */
    public enum menuGlobal {
        GRAFS,
        TREE,
        RTREE,
        TABLES,
        EXIT,
        ERROR_OPTION
    }

    /**
     * Method that contains the options of the tree
     */
    public enum menuOptionsTree {
        AFEGIR_HABITANT,
        ELIMINAR_HABITANT,
        REPRESENTACIO_VISUAL,
        IDENTIFICACIO_DE_BRUIXES,
        BATUDA,
        GO_BACK,
        ERROR_OPTION
    }

    /**
     * Menu that return the options of the RTree
     */
    public enum menuOptionsRTree {
        AFEGIR_BARDISSA,
        ELIMINAR_BARDISSA,
        VISUALITZACIO,
        CERCA_PER_AREA,
        OPTIMITZACIO_ESTETICA,
        GO_BACK,
        ERROR_OPTION
    }

    /**
     * Menu that return the options of the tables
     */
    public enum menuOptionsTables {
        AFEGIR_ACUSAT,
        ELIMINAR_ACUSAT,
        EDICTE_DE_GRACIA,
        JUDICI_FINAL_ACUSAT,
        JUDICI_FINAL_RANG,
        HISTOGRAMA_PER_PROFESSIONS,
        GO_BACK,
        ERROR_OPTION
    }

    /**
     * Method that return the option selected by the user
     * from the tables menu
     * @param selection option the user selected
     * @return option selected transformed to enum
     */
    public menuOptionsTables getSelectionOptionsTable(char selection) {
        switch (selection) {
            case 'A' -> {
                return menuOptionsTables.AFEGIR_ACUSAT;
            }
            case 'B' -> {
                return menuOptionsTables.ELIMINAR_ACUSAT;
            }
            case 'C' -> {
                return menuOptionsTables.EDICTE_DE_GRACIA;
            }
            case 'D' -> {
                return menuOptionsTables.JUDICI_FINAL_ACUSAT;
            }
            case 'E' -> {
                return menuOptionsTables.JUDICI_FINAL_RANG;
            }
            case 'F' -> {
                return menuOptionsTables.HISTOGRAMA_PER_PROFESSIONS;
            }
            case 'G' -> {
                return menuOptionsTables.GO_BACK;
            }
            default -> {
                return menuOptionsTables.ERROR_OPTION;
            }
        }
    }

    /**
     * Method that return the option selected by the user
     * from the rtree menu
     * @param selection option the user selected
     * @return option selected transformed to enum
     */
    public menuOptionsRTree getSelectionOptionsRTree(char selection) {
        switch (selection) {
            case 'A' -> {
                return menuOptionsRTree.AFEGIR_BARDISSA;
            }
            case 'B' -> {
                return menuOptionsRTree.ELIMINAR_BARDISSA;
            }
            case 'C' -> {
                return menuOptionsRTree.VISUALITZACIO;
            }
            case 'D' -> {
                return menuOptionsRTree.CERCA_PER_AREA;
            }
            case 'E' -> {
                return menuOptionsRTree.OPTIMITZACIO_ESTETICA;
            }
            case 'F' -> {
                return menuOptionsRTree.GO_BACK;
            }
            default -> {
                return menuOptionsRTree.ERROR_OPTION;
            }
        }
    }

    /**
     * Method that return the option selected by the user
     * from the tree menu
     * @param selection option the user selected
     * @return option selected transformed to enum
     */
    public menuOptionsTree getSelectionOptionsTree(char selection) {
        switch (selection) {
            case 'A' -> {
                return menuOptionsTree.AFEGIR_HABITANT;
            }
            case 'B' -> {
                return menuOptionsTree.ELIMINAR_HABITANT;
            }
            case 'C' -> {
                return menuOptionsTree.REPRESENTACIO_VISUAL;
            }
            case 'D' -> {
                return menuOptionsTree.IDENTIFICACIO_DE_BRUIXES;
            }
            case 'E' -> {
                return menuOptionsTree.BATUDA;
            }
            case 'F' -> {
                return menuOptionsTree.GO_BACK;
            }
            default -> {
                return menuOptionsTree.ERROR_OPTION;
            }
        }
    }

    /**
     * Method that return the option selected by the user
     * from the global menu
     * @param selection option the user selected
     * @return option selected transformed to enum
     *
     */
    public menuGlobal getGlobalSelection(int selection) {
        switch (selection) {
            case 1 -> {
                return menuGlobal.GRAFS;
            }
            case 2 -> {
                return menuGlobal.TREE;
            }
            case 3 -> {
                return menuGlobal.RTREE;
            }
            case 4 -> {
                return menuGlobal.TABLES;
            }
            case  5 -> {
                return menuGlobal.EXIT;
            }
            default -> {
                return menuGlobal.ERROR_OPTION;
            }
        }
    }

    /**
     * Method that print the welcome message
     */
    public void welcomeMessage() {
        System.out.println("'`^\\ The Hashy Grail /^´'");
    }

    /**
     * Method that print the main menu
     */
    public void showMainMenu() {
        System.out.println("\n1. Sobre orenetes i cocos (Grafs)");
        System.out.println("2. Caça de bruixes (Arbres binaris de cerca)");
        System.out.println("3. Tanques de bardissa (Arbres R)");
        System.out.println("4. D'heretges i blasfems (Taules)\n");
        System.out.println("5. Exit\n");
        System.out.print("Esculli una opció: ");
    }

    /**
     * Method that ask for an integer to the user
     * @return integer checked
     */
    public int askForInteger() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("T'has equivocat no és un enter!");
                System.out.print("Esculli una opció: ");
            } finally {
                scanner.nextLine();
            }
        }
    }

    /**
     * Method that ask for a float to the user
     * @return float checked
     */
    public float askForFloat() {
        while (true) {
            try {
                return scanner.nextFloat();
            } catch (InputMismatchException e) {
                System.out.println("T'has equivocat no és un nombre de coma flotant!");
                System.out.print("Esculli una opció: ");
            } finally {
                scanner.nextLine();
            }
        }
    }

    /**
     * Method that ask for a double to the user
     * @return double checked
     */
    public double askForDouble() {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("T'has equivocat no és un nombre de coma flotant!");
                System.out.print("Entri un valor: ");
            } finally {
                scanner.nextLine();
            }
        }
    }

    /**
     * Method that show the grafs menu
     */
    public void showGrafsMenu() {
        System.out.println("\n\tA. Exploració del regne");
        System.out.println("\tB. Detecció de trajectes habituals");
        System.out.println("\tC. Missatgeria premium\n");
        System.out.println("\tD. Tornar enrere\n");
        System.out.print("Quina funcionalitat vol executar? ");
    }

    /**
     * Method that show the tree menu
     */
    public void showTreeMenu() {
        System.out.println("\n\tA. Afegir habitant");
        System.out.println("\tB. Eliminar habitant");
        System.out.println("\tC. Representació visual");
        System.out.println("\tD. Identificació de bruixes");
        System.out.println("\tE. Batuda\n");
        System.out.println("\tF. Tornar enrere\n");
        System.out.print("Quina funcionalitat vol executar? ");
    }

    /**
     * Method that show the rtree menu
     */
    public void showRTreeMenu() {
        System.out.println("\n\tA. Afegir bardissa");
        System.out.println("\tB. Eliminar bardissa");
        System.out.println("\tC. Visualització");
        System.out.println("\tD. Cerca per àrea");
        System.out.println("\tE. Optimització estètica\n");
        System.out.println("\tF. Tornar enrere\n");
        System.out.print("Quina funcionalitat vol executar? ");
    }

    /**
     * Method that show the tables menu
     */
    public void showTablesMenu() {
        System.out.println("\n\tA. Afegir acusat");
        System.out.println("\tB. Eliminar acusat");
        System.out.println("\tC. Edicte de gràcia");
        System.out.println("\tD. Judici final (un acusat)");
        System.out.println("\tE. Judici final (rang)");
        System.out.println("\tF. Histograma per professions\n");
        System.out.println("\tG. Tornar enrere\n");
        System.out.print("Quina funcionalitat vol executar? ");
    }

    /**
     * Method that prints a message to the user
     * @param message message to print
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Method that prints a message to the user in the same line
     * @param message message to print
     */
    public void showMessageInSameLine(String message) {
        System.out.print(message);
    }

    /**
     * Method that ask for a type of tree to the user
     * @return true if the user wants an AVL tree, false if the user wants a BST tree and otherwise
     */
    public boolean askForTreeType() {
        System.out.print("Quin tipus d'arbre vol? (1. AVL, 2. BST): ");
        int selection = askForInteger();
        return selection == 1;
    }

    /**
     * Method that ask for a character to the user
     * @return character checked
     */
    public char askForCharacter() {
        return scanner.nextLine().substring(0, 1).toUpperCase().charAt(0);
    }

    /**
     * Method that ask for a string to the user
     * @return string checked
     */
    public String askForString() {
        return scanner.nextLine();
    }

    /**
     * Method that prints the exit message
     */
    public void exitMessage() {
        System.out.println("\nAturant The Hashy Grail...");
    }

    /**
     * Method that prints the error message
     */
    public void errorOption() {
        System.out.println("\nNot a valid option");
    }

    /**
     * Method that prints the realm exploration question
     */
    public void realmExplorationMenu() {
        System.out.print("Quin lloc vol explorar? ");
    }

    /**
     * Method that prints the habitual known routes
     */
    public void trajectoryDetectionMenu() {
        System.out.println("\nEls trajectes més habituals són els següents:");
    }

    /**
     * Method that prints the first premium messaging question
     */
    public void originPlaceMenu() {
        System.out.print("Quin és el lloc d’origen? ");
    }

    /**
     * Method that prints the second premium messaging question
     */
    public void destinyPlaceMenu() {
        System.out.print("Quin és el lloc de destí? ");
    }

    /**
     * Method that prints the third premium messaging question
     */
    public void hasCocoMenu() {
        System.out.print("L’oreneta carrega un coco? ");
    }

    /**
     * Method that activate the admin options if the user wants
     * @return true if the admin mode is activated, false otherwise
     */
    public boolean adminOptions() {
        System.out.print("Vols activar les opcions d'administrador (anàlisi)? (Y/N) ");
        if (askForCharacter() == 'Y') {
            System.out.print("Introdueix la contrasenya: ");
            if (askForString().equals("admin")) {
                System.out.println("Opcions d'administrador activades");
                System.out.println();
                return true;
            } else {
                System.out.println("Contrasenya incorrecta");
                System.out.println("No has entrat en el mode d'administrador");
                System.out.println();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Method that shows the results of the admin mode
     * @param times array with the times
     * @param timeComplete time of the complete execution
     */
    public void showAdminConfig(long[] times, long timeComplete) {

        System.out.println("Temps que ha trigat en mostrar i fer l'execució: " + timeComplete + " ms");

        System.out.println("Temps que ha trigat en inicialitzar el graf: " + times[0] + " ms");
        System.out.println("Temps que ha trigat en fer l'execució: " + times[1] + " ms");
        System.out.println("Temps que ha trigat en fer l'execució per segon cop: " + times[2] + " ms");
        System.out.println("La segona execució ha estat feta sense inicialitzar el graf");
        System.out.println();

    }
}
