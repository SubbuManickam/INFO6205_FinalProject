package org.project.algorithm;

import org.project.entity.Point;
import org.project.optimization.ThreeOptSwapOptimization;
import org.project.optimization.TwoOptSwapOptimization;

import java.util.*;

public class Christofides {

    public static void tsp(List<Point> points) {

        int n1 = points.size();
        double[][] tsp_g = new double[n1][n1];
        for (int i = 0; i < n1; i++) {
            Point point1 = points.get(i);
            for (int j = 0; j < n1; j++) {
                Point point2 = points.get(j);
                tsp_g[i][j] = calculateDistance(point1.getLatitude(), point1.getLongitude(), point2.getLatitude(), point2.getLongitude());
            }
        }
        int n = tsp_g.length;

        //Find a minimum spanning tree
        double[][] mst = MinimumSpanningTree.primMST(tsp_g);

        //Find the set of vertices with odd degree
        List<Integer> oddVertices = getOddVertices(mst);

        //Find minimum weight perfect matching for the odd vertices
        double[][] matching = getMinimumMatching(tsp_g, oddVertices);

        //Combine the minimum spanning tree and minimum weight perfect matching

        double minCost = Double.POSITIVE_INFINITY;
        List<Integer> finalTour = new ArrayList<>();
        int index = 0;
        for(int i=0; i<n1; i++) {
            double[][] combined = combine(mst, matching);
            //Generate a Eulerian tour
            List<Integer> eulerTour = getEulerTour(combined, i);

            //Generate a Hamiltonian tour from the Eulerian tour
            List<Integer> hamiltonianTour = getHamiltonianTour(eulerTour);

            double cost = calculateTourCost(tsp_g, hamiltonianTour);

            if(cost < minCost) {
                minCost = cost;
                finalTour = hamiltonianTour;
                index = i;
            }
        }
        for(Integer tour : finalTour) {
            for(Point point : points) {
                if(point.getId().equals(tour)) {
                    System.out.print(point.getCrimeId() + "->");
                }
            }
        }
        System.out.println("\n Minimum Cost Christofide's is: " + minCost);

        double[][] combined = combine(mst, matching);
        List<Integer> eulerTour = getEulerTour(combined, index);
        // Calculate cost of each tour and optimization
        List<Integer> hamiltonianTwoOpt = TwoOptSwapOptimization.getHamiltonianTourTwoOpt(eulerTour, tsp_g, points);
        double costTwoOpt = calculateTourCost(tsp_g, hamiltonianTwoOpt);
        System.out.println("\n Minimum Cost Two Opt is: " + costTwoOpt);

//        List<Integer> hamiltonianThreeOpt = ThreeOptSwapOptimization.getHamiltonianTourThreeOpt(eulerTour, tsp_g, points);
//        double costThreeOpt = calculateTourCost(tsp_g, hamiltonianThreeOpt);
//        System.out.println("Minimum Cost Three Opt is: " + costThreeOpt);

//        List<Integer> annealedTour = simulatedAnnealing(tsp_g, hamiltonianTwoOpt, 1000000, 0.01);
//        double costSimulatedAnnealing = calculateTourCost(tsp_g, annealedTour);
//        System.out.println("Minimum Cost Simulated Annealing is: " + costSimulatedAnnealing);
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
        return distance;
    }

    public static List<Integer> simulatedAnnealing(double[][] tsp_g, List<Integer> tour, double temperature, double coolingRate) {
        List<Integer> bestTour = new ArrayList<>(tour);
        List<Integer> currentTour = new ArrayList<>(tour);

        while (temperature > 1) {
            List<Integer> newTour = new ArrayList<>(currentTour);
            int tourSize = newTour.size();

            // Generate two random indices
            int i = (int) (Math.random() * tourSize);
            int j = (int) (Math.random() * tourSize);

            // Swap the cities at those indices
            int temp = newTour.get(i);
            newTour.set(i, newTour.get(j));
            newTour.set(j, temp);

            // Calculate the new cost and the change in cost
            double currentCost = calculateTourCost(tsp_g, currentTour);
            double newCost = calculateTourCost(tsp_g, newTour);
            double delta = newCost - currentCost;

            // Decide whether to accept the new tour
            if (delta < 0 || Math.exp(-delta / temperature) > Math.random()) {
                currentTour = new ArrayList<>(newTour);
                if (newCost < calculateTourCost(tsp_g, bestTour)) {
                    bestTour = new ArrayList<>(newTour);
                }
            }

            // Decrease the temperature
            temperature *= coolingRate;
        }

        return bestTour;
    }


}
