import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

public class Graph {
    private int V;
    private int E;
    private String Path;
    private int graph[][];
    private Vector<Edge> edges=new Vector<>(V);

    public Graph(String path) {

        Path=path;
        readFile(path);
    }
    public int getV()
    {
        return V;
    }
    public int[][] readFile(String path) {
        try {
            int n1;
            int n2;
            int n3;
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            V = scanner.nextInt();
            E = scanner.nextInt();
            graph=new int[V][V];
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    graph[i][j]=0;
                }
            }
            while (scanner.hasNextLine()) {

                n1 = scanner.nextInt();
                n2 = scanner.nextInt();
                n3 = scanner.nextInt();
                Edge edge=new Edge();
                edge.setSrc(n1);
                edge.setDest(n2);
                edge.setWeight(n3);
                edges.add(edge);
                graph[n1][n2] = n3;
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("\u001B[31mERROR opening the file\u001B[0m");
        }
        return graph;
    }
    public void printSolution() {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(graph[i][j] + "   ");
            }
            System.out.println();
        }
    }
    public int selectMinVertex(int [] cost,boolean[] processed)
    {
        int minimum = Integer.MAX_VALUE;
        int vertex=0;
        for(int i=0;i<V;++i)
        {
            if(processed[i]==false && cost[i]<minimum)
            {
                vertex = i;
                minimum = cost[i];
            }
        }
        return vertex;
    }
public void dijkstra(int source,int [] parent,int[] cost)
{
    for(int i=0;i<cost.length;i++)cost[i]=Integer.MAX_VALUE;
    boolean [] processed=new boolean[V];
   parent[source]=-1;
   cost[source]=0;
   for(int i=0;i<V-1;i++)
   {
       int U = selectMinVertex(cost,processed);
       processed[U]=true;
       for(int j=0;j<V;j++)
       {
           if(graph[U][j]!=0 && processed[j]==false && cost[U]!=Integer.MAX_VALUE
                   && (cost[U]+graph[U][j] < cost[j]))
           {
               cost[j] = cost[U]+graph[U][j];
               parent[j] = U;
           }
       }

   }
   for (int i=0;i<V;i++)
   {
       System.out.println(i+"->"+cost[i]);
   }
}


    public Boolean bellman_ford(int source,int [] parent,int[] cost)
    {
        int[] values=new int[V];
        for(int i=0;i<V;i++)
        {
            values[i]=Integer.MAX_VALUE;
        }
        parent[source]=-1;
        values[source]=0;
        boolean updated=false;
        boolean found=false;
        for(int i=0;i<V-1;i++)
        {
            updated=false;
            for (int j=0;j<E;j++)
            {
                int u=edges.get(j).getSrc();
                int v=edges.get(j).getDest();
                int w=edges.get(j).getWeight();
                if(values[u]!=Integer.MAX_VALUE && values[u]+w<values[v])
                {
                    values[v]=values[u]+w;
                    parent[v]=u;
                    cost[v]=values[v];
                    updated=true;
                }
            }
            if(updated==false) break;

        }
        for (int j=0;j<E && updated==true;j++)
        {
            int u=edges.get(j).getSrc();
            int v=edges.get(j).getDest();
            int w=edges.get(j).getWeight();
            if(values[u]!=Integer.MAX_VALUE && values[u]+w<values[v])
            {
                found=true;
            }
        }

        if(found){
            System.out.println("negative cycle found");
            return false;}
        for(int i=0;i<V;i++)
        {
            System.out.println(i+"-> "+cost[i]+"\n");
        }
        return true;
    }
    public boolean Floyd_Warshall() {
        int [][] ans=graph;
        int size = ans.length;
        int i, j, k;
        // k is the intermediate node
        for (k = 0; k < size; k++) {
            for (i = 0; i < size; i++) {
                for (j = 0; j < size; j++) {
                    if (ans[i][k] + ans[k][j] < ans[i][j]) {
                        ans[i][j] = ans[i][k] + ans[k][j];
                    }
                }
            }
        }
        for ( i = 0; i < V; i++) {
            // If there is a negative cycle at vertex i
            if (ans[i][i] < 0) {
                System.out.println("negative cycle found");
                return false;
            }
        }
        return true;
    }
}
