// ReliefRouteUI.java
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ReliefRouteUI extends JFrame {
    // Distance Matrix
    private int[][] distanceMatrix = {
        {0, 15, 25, 35},
        {15, 0, 30, 28},
        {25, 30, 0, 20},
        {35, 28, 20, 0}
    };
    
    // Location names
    private String[] locations = {"Port A", "Port B", "Relief Center C", "Relief Center D"};
    
    // Algorithms
    private List<TSPAlgorithm> algorithms;
    
    // UI Components
    private JTextArea outputArea;
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    
    public ReliefRouteUI() {
        initializeAlgorithms();
        initializeUI();
    }
    
    private void initializeAlgorithms() {
        algorithms = new ArrayList<>();
        algorithms.add(new GreedyTSP());
        algorithms.add(new DynamicProgrammingTSP());
        algorithms.add(new BacktrackingTSP());
        algorithms.add(new DivideConquerTSP());
    }
    
    private void initializeUI() {
        setTitle("International Maritime Relief Mission - Route Optimization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 700));
        
        // Center the window
        centerWindow();
        
        // Create main panel with border
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 245, 255));
        
        // Create header
        createHeader();
        
        // Create output area
        createOutputArea();
        
        // Create button panel
        createButtonPanel();
        
        add(mainPanel);
        pack();
    }
    
    private void centerWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }
    
    private void createHeader() {
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
        
        JLabel subtitleLabel = new JLabel("Optimizing Humanitarian Aid Delivery Routes", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(200, 220, 255));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createOutputArea() {
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
        
        outputArea = new JTextArea(20, 60);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(250, 255, 255));
        outputArea.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 240)));
        
        scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        outputPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(outputPanel, BorderLayout.CENTER);
    }
    
    private void createButtonPanel() {
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
        buttonPanel.add(createStyledButton("Greedy", new Color(65, 105, 225), e -> runAlgorithm(0)));
        buttonPanel.add(createStyledButton("Dynamic Prog", new Color(220, 20, 60), e -> runAlgorithm(1)));
        buttonPanel.add(createStyledButton("Backtracking", new Color(255, 140, 0), e -> runAlgorithm(2)));
        
        // Second row of buttons
        buttonPanel.add(createStyledButton("Divide & Conquer", new Color(138, 43, 226), e -> runAlgorithm(3)));
        buttonPanel.add(createStyledButton("Compare All", new Color(50, 205, 50), e -> compareAlgorithms()));
        buttonPanel.add(createStyledButton("Show Matrix", new Color(70, 130, 180), e -> displayDistanceMatrix()));
        buttonPanel.add(createStyledButton("Clear", Color.GRAY, e -> clearOutput()));
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JButton createStyledButton(String text, Color color, ActionListener action) {
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
    
    private void runAlgorithm(int index) {
        if (index >= 0 && index < algorithms.size()) {
            TSPAlgorithm algorithm = algorithms.get(index);
            
            appendOutput("============================================================", Color.BLACK);
            appendOutput("RUNNING: " + algorithm.getName(), new Color(30, 80, 150));
            appendOutput("Time Complexity: " + algorithm.getTimeComplexity(), Color.DARK_GRAY);
            appendOutput("Space Complexity: " + algorithm.getSpaceComplexity(), Color.DARK_GRAY);
            appendOutput("", Color.BLACK);
            
            // Use Thread instead of SwingWorker for simplicity in BlueJ
            new Thread(() -> {
                long startTime = System.currentTimeMillis();
                String result = algorithm.solve(distanceMatrix, locations);
                long endTime = System.currentTimeMillis();
                
                SwingUtilities.invokeLater(() -> {
                    appendOutput("RESULT:", Color.GREEN.darker());
                    appendOutput(result, Color.BLACK);
                    appendOutput("Execution Time: " + (endTime - startTime) + " ms", Color.BLUE);
                    appendOutput("", Color.BLACK);
                });
            }).start();
        }
    }
    
    private void runAllAlgorithms() {
        clearOutput();
        appendOutput("STARTING COMPREHENSIVE ALGORITHM ANALYSIS", new Color(30, 80, 150));
        appendOutput("", Color.BLACK);
        
        new Thread(() -> {
            for (int i = 0; i < algorithms.size(); i++) {
                TSPAlgorithm algorithm = algorithms.get(i);
                
                final int currentIndex = i;
                SwingUtilities.invokeLater(() -> {
                    appendOutput("============================================================", Color.BLACK);
                    appendOutput((currentIndex + 1) + ". " + algorithm.getName(), new Color(30, 80, 150));
                    appendOutput("Complexity: " + algorithm.getTimeComplexity() + " / " + algorithm.getSpaceComplexity(), Color.DARK_GRAY);
                });
                
                long startTime = System.currentTimeMillis();
                String result = algorithm.solve(distanceMatrix, locations);
                long endTime = System.currentTimeMillis();
                
                SwingUtilities.invokeLater(() -> {
                    appendOutput("Route: " + result.split(" \\| ")[0], new Color(0, 100, 0));
                    appendOutput("Distance: " + result.split(" \\| ")[1], new Color(139, 0, 0));
                    appendOutput("Time: " + (endTime - startTime) + " ms", new Color(128, 0, 128));
                    appendOutput("", Color.BLACK);
                });
                
                try {
                    Thread.sleep(500); // Small delay for visual effect
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            SwingUtilities.invokeLater(() -> {
                appendOutput("ALL ALGORITHMS COMPLETED SUCCESSFULLY", Color.GREEN.darker());
                appendOutput("", Color.BLACK);
            });
        }).start();
    }
    
    private void compareAlgorithms() {
        clearOutput();
        appendOutput("ALGORITHM COMPARISON ANALYSIS", new Color(30, 80, 150));
        appendOutput("", Color.BLACK);
        
        // Header
        appendOutput(String.format("%-25s %-20s %-20s %-15s", 
            "ALGORITHM", "TIME COMPLEXITY", "SPACE COMPLEXITY", "EXECUTION TIME"), 
            new Color(70, 130, 180));
        appendOutput("-------------------------------------------------------------------------------", Color.GRAY);
        
        new Thread(() -> {
            for (TSPAlgorithm algorithm : algorithms) {
                long startTime = System.currentTimeMillis();
                algorithm.solve(distanceMatrix, locations);
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                
                final String line = String.format("%-25s %-20s %-20s %-15d ms",
                    algorithm.getName(),
                    algorithm.getTimeComplexity(),
                    algorithm.getSpaceComplexity(),
                    executionTime);
                
                SwingUtilities.invokeLater(() -> {
                    appendOutput(line, Color.BLACK);
                });
                
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            SwingUtilities.invokeLater(() -> {
                appendOutput("", Color.BLACK);
                appendOutput("Comparison completed. Greedy algorithm is fastest for small datasets.", 
                           new Color(0, 100, 0));
            });
        }).start();
    }
    
    private void displayDistanceMatrix() {
        clearOutput();
        appendOutput("DISTANCE MATRIX (nautical miles)", new Color(30, 80, 150));
        appendOutput("", Color.BLACK);
        
        // Header row
        StringBuilder header = new StringBuilder(String.format("%-15s", ""));
        for (String location : locations) {
            header.append(String.format("%-18s", location));
        }
        appendOutput(header.toString(), new Color(70, 130, 180));
        
        appendOutput("-------------------------------------------------------------------------------", Color.GRAY);
        
        // Data rows
        for (int i = 0; i < distanceMatrix.length; i++) {
            StringBuilder row = new StringBuilder(String.format("%-15s", locations[i]));
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                row.append(String.format("%-18d", distanceMatrix[i][j]));
            }
            appendOutput(row.toString(), (i % 2 == 0) ? Color.BLACK : new Color(60, 60, 60));
        }
    }
    
    private void clearOutput() {
        outputArea.setText("");
    }
    
    private void appendOutput(String text, Color color) {
        SwingUtilities.invokeLater(() -> {
            outputArea.setForeground(color);
            outputArea.append(text + "\n");
            // Auto-scroll to bottom
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReliefRouteUI ui = new ReliefRouteUI();
            ui.setVisible(true);
        });
    }
}