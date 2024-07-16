package business.estructuraGraph.part3;

import business.estructuraGraph.entities.KnownRoute;
import business.estructuraGraph.graph.Graph;
import business.estructuraGraph.graph.Node;

import java.util.*;

/**
 * Class that represents the premium messaging or dijkstra algorithm to find the best route to follow applied to
 * our graph.
 */
public class PremiumMessaging {
    private Graph graph;
    private boolean isAfrican = false;
    private boolean isEuropean = false;
    private final String CONTINENTAL = "CONTINENTAL";
    private final String TROPICAL = "TROPICAL";
    private final String POLAR = "POLAR";
    private final int SWALLOW_ERROR_TIME = -1;

    /**
     * Constructor of the class PremiumMessaging
     *
     * @param graph graph of the project
     */
    public PremiumMessaging(Graph graph) {
        this.graph = graph;
    }

    /**
     * Method that compares the european route with the african route and returns the best one depending on the time.
     *
     * @param startPoint    Start point of the route.
     * @param endPoint      End point of the route.
     * @param coconut       If the "oreneta" will have a coconut or not during the route.
     * @return An arraylist of nodes that contains the best route to follow.
     */
    public ArrayList<Node> premiumMessaging(Node startPoint, Node endPoint, boolean coconut) {
        String startWeather = startPoint.getNodeId().getWeather();
        String endWeather = endPoint.getNodeId().getWeather();

        // If origin tropical and destiny tropical or continental -> african.
        // If origin polar and destiny polar or continental -> european.
        // If origin continental and destiny tropical -> african.
        // If origin continental and destiny polar -> european.
        // If origin continental and destiny continental -> both.
        // If origin polar and destiny tropical -> error.
        if (startWeather.equals(TROPICAL) && (endWeather.equals(TROPICAL) || endWeather.equals(CONTINENTAL))) {
            isAfrican = true;
        } else if (startWeather.equals(POLAR) && (endWeather.equals(POLAR) || endWeather.equals(CONTINENTAL))) {
            isEuropean = true;
        } else if (startWeather.equals(CONTINENTAL)) {
            if (endWeather.equals(TROPICAL)) {
                isAfrican = true;
            } else if (endWeather.equals(POLAR)) {
                isEuropean = true;
            } else {
                isAfrican = true;
                isEuropean = true;
            }
        } else if (startWeather.equals(POLAR) && endWeather.equals(TROPICAL)
                || startWeather.equals(TROPICAL) && endWeather.equals(POLAR)) {
            return null;
        }

        initNodes(startPoint);
        Queue<Node> path = new LinkedList<>();
        float totalDistance = 0;

        ArrayList<Node> africanPath = new ArrayList<>();
        ArrayList<Node> europeanPath = new ArrayList<>();
        float totalTimeAfrican = 0;
        float totalTimeEuropean = 0;

        if (isEuropean && isAfrican) {
            europeanPath = dijkstraEuropean(startPoint, endPoint, coconut);
            africanPath = dijkstraAfrican(startPoint, endPoint, coconut);
            totalTimeAfrican = africanPath.get(africanPath.size() - 1).getTime();
            totalTimeEuropean = europeanPath.get(europeanPath.size() - 1).getTime();
        } else if (isAfrican) {
            africanPath = dijkstraAfrican(startPoint, endPoint, coconut);
            totalTimeAfrican = africanPath.get(africanPath.size() - 1).getTime();
        } else {
            europeanPath = dijkstraEuropean(startPoint, endPoint, coconut);
            totalTimeEuropean = europeanPath.get(europeanPath.size() - 1).getTime();
        }

        if (isEuropean && isAfrican) {
            if (totalTimeAfrican > totalTimeEuropean) {
                isAfrican = false;
                return europeanPath;
            } else {
                isAfrican = true;
                return africanPath;
            }
        } else if (isEuropean) {
            isAfrican = false;
            return europeanPath;
        } else {
            isAfrican = true;
            return africanPath;
        }
    }

