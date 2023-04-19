import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;


public class Graph {
    final static int INF = Integer.MAX_VALUE;
    private int V;
    private int E;
    private String path;
    private int[][] graph;
    public int[][] FloydMatrix;

    public int[][] getGraph() {
        return graph;
    }

    private Vector<Edge> edges = new Vector<>(V);

    public Graph(String path) {

        this.path = path;
        readFile(path);
    }

    public int getV() {
        return V;
    }

    public void readFile(String path) {
        try {
            int n1;
            int n2;
            int n3;
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            V = scanner.nextInt();
            E = scanner.nextInt();

            //fill graph for dijkstra & bellman-ford with zeros
            graph = new int[V][V];
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    graph[i][j] = 0;
                }
            }

            //fill floydMatrix with INF & zeros
            FloydMatrix = new int[V][V];
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (i == j) {
                        FloydMatrix[i][j] = 0;
                    } else {
                        FloydMatrix[i][j] = INF;
                    }
                }
            }
            while (scanner.hasNextLine()) {

                n1 = scanner.nextInt();
                n2 = scanner.nextInt();
                n3 = scanner.nextInt();
                Edge edge = new Edge();
                edge.setSrc(n1);
                edge.setDest(n2);
                edge.setWeight(n3);
                edges.add(edge);
                graph[n1][n2] = n3;
                FloydMatrix[n1][n2] = n3;
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("\u001B[31mERROR opening the file\u001B[0m");
        }
    }

    public void printSolution() {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(graph[i][j] + "   ");
            }
            System.out.println();
        }
    }

    public int selectMinVertex(int[] cost, boolean[] processed) {
        int minimum = Integer.MAX_VALUE;
        int vertex = 0;
        for (int i = 0; i < V; ++i) {
            if (processed[i] == false && cost[i] < minimum) {
                vertex = i;
                minimum = cost[i];
            }
        }
        return vertex;
    }

    public void dijkstra(int source, int[] parent, int[] cost) {
        for (int i = 0; i < cost.length; i++) cost[i] = Integer.MAX_VALUE;
        boolean[] processed = new boolean[V];
        parent[source] = -1;
        cost[source] = 0;
        for (int i = 0; i < V - 1; i++) {
            int U = selectMinVertex(cost, processed);
            processed[U] = true;
            for (int j = 0; j < V; j++) {
                if (graph[U][j] != 0 && processed[j] == false && cost[U] != Integer.MAX_VALUE
                        && (cost[U] + graph[U][j] < cost[j])) {
                    cost[j] = cost[U] + graph[U][j];
                    parent[j] = U;
                }
            }

        }
        for (int i = 0; i < V; i++) {
            System.out.println(i + "->" + cost[i]);
        }
    }

    public Boolean bellman_ford(int source, int[] parent, int[] cost) {
        int[] values = new int[V];
        for (int i = 0; i < V; i++) {
            values[i] = Integer.MAX_VALUE;
        }
        parent[source] = -1;
        values[source] = 0;
        boolean updated = false;
        boolean found = false;
        for (int i = 0; i < V - 1; i++) {
            updated = false;
            for (int j = 0; j < E; j++) {
                int u = edges.get(j).getSrc();
                int v = edges.get(j).getDest();
                int w = edges.get(j).getWeight();
                if (values[u] != Integer.MAX_VALUE && values[u] + w < values[v]) {
                    values[v] = values[u] + w;
                    parent[v] = u;
                    cost[v] = values[v];
                    updated = true;
                }
            }
            if (updated == false) break;

        }
        for (int j = 0; j < E && updated == true; j++) {
            int u = edges.get(j).getSrc();
            int v = edges.get(j).getDest();
            int w = edges.get(j).getWeight();
            if (values[u] != Integer.MAX_VALUE && values[u] + w < values[v]) {
                found = true;
            }
        }

        if (found) {
            System.out.println("\u001B[31mnegative cycle found\u001B[0m");
            return false;
        }
        for (int i = 0; i < V; i++) {
            System.out.println(i + "-> " + cost[i]);
        }
        return true;
    }

    public boolean Floyd_Warshall(int[][] cost, int[][] predecessors) {
        int[][] checker = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                cost[i][j] = FloydMatrix[i][j];
                checker[i][j] = FloydMatrix[i][j];
                predecessors[i][j] = i;
            }
        }
        // Floyd-Warshall algorithm
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (checker[i][k] != INF && checker[k][j] != INF && cost[i][k] + cost[k][j] < cost[i][j]) {
                        cost[i][j] = cost[i][k] + cost[k][j];
                        checker[i][j] = checker[k][j];
                        predecessors[i][j] = k;
                    }
                }
            }
        }

        // detect negative cycle
        for (int i = 0; i < V; i++) {
            if (cost[i][i] < 0) {
                System.out.println("\u001B[31mnegative cycle found\u001B[0m");
                return false;
            }
        }

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {

                if (cost[i][j] == INF) {
                    predecessors[i][j] = -1;
                }
                if (checker[i][j] == INF) {
                    System.out.println("from " + i + " to " + j + "-> NO EDGE");
                } else {
                    System.out.println("from " + i + " to " + j + "-> " + cost[i][j]);
                }
            }
            System.out.println();
        }

        return true;
    }
}
