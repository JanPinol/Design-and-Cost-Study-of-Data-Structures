package business.estructuraGraph.graph;

import business.estructuraGraph.entities.InterestPoint;
import business.estructuraGraph.entities.KnownRoute;

import java.util.ArrayList;

/**
 * Represents a Node in the Graph.
 */
public class Node implements Comparable<Node>, Cloneable{
    private InterestPoint nodeId;
    private ArrayList<Integer> connectedNodes = new ArrayList<>();
    private boolean visited = false;
    private float time;
    private float totalDistance;
    private Node previousNode;
    private ArrayList<KnownRoute> edges = new ArrayList<>();

    /**
     * Constructs a Node with only the ID and the connected nodes.
     * @param nodeId            The ID of the Node.
     * @param connectedNodes    The connected Nodes.
     */
    public Node(InterestPoint nodeId, ArrayList<Integer> connectedNodes) {
        this.nodeId = nodeId;
        this.connectedNodes = connectedNodes;
    }

    /**
     * Constructs a copy of a Node object.
     *
     * @param that The Node object to be copied.
     */
    public Node (Node that) {
        this.nodeId = that.nodeId;
        this.connectedNodes = that.connectedNodes;
        this.visited = that.isVisited();
        this.previousNode = that.previousNode;
        this.time = that.time;
        this.totalDistance = that.totalDistance;
        this.edges = that.edges;
    }

    /**
     * Constructs a new Node.
     */
    public Node(){}

    /**
     * Setter of the visited status of the node.
     *
     * @param visited The visited status.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Getter of the visited status of the node.
     *
     * @return The visited status.
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Getter of the ID of the node.
     *
     * @return The ID of the node.
     */
    public InterestPoint getNodeId() {
        return nodeId;
    }

    /**
     * Getter of the connected nodes.
     *
     * @return The connected nodes.
     */
    public ArrayList<Integer> getConnectedNodes() {
        return connectedNodes;
    }

    /**
     * Adds an edge to the node.
     *
     * @param edge The edge to be added.
     */
    public void addEdge(int edge) {
        this.connectedNodes.add(edge);
    }

    /**
     * Clears the edges of the node.
     */
    public void clearEdges() {
        edges.clear();
    }

    /**
     * Clears the connected nodes of the node.
     */
    public void clearConnectedNodes() { connectedNodes.clear(); }

    /**
     * Adds a connected node to the list.
     *
     * @param node The node to be added.
     */
    public void addConnectedNode(Node node) { this.connectedNodes.add(node.getNodeId().getId()); }

    /**
     * Adds a single edge to the node.
     *
     * @param newEdge the edge to be added.
     */
    public void addSingleEdge(KnownRoute newEdge) {
        this.edges.add(newEdge);
    }

    /**
     * Setter of the ID of the node.
     *
     * @param nodeId The ID of the node.
     */
    public void setNodeId(InterestPoint nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * Getter of the edges of the node.
     *
     * @return The edges of the node.
     */
    public ArrayList<KnownRoute> getEdges() {
        return edges;
    }

    /**
     * Setter of the edges of the node.
     *
     * @param edges The edges to be set.
     */
    public void setEdges(ArrayList<KnownRoute> edges) {
        this.edges = edges;
    }

    /**
     * Getter of the time.
     *
     * @return The time.
     */
    public float getTime() {
        return time;
    }

    /**
     * Setter of the time.
     *
     * @param time The time to be set.
     */
    public void setTime(float time) {
        this.time = time;
    }

    /**
     * Getter of the total distance.
     *
     * @return The total distance.
     */
    public float getTotalDistance() {
        return totalDistance;
    }

    /**
     * Setter of the total distance.
     *
     * @param totalDistance The total distance.
     */
    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }

    /**
     * Getter of the previous node.
     *
     * @return The previous node.
     */
    public Node getPreviousNode() {
        return previousNode;
    }

    /**
     * Setter of the previous node.
     *
     * @param previousNode The previous node to be set.
     */
    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    /**
     * Compares the time of two nodes.
     *
     * @param that The object to be compared.
     *
     * @return The difference of time.
     */
    @Override
    public int compareTo(Node that) {
        return Float.compare(time, that.time);
    }

}
