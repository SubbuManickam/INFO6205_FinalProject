import org.junit.Test;
import org.project.algorithm.Christofides;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChristofidesTest {

    @Test
    public void calculateDistanceTest() {
        double lat1 = 51.483548;
        double lat2 = 51.513075;
        double lon1 = -0.009691;
        double lon2 = -0.118888;
        double distance = Christofides.calculateDistance(lat1, lon1, lat2, lon2);
        assertEquals(8241.20, distance, 10.0);
    }

    @Test
    public void oddVerticesTest() {
        double INF = Double.POSITIVE_INFINITY;
        double[][] graph = {
                {0, 2, 3, INF},
                {2, 0, INF, 5},
                {3, INF, 0, 4},
                {INF, 5, 4, 0}
        };
        List<Integer> oddVertices = Christofides.getOddVertices(graph);
        List<Integer> output = new ArrayList<>();
        output.add(0);
        output.add(1);
        output.add(2);
        output.add(3);
        assertEquals(output, oddVertices);
    }

    @Test
    public void minimumMatchingTest() {
        double INF = Double.POSITIVE_INFINITY;
        double[][] graph = {
                {0, 2, 3, INF},
                {2, 0, INF, 5},
                {3, INF, 0, 4},
                {INF, 5, 4, 0}
        };
        double[][] output = {
                {0,2,0,0},
                {2,0,0,0},
                {0,0,0,0},
                {0,0,0,0}
        };
        List<Integer> oddVertices = Christofides.getOddVertices(graph);
        double[][] value = Christofides.getMinimumMatching(graph, oddVertices);
        assertEquals(output, value);
    }

    @Test
    public void eulerTourTest() {
        double INF = Double.POSITIVE_INFINITY;
        double[][] graph = {
                {0, 2, 3, INF},
                {2, 0, INF, 5},
                {3, INF, 0, 4},
                {INF, 5, 4, 0}
        };
        List<Integer> output = new ArrayList<>();
        output.add(1);
        output.add(2);
        output.add(3);
        output.add(0);
        output.add(2);
        output.add(1);
        output.add(0);
        List<Integer> value = Christofides.getEulerTour(graph, 0);
        assertEquals(output, value);
    }

    @Test
    public void hamiltonianTourTest() {
        double INF = Double.POSITIVE_INFINITY;
        double[][] graph = {
                {0, 2, 3, INF},
                {2, 0, INF, 5},
                {3, INF, 0, 4},
                {INF, 5, 4, 0}
        };
        List<Integer> output = new ArrayList<>();
        output.add(1);
        output.add(2);
        output.add(3);
        output.add(0);
        output.add(1);
        List<Integer> eulerTour = Christofides.getEulerTour(graph, 0);
        List<Integer> value = Christofides.getHamiltonianTour(eulerTour);
        assertEquals(output, value);
    }
}
