package org.project.algorithm;

import org.project.entity.Point;

import java.util.ArrayList;
import java.util.List;

public class GreedyAlgorithm {
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
            double cost = 0.0;
            int count = 0;
            int j = 0, i = 0;
            double min = Double.MAX_VALUE;
            List<Integer> visited = new ArrayList<>();
            // The problem starts from 0th index city
            visited.add(0);
            int[] path = new int[tsp_g.length];
            while (i < tsp_g.length && j < tsp_g[i].length) {
                if (count >= tsp_g[i].length - 1) {
                    break;
                }

                // If the city is unvisited and has minimum cost, update the cost
                if (j != i && !(visited.contains(j))) {
                    if (tsp_g[i][j] < min) {
                        min = tsp_g[i][j];
                        path[count] = j + 1;
                    }
                }
                j++;

                // Check all paths from the
                // ith indexed city
                if (j == tsp_g[i].length) {
                    cost += min;
                    min = Double.MAX_VALUE;
                    visited.add(path[count] - 1);
                    j = 0;
                    i = path[count] - 1;
                    count++;
                }
            }

            // Update the ending city in array
            // from city which was last visited
            i = path[count - 1] - 1;
            for (j = 0; j < tsp_g.length; j++) {
                if ((i != j) && tsp_g[i][j] < min) {
                    min = tsp_g[i][j];
                    path[count] = j + 1;
                }
            }
            cost += min;

            // Started from the node where
            // we finished as well.
            System.out.print("Minimum Cost is : ");
            System.out.println(cost);

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

}
