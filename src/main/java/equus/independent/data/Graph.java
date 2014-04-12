package equus.independent.data;

import java.util.Collection;

public interface Graph<V, E> {

    boolean containsVertex(V vertex);

    Collection<E> getIncomingEdges(V vertex);

    Collection<E> getOutgoingEdges(V vertex);

    Pair<V> getEndpoints(E edge);

    V getOpposite(V vertex, E edge);

    int getVertexCount();
}
