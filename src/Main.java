import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("\u001B[33mEnter the input file path: \u001B[0m");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();

        Graph graph = new Graph(path);
        graph.printSolution();
        int[][] parent = new int[graph.getV()][graph.getV()];
        int[][] cost = new int[graph.getV()][graph.getV()];;
        graph.Floyd_Warshall(cost,parent);

    }
}