package presentation;

import business.*;
import business.estructuraArbreR.entities.Bardissa;
import business.estructuraArbreR.tree.Coordinate;
import business.exceptions.BusinessException;

import java.util.Arrays;

public class Controller {
    private final Menu UI;
    private final GraphManager graphManager;
    private final TreeManager treeManager;
    private final RTreeManager rTreeManager;
    private final HashManager hashManager;
    private boolean adminMode = false;
    private long startTime = 0;
    private long endTime = 0;
    private final String[] professions = {"MINSTREL", "KNIGHT", "KING", "QUEEN", "PEASANT", "SHRUBBER", "CLERGYMAN",
            "ENCHANTER"};


    public Controller(Menu UI, GraphManager graphManager, TreeManager treeManager, RTreeManager rTreeManager, HashManager hashManager) {
        this.UI = UI;
        this.graphManager = graphManager;
        this.treeManager = treeManager;
        this.rTreeManager = rTreeManager;
        this.hashManager = hashManager;
    }

    public void run () {
        int option;
        boolean stop;

        UI.welcomeMessage();
        // adminMode = UI.adminOptions();
        do {
            UI.showMainMenu();
            option = UI.askForInteger();
            stop = executeOption(option);
        } while(!stop);
    }

    private boolean executeOption(int option) {
        switch (UI.getGlobalSelection(option)) {
            case GRAFS -> {
                try {
                    executeGrafsOption();
                } catch (BusinessException e) {
                    UI.showMessage(e.getMessage());
                }
            }
            case TREE -> {
                GlobalsBusiness.isAVL = UI.askForTreeType();
                try {
                    executeTreeOption();
                } catch (BusinessException e) {
                    UI.showMessage(e.getMessage());
                }
            }
            case RTREE -> {
                try {
                    executeRTreeOption();
                } catch (BusinessException e) {
                    UI.showMessage(e.getMessage());
                }
            }
            case TABLES -> {
                try {
                    executeTablesOption();
                } catch (BusinessException e) {
                    UI.showMessage(e.getMessage());
                }
            }
            case EXIT -> {
                UI.exitMessage();
                return true;
            }
            default -> UI.errorOption();
        }
        return false;
    }

    private void executeTablesOption() throws BusinessException {
        hashManager.createTable();
        while (true) {
            UI.showTablesMenu();
            char option = UI.askForCharacter();
            switch (UI.getSelectionOptionsTable(option)) {
                case AFEGIR_ACUSAT -> {
                    UI.showMessageInSameLine("\nNom de l'acusat: ");
                    String name = UI.askForString();

                    UI.showMessageInSameLine("Nombre de conills vistos: ");
                    int numRabbits = UI.askForInteger();

                    String profession;
                    do {
                        UI.showMessageInSameLine("Professió: ");
                        profession = UI.askForString();
                        if (!Arrays.stream(professions).toList().contains(profession))
                            UI.showMessage("Professió no vàlida");
                    } while (!Arrays.stream(professions).toList().contains(profession));

                    hashManager.insert(name, numRabbits, profession);
                    UI.showMessage("\nS’ha enregistrat un nou possible heretge.");
                }
                case ELIMINAR_ACUSAT -> {
                    UI.showMessageInSameLine("\nNom de l'acusat: ");
                    String name = UI.askForString();

                    UI.showMessage(hashManager.remove(name));
                }
                case EDICTE_DE_GRACIA -> {
                    UI.showMessageInSameLine("\nNom de l'acusat: ");
                    String name = UI.askForString();

                    String heretic;
                    do {
                        UI.showMessageInSameLine("Marcar com a heretge (Y/N)? ");
                        heretic = UI.askForString();
                        heretic = heretic.toUpperCase();
                    } while (!heretic.equals("Y") && !heretic.equals("N"));

                     UI.showMessage("\n"+hashManager.edicteGracia(name, heretic.equals("Y")));
                }
                case JUDICI_FINAL_ACUSAT -> {
                    UI.showMessageInSameLine("\nNom de l'acusat: ");
                    String name = UI.askForString();

                    String info = hashManager.judiciFinal(name);
                    if(info.equals("No s'ha trobat el culpable")) {
                        System.out.println(info);
                    } else {
                        UI.showMessage("\nRegistre per \"" + name + "\":");
                        String parts[] = info.split(" ");
                        UI.showMessage("\t * Nombre de conills vistos: " + parts[0]);
                        UI.showMessage("\t * Professió: " + parts[1]);
                        UI.showMessage("\t * Heretge? " + ((parts[2].equals("true") ? "Sí" : "No")));
                    }

                }
                case JUDICI_FINAL_RANG -> {
                    UI.showMessageInSameLine("\nNombre mínim de conills: ");
                    int minRabbits = UI.askForInteger();

                    UI.showMessageInSameLine("Nombre màxim de conills: ");
                    int maxRabbits = UI.askForInteger();

                    UI.showMessage("\nS'han trobat els següents acusats:");
                    UI.showGuilties(hashManager.judiciFinalRang(minRabbits, maxRabbits));

                }
                case HISTOGRAMA_PER_PROFESSIONS -> {
                    UI.showMessage("\nGenerant histograma...");
                    hashManager.histograma();
                }
                case GO_BACK -> {
                    return;
                }
            }
        }
    }