    /**
     * Method that performs the european route and returns the best one depending on the time. Uses an implementation of
     * Dijkstra's algorithm applied to our graph.
     *
     * @param startPoint    Start point of the route.
     * @param endPoint      End point of the route.
     * @param coconut       If the "oreneta" will have a coconut or not during the route.
     * @return An arraylist of nodes that contains the best route to follow given the restrictions of the european route.
     */
    private ArrayList<Node> dijkstraEuropean(Node startPoint, Node endPoint, boolean coconut) {
        Node startNode = graph.searchNodeById(startPoint.getNodeId().getId());
        Node endNode = graph.searchNodeById(endPoint.getNodeId().getId());

        List<KnownRoute> distances = new ArrayList<>();
        List<Node> visitedNodes = new ArrayList<>();
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(startNode);

        while (visitedNodes.size() != graph.getGraph().size() && !endPoint.isVisited() && !nodes.isEmpty()) {
            Node currentNode = nodes.poll();

            for (KnownRoute route : currentNode.getEdges()) {
                Node adjacentNode = findNodeById(currentNode, route);
                String adjacentNodeWeather = adjacentNode.getNodeId().getWeather();

                if (!visitedNodes.contains(adjacentNode)) {
                    if (adjacentNodeWeather.equals(POLAR) || adjacentNodeWeather.equals(CONTINENTAL)
                            && route.getTimeE() != SWALLOW_ERROR_TIME) {
                        float time = currentNode.getTime() + route.getTimeE();

                        if (coconut) {
                            if (route.getDistance() < 50) {
                                if (time < adjacentNode.getTime()) {
                                    adjacentNode.setTotalDistance(currentNode.getTotalDistance() + route.getDistance());
                                    adjacentNode.setTime(time);
                                    adjacentNode.setPreviousNode(currentNode);

                                    nodes.add(adjacentNode);
                                    visitedNodes.add(adjacentNode);
                                }
                            }
                        } else if (time < adjacentNode.getTime()){
                            adjacentNode.setTotalDistance(currentNode.getTotalDistance() + route.getDistance());
                            adjacentNode.setTime(time);
                            adjacentNode.setPreviousNode(currentNode);

                            nodes.add(adjacentNode);
                            visitedNodes.add(adjacentNode);
                        }
                    }
                }
            }
        }

        ArrayList<Node> cami = new ArrayList<>();
        Node actual = endNode;

        while (actual != null) {
            cami.add(actual);
            actual = actual.getPreviousNode();
        }

        Collections.reverse(cami);

        return cami;
    }

    /**
     * Method that performs the african route and returns the best one depending on the time. Uses an implementation of
     * Dijkstra's algorithm applied to our graph.
     *
     * @param startPoint    Start point of the route.
     * @param endPoint      End point of the route.
     * @param coconut       If the "oreneta" will have a coconut or not during the route.
     * @return An arraylist of nodes that contains the best route to follow given the restrictions of the african route.
     */
    private ArrayList<Node> dijkstraAfrican(Node startPoint, Node endPoint, boolean coconut) {
        Node startNode = graph.searchNodeById(startPoint.getNodeId().getId());
        Node endNode = graph.searchNodeById(endPoint.getNodeId().getId());

        List<KnownRoute> distances = new ArrayList<>();
        List<Node> visitedNodes = new ArrayList<>();
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(startNode);

        while (visitedNodes.size() != graph.getGraph().size() && !endPoint.isVisited() && !nodes.isEmpty()) {
            Node currentNode = nodes.poll();

            for (KnownRoute route : currentNode.getEdges()) {
                Node adjacentNode = findNodeById(currentNode, route);
                String adjacentNodeWeather = adjacentNode.getNodeId().getWeather();

                if (!visitedNodes.contains(adjacentNode)) {
                    if (adjacentNodeWeather.equals(TROPICAL) || adjacentNodeWeather.equals(CONTINENTAL)
                            && route.getTimeA() != SWALLOW_ERROR_TIME) {
                        float time = currentNode.getTime() + route.getTimeA();

                        if (coconut) {
                            if (route.getDistance() < 50) {
                                if (time < adjacentNode.getTime()) {
                                    adjacentNode.setTotalDistance(currentNode.getTotalDistance() + route.getDistance());
                                    adjacentNode.setTime(time);
                                    adjacentNode.setPreviousNode(currentNode);

                                    nodes.add(adjacentNode);
                                    visitedNodes.add(adjacentNode);
                                }
                            }
                        } else if (time < adjacentNode.getTime()){
                            adjacentNode.setTotalDistance(currentNode.getTotalDistance() + route.getDistance());
                            adjacentNode.setTime(time);
                            adjacentNode.setPreviousNode(currentNode);

                            nodes.add(adjacentNode);
                            visitedNodes.add(adjacentNode);
                        }
                    }
                }
            }
        }

        ArrayList<Node> cami = new ArrayList<>();
        Node actual = endNode;

        while (actual != null) {
            cami.add(actual);
            actual = actual.getPreviousNode();
        }

        Collections.reverse(cami);

        return cami;
    }

    private Node findNodeById(Node fromNode, KnownRoute edge) {
        int nodeId;
        // Check which node is the one to be found.
        if (fromNode.getNodeId().getId() == edge.getPointA()) {
            nodeId = edge.getPointB();
        } else {
            nodeId = edge.getPointA();
        }

        // Find the node from the original graph.
        for (int i = 0; i < graph.getGraph().size(); i++) {
            if (graph.getGraph().get(i).getNodeId().getId() == nodeId) {
                return graph.getGraph().get(i);
            }
        }
        return null;
    }

    /**
     * Method that initializes the nodes of the graph, the first one to 0 and the others to the max float value.
     *
     * @param startNode Initial node.
     */
    private void initNodes(Node startNode) {
        startNode = graph.searchNodeById(startNode.getNodeId().getId());
        startNode.setTime(0);
        startNode.setTotalDistance(0);
        startNode.setVisited(false);

        for (Node n: graph.getGraph()) {
            if(n.getNodeId().getId() != startNode.getNodeId().getId()) {
                n.setTime(Float.MAX_VALUE);
                n.setTotalDistance(0);
                n.setVisited(false);
                n.setPreviousNode(null);
            }
        }
    }

    /**
     * Method that checks if is African.
     *
     * @return True if is African.
     */
    public boolean isAfrican() {
        return isAfrican;
    }
}
