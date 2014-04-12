package equus.independent.data;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import equus.independent.util.Function;

public class DijkstraShortestPath<V, E> {

    private final Graph<V, E> graph;

    private final Function<E, ? extends Number> weightProvider;

    private final Map<V, SourcePathData> sourceMap;

    private final double maxDistance;

    private final int maxTargets;

    private static final double DEFAULT_MAX_DISTANCE = Double.POSITIVE_INFINITY;

    private static final int DEFAULT_MAX_TARGETS = Integer.MAX_VALUE;

    private static <E> Function<E, ? extends Number> getNoWeightProvider() {
        return new Function<E, Integer>() {

            @Override
            public Integer apply(E input) {
                return 1;
            }
        };
    }

    public DijkstraShortestPath(Graph<V, E> graph, Function<E, ? extends Number> weightProvider, double maxDistance,
                    int maxTargets) {
        this.graph = graph;
        this.weightProvider = weightProvider;
        this.maxDistance = maxDistance;
        this.maxTargets = maxTargets;
        this.sourceMap = new HashMap<V, SourcePathData>();
    }

    public DijkstraShortestPath(Graph<V, E> graph) {
        this(graph, DijkstraShortestPath.<E> getNoWeightProvider(), DEFAULT_MAX_DISTANCE, DEFAULT_MAX_TARGETS);
    }

    public DijkstraShortestPath(Graph<V, E> graph, Function<E, ? extends Number> weightProvider) {
        this(graph, weightProvider, DEFAULT_MAX_DISTANCE, DEFAULT_MAX_TARGETS);
    }

    public DijkstraShortestPath(Graph<V, E> graph, Function<E, ? extends Number> weightProvider, double maxTargets) {
        this(graph, weightProvider, maxTargets, DEFAULT_MAX_TARGETS);
    }

    public DijkstraShortestPath(Graph<V, E> graph, Function<E, ? extends Number> weightProvider, int maxTargets) {
        this(graph, weightProvider, DEFAULT_MAX_DISTANCE, maxTargets);
    }

    public Double getShortestDistance(V source, V target) {
        if (!graph.containsVertex(source)) {
            throw new IllegalArgumentException();
        }
        if (!graph.containsVertex(target)) {
            throw new IllegalArgumentException();
        }

        calculateShortestPath(source, target);
        return getSourcePathData(source).getDistance(target);
    }

    public List<E> getShortestPath(V source, V target) {
        if (!graph.containsVertex(source)) {
            throw new IllegalArgumentException();
        }
        if (!graph.containsVertex(target)) {
            throw new IllegalArgumentException();
        }

        calculateShortestPath(source, target);
        return getSourcePathData(source).getPath(target);
    }

    public void calculateShortestPath(V source, V target) {
        Set<V> targets = new HashSet<V>();
        targets.add(target);
        calculateShortestPath(source, targets);
    }

    public void calculateShortestPath(V source, Collection<V> targets) {
        SourcePathData data = getSourcePathData(source);
        if (data.isCalculatedAll()) {
            return;
        }

        Set<V> toGetVerteces = new HashSet<V>();
        if (targets != null) {
            Set<V> calculatedVerteces = data.calculatedDistances.keySet();
            for (V target : targets) {
                if (!calculatedVerteces.contains(target)) {
                    toGetVerteces.add(target);
                }
            }
        }

        while (!data.unknownVerticesPriorityQueue.isEmpty() && !data.isCalculatedAll() && !toGetVerteces.isEmpty()) {
            Tuple<V, Number> next = data.getNextVertex();
            if (next == null) {
                break;
            }
            V fixedVertex = next.getValue1();
            double fixedVertexDistance = next.getValue2().doubleValue();
            toGetVerteces.remove(fixedVertex);
            if (data.isCalculatedAll()) {
                break;
            }

            for (E toCheckEdge : graph.getOutgoingEdges(fixedVertex)) {
                for (V toCheckVertex : graph.getEndpoints(toCheckEdge).getCollection()) {
                    if (data.isFixed(toCheckVertex)) {
                        continue;
                    }
                    double edgeWeight = weightProvider.apply(toCheckEdge).doubleValue();
                    if (edgeWeight < 0) {
                        throw new IllegalArgumentException("Edges weights must be non-negative");
                    }
                    double newDistance = fixedVertexDistance + edgeWeight;
                    if (!data.estimatedDistances.containsKey(toCheckVertex)) {
                        data.createRecord(toCheckVertex, toCheckEdge, newDistance);
                    } else {
                        double estimatedDistance = data.estimatedDistances.get(toCheckVertex).doubleValue();
                        if (newDistance < estimatedDistance) {
                            data.update(toCheckVertex, toCheckEdge, newDistance);
                        }
                    }

                }
            }
        }
    }

