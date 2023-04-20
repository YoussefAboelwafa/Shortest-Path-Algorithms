import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("\u001B[33mEnter the input file path: \u001B[0m");
        Scanner scanner = new Scanner(System.in);
        //String path = scanner.nextLine();

        Graph graph = new Graph("test1.txt");
        graph.printMatrix(graph.getGraph());
        int[][] predecessors = new int[graph.getV()][graph.getV()];
        int[][] cost = new int[graph.getV()][graph.getV()];
        graph.Floyd_Warshall(cost, predecessors);

        graph.printMatrix(cost);
        graph.printMatrix(predecessors);


//        int [] parent1=new int[graph.getV()];
//        int [] cost1=new int[graph.getV()];
//        graph.dijkstra(0,parent1,cost1);
//        System.out.println();
//        graph.bellman_ford(0,parent1,cost1);
    }
}
