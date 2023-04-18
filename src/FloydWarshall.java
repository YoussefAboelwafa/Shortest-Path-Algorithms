public class FloydWarshall {

    public int[][] alg(int[][] graph) {
        int size = graph.length;
        int i, j, k;
        // k is the intermediate node
        for (k = 0; k < size; k++) {
            for (i = 0; i < size; i++) {
                for (j = 0; j < size; j++) {
                    if (graph[i][k] + graph[k][j] < graph[i][j]) {
                        graph[i][j] = graph[i][k] + graph[k][j];
                    }
                }
            }
        }
        return graph;
    }
}


