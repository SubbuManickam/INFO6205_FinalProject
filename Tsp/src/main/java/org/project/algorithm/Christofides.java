package org.project.algorithm;

import org.project.entity.Point;
import org.project.optimization.AntColonyOptimization;
import org.project.optimization.SimulatedAnnealing;
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

        //Find minimum spanning tree
        double[][] mst = MinimumSpanningTree.primMST(tsp_g);

        //Find vertices with odd degrees
        List<Integer> oddVertices = getOddVertices(mst);

        //Find minimum weight perfect matching for the odd vertices
        double[][] matching = getMinimumMatching(tsp_g, oddVertices);

        //Combine the minimum spanning tree and minimum weight perfect matching and swap different start points to find the minimum cost point
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
        System.out.println("\nMinimum Cost Christofide's is: " + minCost + "\n");

        double[][] combined = combine(mst, matching);
        List<Integer> eulerTour = getEulerTour(combined, index);

        // Calculate cost of each tour and optimization
        List<Integer> hamiltonianTwoOpt = TwoOptSwapOptimization.getHamiltonianTourTwoOpt(eulerTour, tsp_g, points);
        double costTwoOpt = calculateTourCost(tsp_g, hamiltonianTwoOpt);
        System.out.println("\nMinimum Cost Two Opt is: " + costTwoOpt + "\n");

        List<Integer> hamiltonianThreeOpt = ThreeOptSwapOptimization.getHamiltonianTourThreeOpt(eulerTour, tsp_g, points);
        double costThreeOpt = calculateTourCost(tsp_g, hamiltonianThreeOpt);
        System.out.println("\nMinimum Cost Three Opt is: " + costThreeOpt + "\n");

        double annealedTour = SimulatedAnnealing.simulate(hamiltonianTwoOpt, tsp_g, points);
        System.out.println("\nMinimum Cost Simulated Annealing is: " + annealedTour + "\n");

//        int numAnts = 10;
//        double alpha = 1.0;
//        double beta = 5.0;
//        double evaporationRate = 0.5;
//        double initialPheromone = 0.1;
//        int maxIterations = 100;
//
//   Create an instance of the AntColonyOptimizationTSP class
//        AntColonyOptimization acoTSP = new AntColonyOptimization(
//                tsp_g, numAnts, alpha, beta, evaporationRate, initialPheromone, maxIterations);
//
//   Solve the TSP problem using the ACO algorithm
//        ArrayList<Integer> bestTour = acoTSP.solve();
//        double costAnt = calculateTourCost(tsp_g, bestTour);
//        System.out.println("\nMinimum Cost Ant is: " + costAnt + "\n");
    }

    public static List<Integer> getOddVertices(double[][] graph) {
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

    public static double[][] getMinimumMatching(double[][] graph, List<Integer> oddVertices) {
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
        int currentVertex = start;

        Stack<Integer> stack = new Stack<>();
        stack.push(currentVertex);
        while (!stack.isEmpty()) {

            int vertex = stack.peek();
            boolean hasUnvisitedEdges = false;

            for (int i = 0; i < graph.length; i++) {
                if (graph[vertex][i] != 0) {
                    hasUnvisitedEdges = true;
                    break;
                }
            }

            if (hasUnvisitedEdges) {
                int nextVertex = -1;
                for (int i = 0; i < graph.length; i++) {
                    if (graph[vertex][i] != 0) {
                        nextVertex = i;
                        break;
                    }
                }

                graph[vertex][nextVertex] = 0;
                graph[nextVertex][vertex] = 0;
                stack.push(nextVertex);
            } else {
                stack.pop();
                tour.add(vertex);
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
        double distance = radius * c * 1000;
        return distance;
    }

}
