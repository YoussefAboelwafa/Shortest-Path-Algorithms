import java.util.Scanner;

public class CommandLine {
    Scanner scanner = new Scanner(System.in);
    Graph graph;
    int[][] predecessors;
    int[][] cost;
    int[] parent;
    int[] cost2;

    public void UserInterface() {

        while (true) {
            InputFile();
            while (true) {
                int Menu = InputMainMenu();
                switch (Menu) {
                    case 1:
                        Menu1();
                    case 2:
                        Menu2();
                    case 3:
                        Menu3();
                }
                if (Menu == 4) {
                    break;
                }

            }


        }
    }

    public int InputMainMenu() {
        System.out.println("1.Find the shortest paths from source node to all other nodes");
        System.out.println("2.Find the shortest paths between all the pairs of nodes");
        System.out.println("3.Check if the graph contains a negative cycle");
        System.out.println("4.Input Another File");
        String option = scanner.nextLine();
        while (!(option.equals("1") || option.equals("2") || option.equals("3") || option.equals("4"))) {
            System.out.println("\u001B[31mINVALID INPUT\u001B[33m");
            option = scanner.nextLine();
        }
        return Integer.valueOf(option);
    }

    public void Menu1() {
        while (true) {
            int source_node = InputNode("Source");
            int algorithm;
            while (true) {
                algorithm = InputAlgorithm();
                //call algorithm function
                switch (algorithm) {
                    case 1:
                        graph.dijkstra(source_node, parent, cost2);
                    case 2:
                        graph.bellman_ford(source_node, parent, cost2);
                    case 3:
                        graph.Floyd_Warshall(cost, predecessors);
                }
                if (algorithm == 4) {
                    break;
                }
                int destination_node = InputNode("Destination");
                while (true) {
                    int PathCost = InputPathCost();
                    switch (PathCost) {
                        //path length
                        case 1:
                            if (algorithm == 3) {

                                if (cost[source_node][destination_node] == Integer.MAX_VALUE)
                                    System.out.println("\u001B[34m" + "Shortest path from " + source_node + " to " + destination_node + " = " + "NO PATH" + "\u001B[0m");
                                else
                                    System.out.println("\u001B[34m" + "Shortest path from " + source_node + " to " + destination_node + " = " + cost[source_node][destination_node] + "\u001B[0m");
                            } else {

                                if (cost2[destination_node] == Integer.MAX_VALUE)
                                    System.out.println("\u001B[34m" + "Shortest path from " + source_node + " to " + destination_node + " = " + "NO PATH" + "\u001B[0m");
                                else
                                    System.out.println("\u001B[34m" + "Shortest path from " + source_node + " to " + destination_node + " = " + cost2[destination_node] + "\u001B[0m");
                            }
                            break;
                        //path
                        case 2:
                            if (algorithm == 3) {
                                //print path for floyd warshall
                                graph.print_floyd_path(source_node, destination_node, predecessors, cost);
                            } else {
                                graph.print_path(destination_node, parent);
                            }
                            break;
                    }
                    if (PathCost == 3) {
                        break;
                    }
                }
            }
            break;
        }
    }

    public void Menu2() {
        while (true) {

            int algorithm = InputAlgorithm();
            //call algorithm function
            switch (algorithm) {
                case 1:
                    for (int i = 0; i < graph.getV(); i++) {
                        graph.dijkstra(i, parent, cost2);
                        predecessors[i] = parent;
                        cost[i] = cost2;
                        parent = new int[graph.getV()];
                        cost2 = new int[graph.getV()];
                    }
                    break;
                case 2:
                    for (int i = 0; i < graph.getV(); i++) {
                        graph.bellman_ford(i, parent, cost2);
                        predecessors[i] = parent;
                        cost[i] = cost2;
                        parent = new int[graph.getV()];
                        cost2 = new int[graph.getV()];
                    }
                    break;
                case 3:
                    graph.Floyd_Warshall(cost, predecessors);
                    break;
            }
            if (algorithm == 4) {
                break;
            }
            while (true) {
                int source_node = InputNode("Source");
                int destination_node = InputNode("Destination");
                int PathCost = InputPathCost();
                switch (PathCost) {
                    case 1:
                        if (cost[source_node][destination_node] == Integer.MAX_VALUE)
                            System.out.println("\u001B[34m" + "Shortest path from " + source_node + " to " + destination_node + " = " + "NO PATH" + "\u001B[0m");
                        else
                            System.out.println("\u001B[34m" + "Shortest path from " + source_node + " to " + destination_node + " = " + cost[source_node][destination_node] + "\u001B[0m");
                        break;
                    case 2:
                        graph.print_floyd_path(source_node, destination_node, predecessors, cost);
                        break;
                }
                if (PathCost == 3) {
                    break;
                }

            }

        }
    }

