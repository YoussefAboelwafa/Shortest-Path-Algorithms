import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        System.out.println("\u001B[33mEnter the input file path: \u001B[0m");
//        Scanner scanner = new Scanner(System.in);
//        //String path = scanner.nextLine();
//
//        Graph graph = new Graph("test1.txt");
//
//        int[][] predecessors = new int[graph.getV()][graph.getV()];
//        int[][] cost = new int[graph.getV()][graph.getV()];
//        System.out.println("floyd-warshall");
//        graph.Floyd_Warshall(cost, predecessors);
//
//
//        int [] parent1=new int[graph.getV()];
//        int [] cost1=new int[graph.getV()];
//        System.out.println("dijkstra");
//        graph.dijkstra(1,parent1,cost1);
//        System.out.println("bellman-ford");
//        graph.bellman_ford(1,parent1,cost1);
        CommandLine commandLine=new CommandLine();
        commandLine.UserInterface();
    }
}
