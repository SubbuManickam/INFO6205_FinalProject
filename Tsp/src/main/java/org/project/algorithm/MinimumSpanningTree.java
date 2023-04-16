package org.project.algorithm;

import org.project.entity.Connect;
import org.project.entity.DisjointSet;
import org.project.entity.Point;

import java.util.*;

public class MinimumSpanningTree {

    public static List<Connect> addConnections(List<Point> points) {
        List<Connect> connections = new ArrayList<>();
        PriorityQueue<Connect> pq = new PriorityQueue<>();
        double totalWeight = 0.0;

        for (int i = 0; i < points.size() - 1; i++) {
            Point point1 = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                Point point2 = points.get(j);
                double distance = calculateDistance(point1.getLatitude(), point1.getLongitude(), point2.getLatitude(), point2.getLongitude());
                Connect connect = new Connect(point1, point2, distance);
                pq.add(connect);
                connections.add(connect);
            }
        }
        DisjointSet<Point> ds = new DisjointSet<>(points);
        while (!pq.isEmpty() && ds.count() > 1) {
            Connect e = pq.poll();
            Point u = e.getFrom();
            Point v = e.getTo();

            if (ds.find(u) != ds.find(v)) {
                ds.union(u, v);
                totalWeight += e.getWeight();
            }
        }
        System.out.println(totalWeight);
        return connections;
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
