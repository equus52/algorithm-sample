package equus.independent.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class DijkstraShortestPathTest {

    @Test
    public void test_1() {
        UndirectedGraph<Integer, Integer> graph = new UndirectedGraph<>();
        int edgeId = 0;
        graph.addEdge(edgeId++, 1, 2);
        graph.addEdge(edgeId++, 2, 3);
        graph.addEdge(edgeId++, 5, 1);
        graph.addEdge(edgeId++, 6, 2);
        graph.addEdge(edgeId++, 6, 5);
        graph.addEdge(edgeId++, 9, 5);
        graph.addEdge(edgeId++, 12, 8);
        graph.addEdge(edgeId++, 13, 9);
        graph.addEdge(edgeId++, 14, 13);
        graph.addEdge(edgeId++, 16, 12);
        graph.addEdge(edgeId++, 17, 13);
        graph.addEdge(edgeId++, 18, 14);
        graph.addEdge(edgeId++, 18, 17);
        graph.addEdge(edgeId++, 19, 18);
        graph.addEdge(edgeId++, 20, 16);
        graph.addEdge(edgeId++, 20, 19);

        DijkstraShortestPath<Integer, Integer> dijkstra = new DijkstraShortestPath<>(graph);
        Double distance = dijkstra.getShortestDistance(2, 16);
        assertThat(distance, is(9.0));
    }

    @Test
    public void test_2() {
        UndirectedGraph<Integer, Integer> graph = new UndirectedGraph<>();
        int edgeId = 0;
        graph.addEdge(edgeId++, 1, 2);
        graph.addEdge(edgeId++, 2, 3);
        graph.addEdge(edgeId++, 5, 1);
        graph.addEdge(edgeId++, 6, 2);
        graph.addEdge(edgeId++, 6, 5);
        graph.addEdge(edgeId++, 12, 8);
        graph.addEdge(edgeId++, 14, 13);
        graph.addEdge(edgeId++, 16, 12);
        graph.addEdge(edgeId++, 17, 13);
        graph.addEdge(edgeId++, 18, 14);
        graph.addEdge(edgeId++, 18, 17);
        graph.addEdge(edgeId++, 19, 18);
        graph.addEdge(edgeId++, 20, 16);
        graph.addEdge(edgeId++, 20, 19);

        DijkstraShortestPath<Integer, Integer> dijkstra = new DijkstraShortestPath<>(graph);
        Double distance = dijkstra.getShortestDistance(2, 16);
        assertThat(distance, is((Double) null));
    }

    private static class DirectedEdgeBuilder<V> {

        final DirectedGraph<V, Integer> graph;

        int edgeId;

        DirectedEdgeBuilder(DirectedGraph<V, Integer> graph, int edgeId) {
            this.graph = graph;
            this.edgeId = edgeId;
        }

        void addDirectedEdge(V v1, V v2) {
            graph.addEdge(edgeId++, v1, v2);
        }

        void addUndirectedEdge(V v1, V v2) {
            graph.addEdge(edgeId++, v1, v2);
            graph.addEdge(edgeId++, v2, v1);
        }

    }

    @Test
    public void test_3() {
        DirectedGraph<Integer, Integer> graph = new DirectedGraph<>();
        int edgeId = 0;
        DirectedEdgeBuilder<Integer> builder = new DirectedEdgeBuilder<>(graph, edgeId);
        builder.addUndirectedEdge(1, 2);
        builder.addUndirectedEdge(2, 3);
        builder.addUndirectedEdge(5, 1);
        builder.addUndirectedEdge(6, 2);
        builder.addUndirectedEdge(6, 5);
        builder.addUndirectedEdge(9, 5);
        builder.addUndirectedEdge(12, 8);
        builder.addUndirectedEdge(13, 9);
        builder.addUndirectedEdge(14, 13);
        builder.addUndirectedEdge(16, 12);
        builder.addUndirectedEdge(17, 13);
        builder.addUndirectedEdge(18, 14);
        builder.addUndirectedEdge(18, 17);
        builder.addUndirectedEdge(19, 18);
        builder.addUndirectedEdge(20, 16);
        builder.addUndirectedEdge(20, 19);

        DijkstraShortestPath<Integer, Integer> dijkstra = new DijkstraShortestPath<>(graph);
        Double distance = dijkstra.getShortestDistance(2, 16);
        assertThat(distance, is(9.0));
    }

    @Test
    public void test_4() {
        DirectedGraph<Integer, Integer> graph = new DirectedGraph<>();
        int edgeId = 0;
        DirectedEdgeBuilder<Integer> builder = new DirectedEdgeBuilder<>(graph, edgeId);
        builder.addUndirectedEdge(1, 2);
        builder.addUndirectedEdge(2, 3);
        builder.addUndirectedEdge(5, 1);
        builder.addUndirectedEdge(6, 2);
        builder.addUndirectedEdge(6, 5);
        builder.addUndirectedEdge(12, 8);
        builder.addUndirectedEdge(14, 13);
        builder.addUndirectedEdge(16, 12);
        builder.addUndirectedEdge(17, 13);
        builder.addUndirectedEdge(18, 14);
        builder.addUndirectedEdge(18, 17);
        builder.addUndirectedEdge(19, 18);
        builder.addUndirectedEdge(20, 16);
        builder.addUndirectedEdge(20, 19);

        DijkstraShortestPath<Integer, Integer> dijkstra = new DijkstraShortestPath<>(graph);
        Double distance = dijkstra.getShortestDistance(2, 16);
        assertThat(distance, is((Double) null));
    }

    @Test
    public void test_5() {
        DirectedGraph<Integer, Integer> graph = new DirectedGraph<>();
        int edgeId = 0;
        DirectedEdgeBuilder<Integer> builder = new DirectedEdgeBuilder<>(graph, edgeId);
        builder.addUndirectedEdge(1, 2);
        builder.addUndirectedEdge(2, 3);
        builder.addUndirectedEdge(5, 1);
        builder.addUndirectedEdge(6, 2);
        builder.addUndirectedEdge(6, 5);
        builder.addUndirectedEdge(9, 5);
        builder.addUndirectedEdge(12, 8);
        builder.addUndirectedEdge(13, 9);
        builder.addUndirectedEdge(14, 13);
        builder.addUndirectedEdge(16, 12);
        builder.addUndirectedEdge(17, 13);
        builder.addUndirectedEdge(18, 14);
        builder.addUndirectedEdge(18, 17);
        builder.addDirectedEdge(19, 18);
        builder.addDirectedEdge(18, 21);
        builder.addDirectedEdge(21, 19);
        builder.addUndirectedEdge(20, 16);
        builder.addUndirectedEdge(20, 19);

        DijkstraShortestPath<Integer, Integer> dijkstra = new DijkstraShortestPath<>(graph);
        Double distance = dijkstra.getShortestDistance(2, 16);
        assertThat(distance, is(10.0));
    }
}
