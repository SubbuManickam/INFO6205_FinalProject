package org.project.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisjointSet<T> {
    private Map<T, T> parent;
    private Map<T, Integer> rank;
    private int count;

    public DisjointSet(List<T> nodes) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        count = nodes.size();
        for (T node : nodes) {
            parent.put(node, node);
            rank.put(node, 0);
        }
    }

    public T find(T x) {
        T p = parent.get(x);
        if (p == x) {
            return x;
        }
        T root = find(p);
        parent.put(x, root);
        return root;
    }

    public void union(T x, T y) {
        T px = find(x);
        T py = find(y);
        if (px == py) {
            return;
        }
        if (rank.get(px) > rank.get(py)) {
            parent.put(py, px);
        } else if (rank.get(px) < rank.get(py)) {
            parent.put(px, py);
        } else {
            parent.put(py, px);
            rank.put(px, rank.get(px) + 1);
        }
        count--;
    }

    public int count() {
        return count;
    }
}
