package budgetbuddy;

import javax.swing.*;
import java.awt.*;

import org.jfree.data.general.DefaultPieDataset;
import java.sql.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.data.category.DefaultCategoryDataset;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.renderer.category.BarRenderer;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.HashMap;

public class Dashboard extends javax.swing.JFrame {

    /*
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 845, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 629, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
*/
    private static final String DEFAULT_USERNAME = "Guest";

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new Dashboard(DEFAULT_USERNAME).setVisible(true));
    }

    public Dashboard(String username) {

        setTitle("BudgetBuddy - Dashboard");
        setSize(1900, 820);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topBar.setBackground(new Color(230, 230, 230));
        topBar.setPreferredSize(new Dimension(1190, 70));

        ImageIcon logo = new ImageIcon(new ImageIcon("D:\\Drive E\\NetBeansProjects\\BudgetBuddy\\src\\budgetbuddy\\logo1.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel logoLabel = new JLabel(logo);

        JLabel projectNameLabel = new JLabel("BudgetBuddy - Expense Analysis");
        projectNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        projectNameLabel.setForeground(Color.DARK_GRAY);

        // Create Notification Bell Button as a Normal Button 
        JButton notificationBell = new JButton("🔔 Notifications"); // Using Unicode Bell Icon as Text
        notificationBell.setBorderPainted(true);  // Keep visible border
        notificationBell.setContentAreaFilled(true);
        notificationBell.setFocusPainted(false);
        notificationBell.setToolTipText("View Notifications & Alerts");

        // Open Notification & Alerts Frame on Click
        notificationBell.addActionListener(e -> {
            AlertsNotifications notification = new AlertsNotifications(); // Your existing frame
            notification.setVisible(true);
        });

        // Ensures that topBar uses BoxLayout for proper alignment
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));

        // Add to the right corner of the horizontal bar
        topBar.add(logoLabel);
        topBar.add(projectNameLabel);
        add(topBar, BorderLayout.NORTH);
        // Push notification button to the right side
        topBar.add(Box.createHorizontalGlue()); // Pushes components to the right
        topBar.add(notificationBell);

        /*
        // Add to the right corner of the horizontal bar
        topBar.add(notificationBell, BorderLayout.SOUTH); // Assuming horizontalBarPanel is your top bar
         */
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 700));
        sidebar.setBackground(new Color(30, 30, 30));

        ImageIcon userIcon = new ImageIcon(new ImageIcon("D:\\Drive E\\NetBeansProjects\\BudgetBuddy\\src\\budgetbuddy\\signup.png")
                .getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
        JLabel userLabel = new JLabel(userIcon);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Welcome  " + username, SwingConstants.CENTER);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(userLabel);
        sidebar.add(usernameLabel);
        sidebar.add(Box.createVerticalGlue());

        // Sidebar Menu Items
        String[] menuItems = {"Home (Dashboard)", "Add Transactions", "View Transactions", "Budget Management", "Saving Goals", "Reports", "Settings", "Log Out"};

        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(200, 40));
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            sidebar.add(btn);

            // Add action listener for Log Out button
            if (item.equals("Log Out")) {
                btn.addActionListener(e -> {
                    int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        dispose();  // Close the Dashboard
                        System.out.println("Successfully Redirected to Login page!");
                        new Login().setVisible(true);
                    }
                });
            }

            if (item.equals("Home (Dashboard)")) {
                btn.addActionListener(e -> {
                    dispose();  // Close current window
                    new Dashboard("Guest").setVisible(true);  // Open Dashboard Frame
                });
            }

            if (item.equals("Add Transactions")) {
                btn.addActionListener(e -> {
                    new AddTransactions().setVisible(true); 
                });
            }

            if (item.equals("View Transactions")) {
                btn.addActionListener(e -> new ViewTransactions().setVisible(true));
            }

            if (item.equals("Reports")) {
                btn.addActionListener(e -> new Reports(DEFAULT_USERNAME).setVisible(true));
            }
            if (item.equals("Settings")) {
                btn.addActionListener(e -> new Settings().setVisible(true));
            }
            if (item.equals("Alerts & Notifications")) {
                btn.addActionListener(e -> new AlertsNotifications().setVisible(true));
            }
            if (item.equals("Budget Management")) {
                btn.addActionListener(e -> new BudgetManagement().setVisible(true));
            }

            if (item.equals("Saving Goals")) {
                btn.addActionListener(e -> new SavingGoals().setVisible(true));
            }

            add(sidebar, BorderLayout.WEST);

            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBackground(Color.WHITE);

            // Summary Panel
            JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 10));
            summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

