import java.util.Scanner;

public class CommandLine {
    Scanner scanner = new Scanner(System.in);
    Graph graph;
    int[][] predecessors;
    int[][] cost;
    public  void UserInterface() {

        while (true) {
           InputFile();
           while (true) {
               int Menu = InputMainMenu();
               switch (Menu){
                   case 1: Menu1();
                   case 2:Menu2();
                   case 3:Menu3();
               }
               if(Menu==4){
                   break;
               }

           }


        }
    }


    public int InputMainMenu(){
        System.out.println("1.Find the shortest paths from source node to all other nodes");
        System.out.println("2.Find the shortest paths between all the pairs of nodes");
        System.out.println("3.Check if the graph contains a negative cycle");
        System.out.println("4.Input Another File");
        String option=scanner.nextLine();
        while (!(option.equals("1")||option.equals("2")||option.equals("3")||option.equals("4"))){
            System.out.println("INVALID INPUT");
            option=scanner.nextLine();
        }
        return Integer.valueOf(option);
    }
    public void Menu1(){
        while(true){
            int source_node=InputNode("Source");
            int algorithm;
            while (true){
                 algorithm=InputAlgorithm();
                //call algorithm function
                switch (algorithm){
                    case 1:
                    case 2:
                    case 3:
                }
                if(algorithm==4){
                    break;
                }
                int destination_node=InputNode("Destination");
               ;
                while (true){
                    int PathCost=InputPathCost();
                    switch (PathCost){
                        case 1: //get Path length
                        case 2:  //get Path
                    }
                    if(PathCost==3){
                        break;
                    }
                }
            }
                break;
        }
    }
    public void Menu2(){
        while(true){

            int algorithm=InputAlgorithm();
            //call algorithm function
            switch (algorithm){
                case 1:
                case 2:
                case 3:
            }
            if(algorithm==4){
                break;
            }
            while (true){
                int source_node=InputNode("Source");
                int destination_node=InputNode("Destination");
                int PathCost=InputPathCost();
                 switch (PathCost){
                        case 1: //get Path length
                        case 2:  //get Path
                    }
                    if(PathCost==3){
                        break;
                    }

            }

        }
    }
    public void Menu3(){
        while (true){
            int algorithm=InputAlgorithm2();
            //call algorithm function
            switch (algorithm){
                case 1:
                case 2:
            }
            if(algorithm==3){
                break;
            }
        }



    }

    public void InputFile(){
        System.out.println("\u001B[33mEnter the input file path: \u001B[0m");

        String path = scanner.nextLine();

        graph = new Graph(path);
        while (graph.file_path_error){
            System.out.println("\u001B[31mERROR opening the file\u001B[0m");
            System.out.println("\u001B[33mEnter the input file path: \u001B[0m");
            path = scanner.nextLine();
            graph = new Graph(path);
        }
         predecessors = new int[graph.getV()][graph.getV()];
         cost = new int[graph.getV()][graph.getV()];
         System.out.println("Graph successfully created");


    }
    public int InputAlgorithm(){
        System.out.println("CHOOSE AN ALGORITHM :");
        System.out.println("1.Dijkstra");
        System.out.println("2.Bellman-Ford");
        System.out.println("3.Floyd-Warshall");
        System.out.println("4.Return To Previous Menu");
        String option=scanner.nextLine();
        while (!(option.equals("1")||option.equals("2")||option.equals("3")||option.equals("4"))){
            System.out.println("INVALID INPUT");
             option=scanner.nextLine();
        }
        return Integer.valueOf(option);

    }

    public int InputPathCost(){
        System.out.println("1.Get Path length");
        System.out.println("2.Get Path");
        System.out.println("3.Choose Another Algorithm");
        String option=scanner.nextLine();
        while (!(option.equals("1")||option.equals("2")||option.equals("3"))){
            System.out.println("INVALID INPUT");
            option=scanner.nextLine();
        }
        return Integer.valueOf(option);
    }

    public int InputNode(String type){
        System.out.println("Enter "+type+" node ");
        String node=scanner.nextLine();
        while (!isValidInput(node)){
            System.out.println("INVALID INPUT");
            node=scanner.nextLine();
        }
        return Integer.valueOf(node);
    }

    public boolean isValidInput(String input){
        try {
             Integer.valueOf(input);
             return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public int InputAlgorithm2(){
        System.out.println("CHOOSE AN ALGORITHM :");

        System.out.println("1.Bellman-Ford");
        System.out.println("2.Floyd-Warshall");
        System.out.println("3.Return To Previous Menu");
        String option=scanner.nextLine();
        while (!(option.equals("1")||option.equals("2")||option.equals("3"))){
            System.out.println("INVALID INPUT");
            option=scanner.nextLine();
        }
        return Integer.valueOf(option);

    }
}
