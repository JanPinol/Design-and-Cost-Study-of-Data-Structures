package business.estructuraGraph.part1;

import business.estructuraGraph.entities.InterestPoint;
import business.estructuraGraph.graph.Graph;
import business.estructuraGraph.graph.Node;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that represents the RealmExploration function.
 * Searches all the realms of a input using a DFS.
 */
public class RealmExploration {
    private final Graph graph;

    /**
     * Constructs the realm exploration graph.
     *
     * @param graph The graph.
     */
    public RealmExploration(Graph graph) {
        this.graph = graph;
    }

    /**
     * Finds all the realms of a node using DFS.
     *
     * @param node The input node to find the realms.
     * @return The realms found.
     */
    public ArrayList<InterestPoint> realExplorationDFS(Node node) {
        ArrayList<InterestPoint> connectedToSameRealm = new ArrayList<>();
        ArrayList<InterestPoint> notConnectedToDistinctRealm = new ArrayList<>();

        DFS(node, connectedToSameRealm, notConnectedToDistinctRealm, node.getNodeId().getReign(), false);

        //Girem ja que els que hem afegit primerament tenen prioritat
        Collections.reverse(connectedToSameRealm);

        // add not prioritat to prioritat
        connectedToSameRealm.addAll(notConnectedToDistinctRealm);
        return connectedToSameRealm;
    }

    /**
     * Implementation of the DFS with the criteria of the realm exploration function.
     *
     * @param node          The node that has the realm nodes to be found.
     * @param prioritat     The priority nodes.
     * @param noPrioritat   The no-priority nodes.
     * @param realm         The realm of the current node.
     * @param heSortit      The indicative if the node belongs to the desired realm.
     */
    private void DFS(Node node, ArrayList<InterestPoint> prioritat, ArrayList<InterestPoint> noPrioritat, String realm,
                     boolean heSortit) {
        node.setVisited(true);
        //Per cada id de les arestes del node
        for (int id : node.getConnectedNodes()) {
            //Busquem el node al Graph mitjançant el seu id
            for (Node adjacent : graph.getGraph()) {
                // Si el id coincideix --> hem trobat el node
                if (id == adjacent.getNodeId().getId()) {
                    //Si no esta ja visitat
                    if (!adjacent.isVisited()) {
                        //Si el node trobat es troba al mateix regne que el node que ens introdueix l'usuari l'afegim
                        if(adjacent.getNodeId().getReign().equals(realm)) {
                            //Si no es troba ja a la llista
                            if(!prioritat.contains(adjacent.getNodeId()) && !noPrioritat.contains(adjacent.getNodeId()))
                            {
                                if(heSortit) {
                                    //L'afegim a la llista sense prioritat
                                    noPrioritat.add(adjacent.getNodeId());
                                } else{
                                    prioritat.add(adjacent.getNodeId());
                                }
                            }
                        } else if (!heSortit) {
                            heSortit = true;
                        }
                        //Busquem els adjacents del node trobat per seguir buscant
                        DFS(adjacent, prioritat, noPrioritat , realm, heSortit);
                    }
                }
            }
        }
    }
}
