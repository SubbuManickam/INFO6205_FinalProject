package com.info6205.project.optimization;

public class TwoOptSwapOptimization {
    public class TwoOptSwapOptimizer {
        public static int[] optimize(int[] tour, int[][] distanceMatrix) {
            int numCities = tour.length;
            int[] newTour = tour.clone();
            boolean improved = true;
            while (improved) {
                improved = false;
                for (int i = 0; i < numCities - 2; i++) {
                    for (int j = i + 2; j < numCities; j++) {
                        int currentDistance = distanceMatrix[newTour[i]][newTour[i+1]]
                                + distanceMatrix[newTour[j]][newTour[(j+1)%numCities]];
                        int newDistance = distanceMatrix[newTour[i]][newTour[j]]
                                + distanceMatrix[newTour[i+1]][newTour[(j+1)%numCities]];
                        if (newDistance < currentDistance) {
                            reverse(newTour, i+1, j);
                            improved = true;
                        }
                    }
                }
            }
            return newTour;
        }
        private static void reverse(int[] tour, int i, int j) {
            while (i < j) {
                int temp = tour[i];
                tour[i] = tour[j];
                tour[j] = temp;
                i++;
                j--;
            }
        }
    }
}
