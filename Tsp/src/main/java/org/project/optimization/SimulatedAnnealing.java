package org.project.optimization;

import org.project.entity.Point;

import java.util.ArrayList;
import java.util.List;

public class SimulatedAnnealing {

    private static final double MAX_TEMPERATURE = 10000;
    private static final double MIN_TEMPERATURE = 1;
    private static final double COOLING_RATE = 0.001;

    public static double simulate(List<Integer> cities, double[][] distances, List<Point> points) {
        double temp = MAX_TEMPERATURE;

        List<Integer> actualState = new ArrayList<>(cities);
        List<Integer> bestState = new ArrayList<>(actualState);
        double bestStateCost = calculateTourCost(distances, bestState);

//        System.out.println("Initial solution distance: " + bestStateCost);

        while (temp > MIN_TEMPERATURE) {
            List<Integer> nextState = generateNeighborState(actualState);

            double currentEnergy = calculateTourCost(distances, actualState);
            double neighborEnergy = calculateTourCost(distances, nextState);

//            System.out.println("Acceptance probability " + acceptanceProbability(currentEnergy, neighborEnergy, temp));
            if (acceptanceProbability(currentEnergy, neighborEnergy, temp) > Math.random()) {
                actualState = new ArrayList<>(nextState);
            }
            double currentCost = calculateTourCost(distances, actualState);
//            System.out.println("Best state cost "+ currentCost);
            if (currentCost < bestStateCost) {
                bestState = new ArrayList<>(actualState);
                bestStateCost = currentCost;
            }

            temp *= 1 - COOLING_RATE;
        }
        for(Integer tour : bestState) {
            for(Point point : points) {
                if(point.getId().equals(tour)) {
                    System.out.print(point.getCrimeId() + "->");
                }
            }
        }
        return bestStateCost;
    }

    private static List<Integer> generateNeighborState(List<Integer> actualState) {
        List<Integer> newState = new ArrayList<>(actualState);

        int randomIndex1 = (int) (Math.random() * 10);
        int randomIndex2 = (int) (Math.random() * 10);

        int city1 = newState.get(randomIndex1);
        int city2 = newState.get(randomIndex2);

        newState.set(randomIndex1, city2);
        newState.set(randomIndex2, city1);

        return newState;
    }

    private static double acceptanceProbability(double currentEnergy, double neighborEnergy, double temp) {
        if (neighborEnergy < currentEnergy) {
            return 1.0;
        }
        return Math.exp((currentEnergy - neighborEnergy) / temp);
    }

    private static double calculateTourCost(double[][] distances, List<Integer> tour) {
        double cost = 0.0;

        int prev = tour.get(0);


        for (int i = 1; i < tour.size(); i++) {
            int curr = tour.get(i);
            cost = cost + distances[prev][curr];
            prev = curr;
        }

        return cost;
    }
}
