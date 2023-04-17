package com.info6205.project.optimization;

public class SimulatedAnnealing {
        // The objective function to be optimized
        private static double objectiveFunction(double x) {
            return Math.sin(x) + Math.sin(10/3 * x);
        }

        public static void main(String[] args) {

            // Set the initial temperature and cooling rate
            double initialTemperature = 1000;
            double coolingRate = 0.003;

            // Set the initial state (i.e., the initial value of x)
            double currentState = 0.0;

            // Set the best state and best objective function value
            double bestState = currentState;
            double bestObjectiveFunctionValue = objectiveFunction(currentState);

            // Loop until the temperature is zero
            while (initialTemperature > 1) {

                // Choose a random neighbor of the current state
                double neighbor = currentState + (Math.random() * 2 - 1) * initialTemperature;

                // Calculate the objective function value for the neighbor
                double neighborObjectiveFunctionValue = objectiveFunction(neighbor);

                // Calculate the difference in objective function values between the current state and the neighbor
                double delta = neighborObjectiveFunctionValue - bestObjectiveFunctionValue;

                // If the neighbor has a better objective function value, move to the neighbor
                if (delta < 0) {
                    currentState = neighbor;
                    bestObjectiveFunctionValue = neighborObjectiveFunctionValue;

                    // If the neighbor is the best state so far, update the best state
                    if (bestObjectiveFunctionValue < objectiveFunction(bestState)) {
                        bestState = currentState;
                    }

                    // If the neighbor has a worse objective function value, move to the neighbor with a certain probability
                } else {
                    double probability = Math.exp(-delta / initialTemperature);
                    if (Math.random() < probability) {
                        currentState = neighbor;
                    }
                }

                // Cool down the temperature
                initialTemperature *= 1 - coolingRate;
            }

            // Print the best state and best objective function value
            System.out.println("Best state: " + bestState);
            System.out.println("Best objective function value: " + objectiveFunction(bestState));
        }
}
