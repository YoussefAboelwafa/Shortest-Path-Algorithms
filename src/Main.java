import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        Demo d = new Demo();
        System.out.println("\u001B[33mEnter the input file path: \u001B[0m");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
//        int[][] graph = d.readFile(path);
//        d.printSolution(graph);
//        int[][] ans = d.solve(graph,3);
//        d.printSolution(ans);
        Graph graph=new Graph(path);
        graph.printSolution();
        int [] parent=new int[graph.getV()];
        int [] cost=new int[graph.getV()];
        graph.Floyd_Warshall();

    }
}