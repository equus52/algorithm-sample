package equus.independent.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("serial")
public class DirectedGraph<V, E> implements Graph<V, E>, Serializable {

    private final Map<V, Pair<Map<V, E>>> vertices; // Pair's first is incoming vertex and edge, second is outgoing.

    private final Map<E, Pair<V>> edges;

    public DirectedGraph() {
        vertices = new HashMap<>();
        edges = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<E> getIncomingEdges(V vertex) {
        if (!containsVertex(vertex)) {
            return Collections.EMPTY_LIST;
        }
        return vertices.get(vertex).getFirst().values();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<E> getOutgoingEdges(V vertex) {
        if (!containsVertex(vertex)) {
            return Collections.EMPTY_LIST;
        }
        return vertices.get(vertex).getSecond().values();
    }

    @SuppressWarnings("unchecked")
    public Collection<V> getPredecessors(V vertex) {
        if (!containsVertex(vertex)) {
            return Collections.EMPTY_LIST;
        }
        return vertices.get(vertex).getFirst().keySet();
    }

    @SuppressWarnings("unchecked")
    public Collection<V> getSuccessors(V vertex) {
        if (!containsVertex(vertex)) {
            return Collections.EMPTY_LIST;
        }
        return vertices.get(vertex).getSecond().keySet();
    }

    @SuppressWarnings("unchecked")
    public Collection<E> getIncidentEdges(V vertex) {
        if (!containsVertex(vertex)) {
            return Collections.EMPTY_LIST;
        }

        Collection<E> incidentEdges = new HashSet<E>();
        incidentEdges.addAll(getIncomingEdges(vertex));
        incidentEdges.addAll(getOutgoingEdges(vertex));
        return incidentEdges;
    }

    @SuppressWarnings("unchecked")
    public Collection<V> getNeighbors(V vertex) {
        if (!containsVertex(vertex)) {
            return Collections.EMPTY_LIST;
        }
        Collection<V> neighbors = new HashSet<V>();
        neighbors.addAll(getPredecessors(vertex));
        neighbors.addAll(getSuccessors(vertex));
        return neighbors;
    }

    @Override
    public Pair<V> getEndpoints(E edge) {
        return edges.get(edge);
    }

    @Override
    public V getOpposite(V vertex, E edge) {
        Pair<V> incident = getEndpoints(edge);
        V first = incident.getFirst();
        V second = incident.getSecond();
        if (vertex.equals(first)) {
            return second;
        } else if (vertex.equals(second)) {
            return first;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean containsVertex(V vertex) {
        return vertices.containsKey(vertex);
    }

    public boolean containsEdge(E edge) {
        return edges.containsKey(edge);
    }

    private E findEdge(V source, V distination) {
        if (!containsVertex(source) || !containsVertex(distination)) {
            return null;
        }
        return vertices.get(source).getSecond().get(distination);
    }

    public boolean addEdge(E edge, V source, V distination) {
        Objects.requireNonNull(edge);
        Objects.requireNonNull(source);
        Objects.requireNonNull(distination);

        Pair<V> newEndpoints = new Pair<V>(source, distination);

        if (containsEdge(edge)) {
            Pair<V> existingEndpoints = getEndpoints(edge);
            if (!existingEndpoints.equals(newEndpoints)) {
                throw new IllegalArgumentException();
            } else {
                return false;
            }
        }
        if (findEdge(source, distination) != null) {
            return false;
        }

        edges.put(edge, newEndpoints);
        addVertex(source);
        addVertex(distination);
        vertices.get(source).getSecond().put(distination, edge);
        vertices.get(distination).getFirst().put(source, edge);
        return true;
    }

    private boolean addVertex(V vertex) {
        if (!containsVertex(vertex)) {
            vertices.put(vertex, new Pair<Map<V, E>>(new HashMap<V, E>(), new HashMap<V, E>()));
            return true;
        } else {
            return false;
        }
    }

    public boolean removeVertex(V vertex) {
        if (!containsVertex(vertex)) {
            return false;
        }
        for (E edge : getIncidentEdges(vertex)) {
            removeEdge(edge);
        }
        vertices.remove(vertex);
        return true;
    }

    public boolean removeEdge(E edge) {
        if (!containsEdge(edge)) {
            return false;
        }
        Pair<V> endpoints = getEndpoints(edge);
        V source = endpoints.getFirst();
        V distination = endpoints.getSecond();

        vertices.get(source).getSecond().remove(distination);
        vertices.get(distination).getFirst().remove(source);

        edges.remove(edge);
        return true;
    }

    @Override
    public int getVertexCount() {
        return vertices.size();
    }
}
