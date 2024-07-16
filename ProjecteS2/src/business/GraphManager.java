package business;

import business.estructuraGraph.entities.InterestPoint;
import business.estructuraGraph.entities.KnownRoute;
import business.estructuraGraph.graph.Graph;
import business.estructuraGraph.graph.Node;
import business.estructuraGraph.part1.RealmExploration;
import business.estructuraGraph.part2.HabitualRoutes;
import business.estructuraGraph.part3.PremiumMessaging;
import business.exceptions.BusinessException;
import persistence.DAO;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GraphManager {
    private Graph graph;
    private ArrayList<InterestPoint> listInterestPoints;
    private ArrayList<KnownRoute> listKnownRoutes;
    private boolean isAfrican = false;
    private boolean adminMode = false;


    public void initializeGraph() throws BusinessException {
        graph = new Graph();
        graph.createGraph(getListInterestPoint(), getListKnownRoute());
    }

    public ArrayList<InterestPoint> realmExploration(int place) throws BusinessException {
        if (!adminMode) initializeGraph();
        Node node;
        node = getNodeById(place);

        if (node == null) {
            throw new BusinessException("El lloc introduït no existeix.");
        } else {
            RealmExploration realmExploration = new RealmExploration(graph);
            return realmExploration.realExplorationDFS(node);
        }

    }

    public Graph habitualRoutes() throws BusinessException {
        if (!adminMode) initializeGraph();
        initializeGraph();
        HabitualRoutes habitualRoutes = new HabitualRoutes(graph);
        return habitualRoutes.habitualRoutes();
    }

    public ArrayList<Node> premiumMessaging(int origin, int destiny, String coco) throws BusinessException {
        if (!adminMode) initializeGraph();
        PremiumMessaging premiumMessaging = new PremiumMessaging(graph);
        Node start = graph.searchNodeById(origin);
        Node end = graph.searchNodeById(destiny);
        boolean cocoBoolean = false;
        coco = coco.toLowerCase();

        if(coco.equals("si")) {
            cocoBoolean = true;
        }
        ArrayList<Node> bestRoute = premiumMessaging.premiumMessaging(start, end, cocoBoolean);
        isAfrican = premiumMessaging.isAfrican();
        return bestRoute;
    }

    public long[] adminTesting(int partToTest, int place, int origin, int destiny, String coco) {
        // Array de longs que conté els temps de cada execució
        long[] times = new long[3];
        // Variables per calcular el temps
        long start, end;

        // Execució de la inicialització del graf [0]
        try {
            start = System.currentTimeMillis();
            initializeGraph();
            end = System.currentTimeMillis();
            times[0] = end - start;
        } catch (BusinessException e) {
            times[0] = -1;
        }

        // Execució de la part a testar [1] i [2]
        //      [1] -> Execució inicialitzant el graf
        //      [2] -> Execució sense inicialitzar el graf
        switch (partToTest) {
            case 1 -> {
                start = System.currentTimeMillis();
                try {
                    realmExploration(place);
                    end = System.currentTimeMillis();
                    times[1] = end - start;
                } catch (BusinessException e) {
                    times[1] = -1;
                }
                try {
                    adminMode = true;
                    realmExploration(place);
                    end = System.currentTimeMillis();
                    times[2] = end - start;
                    adminMode = false;
                } catch (BusinessException e) {
                    times[2] = -1;
                }
            }
            case 2 -> {
                start = System.currentTimeMillis();
                try {
                    habitualRoutes();
                    end = System.currentTimeMillis();
                    times[1] = end - start;
                } catch (BusinessException e) {
                    times[1] = -1;
                }
                try {
                    adminMode = true;
                    habitualRoutes();
                    end = System.currentTimeMillis();
                    times[2] = end - start;
                    adminMode = false;
                } catch (BusinessException e) {
                    times[2] = -1;
                }
            }
            case 3 -> {
                start = System.currentTimeMillis();
                try {
                    premiumMessaging(origin, destiny, coco);
                    end = System.currentTimeMillis();
                    times[1] = end - start;
                } catch (BusinessException e) {
                    times[1] = -1;
                }
                try {
                    adminMode = true;
                    premiumMessaging(origin, destiny, coco);
                    end = System.currentTimeMillis();
                    times[2] = end - start;
                    adminMode = false;
                } catch (BusinessException e) {
                    times[2] = -1;
                }

            }
        }
        adminMode = false;
        return times;
    }

    private ArrayList<InterestPoint> getListInterestPoint() throws BusinessException {
        try {
            DAO dao = new DAO();
            listInterestPoints = dao.readDataFileInterestPoint();
        } catch (FileNotFoundException e) {
            throw new BusinessException("ERROR: The data cannot be found.");
        }
        return listInterestPoints;
    }

    private ArrayList<KnownRoute> getListKnownRoute() throws BusinessException{
        try {
            DAO dao = new DAO();
            listKnownRoutes = dao.readDataFileKnownRoute();
        } catch (FileNotFoundException e) {
            throw new BusinessException("ERROR: The data cannot be found.");
        }
        return listKnownRoutes;
    }

    public Node getNodeById(int place) {
        return graph.searchNodeById(place);
    }

    public boolean isAfrican() {
        return isAfrican;
    }

    public void printGraph() throws BusinessException{
        initializeGraph();
        ArrayList<Node> graph = this.graph.getGraph();
        for (Node node : graph) {
            System.out.println("Node: " + node.getNodeId().getId());
            System.out.println("Edges: ");
            for (KnownRoute edge : node.getEdges()) {
                System.out.println(edge.getPointA() + " - " + edge.getPointB());
            }
        }
    }

}
