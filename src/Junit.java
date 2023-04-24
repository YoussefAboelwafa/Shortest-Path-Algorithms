import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.Assert.*;

public class Junit<T extends Comparable<T>> {

    Graph graph1 = new Graph("test1.txt");
    Graph graph2 = new Graph("test2.txt");
    Graph graph3 = new Graph("test3.txt");
    Graph graph4 = new Graph("test4.txt"); //contains negative cycle
    Graph graph5 = new Graph("test5.txt");
    Graph graph6 = new Graph("test6.txt"); // large file with no negative cycle

    int n; // number of nodes
    int[] dijkstra_cost;
    int[] dijkstra_parent;

    int[] bellman_cost;
    int[] bellman_parent;

    int[][] floyd_cost;
    int[][] floyd_parent;


    @Test
    public void all_pairs_time_comparasion() {
        n = graph5.getV();
        dijkstra_cost = new int[n];
        bellman_cost = new int[n];
        dijkstra_parent = new int[n];
        bellman_parent = new int[n];
        floyd_cost = new int[n][n];
        floyd_parent = new int[n][n];
        //dijkstra
        long start1 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            graph5.dijkstra(i, dijkstra_parent, dijkstra_cost);
        }
        long end1 = System.nanoTime();

        //bellman-ford
        long start2 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            graph5.bellman_ford(i, bellman_parent, bellman_cost);
        }
        long end2 = System.nanoTime();

        //floyd-warshall
        long start3 = System.nanoTime();
        graph5.Floyd_Warshall(floyd_cost, floyd_parent);
        long end3 = System.nanoTime();
        System.out.println("Time to get the shortest paths between all pairs of nodes with:");
        System.out.println("\u001B[35m[Dijkstra Algorithm] = (" + (end1 - start1) + ") ns\u001B[0m");
        System.out.println("\u001B[34m[Bellman-ford Algorithm] = (" + (end2 - start2) + ") ns\u001B[0m");
        System.out.println("\u001B[33m[Floyd-Warshall Algorithm] = (" + (end3 - start3) + ") ns\u001B[0m");
    }

    @Test
    public void two_nodes_time_comparasion() {
        n = graph5.getV();
        dijkstra_cost = new int[n];
        bellman_cost = new int[n];
        dijkstra_parent = new int[n];
        bellman_parent = new int[n];
        floyd_cost = new int[n][n];
        floyd_parent = new int[n][n];

        //dijkstra
        long start1 = System.nanoTime();
        graph5.dijkstra(0, dijkstra_parent, dijkstra_cost);
        long end1 = System.nanoTime();

        //bellman-ford
        long start2 = System.nanoTime();
        graph5.bellman_ford(0, bellman_parent, dijkstra_cost);
        long end2 = System.nanoTime();

        //floyd-warshall
        long start3 = System.nanoTime();
        graph5.Floyd_Warshall(floyd_cost, floyd_parent);
        long end3 = System.nanoTime();
        System.out.println("Time to get the shortest paths between 2 specific nodes with:");
        System.out.println("\u001B[35m[Dijkstra Algorithm] = (" + (end1 - start1) + ") ns\u001B[0m");
        System.out.println("\u001B[34m[Bellman-ford Algorithm] = (" + (end2 - start2) + ") ns\u001B[0m");
        System.out.println("\u001B[33m[Floyd-Warshall Algorithm] = (" + (end3 - start3) + ") ns\u001B[0m");
    }

    @Test
    public void bellman_ford_negative_cycle_check() {
        n = graph4.getV();
        bellman_cost = new int[n];
        bellman_parent = new int[n];

        boolean negative_cycle = graph4.bellman_ford(0, bellman_parent, bellman_cost);
        System.out.println(negative_cycle);
        //return false if negative cycle exist
        assertFalse(negative_cycle);
    }

    @Test
    public void foyd_warshall_negative_cycle_check() {
        n = graph4.getV();
        floyd_cost = new int[n][n];
        floyd_parent = new int[n][n];

        boolean negative_cycle = graph4.Floyd_Warshall(floyd_cost, floyd_parent);
        System.out.println(negative_cycle);
        //return false if negative cycle exist
        assertFalse(negative_cycle);
    }

    @Test
    public void bellman_cost_check() {
        n = graph2.getV();
        bellman_cost = new int[n];
        bellman_parent = new int[n];
        int[] ans = {0, 2, 7, 3, 9, 5, 2};
        graph2.bellman_ford(0, bellman_parent, bellman_cost);
        boolean isEqual = Arrays.equals(ans, bellman_cost);
        assertTrue(isEqual);
    }

    @Test
    public void bellman_parent_check() {
        n = graph3.getV();
        bellman_cost = new int[n];
        bellman_parent = new int[n];
        int[] ans = {3, -1, 0, 1, 0};
        graph3.bellman_ford(1, bellman_parent, bellman_cost);
        boolean isEqual = Arrays.equals(ans, bellman_parent);
        assertTrue(isEqual);
    }


    @Test
    public void floyd_cost_check() {
        n = graph2.getV();
        floyd_parent = new int[n][n];
        floyd_cost = new int[n][n];
        graph2.Floyd_Warshall(floyd_cost, floyd_parent);
        int[] ans = {0, 2, 7, 3, 9, 5, 2};
        int[] first_cost_row = floyd_cost[0];
        boolean isEqual = Arrays.equals(ans, first_cost_row);
        System.out.println(Arrays.deepToString(floyd_cost));
        assertTrue(isEqual);
    }


}