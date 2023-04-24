import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;


public class Graph {
    final static int INF = Integer.MAX_VALUE;
    public int[][] FloydMatrix;
    public boolean file_path_error;
    private int V;
    private int E;
    private String path;
    private int[][] graph;
    private Vector<Edge> edges = new Vector<>(V);

    public Graph(String path) {

        this.path = path;
        readFile(path);
    }

    public int[][] getGraph() {
        return graph;
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

    public void print_path(int dest, int[] parent) {
        int i = dest;
        Stack<Integer> stack = new Stack<>();
        stack.push(i);
        while (parent[i] != -1) {
            stack.push(parent[i]);
            i = parent[i];
        }
        while (!stack.empty()) {
            if (stack.size() == 1) System.out.print("\u001B[34m" + stack.pop() + "\u001B[0m");
            else
                System.out.print("\u001B[34m" + stack.pop() + " -> " + "\u001B[0m");
        }
        System.out.println();
    }

    public void print_floyd_path(int source, int dest, int[][] predecessor, int[][] cost) {
        if (cost[source][dest] == Integer.MAX_VALUE) System.out.println("\u001B[34m" + "NO PATH" + "\u001B[0m");
        else {
            Stack<Integer> stack = new Stack<>();
            stack.push(dest);
            while (predecessor[source][dest] != -1) {
                stack.push(predecessor[source][dest]);
                dest = predecessor[source][dest];
            }
            while (!stack.empty()) {
                if (stack.size() == 1) System.out.print("\u001B[34m" + stack.pop() + "\u001B[0m");
                else System.out.print("\u001B[34m" + stack.pop() + " -> " + "\u001B[0m");
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
        for (int i = 0; i < parent.length; i++) parent[i] = -1;
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
            return false;
        }
        System.arraycopy(values, 0, cost, 0, V);
        return true;
    }

    public boolean Floyd_Warshall(int[][] cost, int[][] predecessors) {

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                cost[i][j] = FloydMatrix[i][j];
                if (i == j) {
                    predecessors[i][j] = -1;
                } else if (FloydMatrix[i][j] != INF) {
                    predecessors[i][j] = i;
                }
            }
        }

        // Floyd-Warshall algorithm
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if ((cost[i][k] != INF) && (cost[k][j] != INF) && (cost[i][k] + cost[k][j] < cost[i][j])) {
                        cost[i][j] = cost[i][k] + cost[k][j];
                        predecessors[i][j] = predecessors[k][j];
                    }
                }
            }
        }

        // detect negative cycle
        for (int i = 0; i < V; i++) {
            if (cost[i][i] < 0) {
                return false;
            }
        }
        return true;
    }
}
