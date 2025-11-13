// GreedyTSP.java
import java.util.ArrayList;
import java.util.List;

public class GreedyTSP implements TSPAlgorithm {
    private String name = "Greedy Algorithm";
    private String timeComplexity = "O(n^2)";
    private String spaceComplexity = "O(n)";
    
    @Override
    public String solve(int[][] dist, String[] locations) {
        int n = dist.length;
        boolean[] visited = new boolean[n];
        List<Integer> path = new ArrayList<>(); // Changed to ArrayList
        
        // Start from first location (Port A)
        int current = 0;
        path.add(current);
        visited[current] = true;
        
        int totalDistance = 0;
        
        // Visit all locations using greedy approach
        for (int i = 1; i < n; i++) {
            int next = -1;
            int minDist = Integer.MAX_VALUE;
            
            // Find nearest unvisited neighbor
            for (int j = 0; j < n; j++) {
                if (!visited[j] && dist[current][j] < minDist && dist[current][j] > 0) {
                    minDist = dist[current][j];
                    next = j;
                }
            }
            
            if (next != -1) {
                path.add(next);
                visited[next] = true;
                totalDistance += minDist;
                current = next;
            }
        }
        
        // Return to start
        totalDistance += dist[current][0];
        path.add(0);
        
        // Build route string
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) route.append(" -> ");
            route.append(locations[path.get(i)]);
        }
        
        return route.toString() + " | Total Distance: " + totalDistance + " nm";
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