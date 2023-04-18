package org.project.optimization;

import java.util.ArrayList;
import java.util.Random;

/**
 * Solves the Traveling Salesman Problem using vanilla Ant Colony Optimization.
 */
public class AntColonyOptimization {

        private double[][] graph;
        private int numCities;
        private int numAnts;
        private double alpha;
        private double beta;
        private double evaporationRate;
        private double initialPheromone;
        private int maxIterations;

        private double[][] pheromone;
        private double[][] heuristic;
        private double[][] probabilities;
        private Random random;

        public AntColonyOptimization(double[][] graph, int numAnts, double alpha, double beta, double evaporationRate, double initialPheromone, int maxIterations) {
            this.graph = graph;
            this.numCities = 585;
            this.numAnts = numAnts;
            this.alpha = alpha;
            this.beta = beta;
            this.evaporationRate = evaporationRate;
            this.initialPheromone = initialPheromone;
            this.maxIterations = maxIterations;

            this.pheromone = new double[numCities][numCities];
            this.heuristic = new double[numCities][numCities];
            this.probabilities = new double[numCities][numCities];
            this.random = new Random();

            // Initialize pheromone levels
            for (int i = 0; i < numCities; i++) {
                for (int j = 0; j < numCities; j++) {
                    pheromone[i][j] = initialPheromone;
                }
            }

            // Compute heuristic values
            for (int i = 0; i < numCities; i++) {
                for (int j = 0; j < numCities; j++) {
                    if (i == j) {
                        heuristic[i][j] = 0.0;
                    } else {
                        heuristic[i][j] = 1.0 / graph[i][j];
                    }
                }
            }
        }

        public ArrayList<Integer> solve() {
            ArrayList<Integer> bestTour = null;
            double bestTourLength = Double.MAX_VALUE;

            for (int iteration = 0; iteration < maxIterations; iteration++) {
                ArrayList<ArrayList<Integer>> antTours = new ArrayList<>();
                ArrayList<Double> antTourLengths = new ArrayList<>();

                // Construct ant tours
                for (int ant = 0; ant < numAnts; ant++) {
                    ArrayList<Integer> tour = new ArrayList<>();
                    boolean[] visited = new boolean[numCities];
                    int currentCity = random.nextInt(numCities);
                    visited[currentCity] = true;
                    tour.add(currentCity);

                    while (tour.size() < numCities) {
                        computeProbabilities(currentCity, visited);
                        int nextCity = selectNextCity(currentCity);
                        if(nextCity != -1) {
                            visited[nextCity] = true;
                            tour.add(nextCity);
                            currentCity = nextCity;
                        }
                    }

                    double tourLength = computeTourLength(tour);
                    antTours.add(tour);
                    antTourLengths.add(tourLength);

                    // Update pheromone levels
                    updatePheromoneLevels(tour, tourLength);
                }

                // Update pheromone levels globally
                updatePheromoneLevelsGlobal(bestTour, bestTourLength);

                // Update best tour
                for (int i = 0; i < numAnts; i++) {
                    if (antTourLengths.get(i) < bestTourLength) {
                        bestTourLength = antTourLengths.get(i);
                        bestTour = antTours.get(i);
                    }
                }

                System.out.println("Iteration " + iteration + ": " + bestTourLength);
            }

            return bestTour;
        }

        private void computeProbabilities(int currentCity, boolean[] visited) {
            double total = 0.0;

            for (int i = 0; i < numCities; i++) {
                if (!visited[i]) {
                    probabilities[currentCity][i] = Math.pow(pheromone[currentCity][i], alpha) * Math.pow(heuristic[currentCity][i], beta);
                    total += probabilities[currentCity][i];
                } else {
                    probabilities[currentCity][i] = 0.0;
                }
            }

            for (int i = 0; i < numCities; i++) {
                if (!visited[i]) {
                    probabilities[currentCity][i] /= total;
                }
            }
        }

        private int selectNextCity(int currentCity) {
            double r = random.nextDouble();
            double total = 0.0;

            for (int i = 0; i < numCities; i++) {
                total += probabilities[currentCity][i];
                if (total >= r) {
                    return i;
                }
            }

            return -1;
        }

        private double computeTourLength(ArrayList<Integer> tour) {
            double tourLength = 0.0;

            for (int i = 0; i < numCities; i++) {
                int j = (i + 1) % numCities;
                int city1 = tour.get(i);
                int city2 = tour.get(j);
                tourLength += graph[city1][city2];
            }

            return tourLength;
        }

        private void updatePheromoneLevels(ArrayList<Integer> tour, double tourLength) {
            double pheromoneDeposit = 1.0 / tourLength;

            for (int i = 0; i < numCities; i++) {
                int j = (i + 1) % numCities;
                int city1 = tour.get(i);
                int city2 = tour.get(j);
                pheromone[city1][city2] += pheromoneDeposit;
            }
        }

        private void updatePheromoneLevelsGlobal(ArrayList<Integer> bestTour, double bestTourLength) {
            double evaporation = 0.5;

            for (int i = 0; i < numCities; i++) {
                for (int j = 0; j < numCities; j++) {
                    pheromone[i][j] *= evaporation;
                }
            }

            double pheromoneDeposit = 1.0 / bestTourLength;

            for (int i = 0; i < numCities; i++) {
                int j = (i + 1) % numCities;
                int city1 = bestTour.get(i);
                int city2 = bestTour.get(j);
                pheromone[city1][city2] += pheromoneDeposit;
            }
        }
    }
