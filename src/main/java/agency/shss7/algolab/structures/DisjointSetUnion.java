package agency.shss7.algolab.structures;

/**
 * Disjoint Set Union (Union-Find) with path compression and union by rank.
 * Supports near-constant O(alpha(N)) connectivity queries and Kruskal's MST.
 */
public class DisjointSetUnion {

    private final int[] parent;
    private final int[] rank;
    private int componentCount;

    public DisjointSetUnion(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }
        this.parent = new int[size];
        this.rank = new int[size];
        this.componentCount = size;
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    public int find(int node) {
        validate(node);
        int root = node;
        while (root != parent[root]) {
            root = parent[root];
        }
        // Path compression
        int curr = node;
        while (curr != root) {
            int next = parent[curr];
            parent[curr] = root;
            curr = next;
        }
        return root;
    }

    public boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA == rootB) {
            return false;
        }
        if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
        } else if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }
        componentCount--;
        return true;
    }

    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    public int componentCount() {
        return componentCount;
    }

    private void validate(int node) {
        if (node < 0 || node >= parent.length) {
            throw new IndexOutOfBoundsException("Node index out of bounds: " + node);
        }
    }
}
