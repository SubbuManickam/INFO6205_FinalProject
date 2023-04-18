package org.project.optimization;

import org.project.entity.Point;

import java.util.ArrayList;
import java.util.List;

public class ThreeOptSwapOptimization {
    public static List<Integer> getHamiltonianTourThreeOpt(List<Integer> eulerTour, double[][] tsp_g, List<Point> points) {
        List<Integer> tour = new ArrayList<>(eulerTour);
        int n = tour.size();
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (int i = 0; i < n - 2; i++) {
                for (int j = i + 2; j < n - 1; j++) {
                    for (int k = j + 2; k < n; k++) {
                        double oldCost = calculateTourCost(tour, tsp_g);
                        List<Integer> newTour = reverseSubtour(tour, i + 1, j, k);
                        double newCost = calculateTourCost(newTour, tsp_g);
                        if (newCost < oldCost) {
                            tour = newTour;
                            improvement = true;
                        }
                    }
                }
            }
        }
        for(Integer tour1 : tour) {
            for(Point point : points) {
                if(point.getId().equals(tour1)) {
                    System.out.println(point.getCrimeId() + "->");
                }
            }
        }
        return tour;
    }

    public static List<Integer> reverseSubtour(List<Integer> tour, int i, int j, int k) {
        List<Integer> newTour = new ArrayList<>();
        for (int x = 0; x < i; x++) {
            newTour.add(tour.get(x));
        }
        for (int x = j; x >= i; x--) {
            newTour.add(tour.get(x));
        }
        for (int x = k; x > j; x--) {
            newTour.add(tour.get(x));
        }
        for (int x = k + 1; x < tour.size(); x++) {
            newTour.add(tour.get(x));
        }
        return newTour;
    }

    public static double calculateTourCost(List<Integer> tour, double[][] tsp_g) {
        double cost = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            cost += tsp_g[tour.get(i)][tour.get(i + 1)];
        }
        cost += tsp_g[tour.get(tour.size() - 1)][tour.get(0)];
        return cost;
    }
}