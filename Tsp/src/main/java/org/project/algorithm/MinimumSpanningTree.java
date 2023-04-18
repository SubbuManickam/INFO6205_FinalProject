package org.project.algorithm;

public class MinimumSpanningTree {

    public static double[][] primMST(double[][] graph) {
        int n = graph.length;
        double[][] mst = new double[n][n];
        boolean[] visited = new boolean[n];
        double[] key = new double[n];
        int[] parent = new int[n];

        for (int i = 0; i < n; i++) {
            key[i] = Double.POSITIVE_INFINITY;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < n - 1; i++) {
            int u = -1;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || key[j] < key[u])) {
                    u = j;
                }
            }
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (graph[u][v] != 0 && !visited[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        for (int i = 1; i < n; i++) {
            mst[parent[i]][i] = graph[parent[i]][i];
            mst[i][parent[i]] = graph[parent[i]][i];
        }

        double cost = 0.0;
        for(int i=0; i<mst.length; i++) {
            for(int j=0; j<mst.length; j++) {
                cost += mst[i][j];
            }
        }
        System.out.println("Cost Minimum Spanning Tree is: " + cost/2.0);
        return mst;
    }
}
