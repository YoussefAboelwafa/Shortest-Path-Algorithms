import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Demo {
    final static int INF = Integer.MAX_VALUE;
    public int v;
    public int e;
    int[][] adjMatrix;

    public int[][] readFile(String path) {
        try {
            int n1;
            int n2;
            int n3;
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            v = scanner.nextInt();
            e = scanner.nextInt();
            adjMatrix = new int[v][v];
            //fill matrix with INF
            for (int i = 0; i < v; i++) {
                for (int j = 0; j < v; j++) {
                    if (i == j) {
                        adjMatrix[i][j] = 0;
                    } else {
                        adjMatrix[i][j] = INF;
                    }
                }
            }
            while (scanner.hasNextLine()) {
                n1 = scanner.nextInt();
                n2 = scanner.nextInt();
                n3 = scanner.nextInt();
                adjMatrix[n1 - 1][n2 - 1] = n3;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("\u001B[31mERROR opening the file\u001B[0m");
        }
        return adjMatrix;
    }

    public void printSolution(int[][] matrix) {
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                if (matrix[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.print(matrix[i][j] + "   ");
            }
            System.out.println();
        }
    }

    //todo
    FloydWarshall f = new FloydWarshall();

    public int[][] solve(int[][] graph, int algorithm) {
        // 1 = Dijkstra
        // 2 = Bellman-Ford
        // 3 = Floyd-Warshall
        int[][] ans;
        if (algorithm == 1) {
            ans = f.alg(graph);
        } else if (algorithm == 2) {
            ans = f.alg(graph);
        } else {
            ans = f.alg(graph);
        }
       return ans;
    }
}
