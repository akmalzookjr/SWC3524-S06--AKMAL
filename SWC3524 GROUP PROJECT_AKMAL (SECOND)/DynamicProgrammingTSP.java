// DynamicProgrammingTSP.java
import java.util.*;

public class DynamicProgrammingTSP implements TSPAlgorithm {
    private String name = "Dynamic Programming";
    private String timeComplexity = "O(n^2 * 2^n)";
    private String spaceComplexity = "O(n * 2^n)";
    
    @Override
    public String solve(int[][] dist, String[] locations) {
        int n = dist.length;
        int VISITED_ALL = (1 << n) - 1;
        int[][] memo = new int[n][1 << n];
        int[][] path = new int[n][1 << n];
        
        // Initialize memo with -1
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        
        int minDistance = tsp(0, 1, dist, memo, path, VISITED_ALL);
        
        // Reconstruct path
        List<Integer> bestPath = reconstructPath(path, n);
        
        // Build route string
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < bestPath.size(); i++) {
            if (i > 0) route.append(" -> ");
            route.append(locations[bestPath.get(i)]);
        }
        
        return route.toString() + " | Total Distance: " + minDistance + " nm";
    }
    
    private int tsp(int pos, int mask, int[][] dist, int[][] memo, int[][] path, int VISITED_ALL) {
        if (mask == VISITED_ALL) {
            return dist[pos][0];
        }
        
        if (memo[pos][mask] != -1) {
            return memo[pos][mask];
        }
        
        int ans = Integer.MAX_VALUE;
        int bestNext = -1;
        
        for (int next = 0; next < dist.length; next++) {
            if ((mask & (1 << next)) == 0) {
                int newAns = dist[pos][next] + tsp(next, mask | (1 << next), dist, memo, path, VISITED_ALL);
                if (newAns < ans) {
                    ans = newAns;
                    bestNext = next;
                }
            }
        }
        
        path[pos][mask] = bestNext;
        memo[pos][mask] = ans;
        return ans;
    }
    
    private List<Integer> reconstructPath(int[][] path, int n) {
        List<Integer> result = new ArrayList<>();
        int current = 0;
        int mask = 1;
        
        result.add(current);
        
        for (int i = 1; i < n; i++) {
            int next = path[current][mask];
            result.add(next);
            mask |= (1 << next);
            current = next;
        }
        
        result.add(0); // Return to start
        return result;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getTimeComplexity() {
        return timeComplexity;
    }
    
    @Override
    public String getSpaceComplexity() {
        return spaceComplexity;
    }
}