// Fetch data from the database for summary cards with error handling
            String totalExpenses = (getTotalExpenses() != null) ? getTotalExpenses() : "0";
            String expensesThisMonth = (getExpensesThisMonth() != null) ? getExpensesThisMonth() : "0";
            String mostSpending = (getMostSpending() != null) ? getMostSpending() : "N/A";
// ✅ Update UI to display only the logged-in user's data
            summaryPanel.add(createSummaryCard("Total Expenses", "₹" + totalExpenses, calculateMonthlyChange() + " from last month", Color.RED));
            summaryPanel.add(createSummaryCard("Expenses This Month", "₹" + expensesThisMonth, calculateMonthlyChange() + " from last month", Color.RED));
            summaryPanel.add(createSummaryCard("Most Spending", mostSpending, "", Color.RED));

            JPanel topSectionPanel = new JPanel(new BorderLayout(10, 10));

// Panel to hold Current Balance & Recent Transactions
            JPanel balanceAndTransactionsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
            balanceAndTransactionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            JPanel balancePanel = new JPanel(new BorderLayout());
            balancePanel.setBorder(BorderFactory.createTitledBorder("Current Balance"));

// Fetch current balance from the database with error handling
            String currentBalance = (getCurrentBalance() != null) ? getCurrentBalance() : "0";
            JButton balanceButton = new JButton("₹" + currentBalance); // Display balance as currency
            balanceButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
            balanceButton.setFocusPainted(false);
            balanceButton.setBorderPainted(false);
            balanceButton.setContentAreaFilled(false);
            balancePanel.add(balanceButton, BorderLayout.CENTER);

