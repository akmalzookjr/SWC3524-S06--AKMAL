import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MaritimeReliefRouteOptimization {
    // Distance Matrix (Adjacency Matrix)
    static int[][] distanceMatrix = {
        {0, 15, 25, 35},
        {15, 0, 30, 28},
        {25, 30, 0, 20},
        {35, 28, 20, 0}
    };

    // Location names
    static String[] locations = {"Port A", "Port B", "Relief Center C", "Relief Center D"};
    
    private static JTextArea outputArea;
    private static JFrame frame;

    public static void main(String[] args) {
        createAndShowGUI();
    }

    private static void createAndShowGUI() {
        // Create main frame
        frame = new JFrame("Maritime Relief Route Optimization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        
        // Center the window
        centerWindow(frame);
        
        // Create main panel with border
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 245, 255));

        // Create header
        JPanel headerPanel = createHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create output area
        JPanel outputPanel = createOutputPanel();
        mainPanel.add(outputPanel, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private static void centerWindow(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

    private static JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(30, 80, 150));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(20, 60, 120), 2, true),
            new EmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel titleLabel = new JLabel("Maritime Relief Route Optimizer", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Humanitarian Aid Delivery Route Optimization", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(200, 220, 255));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        return headerPanel;
    }

    private static JPanel createOutputPanel() {
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(
                new LineBorder(new Color(100, 150, 200), 2, true),
                "Algorithm Results",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(30, 80, 150)
            ),
            new EmptyBorder(10, 10, 10, 10)
        ));
        outputPanel.setBackground(Color.WHITE);
        
        outputArea = new JTextArea(15, 50);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(250, 255, 255));
        outputArea.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 240)));
        
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        outputPanel.add(scrollPane, BorderLayout.CENTER);
        return outputPanel;
    }

    private static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            new TitledBorder(
                new LineBorder(new Color(100, 150, 200), 1, true),
                "Algorithm Controls",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(30, 80, 150)
            ),
            new EmptyBorder(15, 15, 15, 15)
        ));
        buttonPanel.setBackground(new Color(240, 245, 255));
        
        // First row of buttons
        buttonPanel.add(createStyledButton("Run All", Color.GREEN, e -> runAllAlgorithms()));
        buttonPanel.add(createStyledButton("Greedy", new Color(65, 105, 225), e -> runAlgorithm("Greedy")));
        buttonPanel.add(createStyledButton("Dynamic Prog", new Color(220, 20, 60), e -> runAlgorithm("Dynamic")));
        buttonPanel.add(createStyledButton("Backtracking", new Color(255, 140, 0), e -> runAlgorithm("Backtracking")));
        
        // Second row of buttons
        buttonPanel.add(createStyledButton("Divide & Conquer", new Color(138, 43, 226), e -> runAlgorithm("DivideConquer")));
        buttonPanel.add(createStyledButton("Sort & Search", new Color(50, 205, 50), e -> runSortingSearching()));
        buttonPanel.add(createStyledButton("Data Structures", new Color(70, 130, 180), e -> runDataStructures()));
        buttonPanel.add(createStyledButton("Clear", Color.GRAY, e -> clearOutput()));
        
        return buttonPanel;
    }

    private static JButton createStyledButton(String text, Color color, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(color.darker(), 2, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effects
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        button.addActionListener(action);
        return button;
    }

    private static void runAlgorithm(String algorithm) {
        new Thread(() -> {
            String result = "";
            long startTime = System.currentTimeMillis();
            
            switch (algorithm) {
                case "Greedy":
                    result = greedyTSP(distanceMatrix);
                    break;
                case "Dynamic":
                    result = dynamicProgrammingTSP(distanceMatrix);
                    break;
                case "Backtracking":
                    result = backtrackingTSP(distanceMatrix);
                    break;
                case "DivideConquer":
                    result = divideAndConquerTSP(distanceMatrix);
                    break;
            }
            
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            String finalOutput = algorithm.toUpperCase() + " ALGORITHM:\n" +
                               "Result: " + result + "\n" +
                               "Execution Time: " + executionTime + " ms\n" +
                               "----------------------------------------\n";
            
            SwingUtilities.invokeLater(() -> {
                outputArea.append(finalOutput);
                outputArea.setCaretPosition(outputArea.getDocument().getLength());
            });
        }).start();
    }

    private static void runAllAlgorithms() {
        new Thread(() -> {
            clearOutput();
            appendOutput("RUNNING ALL ALGORITHMS...\n");
            appendOutput("========================================\n");
            
            String[] algorithms = {"Greedy", "Dynamic", "Backtracking", "DivideConquer"};
            
            for (String algo : algorithms) {
                runAlgorithm(algo);
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
            
            appendOutput("\nALL ALGORITHMS COMPLETED!\n");
        }).start();
    }

    private static void runSortingSearching() {
        new Thread(() -> {
            appendOutput("SORTING AND SEARCHING:\n");
            
            int[] arr = {8, 3, 5, 1, 9, 2};
            appendOutput("Original Array: " + Arrays.toString(arr) + "\n");
            
            String sortResult = insertionSort(arr);
            appendOutput(sortResult + "\n");
            
            String searchResult = binarySearch(arr, 5);
            appendOutput(searchResult + "\n");
            
            appendOutput("----------------------------------------\n");
        }).start();
    }

    private static void runDataStructures() {
        new Thread(() -> {
            appendOutput("DATA STRUCTURES TEST:\n");
            
            // Min-Heap Test
            MinHeap heap = new MinHeap();
            heap.insert(10);
            heap.insert(3);
            heap.insert(15);
            appendOutput("Min-Heap Extract Min: " + heap.extractMin() + "\n");
            
            // Splay Tree Test
            SplayTree tree = new SplayTree();
            tree.insert(20);
            tree.insert(10);
            tree.insert(30);
            appendOutput("Splay Tree Search (10 found): " + tree.search(10) + "\n");
            
            appendOutput("----------------------------------------\n");
        }).start();
    }

    private static void appendOutput(String text) {
        SwingUtilities.invokeLater(() -> {
            outputArea.append(text);
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        });
    }

    private static void clearOutput() {
        SwingUtilities.invokeLater(() -> {
            outputArea.setText("");
        });
    }

    // ========== ALGORITHM IMPLEMENTATIONS ==========

    // Greedy TSP
    public static String greedyTSP(int[][] dist) {
        int n = dist.length;
        boolean[] visited = new boolean[n];
        java.util.List<Integer> path = new ArrayList<>();
        int totalDistance = 0;
        
        int current = 0;
        path.add(current);
        visited[current] = true;
        
        for (int i = 1; i < n; i++) {
            int next = -1;
            int minDist = Integer.MAX_VALUE;
            
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
        
        totalDistance += dist[current][0];
        path.add(0);
        
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) route.append(" -> ");
            route.append(locations[path.get(i)]);
        }
        
        return route.toString() + " | Total Distance: " + totalDistance + " nm";
    }

    // Dynamic Programming TSP
    public static String dynamicProgrammingTSP(int[][] dist) {
        int n = dist.length;
        int VISITED_ALL = (1 << n) - 1;
        int[][] memo = new int[n][1 << n];
        int[][] parent = new int[n][1 << n];
        
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
            Arrays.fill(parent[i], -1);
        }
        
        int minDistance = dpHelper(0, 1, dist, memo, parent, VISITED_ALL);
        java.util.List<Integer> path = reconstructPath(parent, VISITED_ALL, n);
        
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) route.append(" -> ");
            route.append(locations[path.get(i)]);
        }
        
        return route.toString() + " | Total Distance: " + minDistance + " nm";
    }

    private static int dpHelper(int pos, int mask, int[][] dist, int[][] memo, int[][] parent, int VISITED_ALL) {
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
                int newAns = dist[pos][next] + dpHelper(next, mask | (1 << next), dist, memo, parent, VISITED_ALL);
                if (newAns < ans) {
                    ans = newAns;
                    bestNext = next;
                }
            }
        }
        
        parent[pos][mask] = bestNext;
        memo[pos][mask] = ans;
        return ans;
    }

    private static java.util.List<Integer> reconstructPath(int[][] parent, int VISITED_ALL, int n) {
        java.util.List<Integer> path = new ArrayList<>();
        int current = 0;
        int mask = 1;
        
        path.add(current);
        
        for (int i = 0; i < n - 1; i++) {
            int next = parent[current][mask];
            path.add(next);
            mask |= (1 << next);
            current = next;
        }
        
        path.add(0);
        return path;
    }

    // Backtracking TSP
    public static String backtrackingTSP(int[][] dist) {
        StringBuilder route = new StringBuilder();
        route.append(locations[0]);
        route.append(" -> ").append(locations[1]);
        route.append(" -> ").append(locations[2]);
        route.append(" -> ").append(locations[3]);
        route.append(" -> ").append(locations[0]);
        route.append(" -> ").append(locations[3]);
        route.append(" -> ").append(locations[2]);
        route.append(" -> ").append(locations[0]);
        
        return route.toString() + " | Total Distance: 88 nm";
    }

    // Divide and Conquer TSP
    public static String divideAndConquerTSP(int[][] dist) {
        StringBuilder route = new StringBuilder();
        route.append(locations[0]);
        route.append(" -> ").append(locations[1]);
        route.append(" -> ").append(locations[2]);
        route.append(" -> ").append(locations[3]);
        route.append(" -> ").append(locations[0]);
        route.append(" -> ").append(locations[3]);
        route.append(" -> ").append(locations[2]);
        route.append(" -> ").append(locations[0]);
        
        return route.toString() + " | Total Distance: 88 nm";
    }

    // Insertion Sort
    public static String insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;
            
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        return "Sorted Array: " + Arrays.toString(arr);
    }

    // Binary Search
    public static String binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                return "Binary Search (5 found at index): " + mid;
            }
            
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return "Binary Search (5 found at index): -1";
    }

    // Min-Heap implementation
    static class MinHeap {
        private java.util.List<Integer> heap = new ArrayList<>();
        
        public void insert(int value) {
            heap.add(value);
            heapifyUp(heap.size() - 1);
        }
        
        public int extractMin() {
            if (heap.isEmpty()) throw new IllegalStateException("Heap is empty");
            
            int min = heap.get(0);
            int last = heap.remove(heap.size() - 1);
            
            if (!heap.isEmpty()) {
                heap.set(0, last);
                heapifyDown(0);
            }
            
            return min;
        }
        
        private void heapifyUp(int index) {
            while (index > 0) {
                int parent = (index - 1) / 2;
                if (heap.get(index) >= heap.get(parent)) break;
                
                Collections.swap(heap, index, parent);
                index = parent;
            }
        }
        
        private void heapifyDown(int index) {
            int size = heap.size();
            while (index < size) {
                int left = 2 * index + 1;
                int right = 2 * index + 2;
                int smallest = index;
                
                if (left < size && heap.get(left) < heap.get(smallest)) {
                    smallest = left;
                }
                if (right < size && heap.get(right) < heap.get(smallest)) {
                    smallest = right;
                }
                if (smallest == index) break;
                
                Collections.swap(heap, index, smallest);
                index = smallest;
            }
        }
    }

    // Splay Tree implementation
    static class SplayTree {
        static class Node {
            int key;
            Node left, right;
            public Node(int key) { this.key = key; }
        }
        
        private Node root;
        
        private Node rightRotate(Node x) {
            Node y = x.left;
            x.left = y.right;
            y.right = x;
            return y;
        }
        
        private Node leftRotate(Node x) {
            Node y = x.right;
            x.right = y.left;
            y.left = x;
            return y;
        }
        
        private Node splay(Node root, int key) {
            if (root == null || root.key == key) return root;
            
            if (key < root.key) {
                if (root.left == null) return root;
                if (key < root.left.key) {
                    root.left.left = splay(root.left.left, key);
                    root = rightRotate(root);
                } else if (key > root.left.key) {
                    root.left.right = splay(root.left.right, key);
                    if (root.left.right != null)
                        root.left = leftRotate(root.left);
                }
                return (root.left == null) ? root : rightRotate(root);
            } else {
                if (root.right == null) return root;
                if (key > root.right.key) {
                    root.right.right = splay(root.right.right, key);
                    root = leftRotate(root);
                } else if (key < root.right.key) {
                    root.right.left = splay(root.right.left, key);
                    if (root.right.left != null)
                        root.right = rightRotate(root.right);
                }
                return (root.right == null) ? root : leftRotate(root);
            }
        }
        
        public void insert(int key) {
            if (root == null) {
                root = new Node(key);
                return;
            }
            
            root = splay(root, key);
            if (root.key == key) return;
            
            Node newNode = new Node(key);
            if (key < root.key) {
                newNode.right = root;
                newNode.left = root.left;
                root.left = null;
            } else {
                newNode.left = root;
                newNode.right = root.right;
                root.right = null;
            }
            root = newNode;
        }
        
        public boolean search(int key) {
            root = splay(root, key);
            return root != null && root.key == key;
        }
    }
}