    public SourcePathData getSourcePathData(V source) {
        SourcePathData data = sourceMap.get(source);
        if (data == null) {
            data = new SourcePathData(source);
            sourceMap.put(data.getSource(), data);
        }
        return data;
    }

    private class SourcePathData {

        final V source;

        final LinkedHashMap<V, Double> calculatedDistances;

        final Map<V, Double> estimatedDistances;

        final Map<V, E> tentativeIncomingEdges;

        final LinkedHashMap<V, E> incomingEdges;

        final PriorityQueue<V> unknownVerticesPriorityQueue;

        double calculatedLongestDistance = 0;

        SourcePathData(V source) {
            this.source = source;
            this.calculatedDistances = new LinkedHashMap<V, Double>();
            this.incomingEdges = new LinkedHashMap<V, E>();
            this.estimatedDistances = new HashMap<V, Double>();
            this.tentativeIncomingEdges = new HashMap<V, E>();
            this.unknownVerticesPriorityQueue = new PriorityQueue<V>(graph.getVertexCount(), new VertexComparator(
                            estimatedDistances));
            initialize();
        }

        V getSource() {
            return source;
        }

        void initialize() {
            estimatedDistances.put(source, new Double(0));
            unknownVerticesPriorityQueue.add(source);
        }

        boolean isCalculatedAll() {
            return calculatedDistances.size() >= graph.getVertexCount() //
                            || calculatedDistances.size() >= maxTargets //
                            || isReachedMaxDistance();
        }

        Tuple<V, Number> getNextVertex() {
            V fixedVertex = unknownVerticesPriorityQueue.remove();
            Double fixedVertexDistance = estimatedDistances.remove(fixedVertex);
            calculatedLongestDistance = fixedVertexDistance;
            if (isReachedMaxDistance()) {
                restoreVertex(fixedVertex, fixedVertexDistance);
                return null;
            }
            calculatedDistances.put(fixedVertex, fixedVertexDistance);
            E incomingEdge = tentativeIncomingEdges.remove(fixedVertex);
            incomingEdges.put(fixedVertex, incomingEdge);
            return new Tuple<V, Number>(fixedVertex, fixedVertexDistance);
        }

        private boolean isReachedMaxDistance() {
            return calculatedLongestDistance > maxDistance;
        }

        boolean isFixed(V vertex) {
            return calculatedDistances.containsKey(vertex);
        }

        void update(V vertex, E edge, double newDistance) {
            estimatedDistances.put(vertex, newDistance);
            unknownVerticesPriorityQueue.remove(vertex);
            unknownVerticesPriorityQueue.add(vertex);
            tentativeIncomingEdges.put(vertex, edge);
        }

        void createRecord(V vertex, E edge, double newDistance) {
            estimatedDistances.put(vertex, newDistance);
            unknownVerticesPriorityQueue.add(vertex);
            tentativeIncomingEdges.put(vertex, edge);
        }

        void restoreVertex(V vertex, double distance) {
            estimatedDistances.put(vertex, distance);
            unknownVerticesPriorityQueue.add(vertex);
            calculatedDistances.remove(vertex);
            E incoming = incomingEdges.get(vertex);
            if (incoming != null) {
                tentativeIncomingEdges.put(vertex, incoming);
            }
        }

        LinkedList<E> getPath(V target) {
            LinkedList<E> path = new LinkedList<E>();
            if (incomingEdges.isEmpty() || incomingEdges.get(target) == null) {
                return path;
            }
            V current = target;
            while (!current.equals(source)) {
                E incoming = incomingEdges.get(current);
                path.addFirst(incoming);
                current = graph.getOpposite(current, incoming);
            }
            return path;
        }

        Double getDistance(V target) {
            return calculatedDistances.get(target);
        }

        private class VertexComparator implements Comparator<V> {

            private final Map<V, Double> distances;

            protected VertexComparator(Map<V, Double> distances) {
                this.distances = distances;
            }

            @Override
            public int compare(V o1, V o2) {
                return distances.get(o1).compareTo(distances.get(o2));
            }
        }
    }
}