    private void executeRTreeOption() throws BusinessException {
        rTreeManager.createTree();
        while (true) {
            UI.showRTreeMenu();
            char option = UI.askForCharacter();
            switch (UI.getSelectionOptionsRTree(option)) {
                case AFEGIR_BARDISSA -> {
                    String type;
                    do {
                        UI.showMessageInSameLine("\nTipus de la bardissa: ");
                        type = UI.askForString();
                    } while (!type.equals("CIRCLE") && !type.equals("SQUARE"));

                    UI.showMessageInSameLine("Mida de la bardissa: ");
                    double size = UI.askForDouble();

                    UI.showMessageInSameLine("Latitud de la bardissa: ");
                    double latitude = UI.askForDouble();

                    UI.showMessageInSameLine("Longitud de la bardissa: ");
                    double longitude = UI.askForDouble();

                    UI.showMessageInSameLine("Color de la bardissa: ");
                    String color = UI.askForString();

                    UI.showMessage(rTreeManager.addBardissa(type, size, latitude, longitude, color));
                }
                case ELIMINAR_BARDISSA -> {
                    UI.showMessageInSameLine("\nLatitud de la bardissa: ");
                    double latitude = UI.askForDouble();

                    UI.showMessageInSameLine("Longitud de la bardissa: ");
                    double longitude = UI.askForDouble();

                    UI.showMessage(rTreeManager.removeBardissa(latitude, longitude));
                }
                case VISUALITZACIO -> {
                    UI.showMessage("\nGenerant la visualització...\n");
                    rTreeManager.visualization();
                }
                case CERCA_PER_AREA -> {
                    UI.showMessageInSameLine("\nEntra el primer punt de l'àrea (lat,long): ");
                    String firstPoint = UI.askForString();

                    UI.showMessageInSameLine("Entra el segon punt de l'àrea (lat,long): ");
                    String secondPoint = UI.askForString();

                    UI.showFoundBardisses(rTreeManager.searchInArea(firstPoint, secondPoint));
                }
                case OPTIMITZACIO_ESTETICA -> {
                    UI.showMessageInSameLine("\nEntra el punt a consultar (lat,long): ");
                    String pointQueriedAux = UI.askForString();
                    String[] pointQueriedAuxArray = pointQueriedAux.split(",");
                    Coordinate pointQueried = new Coordinate(Double.parseDouble(pointQueriedAuxArray[0]), Double.parseDouble(pointQueriedAuxArray[1]));

                    UI.showMessageInSameLine("Entra la quantitat de bardisses a considerar (K): ");
                    int bardissesQuantity = UI.askForInteger();

                    Bardissa bardissa = rTreeManager.optimitzacioEstetica(pointQueried, bardissesQuantity);

                    UI.showOptimitzacioEstetica(bardissa);
                }
                case GO_BACK -> {
                    rTreeManager.removeTree();
                    return;
                }
            }
        }
    }

