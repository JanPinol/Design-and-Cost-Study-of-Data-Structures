package business.estructuraGraph.part2;

import business.estructuraGraph.entities.KnownRoute;
import business.estructuraGraph.graph.Graph;
import business.estructuraGraph.graph.Node;

import java.util.*;

/**
 * The HabitualRoutes class performs a MST Prim algorithm in order to solve
 * the statement of "habitual routes".
 */
public class HabitualRoutes {
    private final Graph graph;

    /**
     * Constructor for the HabitualRoutes class.
     *
     * @param graph Graph created from the data of the .paed files.
     */
    public HabitualRoutes(Graph graph) {
        this.graph = graph;
    }

    /**
     * Function that creates the visited nodes (nodes added to the MST graph)
     * list and calls the MST function to create the MST graph.
     *
     * @return The MST graph.
     */
    public Graph habitualRoutes() {
        List<Node> visitedNodes = new ArrayList<>();
        return habitualRoutesMST(visitedNodes);
    }

    /**
     * Function that implements the MST Prim algorithm.
     *
     * @param visitedNodes  The list of the visited nodes of the graph.
     *
     * @return The MST Prim graph.
     */
    private Graph habitualRoutesMST(List<Node> visitedNodes) {
        // Empty MST Graph.
        Graph mst = new Graph();
        // First node of the graph (could be random).
        Node initialNode = new Node(graph.getGraph().get(0));
        // Priority queue that will save the current edges from the current node.
        PriorityQueue<KnownRoute> edgesQueue = new PriorityQueue<>(Comparator.comparing(KnownRoute::getDistance));
        // Priority queue that will save all the edges from the original graph.
        PriorityQueue<KnownRoute> queue = new PriorityQueue<>(Comparator.comparing(KnownRoute::getDistance));
        // Candidate to be the best current edge.
        KnownRoute edge;

        // Add all the edges to the queue.
        edgesQueue.addAll(initialNode.getEdges());
        queue.addAll(initialNode.getEdges());

        // Remove all connections from the initial node (edges and connected nodes).
        initialNode.clearEdges();
        initialNode.clearConnectedNodes();
        initialNode.setPreviousNode(initialNode);
        // Add the node to the MST graph and the visited nodes list.
        mst.addNode(initialNode);
        visitedNodes.add(initialNode);

        // Initialize the "current" node to the initial node.
        Node fromNode = initialNode;

        // Loop that will save the nodes and best edges to the MST graph
        // till both graphs (original and MST) have the same amount of nodes.
        while (!graphSameNodes(mst)) {
            // Poll the edge of the current node from the priority queue.
            edge = edgesQueue.poll();
            Node toNode = new Node(findNodeById(fromNode, edge));

            // Evaluate the edge.
            // If the next node it is not visited: Set the node info and adds the node to the MST graph
            // Else if the node is visited and there are no more candidates for the next node: Add the remaining nodes
            // from the original graph with the best edges.
            if (!nodeVisited(visitedNodes, toNode)) {
                fromNode = addNodes(mst, fromNode, toNode, queue, edgesQueue, visitedNodes, edge);
            } else if (edgesQueue.isEmpty()) {
                addNodesLeft(mst, queue, visitedNodes);
            }
        }

        return mst;
    }

    /**
     * Function that adds the node with the best edge to the MST graph.
     *
     * @param mst           The MST Prim graph.
     * @param fromNode      The current node (last node added to the MST graph).
     * @param toNode        The next node to be added to the MST graph.
     * @param queue         The priority queue with all the edges from the original graph.
     * @param edgesQueue    The priority queue with the edges of the current node.
     * @param visitedNodes  The list of the nodes added to the MST graph.
     * @param edge          The edge to be added to the MST graph.
     *
     * @return New instance of the next node.
     */
    private Node addNodes(Graph mst, Node fromNode, Node toNode, PriorityQueue<KnownRoute> queue,
                          PriorityQueue<KnownRoute> edgesQueue, List<Node> visitedNodes, KnownRoute edge) {
        // Clear the priority queue with the best edges of the current node, add the edges of the next node to both
        // priority queue and remove the used edge from the queue that has all the edges of the original graph.
        edgesQueue.clear();
        edgesQueue.addAll(toNode.getEdges());
        queue.addAll(toNode.getEdges());
        queue.remove(edge);

        // Clear and set all node attributes, add the node to the MST graph and to the visited nodes list.
        toNode.clearEdges();
        toNode.clearConnectedNodes();
        fromNode.addConnectedNode(toNode);
        toNode.addConnectedNode(fromNode);
        toNode.setPreviousNode(fromNode);
        mst.addNode(toNode);
        mst.addEdgeMST(fromNode, new KnownRoute(edge));
        mst.addEdgeMST(toNode, new KnownRoute(edge));
        visitedNodes.add(toNode);

        return new Node(toNode);
    }

