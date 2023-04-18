package org.project.optimization;

import org.project.entity.Point;

import java.util.ArrayList;
import java.util.List;

public class TwoOptSwapOptimization {
    public static List<Integer> getHamiltonianTourTwoOpt(List<Integer> eulerTour, double[][] tsp_g, List<Point> points) {
        List<Integer> tour = new ArrayList<>(eulerTour);
        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i = 1; i < tour.size() - 2; i++) {
                for (int j = i + 1; j < tour.size() - 1; j++) {
                    List<Integer> newTour = twoOptSwap(tour, i, j);
                    double newCost = calculateTourCost(tsp_g, newTour);
                    if (newCost < calculateTourCost(tsp_g, tour)) {
                        tour = newTour;
                        improved = true;
                    }
                }
            }
        }
        tour.add(eulerTour.get(0));
        for(Integer tour1 : tour) {
            for(Point point : points) {
                if(point.getId().equals(tour1)) {
                    System.out.print(point.getCrimeId() + "->");
                }
            }
        }
        return tour;
    }

    public static List<Integer> twoOptSwap(List<Integer> tour, int i, int j) {
        List<Integer> newTour = new ArrayList<>();
        for (int k = 0; k < i; k++) {
            newTour.add(tour.get(k));
        }
        for (int k = j; k >= i; k--) {
            newTour.add(tour.get(k));
        }
        for (int k = j + 1; k < tour.size(); k++) {
            newTour.add(tour.get(k));
        }
        return newTour;
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
}