    private void executeTreeOption() throws BusinessException  {
        treeManager.createTrees();
        while (true) {
            UI.showTreeMenu();
            char option = UI.askForCharacter();
            switch (UI.getSelectionOptionsTree(option)) {
                case AFEGIR_HABITANT -> {
                    int id;
                    boolean ok;
                    do {
                        ok = true;
                        UI.showMessageInSameLine("\nIdentificador de l’habitant: ");
                        id = UI.askForInteger();
                        if(treeManager.checkId(id)) {
                            UI.showMessage("Ja existeix un habitant amb aquest identificador!");
                            ok = false;
                        }
                    } while (!ok);

                    UI.showMessageInSameLine("Nom de l’habitant: ");
                    String name = UI.askForString();
                    UI.showMessageInSameLine("Pes de l’habitant: ");
                    float pes = UI.askForFloat();
                    UI.showMessageInSameLine("Regne de l’habitant: ");
                    String realm = UI.askForString();

                    treeManager.addHabitant(name, id, realm, pes);

                    UI.showMessage("\n" + name + " ens acompanyarà a partir d’ara.\n");
                }
                case ELIMINAR_HABITANT -> {
                    UI.showMessageInSameLine("Identificador de l’habitant: ");
                    int id = UI.askForInteger();
                    UI.showMessage(treeManager.removeHabitant(id));
                }
                case REPRESENTACIO_VISUAL -> {
                    UI.showMessage("");
                    UI.showMessage(treeManager.executePart1());
                }
                case IDENTIFICACIO_DE_BRUIXES -> {
                    UI.showMessageInSameLine("\nIntrodueix el nom: ");
                    String name = UI.askForString();
                    UI.showMessageInSameLine("Introdueix el pes: ");
                    float weight = UI.askForFloat();
                    UI.showMessageInSameLine("Introdueix el tipus: ");
                    String type = UI.askForString();
                    type = type.toUpperCase();
                    UI.showWitches(treeManager.executePart2(name, weight, type), true);
                }
                case BATUDA -> {
                    UI.showMessageInSameLine("\nPes mínim: ");
                    float minWeight = UI.askForFloat();
                    UI.showMessageInSameLine("Pes màxim: ");
                    float maxWeight = UI.askForFloat();
                    UI.showWitches(treeManager.executePart3(minWeight, maxWeight), false);
                }
                case GO_BACK -> {
                    treeManager.removeTrees();
                    return;
                }
                default -> UI.errorOption();
            }
        }
    }

    private void executeGrafsOption() throws BusinessException {
        while (true) {
            if (adminMode) {
                try {
                    graphManager.printGraph();
                    System.out.println();
                } catch (BusinessException e) {
                    System.out.println(e.getMessage());
                }
            }
            UI.showGrafsMenu();
            char option = UI.askForCharacter();
            switch (UI.getSelectionOptionsGraph(option)) {
                case REALM_EXPLORATION -> {
                    UI.realmExplorationMenu();
                    int place = UI.askForInteger();
                    if (adminMode) {
                        startTime = System.currentTimeMillis();
                        UI.showResultsRealmExploration(graphManager.realmExploration(place), graphManager.getNodeById(place).getNodeId());
                        endTime = System.currentTimeMillis();
                        long[] times = graphManager.adminTesting(1, place, 0, 0, null);
                        UI.showAdminConfig(times, endTime - startTime);
                    } else {
                        UI.showResultsRealmExploration(graphManager.realmExploration(place), graphManager.getNodeById(place).getNodeId());
                    }
                }
                case TRAJECTORY_DETECTION -> {
                    UI.trajectoryDetectionMenu();
                    UI.showResultsHabitualRoutes(graphManager.habitualRoutes());
                }
                case PREMIUM_MESSAGING -> {
                    UI.originPlaceMenu();
                    int origin = UI.askForInteger();
                    UI.destinyPlaceMenu();
                    int destiny = UI.askForInteger();
                    UI.hasCocoMenu();
                    String coco = UI.askForString();
                    if (adminMode) {
                        startTime = System.currentTimeMillis();
                        UI.showResultsPremiumMessaging(graphManager.premiumMessaging(origin, destiny, coco), graphManager.isAfrican());
                        endTime = System.currentTimeMillis();
                        long[] times = graphManager.adminTesting(3, 0, origin, destiny, coco);
                        UI.showAdminConfig(times, endTime - startTime);
                    } else {
                        UI.showResultsPremiumMessaging(graphManager.premiumMessaging(origin, destiny, coco), graphManager.isAfrican());
                    }
                }
                case GO_BACK -> {
                    return;
                }
                default -> UI.errorOption();
            }
        }
    }

}
