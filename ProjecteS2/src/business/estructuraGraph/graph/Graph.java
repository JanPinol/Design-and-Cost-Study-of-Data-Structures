package business.estructuraGraph.graph;

import business.estructuraGraph.entities.InterestPoint;
import business.estructuraGraph.entities.KnownRoute;

import java.util.ArrayList;

/**
 * Represents the Graph.
 */
public class Graph {
    private ArrayList<Node> graph = new ArrayList<>();

    /**
     * Creates the graph with specific nodes and edges.
     *
     * @param listInterestsPoints List of nodes to be added to the graph.
     * @param listKnownRoutes List of edges to be added to the graph.
     */
    public void createGraph(ArrayList<InterestPoint> listInterestsPoints, ArrayList<KnownRoute> listKnownRoutes) {
        //Creem un node per cada punt d'interès
        for (InterestPoint listInterestsPoint : listInterestsPoints) {
            //Creem un nou node
            Node newNode = new Node();
            //Creem una llista temporal per guardar les arestes que té aquell node
            ArrayList<KnownRoute> tempEdges = new ArrayList<>();
            //Afegim el punt d'interès al node que creem
            newNode.setNodeId(listInterestsPoint);
            //Per cada ruta coneguda
            for (KnownRoute listKnownRoute : listKnownRoutes) {
                //Si el punt d'interès que estem tractant és el punt A de la ruta coneguda
                if (listInterestsPoint.getId() == listKnownRoute.getPointA()) {
                    //Afegim l'aresta que connecta els dos punts d'interès
                    newNode.addEdge(listKnownRoute.getPointB());
                    //Afegim l'aresta a la llista temporal
                    tempEdges.add(listKnownRoute);
                }
                //Si el punt d'interès que estem tractant és el punt B de la ruta coneguda
                if (listInterestsPoint.getId() == listKnownRoute.getPointB()) {
                    //Afegim l'aresta que connecta els dos punts d'interès
                    newNode.addEdge(listKnownRoute.getPointA());
                    //Afegim l'aresta a la llista temporal
                    tempEdges.add(listKnownRoute);
                }
                //Creem l'aresta que connecta els dos punts d'interès
            }
            //Afegim totes les arestes que té aquell node al node que creem
            newNode.setEdges(tempEdges);
            //Afegim el node al graf
            graph.add(newNode);
        }
    }

    /**
     * Adds a node to the graph.
     *
     * @param newNode The node to add to the graph.
     */
    public void addNode(Node newNode) {
        graph.add(newNode);
    }

    /**
     * A specific method used in the MST function to add an edge.
     *
     * @param node The Node to be added.
     * @param edge The Edge to be added.
     */
    public void addEdgeMST(Node node, KnownRoute edge) {
        for (Node value : graph) {
            if (value.getNodeId().getId() == node.getNodeId().getId()) {
                value.addSingleEdge(edge);
            }
        }
    }

    /**
     * Searches for a Node with a specific ID.
     *
     * @param id The ID of the node to be found in the Graph.
     * @return If the Node was found, returns the Node. Otherwise null.
     */
    public Node searchNodeById(int id) {
        for (Node node : graph) {
            if (node.getNodeId().getId() == id) {
                return node;
            }
        }
        return null;
    }

    /**
     * Getter of the Graph.
     *
     * @return The graph.
     */
    public ArrayList<Node> getGraph() {
        return graph;
    }
}
