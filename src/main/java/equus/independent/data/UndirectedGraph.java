package equus.independent.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("serial")
public class UndirectedGraph<V, E> implements Graph<V, E>, Serializable {

    private final Map<V, Map<V, E>> vertices;

    private final Map<E, Pair<V>> edges;

    public UndirectedGraph() {
        vertices = new HashMap<V, Map<V, E>>();
        edges = new HashMap<E, Pair<V>>();
    }

    @SuppressWarnings("unchecked")
    public Collection<E> getIncidentEdges(V vertex) {
        if (!containsVertex(vertex)) {
            return Collections.EMPTY_LIST;
        }
        return Collections.unmodifiableCollection(vertices.get(vertex).values());
    }

    @Override
    public Collection<E> getIncomingEdges(V vertex) {
        return getIncidentEdges(vertex);
    }

    @Override
    public Collection<E> getOutgoingEdges(V vertex) {
        return getIncidentEdges(vertex);
    }

    @SuppressWarnings("unchecked")
    public Collection<V> getNeighbors(V vertex) {
        if (!containsVertex(vertex)) {
            return Collections.EMPTY_LIST;
        }
        return vertices.get(vertex).keySet();
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

    private E findEdge(V v1, V v2) {
        if (!containsVertex(v1) || !containsVertex(v2)) {
            return null;
        }
        return vertices.get(v1).get(v2);
    }

    public boolean addEdge(E edge, V vertex1, V vertex2) {
        Objects.requireNonNull(edge);
        Objects.requireNonNull(vertex1);
        Objects.requireNonNull(vertex2);

        Pair<V> newEndpoints = new Pair<V>(vertex1, vertex2);

        if (containsEdge(edge)) {
            Pair<V> existingEndpoints = getEndpoints(edge);
            if (!existingEndpoints.equals(newEndpoints)) {
                throw new IllegalArgumentException();
            } else {
                return false;
            }
        }
        if (findEdge(vertex1, vertex2) != null) {
            return false;
        }

        edges.put(edge, newEndpoints);
        addVertex(vertex1);
        addVertex(vertex2);
        vertices.get(vertex1).put(vertex2, edge);
        vertices.get(vertex2).put(vertex1, edge);
        return true;
    }

    private boolean addVertex(V vertex) {
        if (!containsVertex(vertex)) {
            vertices.put(vertex, new HashMap<V, E>());
            return true;
        } else {
            return false;
        }
    }

    public boolean removeVertex(V vertex) {
        if (!containsVertex(vertex)) {
            return false;
        }
        for (E edge : vertices.get(vertex).values()) {
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
        V v1 = endpoints.getFirst();
        V v2 = endpoints.getSecond();

        vertices.get(v1).remove(v2);
        vertices.get(v2).remove(v1);

        edges.remove(edge);
        return true;
    }

    @Override
    public int getVertexCount() {
        return vertices.size();
    }
}
