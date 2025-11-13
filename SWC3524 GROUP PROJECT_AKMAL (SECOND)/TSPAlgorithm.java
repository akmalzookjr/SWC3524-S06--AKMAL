// TSPAlgorithm.java
public interface TSPAlgorithm {
    String solve(int[][] distanceMatrix, String[] locations);
    String getName();
    String getTimeComplexity();
    String getSpaceComplexity();
}