    /**
     * Method that adds the nodes left from the original graph following the criteria.
     *
     * @param mst           The MST Prim graph.
     * @param queue         The priority queue with all the edges from the original graph.
     * @param visitedNodes  The list of the nodes added to the MST graph.
     */
    private void addNodesLeft(Graph mst, PriorityQueue<KnownRoute> queue, List<Node> visitedNodes) {
        // Loop that will add all the nodes left from the original graph to the MST graph.
        while (!queue.isEmpty() || !graphSameNodes(mst)) {
            KnownRoute edge = queue.poll();
            Node nodeA = graph.searchNodeById(edge.getPointA());
            Node nodeB = graph.searchNodeById(edge.getPointB());

            // Check which is the valid node (next node), then add the node and edge to the MST graph
            // (one node is not visited).
            if (visitedNodesContains(visitedNodes, nodeA) && !visitedNodesContains(visitedNodes, nodeB)) {
                // Set which node is going to be added (nodeB) and which is already added (nodeA) to the MST graph.
                Node nodeAdded = getNodeInMST(nodeA, mst);
                Node nodeToAdd = new Node(nodeB);

                // Add all edges of the node to be added to the priority queue, remove the edge to be added from the
                // queue, clear and add all the necessary attributes of the node and, the node to the MST graph and to
                // the visited nodes list.
                queue.addAll(nodeToAdd.getEdges());
                queue.remove(edge);
                nodeAdded.addSingleEdge(edge);
                nodeAdded.addConnectedNode(nodeToAdd);
                nodeToAdd.clearEdges();
                nodeToAdd.clearConnectedNodes();
                nodeToAdd.addConnectedNode(nodeAdded);
                nodeToAdd.setPreviousNode(nodeAdded);
                mst.addNode(nodeToAdd);
                mst.addEdgeMST(nodeToAdd, new KnownRoute(edge));
                visitedNodes.add(nodeToAdd);
            } else if (!visitedNodesContains(visitedNodes, nodeA) && visitedNodesContains(visitedNodes, nodeB)) {
                // Set which node is going to be added (nodeA) and which is already added (nodeB) to the MST graph.
                Node nodeAdded = getNodeInMST(nodeB, mst);
                Node nodeToAdd = new Node(nodeA);

                // Add all edges of the node to be added to the priority queue, remove the edge to be added from the
                // queue, clear and add all the necessary attributes of the node, add the node to the MST graph and to
                // the visited nodes list.
                queue.addAll(nodeToAdd.getEdges());
                queue.remove(edge);
                nodeAdded.addSingleEdge(edge);
                nodeAdded.addConnectedNode(nodeToAdd);
                nodeToAdd.clearEdges();
                nodeToAdd.clearConnectedNodes();
                nodeToAdd.addConnectedNode(nodeAdded);
                nodeToAdd.setPreviousNode(nodeAdded);
                mst.addNode(nodeToAdd);
                mst.addEdgeMST(nodeToAdd, new KnownRoute(edge));
                visitedNodes.add(nodeToAdd);
            }
        }
    }

    /**
     * Function that checks if the visited nodes list contains a specific node.
     *
     * @param visitedNodes  The list of the nodes added to the MST graph.
     * @param node          The node to be checked.
     *
     * @return If it is contained in the visited nodes list.
     */
    private boolean visitedNodesContains(List<Node> visitedNodes, Node node) {
        for (Node visitedNode : visitedNodes) {
            if (visitedNode.getNodeId().getId() == node.getNodeId().getId()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Function that find the node specified in the MST graph.
     *
     * @param node  The node to be found in the MST graph.
     * @param mst   The MST graph.
     *
     * @return The node if it exists in the MST graph.
     */
    private Node getNodeInMST(Node node, Graph mst) {
        for (int i = 0; i < mst.getGraph().size(); i++) {
            if (node.getNodeId().getId() == mst.getGraph().get(i).getNodeId().getId()) {
                return mst.getGraph().get(i);
            }
        }
        return null;
    }

    /**
     * Function that checks if the visited nodes list contains a specified node.
     *
     * @param visitedNodes  The list of the nodes added to the MST graph.
     * @param toNode        The node to be checked.
     *
     * @return If the list contains the node.
     */
    private boolean nodeVisited(List<Node> visitedNodes, Node toNode) {
        for (Node visitedNode : visitedNodes) {
            if (visitedNode.getNodeId().getId() == toNode.getNodeId().getId()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Function that finds the next node from the best edge
     *
     * @param fromNode  The current node.
     * @param edge      The edge to find the next node
     *
     * @return The next node.
     */
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
     * Function that checks if both graphs have the same amount of nodes.
     *
     * @param mst   The MST graph.
     *
     * @return If both graphs have the same amount of nodes.
     */
    private boolean graphSameNodes(Graph mst) {
        return graph.getGraph().size() == mst.getGraph().size();
    }

}