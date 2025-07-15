package budgetbuddy;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetManagement extends javax.swing.JFrame {

    /*
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
*/
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BudgetManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private JComboBox<String> categoryBox;
    private JTextField budgetField;
    private JLabel spendingLabel;
    private JProgressBar budgetProgressBar;
    private JLabel warningLabel;
    private JButton setBudgetButton;
    private JButton refreshButton;

    public BudgetManagement() {
        setTitle("Budget Management");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Set Budget"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridy = 0;

        JLabel budgetLabel = new JLabel("Budget Amount:");
        gbc.gridx = 0;
        formPanel.add(budgetLabel, gbc);

        budgetField = new JTextField(10);
        budgetField.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        formPanel.add(budgetField, gbc);

        JLabel categoryLabel = new JLabel("Category:");
        gbc.gridx = 2;
        formPanel.add(categoryLabel, gbc);

        categoryBox = new JComboBox<>();
        loadCategories();
        gbc.gridx = 3;
        formPanel.add(categoryBox, gbc);

        setBudgetButton = new JButton("Set Budget");
        setBudgetButton.addActionListener(e -> setBudget());
        gbc.gridy = 1;
        gbc.gridx = 1;
        formPanel.add(setBudgetButton, gbc);

        refreshButton = new JButton("Refresh");
        refreshButton.setPreferredSize(new Dimension(100, 30));
        refreshButton.addActionListener(e -> handleRefreshButton());
        gbc.gridx = 2;
        formPanel.add(refreshButton, gbc);

        add(formPanel, BorderLayout.NORTH);

        JPanel progressPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        progressPanel.setBorder(BorderFactory.createTitledBorder("Budget Progress"));

        warningLabel = new JLabel(" ", SwingConstants.CENTER);
        progressPanel.add(warningLabel);

        spendingLabel = new JLabel("Spending: ₹0.00 / ₹0.00", SwingConstants.CENTER);
        progressPanel.add(spendingLabel);

        budgetProgressBar = new JProgressBar(0, 100);
        budgetProgressBar.setStringPainted(true);
        progressPanel.add(budgetProgressBar);

        add(progressPanel, BorderLayout.CENTER);
    }

   private void handleRefreshButton() {
    loadCategories();
    updateSpendingProgress();
    budgetField.setText("");  // Clear the budget amount field
    JOptionPane.showMessageDialog(this, "Budget and spending progress refreshed!");
}


    private void loadCategories() {
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            JOptionPane.showMessageDialog(this, "User not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        categoryBox.removeAllItems();
        categoryBox.addItem("Select Category");

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(
                "SELECT DISTINCT category FROM transactions WHERE type = 'expense' AND user_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            boolean categoryFound = false;

            while (rs.next()) {
                String category = rs.getString("category");
                if (category != null && !category.isEmpty()) {
                    categoryBox.addItem(category);
                    categoryFound = true;
                }
            }

            if (!categoryFound) {
                JOptionPane.showMessageDialog(this, "No expense categories found for this user.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading categories: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   private void setBudget() {
    int userId = SessionManager.getLoggedInUserId();
    if (userId <= 0) {
        JOptionPane.showMessageDialog(this, "User not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String category = (String) categoryBox.getSelectedItem();
    if (category == null || category.equals("Select Category")) {
        JOptionPane.showMessageDialog(this, "Please select a valid category.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String budgetText = budgetField.getText().trim();
    if (budgetText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Budget amount cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        double budgetAmount = Double.parseDouble(budgetText);
        if (budgetAmount <= 0) {
            JOptionPane.showMessageDialog(this, "Budget amount must be a positive number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = Dbconnect.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(
                "INSERT INTO budgets (user_id, category, budget_amount) VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE budget_amount = VALUES(budget_amount)")) {

            insertStmt.setInt(1, userId);
            insertStmt.setString(2, category);
            insertStmt.setDouble(3, budgetAmount);
            int rowsAffected = insertStmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Budget set successfully!");
                updateSpendingProgress();
            } else {
                JOptionPane.showMessageDialog(this, "Budget not set. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error adding budget: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid budget amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void updateSpendingProgress() {
        int userId = SessionManager.getLoggedInUserId();
        String category = (String) categoryBox.getSelectedItem();
        double budget = 0;
        double spent = 0;

        try (Connection conn = Dbconnect.getConnection()) {
            // Get the budget amount for the selected category
            try (PreparedStatement budgetStmt = conn.prepareStatement(
                    "SELECT budget_amount FROM budgets WHERE user_id = ? AND category = ?")) {
                budgetStmt.setInt(1, userId);
                budgetStmt.setString(2, category);
                ResultSet budgetRs = budgetStmt.executeQuery();

                if (budgetRs.next()) {
                    budget = budgetRs.getDouble("budget_amount");
                }
            }

            // Calculate the total spent amount dynamically from transactions
            try (PreparedStatement spentStmt = conn.prepareStatement(
                    "SELECT COALESCE(SUM(amount), 0) AS total_spent FROM transactions "
                    + "WHERE user_id = ? AND category = ? AND type = 'expense'")) {
                spentStmt.setInt(1, userId);
                spentStmt.setString(2, category);
                ResultSet spentRs = spentStmt.executeQuery();

                if (spentRs.next()) {
                    spent = spentRs.getDouble("total_spent");
                }
            }

            int progress = (budget > 0) ? (int) ((spent * 100) / budget) : 0;
            budgetProgressBar.setValue(progress);
            spendingLabel.setText(String.format("Spending: ₹%.2f / ₹%.2f", spent, budget));
            budgetProgressBar.setForeground(progress < 75 ? Color.GREEN : (progress < 100 ? Color.ORANGE : Color.RED));
            warningLabel.setText(progress < 75 ? "Good! Spending under control."
                    : (progress < 100 ? "Warning! Nearing budget limit." : "Alert! Budget exceeded."));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating spending progress: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
