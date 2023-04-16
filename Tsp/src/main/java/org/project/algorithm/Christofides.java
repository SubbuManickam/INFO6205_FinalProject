package org.project.algorithm;

import org.project.entity.Point;

import java.util.*;

public class Christofides {

    public static void tsp(List<Point> points) {
        int n1 = points.size();
        double[][] tsp_g = new double[n1][n1];
        for (int i = 0; i < points.size(); i++) {
            Point point1 = points.get(i);
            for (int j = 0; j < points.size(); j++) {
                Point point2 = points.get(j);
                tsp_g[i][j] = calculateDistance(point1.getLatitude(), point1.getLongitude(), point2.getLatitude(), point2.getLongitude());
            }
        }
        int n = tsp_g.length;

        // Step 1: Find a minimum spanning tree
        double[][] mst = primMST(tsp_g);

        // Step 2: Find the set of vertices with odd degree
        List<Integer> oddVertices = getOddVertices(mst);

        // Step 3: Find minimum weight perfect matching for the odd vertices
        double[][] matching = getMinimumMatching(tsp_g, oddVertices);

        // Step 4: Combine the minimum spanning tree and minimum weight perfect matching
        double[][] combined = combine(mst, matching);

        // Step 5: Generate a Eulerian tour
        List<Integer> eulerTour = getEulerTour(combined, 0);

        // Step 6: Generate a Hamiltonian tour from the Eulerian tour
        List<Integer> hamiltonianTour = getHamiltonianTour(eulerTour);

        // Step 7: Calculate the total cost of the Hamiltonian tour
        double cost = calculateTourCost(tsp_g, hamiltonianTour);

        System.out.println("Minimum Cost is: " + cost);
    }

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
        System.out.println(cost/2.0);
        return mst;
    }

    private static List<Integer> getOddVertices(double[][] graph) {
        List<Integer> oddVertices = new ArrayList<>();
        for (int i = 0; i < graph.length; i++) {
            int degree = 0;
            for (int j = 0; j < graph[i].length; j++) {
                degree += graph[i][j];
            }
            if (degree % 2 == 1) {
                oddVertices.add(i);
            }
        }
        return oddVertices;
    }
    private static double[][] getMinimumMatching(double[][] graph, List<Integer> oddVertices) {
        double[][] matching = new double[graph.length][graph.length];
        for (int i = 0; i < oddVertices.size(); i++) {
            int u = oddVertices.get(i);
            double minWeight = Double.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < graph.length; j++) {
                if (i != j && oddVertices.contains(j) && graph[u][j] < minWeight) {
                    minWeight = graph[u][j];
                    minIndex = j;
                }
            }
            matching[u][minIndex] = minWeight;
            matching[minIndex][u] = minWeight;
            oddVertices.remove((Integer) minIndex);
        }
        return matching;
    }
    public static double[][] combine(double[][] mst, double[][] matching) {
        int n = mst.length;
        double[][] combined = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                combined[i][j] = mst[i][j] + matching[i][j];
            }
        }

        return combined;
    }

    // Step 5: Generate a Eulerian tour
    public static List<Integer> getEulerTour(double[][] graph, int start) {
        List<Integer> tour = new ArrayList<>();

        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int currentVertex = stack.pop();
            tour.add(currentVertex);

            for (int i = graph.length - 1; i >= 0; i--) {
                if (graph[currentVertex][i] != 0.0) {
                    stack.push(i);
                    graph[currentVertex][i] = 0.0;
                    graph[i][currentVertex] = 0.0;
                }
            }
        }

        return tour;
    }

    // Step 6: Generate a Hamiltonian tour from the Eulerian tour
    public static List<Integer> getHamiltonianTour(List<Integer> eulerTour) {
        Set<Integer> visited = new HashSet<>();
        List<Integer> hamiltonianTour = new ArrayList<>();

        for (int vertex : eulerTour) {
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                hamiltonianTour.add(vertex);
            }
        }

        hamiltonianTour.add(eulerTour.get(0)); // Add the starting vertex to complete the tour
        return hamiltonianTour;
    }

    // Step 7: Calculate the total cost of the Hamiltonian tour
    public static double calculateTourCost(double[][] graph, List<Integer> tour) {
        double cost = 0.0;
        int prev = tour.get(0);

        for (int i = 1; i < tour.size(); i++) {
            int curr = tour.get(i);
            cost += graph[prev][curr];
            prev = curr;
        }

        return cost;
    }
    public static double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        double radius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = radius * c;
//        System.out.println(lat1 + " " + lon1 + " " + lat2 + " " + lon2 + " = " + distance);
        return distance;
    }

}