// Set Balance Button with  validation
            JButton setBalanceButton = new JButton("Set Balance");
            setBalanceButton.addActionListener(e -> {
                String newBalance = JOptionPane.showInputDialog(null, "Enter new balance:", "Set Balance", JOptionPane.PLAIN_MESSAGE);
                if (newBalance != null && !newBalance.isEmpty()) {
                    try {
                        double transactionAmount = Double.parseDouble(newBalance);
                        if (transactionAmount < 0) {
                            JOptionPane.showMessageDialog(null, "Balance cannot be negative!", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return; // Prevent updating negative balance
                        }
                        updateBalance(transactionAmount);
                        balanceButton.setText("₹" + getCurrentBalance()); // Refresh balance display
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid balance format. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            balancePanel.add(setBalanceButton, BorderLayout.SOUTH);

// ActionListener for Current Balance Breakdown
            balanceButton.addActionListener(e -> JOptionPane.showMessageDialog(null,
                    getBalanceBreakdown(),
                    "Current Balance Breakdown", JOptionPane.INFORMATION_MESSAGE));

// Create transactions panel dynamically
            JPanel transactionsPanel = new JPanel(new GridLayout(5, 1));
            transactionsPanel.setBorder(BorderFactory.createTitledBorder("Recent Transactions"));

// Get logged-in user's recent transactions
            List<String> transactions = getRecentTransactions();

// Check if transactions exist
            if (transactions.isEmpty()) {
                JLabel noTransactionLabel = new JLabel("No recent transactions.");
                noTransactionLabel.setFont(new Font("Times New Roman", Font.ITALIC, 14));
                transactionsPanel.add(noTransactionLabel);
            } else {
                // Create buttons for each transaction
                for (String transaction : transactions) {
                    JButton transactionButton = new JButton(transaction);
                    transactionButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                    transactionButton.setFocusPainted(false);
                    transactionButton.setBorderPainted(false);
                    transactionButton.setContentAreaFilled(false);

                    // Show transaction details when clicked
                    transactionButton.addActionListener(e -> {
                        int choice = JOptionPane.showOptionDialog(null,
                                "Choose an action for: " + transaction,
                                "Transaction Details",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                new String[]{"View Details", "Cancel"},
                                "View Details");

                        if (choice == 0) {
                            JOptionPane.showMessageDialog(null,
                                    "Details for transaction: " + transaction,
                                    "Transaction Information",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    });

                    transactionsPanel.add(transactionButton);
                }
            }

// Add both sections to balanceAndTransactionsPanel
            balanceAndTransactionsPanel.add(balancePanel);
            balanceAndTransactionsPanel.add(transactionsPanel);
            topSectionPanel.add(balanceAndTransactionsPanel, BorderLayout.NORTH);

// Date Section Panel (Now on Next Line Below Balance & Transactions)
            JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            datePanel.setBorder(BorderFactory.createTitledBorder("Date Selection"));

// Dynamic date calculation with better formatting
            LocalDate currentDate = LocalDate.now();
            LocalDate startDate = currentDate.minusMonths(1);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            String formattedCurrentDate = currentDate.format(dateFormatter);
            String formattedStartDate = startDate.format(dateFormatter);

            datePanel.add(new JLabel("📅 As on Date: " + formattedCurrentDate));
            datePanel.add(new JLabel("Period From: " + formattedStartDate));
            datePanel.add(new JLabel("To: " + formattedCurrentDate));

// Add the datePanel AFTER balanceAndTransactionsPanel and BEFORE summaryPanel
            topSectionPanel.add(datePanel, BorderLayout.CENTER);
            topSectionPanel.add(summaryPanel, BorderLayout.SOUTH);

            mainPanel.add(topSectionPanel, BorderLayout.NORTH);

// Graph Panel with null check and error handling
            JPanel graphPanel = new JPanel(new GridLayout(1, 2, 15, 10));
            graphPanel.setBackground(Color.WHITE);
            graphPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            try {
                JPanel pieChartPanel = createPieChart();
                JPanel barGraphPanel = createBarGraph();
                if (pieChartPanel != null) {
                    graphPanel.add(pieChartPanel);
                } else {
                    graphPanel.add(new JLabel("Pie Chart not available"));
                }

                if (barGraphPanel != null) {
                    graphPanel.add(barGraphPanel);
                } else {
                    graphPanel.add(new JLabel("Bar Graph not available"));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Error creating graphs: " + ex.getMessage(),
                        "Graph Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            mainPanel.add(graphPanel, BorderLayout.CENTER);

            add(mainPanel, BorderLayout.CENTER);
        }
    }

    private JPanel createSummaryCard(String title, String amount, String change, Color changeColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));

        JLabel amountLabel = new JLabel(amount);
        amountLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JLabel changeLabel = new JLabel(change.isEmpty() ? " " : change);
        changeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        changeLabel.setForeground(changeColor);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(amountLabel, BorderLayout.CENTER);
        card.add(changeLabel, BorderLayout.SOUTH);

        return card;
    }

    //  Fetch Total Expenses for the Logged-in User
    private String getTotalExpenses() {
        double total = 0;
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            return "0.00"; // Ensure valid user
        }
        String sql = "SELECT SUM(amount) AS total_expenses FROM transactions WHERE type = 'Expense' AND user_id = ?";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total_expenses");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.format("%.2f", total);
    }

//  Fetch This Month's Expenses for the Logged-in User
    private String getExpensesThisMonth() {
        double total = 0;
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            return "0.00";
        }

        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        String sql = "SELECT SUM(amount) AS monthly_expenses FROM transactions WHERE type = 'Expense' AND user_id = ? AND MONTH(date) = ? AND YEAR(date) = ?";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, currentMonth);
            stmt.setInt(3, currentYear);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("monthly_expenses");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.format("%.2f", total);
    }

//  Calculate Monthly Expense Change for Logged-in User
    private String calculateMonthlyChange() {
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            return "+₹0.00";
        }

        double currentMonthExpenses = Double.parseDouble(getExpensesThisMonth());
        double previousMonthExpenses = 0;

        LocalDate previousMonthDate = LocalDate.now().minusMonths(1);
        int previousMonth = previousMonthDate.getMonthValue();
        int previousYear = previousMonthDate.getYear();

        String sql = "SELECT SUM(amount) AS total FROM transactions WHERE type = 'Expense' AND user_id = ? AND MONTH(date) = ? AND YEAR(date) = ?";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, previousMonth);
            stmt.setInt(3, previousYear);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                previousMonthExpenses = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        double change = currentMonthExpenses - previousMonthExpenses;
        String sign = (change >= 0) ? "+" : "-";
        return sign + "₹" + String.format("%.2f", Math.abs(change));
    }

