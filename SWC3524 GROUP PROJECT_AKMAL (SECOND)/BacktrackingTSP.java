// BacktrackingTSP.java
import java.util.*;

public class BacktrackingTSP implements TSPAlgorithm {
    private String name = "Backtracking";
    private String timeComplexity = "O(n!)";
    private String spaceComplexity = "O(n)";
    private int minDistance;
    private List<Integer> bestPath;
    
    @Override
    public String solve(int[][] dist, String[] locations) {
        int n = dist.length;
        boolean[] visited = new boolean[n];
        List<Integer> currentPath = new ArrayList<>();
        minDistance = Integer.MAX_VALUE;
        bestPath = new ArrayList<>();
        
        // Start from location 0
        visited[0] = true;
        currentPath.add(0);
        
        backtrack(0, 0, 1, dist, visited, currentPath, n);
        
        // Build route string
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < bestPath.size(); i++) {
            if (i > 0) route.append(" -> ");
            route.append(locations[bestPath.get(i)]);
        }
        route.append(" -> ").append(locations[0]); // Return to start
        
        return route.toString() + " | Total Distance: " + minDistance + " nm";
    }
    
    private void backtrack(int current, int currentDist, int count, 
                          int[][] dist, boolean[] visited, 
                          List<Integer> currentPath, int n) {
        if (count == n) {
            // Complete cycle - return to start
            int totalDist = currentDist + dist[current][0];
            if (totalDist < minDistance) {
                minDistance = totalDist;
                bestPath = new ArrayList<>(currentPath);
            }
            return;
        }
        
        for (int next = 0; next < n; next++) {
            if (!visited[next] && dist[current][next] > 0) {
                visited[next] = true;
                currentPath.add(next);
                
                backtrack(next, currentDist + dist[current][next], count + 1, 
                         dist, visited, currentPath, n);
                
                // Backtrack
                visited[next] = false;
                currentPath.remove(currentPath.size() - 1);
            }
        }
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