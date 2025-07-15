package budgetbuddy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.Statement;
import java.text.ParseException;

public class AddTransactions extends javax.swing.JFrame {

    /*
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 831, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 637, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
*/
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddTransactions().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private JTextField amountField;
    private JComboBox<String> categoryBox, typeBox;
    private JDateChooser dateChooser;
    private DefaultTableModel transactionTableModel;
    private JTable transactionTable;
    private JButton addButton, infoButton;

    public AddTransactions() {

        setTitle("Manage Transactions");
        setSize(800, 600); // or using setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Using BorderLayout for the main frame
        setLayout(new BorderLayout(10, 10));

        // Add the transaction panel to the center
        add(createTransactionPanel(), BorderLayout.CENTER);
    }

    private JPanel createTransactionPanel() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Transaction"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Amount:"), gbc);

        gbc.gridx = 1;
        amountField = new JTextField(10);
        amountField.setPreferredSize(new Dimension(120, 25));
        formPanel.add(amountField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Type:"), gbc);

        gbc.gridx = 3;
        typeBox = new JComboBox<>(new String[]{"Expense", "Income"});
        formPanel.add(typeBox, gbc);

        // Add this line to update the category box when the type changes
        typeBox.addActionListener(e -> updateCategoryBox((String) typeBox.getSelectedItem()));

        // Add the Info button properly
        // Add the Info button properly
        gbc.gridx = 4;  // Adjusted the grid position
        gbc.gridy = 0;
        infoButton = new JButton("?");
        infoButton.setFocusable(false);  // Prevent it from gaining focus
        infoButton.setActionCommand("info");  // Set a unique action command
        infoButton.addActionListener(e -> {
            if ("info".equals(e.getActionCommand())) {
                showCategoryInfo();
            }
        });
        formPanel.add(infoButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Category:"), gbc);

        gbc.gridx = 1;
        categoryBox = new JComboBox<>();
        updateCategoryBox("Expense");  // Initialize with default category type (can be "Expense" or "Income")
        formPanel.add(categoryBox, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Date & Time:"), gbc);

        gbc.gridx = 3;
        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        dateChooser.setDateFormatString("yyyy-MM-dd HH:mm");
        formPanel.add(dateChooser, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // Transaction Table
        transactionTableModel = new DefaultTableModel(new String[]{"ID", "Amount", "Category", "Type", "Date & Time"}, 0);
        transactionTable = new JTable(transactionTableModel);
        centerAlignTable();
        transactionTable.setFillsViewportHeight(true);

// Right-align the "Amount" column with a Rupee symbol
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value != null) {
                    try {
                        double amount = Double.parseDouble(value.toString().replace("₹ ", ""));
                        setText("₹ " + String.format("%.2f", amount)); // Prepend the Rupee symbol
                    } catch (NumberFormatException e) {
                        setText(value.toString());
                    }
                } else {
                    setText("₹ 0.00");
                }
            }
        };
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        transactionTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer); // "Amount" column index

        JScrollPane tableScrollPane = new JScrollPane(transactionTable);
        tableScrollPane.setPreferredSize(new Dimension(600, 400));
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(80, 25));
        addButton.addActionListener(e -> addTransaction());
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit");
        editButton.setPreferredSize(new Dimension(80, 25));
        editButton.addActionListener(e -> {
            int selectedRow = transactionTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a transaction to edit.", "Edit Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            editTransaction(selectedRow);
        });
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(80, 25));
        deleteButton.addActionListener(e -> deleteTransaction());
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void centerAlignTable() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < transactionTable.getColumnCount(); i++) {
            transactionTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        transactionTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    private void updateCategoryBox(String type) {
        categoryBox.removeAllItems();

        if ("Expense".equals(type)) {
            categoryBox.addItem("Food");
            categoryBox.addItem("Rent");
            categoryBox.addItem("Transport");
            categoryBox.addItem("Shopping");
            categoryBox.addItem("Other");
            categoryBox.addItem("Utilities");       // Electricity, Water, Gas, etc.
            categoryBox.addItem("Healthcare");      // Medical, Insurance, Pharmacy
            categoryBox.addItem("Entertainment");   // Movies, Games, Leisure
            categoryBox.addItem("Education");       // Tuition, Books, Courses
            categoryBox.addItem("Groceries");       // Daily essentials and supermarket items
        } else if ("Income".equals(type)) {
            categoryBox.addItem("Salary");
            categoryBox.addItem("Bonus");
            categoryBox.addItem("Investment");
            categoryBox.addItem("Other Income");
            categoryBox.addItem("Freelance");       // Income from gigs or contracts
            categoryBox.addItem("Rental Income");   // Property rental earnings
            categoryBox.addItem("Interest");        // Bank interest and savings
            categoryBox.addItem("Dividends");       // Income from investments or stocks
            categoryBox.addItem("Gifts");
        }
    }

    private void showCategoryInfo() {
        StringBuilder message = new StringBuilder("Category Explanations:\n");
        message.append("\nExpense Categories:\n");
        message.append("- Food: Meals and groceries.\n");
        message.append("- Rent: Housing payments.\n");
        message.append("- Transport: Travel costs.\n");
        message.append("- Shopping: Retail spending.\n");
        message.append("- Other: Miscellaneous expenses.\n");
        message.append("- Utilities: Electricity, water, gas.\n");
        message.append("- Healthcare: Medical and pharmacy.\n");
        message.append("- Entertainment: Leisure and movies.\n");
        message.append("- Education: Tuition and courses.\n");
        message.append("- Groceries: Daily essentials.\n");

        message.append("\nIncome Categories:\n");
        message.append("- Salary: Employment income.\n");
        message.append("- Bonus: Extra incentives.\n");
        message.append("- Investment: Asset earnings.\n");
        message.append("- Other Income: Additional revenue.\n");
        message.append("- Freelance: Contract earnings.\n");
        message.append("- Rental Income: Property rent.\n");
        message.append("- Interest: Bank savings.\n");
        message.append("- Dividends: Investment profits.\n");
        message.append("- Gifts: Monetary donations.\n");

        JOptionPane.showMessageDialog(this, message.toString(), "Category Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addTransaction() {
        String amount = amountField.getText();
        String category = (String) categoryBox.getSelectedItem();
        String type = (String) typeBox.getSelectedItem();

        // Automatically get the current date if not selected
        Date selectedDate = dateChooser.getDate();
        if (selectedDate == null) {
            selectedDate = new Date();  // Use current date and time
            dateChooser.setDate(selectedDate);  // Set the chooser to show current date
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = sdf.format(selectedDate);

        // Validate the amount
        if (!Pattern.matches("\\d+(\\.\\d{1,2})?", amount)) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = SessionManager.getLoggedInUserId();
        if (userId == -1) {
            JOptionPane.showMessageDialog(this, "User not logged in. Please log in to add transactions.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = Dbconnect.getConnection()) {
            // Budget limit check for expense type
            if ("expense".equalsIgnoreCase(type)) {
                double enteredAmount = Double.parseDouble(amount);
                double budgetAmount = 0;
                double spentAmount = 0;

                // Fetch the budget amount and spent amount for the category
                String budgetQuery = "SELECT budget_amount, spent_amount FROM budgets WHERE user_id = ? AND category = ?;";
                try (PreparedStatement budgetStmt = conn.prepareStatement(budgetQuery)) {
                    budgetStmt.setInt(1, userId);
                    budgetStmt.setString(2, category);
                    try (ResultSet rs = budgetStmt.executeQuery()) {
                        if (rs.next()) {
                            budgetAmount = rs.getDouble("budget_amount");
                            spentAmount = rs.getDouble("spent_amount");
                        }
                    }
                }

                // Check if the transaction will exceed the budget
                if (budgetAmount > 0 && (spentAmount + enteredAmount) > budgetAmount) {
                    JOptionPane.showMessageDialog(this, "Warning: Adding this transaction will exceed the budget limit for the selected category.",
                            "Budget Limit Exceeded", JOptionPane.WARNING_MESSAGE);
                }
            }

            // Insert the transaction into the database
            String sql = "INSERT INTO transactions (user_id, amount, category, type, date) VALUES (?, ?, ?, ?, ?);";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, userId);
                stmt.setString(2, amount);
                stmt.setString(3, category);
                stmt.setString(4, type);
                stmt.setString(5, date);
                stmt.executeUpdate();

                ResultSet generatedKeys = stmt.getGeneratedKeys();
                int id = -1;
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }

                // Append the new transaction as a new row without clearing previous rows
                transactionTableModel.addRow(new Object[]{id, "₹ " + amount, category, type, date});
                clearFields();
                JOptionPane.showMessageDialog(this, "Transaction added successfully.");

                // Check if the transaction is an income type for saving goals
                if ("Income".equals(type)) {
                    // Retrieve active saving goals
                    String fetchGoalsSql = "SELECT goal_name, target_amount, saved_amount FROM saving_goals WHERE user_id = ? AND status = 'Active';";
                    try (PreparedStatement fetchStmt = conn.prepareStatement(fetchGoalsSql)) {
                        fetchStmt.setInt(1, userId);
                        try (ResultSet rs = fetchStmt.executeQuery()) {
                            while (rs.next()) {
                                String goalName = rs.getString("goal_name");
                                int targetAmount = rs.getInt("target_amount");
                                int savedAmount = rs.getInt("saved_amount");

                                int newSavedAmount = savedAmount + Integer.parseInt(amount);
                                String newStatus = newSavedAmount >= targetAmount ? "Completed" : "Active";

                                // Update goal in the database
                                String updateGoalSql = "UPDATE saving_goals SET saved_amount = ?, status = ? WHERE user_id = ? AND goal_name = ?;";
                                try (PreparedStatement updateStmt = conn.prepareStatement(updateGoalSql)) {
                                    updateStmt.setInt(1, newSavedAmount);
                                    updateStmt.setString(2, newStatus);
                                    updateStmt.setInt(3, userId);
                                    updateStmt.setString(4, goalName);
                                    updateStmt.executeUpdate();
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding transaction: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editTransaction(int selectedRow) {
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "No transaction selected for editing.", "Edit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(transactionTableModel.getValueAt(selectedRow, 0).toString());
            String amount = transactionTableModel.getValueAt(selectedRow, 1).toString().replace("₹ ", "").trim();
            String category = transactionTableModel.getValueAt(selectedRow, 2).toString();
            String type = transactionTableModel.getValueAt(selectedRow, 3).toString();
            String dateTime = transactionTableModel.getValueAt(selectedRow, 4).toString();

            amountField.setText(amount);
            categoryBox.setSelectedItem(category);
            typeBox.setSelectedItem(type);

            try {
                // Attempt to parse using the primary date format
                SimpleDateFormat primaryFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = primaryFormat.parse(dateTime);

                // If parsing fails, try a secondary format (e.g., if seconds are included)
                if (date == null) {
                    SimpleDateFormat secondaryFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    date = secondaryFormat.parse(dateTime);
                }

                // If both formats fail, set to current date as a fallback
                if (date == null) {
                    date = new Date();
                }

                dateChooser.setDate(date);
            } catch (ParseException ex) {
                dateChooser.setDate(new Date());  // Set to current date if parsing fails
                JOptionPane.showMessageDialog(this, "Error parsing date: " + ex.getMessage(), "Date Error", JOptionPane.ERROR_MESSAGE);
            }

            addButton.setText("Update");
            addButton.removeActionListener(addButton.getActionListeners()[0]);
            addButton.addActionListener(ev -> updateTransaction(selectedRow));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading transaction data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTransaction(int selectedRow) {
        String amount = amountField.getText();
        String category = (String) categoryBox.getSelectedItem();
        String type = (String) typeBox.getSelectedItem();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date selectedDate = dateChooser.getDate();

        if (selectedDate == null) {
            selectedDate = new Date();  // Auto date if not selected
        }
        String date = sdf.format(selectedDate);

        if (!Pattern.matches("\\d+(\\.\\d{1,2})?", amount)) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            JOptionPane.showMessageDialog(this, "User not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = Dbconnect.getConnection()) {
            int id = Integer.parseInt(transactionTableModel.getValueAt(selectedRow, 0).toString());
            String sql = "UPDATE transactions SET amount = ?, category = ?, type = ?, date = ? WHERE id = ? AND user_id = ?;";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, Double.parseDouble(amount));
                stmt.setString(2, category);
                stmt.setString(3, type);
                stmt.setString(4, date);
                stmt.setInt(5, id);
                stmt.setInt(6, userId);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    transactionTableModel.setValueAt("₹ " + amount, selectedRow, 1);
                    transactionTableModel.setValueAt(category, selectedRow, 2);
                    transactionTableModel.setValueAt(type, selectedRow, 3);
                    transactionTableModel.setValueAt(date, selectedRow, 4);
                    JOptionPane.showMessageDialog(this, "Transaction updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "No transaction found or update failed.", "Update Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating transaction: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        addButton.setText("Add");
        addButton.removeActionListener(addButton.getActionListeners()[0]);
        addButton.addActionListener(e -> addTransaction());
        clearFields();
    }

    private void deleteTransaction() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a transaction to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = Integer.parseInt(transactionTableModel.getValueAt(selectedRow, 0).toString());
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            JOptionPane.showMessageDialog(this, "User not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this transaction?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection conn = Dbconnect.getConnection()) {
            String sql = "DELETE FROM transactions WHERE id = ? AND user_id = ?;";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.setInt(2, userId);

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    transactionTableModel.removeRow(selectedRow);
                    resetAutoIncrement();
                    JOptionPane.showMessageDialog(this, "Transaction deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Transaction not found or unauthorized action.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting transaction: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

 private void resetAutoIncrement() {
    Connection conn = null;
    try {
        conn = Dbconnect.getConnection();
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            JOptionPane.showMessageDialog(this, "User not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        conn.setAutoCommit(false); // Start transaction

        // Step 1: Create a temporary table without `id`, to avoid duplicate ID issues
        String createTempTableSQL = "CREATE TEMPORARY TABLE temp_transactions "
                + "SELECT amount, category, type, date, balance, CreatedAt, UpdatedAt, user_id "
                + "FROM transactions WHERE user_id = ? ORDER BY id;";
        try (PreparedStatement stmt = conn.prepareStatement(createTempTableSQL)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }

        // Step 2: Delete old transactions for the user
        String deleteOldRecordsSQL = "DELETE FROM transactions WHERE user_id = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(deleteOldRecordsSQL)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }

        // Step 3: Reset AUTO_INCREMENT to 1
        String resetAutoIncrementSQL = "ALTER TABLE transactions AUTO_INCREMENT = 1;";
        try (PreparedStatement resetStmt = conn.prepareStatement(resetAutoIncrementSQL)) {
            resetStmt.executeUpdate();
        }

        // Step 4: Insert reordered transactions without specifying `id` (MySQL will auto-generate them)
        String insertNewRecordsSQL = "INSERT INTO transactions (amount, category, type, date, balance, CreatedAt, UpdatedAt, user_id) "
                + "SELECT amount, category, type, date, balance, CreatedAt, UpdatedAt, user_id FROM temp_transactions;";
        try (PreparedStatement stmt = conn.prepareStatement(insertNewRecordsSQL)) {
            stmt.executeUpdate();
        }

        // Step 5: Drop the temporary table to clean up
        String dropTempTableSQL = "DROP TEMPORARY TABLE IF EXISTS temp_transactions;";
        try (PreparedStatement stmt = conn.prepareStatement(dropTempTableSQL)) {
            stmt.executeUpdate();
        }

        conn.commit(); // Commit transaction

    } catch (SQLException e) {
        try {
            if (conn != null) {
                conn.rollback();  // Proper rollback
            }
            JOptionPane.showMessageDialog(this, "Error fixing auto-increment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException rollbackEx) {
            JOptionPane.showMessageDialog(this, "Error during rollback: " + rollbackEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true); // Ensure auto-commit is re-enabled before closing
                conn.close(); // Close the connection
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error resetting auto-commit: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    refreshTable(); // Refresh UI after transaction is complete
}

    private void refreshTable() {
        try (Connection conn = Dbconnect.getConnection()) {
            int userId = SessionManager.getLoggedInUserId();
            if (userId <= 0) {
                JOptionPane.showMessageDialog(this, "User not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "SELECT id, amount, category, type, date, balance FROM transactions WHERE user_id = ? ORDER BY id;";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    transactionTableModel.setRowCount(0); // Clear table before inserting new data

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        double amount = rs.getDouble("amount");
                        String category = rs.getString("category");
                        String type = rs.getString("type");
                        String dateTime = rs.getString("date");
                        double balance = rs.getDouble("balance");

                        // Format amount to show currency
                        String formattedAmount = "₹ " + String.format("%.2f", amount);

                        transactionTableModel.addRow(new Object[]{id, formattedAmount, category, type, dateTime, balance});
                    }

                    transactionTableModel.fireTableDataChanged(); // Ensure UI refreshes properly
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error refreshing table: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        amountField.setText("");
        categoryBox.setSelectedIndex(0);
        typeBox.setSelectedIndex(0);
        dateChooser.setDate(null); // Set to null instead of new Date()
    }

}