    public void Menu3() {
        while (true) {
            int algorithm = InputAlgorithm2();
            //call algorithm function
            boolean neg_cycle;
            switch (algorithm) {
                case 1: //bellman
                    neg_cycle = graph.bellman_ford(0, parent, cost2);
                    if (!neg_cycle) {
                        System.out.println("\u001B[31mNegative cycle found\u001B[0m");
                    } else {
                        System.out.println("\u001B[32mNo negative cycle found\u001B[0m");
                    }
                    break;
                case 2: //WARSHALL
                    neg_cycle = graph.Floyd_Warshall(cost, predecessors);
                    if (!neg_cycle) {
                        System.out.println("\u001B[31mNegative cycle found\u001B[0m");
                    } else {
                        System.out.println("\u001B[32mNo negative cycle found\u001B[0m");
                    }
                    break;
            }
            if (algorithm == 3) {
                break;
            }
        }


    }

    public void InputFile() {
        System.out.println("\u001B[33mEnter the input file path: \u001B[0m");

        String path = scanner.nextLine();

        graph = new Graph(path);
        while (graph.file_path_error) {
            System.out.println("\u001B[31mERROR opening the file\u001B[0m");
            System.out.println("\u001B[33mEnter the input file path: \u001B[0m");
            path = scanner.nextLine();
            graph = new Graph(path);
        }
        predecessors = new int[graph.getV()][graph.getV()];
        cost = new int[graph.getV()][graph.getV()];
        parent = new int[graph.getV()];
        cost2 = new int[graph.getV()];
        System.out.println("\u001B[32mGraph successfully created ✅\n\u001B[0m");


    }

    public int InputAlgorithm() {
        System.out.println("\u001B[33mCHOOSE AN ALGORITHM :\u001B[0m");
        System.out.println("1.Dijkstra");
        System.out.println("2.Bellman-Ford");
        System.out.println("3.Floyd-Warshall");
        System.out.println("4.Return To Previous Menu");
        String option = scanner.nextLine();
        while (!(option.equals("1") || option.equals("2") || option.equals("3") || option.equals("4"))) {
            System.out.println("\u001B[31mINVALID INPUT\u001B[0m");
            option = scanner.nextLine();
        }
        return Integer.valueOf(option);

    }

    public int InputPathCost() {
        System.out.println("1.Get Path length");
        System.out.println("2.Get Path");
        System.out.println("3.Choose Another Algorithm");
        String option = scanner.nextLine();
        while (!(option.equals("1") || option.equals("2") || option.equals("3"))) {
            System.out.println("\u001B[31mINVALID INPUT\u001B[0m");
            option = scanner.nextLine();
        }
        return Integer.valueOf(option);
    }

    public int InputNode(String type) {
        System.out.println("\u001B[33m" + "Enter " + type + " node " + "\u001B[0m");
        String node = scanner.nextLine();
        while (!isValidInput(node)) {
            System.out.println("\u001B[31mINVALID INPUT\u001B[0m");
            node = scanner.nextLine();
        }
        return Integer.valueOf(node);
    }

    public boolean isValidInput(String input) {
        try {
            Integer.valueOf(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int InputAlgorithm2() {
        System.out.println("\u001B[33mCHOOSE AN ALGORITHM :\u001B[0m");

        System.out.println("1.Bellman-Ford");
        System.out.println("2.Floyd-Warshall");
        System.out.println("3.Return To Previous Menu");
        String option = scanner.nextLine();
        while (!(option.equals("1") || option.equals("2") || option.equals("3"))) {
            System.out.println("\u001B[31mINVALID INPUT\u001B[0m");
            option = scanner.nextLine();
        }
        return Integer.valueOf(option);

    }
}