//  Fetch Recent Transactions for the Logged-in User
    private List<String> getRecentTransactions() {
        List<String> transactions = new ArrayList<>();
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            return transactions;
        }

        String sql = "SELECT category, amount, date FROM transactions WHERE user_id = ? ORDER BY date DESC LIMIT 5";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String category = rs.getString("category");
                double amount = rs.getDouble("amount");
                String date = rs.getString("date");
                transactions.add(category + " - ₹" + amount + " (" + date + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

//  Fetch the Most Spending Category for the Logged-in User
    private String getMostSpending() {
        String category = "N/A";
        double maxAmount = 0;
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            return category + " (₹0.00)";
        }

        String sql = "SELECT category, SUM(amount) AS total FROM transactions WHERE type = 'Expense' AND user_id = ? GROUP BY category ORDER BY total DESC LIMIT 1";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                category = rs.getString("category");
                maxAmount = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category + " (₹" + String.format("%.2f", maxAmount) + ")";
    }

    private String getBalanceBreakdown() {
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            JOptionPane.showMessageDialog(null, "User not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
            return "Error retrieving balance breakdown";
        }

        StringBuilder breakdown = new StringBuilder();
        double income = 0;
        double expenses = 0;
        double balance = 0;

        try (Connection conn = Dbconnect.getConnection()) {
            String incomeQuery = "SELECT SUM(amount) AS total_income FROM transactions WHERE type = 'income' AND user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(incomeQuery)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        income = rs.getDouble("total_income");
                    }
                }
            }

            String expenseQuery = "SELECT SUM(amount) AS total_expenses FROM transactions WHERE type = 'expense' AND user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(expenseQuery)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        expenses = rs.getDouble("total_expenses");
                    }
                }
            }

            balance = income - expenses;
            breakdown.append("Income: ₹").append(String.format("%.2f", income)).append("\n");
            breakdown.append("Expenses: ₹").append(String.format("%.2f", expenses)).append("\n");
            breakdown.append("Balance: ₹").append(String.format("%.2f", balance));
        } catch (SQLException e) {
            e.printStackTrace();
            breakdown.append("Error retrieving balance breakdown");
        }

        return breakdown.toString();
    }

    private String getCurrentBalance() {
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            return "User not logged in";
        }

        double balance = 0.0;

        String sql = "SELECT "
                + "(SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE user_id = ? AND type = 'Income') AS total_income, "
                + "(SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE user_id = ? AND type = 'Expense') AS total_expense";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double totalIncome = rs.getDouble("total_income");
                double totalExpense = rs.getDouble("total_expense");
                balance = Math.max(totalIncome - totalExpense, 0);  // Prevent negative balance
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching balance from the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return String.format("%.2f", balance);
    }

    private void updateBalance(double transactionAmount) {
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            JOptionPane.showMessageDialog(null, "User not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Fetch current balance
        double currentBalance = Double.parseDouble(getCurrentBalance()); // Ensure correct parsing
        double newBalance = currentBalance + transactionAmount; // Adjust balance

        // Step 1: Insert the transaction
        String insertTransactionSQL = "INSERT INTO transactions (user_id, amount, type, category, date) VALUES (?, ?, ?, ?, CURRENT_DATE())";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(insertTransactionSQL)) {

            stmt.setInt(1, userId);
            stmt.setDouble(2, Math.abs(transactionAmount)); // Ensure amount is positive
            stmt.setString(3, (transactionAmount >= 0) ? "Income" : "Expense"); // Set type
            stmt.setString(4, "Balance Update"); // Default category

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Transaction recorded successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error inserting transaction.", "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Step 2: Update balance in users table
        String updateBalanceSQL = "UPDATE users SET balance = ? WHERE Uid = ?";

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateBalanceSQL)) {

            stmt.setDouble(1, newBalance);
            stmt.setInt(2, userId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Balance updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Balance update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating balance in the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Dbconnect.getConnection();
            int userId = SessionManager.getLoggedInUserId();

            if (userId == -1) {
                JOptionPane.showMessageDialog(null, "User not logged in. Cannot display chart.", "Login Error", JOptionPane.ERROR_MESSAGE);
                return new JPanel();
            }

            // Query to get total expenses per category for the logged-in user
            String sql = "SELECT category, SUM(amount) AS total FROM transactions WHERE user_id = ? AND type = 'Expense' GROUP BY category";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            boolean hasData = false;

            while (rs.next()) {
                String category = rs.getString("category");
                double amount = rs.getDouble("total");

                if (category != null && !category.isEmpty()) {
                    dataset.setValue(category, amount);
                    hasData = true;
                }
            }

            if (!hasData) {
                dataset.setValue("No Expense Data", 1);
            }

            // Create the Pie Chart
            JFreeChart chart = ChartFactory.createPieChart(
                    "Expense Distribution by Category",
                    dataset,
                    true, // Show legend
                    true, // Show tooltips
                    false // No URLs
            );

            // Customize Pie Chart
            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setLabelFont(new Font("Times New Roman", Font.BOLD, 12));
            plot.setSimpleLabels(false); // Ensures labels appear outside
            plot.setLabelGap(0.05); // Reduces gap between labels and chart
            plot.setInteriorGap(0.02); // Maximizes pie size
            plot.setSectionOutlinesVisible(true);
            plot.setShadowPaint(null); // Removes shadow for cleaner look

            // **Enable label leader lines (arrows pointing to segments)**
            plot.setLabelLinksVisible(true);
            plot.setLabelLinkMargin(0.08); // Space between chart and labels
            // plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD); // Remove this if causing an error

            // **Format labels: Show category name, amount, and percentage outside**
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                    "{0}: ₹{1} ({2})", // {0} = Category, {1} = Amount, {2} = Percentage
                    new DecimalFormat("#,##0.00"),
                    new DecimalFormat("0.0%") // Shows percentage
            ));

            // **Ensure that all labels appear outside the chart with arrows**
            plot.setSimpleLabels(false);

            // **Legend will show category name + amount**
            chart.getLegend().setItemFont(new Font("Times New Roman", Font.PLAIN, 12));

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(500, 400)); // Increased size for better readability
            return chartPanel;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error creating pie chart: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close resources properly
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return new JPanel();
    }

    private JPanel createBarGraph() {
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            JOptionPane.showMessageDialog(null, "User not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
            return new JPanel();
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Dbconnect.getConnection();
            String sql = "SELECT SUM(amount) AS total, YEAR(date) AS year, MONTH(date) AS month, type, category "
                    + "FROM transactions "
                    + "WHERE user_id = ? "
                    + "GROUP BY YEAR(date), MONTH(date), type, category";

            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            String[] monthNames = {
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
            };

            // Data structures to store financial data per month
            Map<String, Double> incomeMap = new HashMap<>();
            Map<String, Double> expenseMap = new HashMap<>();
            Map<String, Double> essentialExpenseMap = new HashMap<>();

            while (rs.next()) {
                int monthNum = rs.getInt("month");
                int year = rs.getInt("year");
                double amount = rs.getDouble("total");
                String type = rs.getString("type");
                String category = rs.getString("category");

                if (type == null || type.isEmpty()) {
                    type = "Unknown";
                }

                // Convert month number to "MMM YYYY" format
                String monthName = monthNames[monthNum - 1] + " " + year;

                if (type.equalsIgnoreCase("Income")) {
                    incomeMap.put(monthName, incomeMap.getOrDefault(monthName, 0.0) + amount);
                } else if (type.equalsIgnoreCase("Expense")) {
                    expenseMap.put(monthName, expenseMap.getOrDefault(monthName, 0.0) + amount);

                    // Assume categories like "Rent", "Groceries", "Bills" are essential
                    if (category != null && (category.equalsIgnoreCase("Rent")
                            || category.equalsIgnoreCase("Groceries")
                            || category.equalsIgnoreCase("Bills"))) {
                        essentialExpenseMap.put(monthName, essentialExpenseMap.getOrDefault(monthName, 0.0) + amount);
                    }
                }
            }

            // Populate dataset with calculated values
            for (String month : incomeMap.keySet()) {
                double income = incomeMap.getOrDefault(month, 0.0);
                double expense = expenseMap.getOrDefault(month, 0.0);
                double essentialExpense = essentialExpenseMap.getOrDefault(month, 0.0);

                double netBalance = income - expense; // (Income - Expense)
                double savingsContribution = income - essentialExpense; // (Income - Essential Expenses)

                dataset.addValue(income, "Total Income", month);
                dataset.addValue(expense, "Total Expense", month);
                dataset.addValue(netBalance, "Net Balance", month);
                dataset.addValue(savingsContribution, "Savings Contribution", month);
            }

            // Create bar chart
            JFreeChart chart = ChartFactory.createBarChart(
                    "Monthly Financial Overview",
                    "Month",
                    "Amount (₹)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            // Customize bar chart appearance
            CategoryPlot plot = chart.getCategoryPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

            // Custom bar colors
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, Color.GREEN);   // Total Income
            renderer.setSeriesPaint(1, Color.RED);     // Total Expense
            renderer.setSeriesPaint(2, Color.BLUE);    // Net Balance
            renderer.setSeriesPaint(3, Color.ORANGE);  // Savings Contribution

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(500, 350));
            return chartPanel;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error creating bar graph: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return new JPanel(); // Return an empty panel in case of errors
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
