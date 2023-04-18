import org.junit.Test;
import org.project.algorithm.MinimumSpanningTree;

import static org.junit.Assert.assertEquals;

public class MstTest {

    @Test
    public void MSTCostTest() {
        double INF = Double.POSITIVE_INFINITY;
        double[][] graph = {
                {0, 2, 3, INF},
                {2, 0, INF, 5},
                {3, INF, 0, 4},
                {INF, 5, 4, 0}
        };
        double[][] value = MinimumSpanningTree.primMST(graph);
        double cost = MinimumSpanningTree.calculateMSTCost(value);
        assertEquals(9.0, cost, 1.0);
    }

    @Test
    public void primMSTTest() {
        double INF = Double.POSITIVE_INFINITY;
        double[][] graph = {
                {0, 2, 3, INF},
                {2, 0, INF, 5},
                {3, INF, 0, 4},
                {INF, 5, 4, 0}
        };
        double[][] output = {
                {0,2,3,0},
                {2,0,0,0},
                {3,0,0,4},
                {0,0,4,0}
        };
        double[][] value = MinimumSpanningTree.primMST(graph);
        assertEquals(output, value);
    }
}
