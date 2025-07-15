package budgetbuddy;

import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;

public class ViewTransactions extends javax.swing.JFrame {

    private List<Object[]> allTransactions = new ArrayList<>();

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> categoryFilter, typeFilter;
    private JDateChooser fromDateChooser, toDateChooser;
    private JButton filterButton, refreshButton;
    private JLabel totalIncomeLabel;
    private JLabel totalExpenseLabel;
    private String loggedInUsername;

    public ViewTransactions() {
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            JOptionPane.showMessageDialog(this, "User not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setTitle("View Transactions");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        allTransactions = new ArrayList<>();
        add(createFilterPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        loadTransactionsFromDatabase(userId);
        updateCategoryFilter("All");

    }

    private void loadTransactionsFromDatabase(int userId) {
        try (Connection conn = Dbconnect.getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT id, amount, category, type, date FROM transactions WHERE user_id = ?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            tableModel.setRowCount(0); // Clear previous data
            int idCounter = 1;

            while (rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String category = rs.getString("category");
                String type = rs.getString("type");
                String dateTime = rs.getString("date");
                String formattedAmount = "₹ " + String.format("%.2f", amount);

                tableModel.addRow(new Object[]{idCounter++, formattedAmount, category, type, dateTime});
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No transactions found for the logged-in user.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

            updateTotals();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading transactions: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(createButtonPanel(), BorderLayout.NORTH);
        bottomPanel.add(createTotalPanel(), BorderLayout.SOUTH);
        return bottomPanel;
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter Transactions"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(new JLabel("Category:"), gbc);

        gbc.gridx = 1;
        // Initialize categoryFilter before adding to the panel
        categoryFilter = new JComboBox<>(new String[]{"All"});
        filterPanel.add(categoryFilter, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Type:"), gbc);

        gbc.gridx = 3;
        typeFilter = new JComboBox<>(new String[]{"All", "Income", "Expense"});
        typeFilter.addActionListener(e -> updateCategoryFilter((String) typeFilter.getSelectedItem()));
        filterPanel.add(typeFilter, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        filterPanel.add(new JLabel("From Date:"), gbc);

        gbc.gridx = 1;
        fromDateChooser = new JDateChooser();
        fromDateChooser.setDateFormatString("yyyy-MM-dd HH:mm");
        filterPanel.add(fromDateChooser, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("To Date:"), gbc);

        gbc.gridx = 3;
        toDateChooser = new JDateChooser();
        toDateChooser.setDateFormatString("yyyy-MM-dd HH:mm");
        filterPanel.add(toDateChooser, gbc);

        add(createTotalPanel(), BorderLayout.SOUTH);

        return filterPanel;
    }

    private void updateCategoryFilter(String type) {
        categoryFilter.removeAllItems();
        categoryFilter.addItem("All");

        if ("Expense".equals(type)) {
            categoryFilter.addItem("Food");
            categoryFilter.addItem("Rent");
            categoryFilter.addItem("Transport");
            categoryFilter.addItem("Shopping");
            categoryFilter.addItem("Other");
            // Additional Expense Categories
            categoryFilter.addItem("Utilities");       // Electricity, Water, Gas, etc.
            categoryFilter.addItem("Healthcare");      // Medical, Insurance, Pharmacy
            categoryFilter.addItem("Entertainment");   // Movies, Games, Leisure
            categoryFilter.addItem("Education");       // Tuition, Books, Courses
            categoryFilter.addItem("Groceries");       // Daily essentials and supermarket items
        } else if ("Income".equals(type)) {
            categoryFilter.addItem("Salary");
            categoryFilter.addItem("Bonus");
            categoryFilter.addItem("Investment");
            categoryFilter.addItem("Other Income");
            // Additional Income Categories
            categoryFilter.addItem("Freelance");       // Income from gigs or contracts
            categoryFilter.addItem("Rental Income");   // Property rental earnings
            categoryFilter.addItem("Interest");        // Bank interest and savings
            categoryFilter.addItem("Dividends");       // Income from investments or stocks
            categoryFilter.addItem("Gifts");           // Monetary gifts or donations received
        } else {
            // If "All" is selected or no specific type, show both income and expense categories
            categoryFilter.addItem("Food");
            categoryFilter.addItem("Rent");
            categoryFilter.addItem("Transport");
            categoryFilter.addItem("Shopping");
            categoryFilter.addItem("Other");
            categoryFilter.addItem("Utilities");
            categoryFilter.addItem("Healthcare");
            categoryFilter.addItem("Entertainment");
            categoryFilter.addItem("Education");
            categoryFilter.addItem("Groceries");
            categoryFilter.addItem("Salary");
            categoryFilter.addItem("Bonus");
            categoryFilter.addItem("Investment");
            categoryFilter.addItem("Other Income");
            categoryFilter.addItem("Freelance");
            categoryFilter.addItem("Rental Income");
            categoryFilter.addItem("Interest");
            categoryFilter.addItem("Dividends");
            categoryFilter.addItem("Gifts");
        }
    }

    private JScrollPane createTablePanel() {
        tableModel = new DefaultTableModel(new String[]{"ID", "Amount", "Category", "Type", "Date & Time"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable = new JTable(tableModel);

        DefaultTableCellRenderer rightAlignRenderer = new DefaultTableCellRenderer();
        rightAlignRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        transactionTable.getColumnModel().getColumn(1).setCellRenderer(rightAlignRenderer);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < transactionTable.getColumnCount(); i++) {
            if (i != 1) {
                transactionTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        return new JScrollPane(transactionTable);
    }

    private JPanel createTotalPanel() {
        JPanel totalPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        totalIncomeLabel = new JLabel("Total Income: ₹0.00");
        totalExpenseLabel = new JLabel("Total Expense: ₹0.00");

        totalIncomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalExpenseLabel.setHorizontalAlignment(SwingConstants.CENTER);

        totalPanel.add(totalIncomeLabel);
        totalPanel.add(totalExpenseLabel);

        return totalPanel;
    }

    private void updateTotals() {
        double totalIncome = 0;
        double totalExpense = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String type = (String) tableModel.getValueAt(i, 3);
            String amountStr = ((String) tableModel.getValueAt(i, 1)).replace("₹ ", "");
            double amount = Double.parseDouble(amountStr);
            if ("Income".equalsIgnoreCase(type)) {
                totalIncome += amount;
            } else if ("Expense".equalsIgnoreCase(type)) {
                totalExpense += amount;
            }
        }
        totalIncomeLabel.setText("Total Income: ₹ " + String.format("%.2f", totalIncome));
        totalExpenseLabel.setText("Total Expense: ₹ " + String.format("%.2f", totalExpense));
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        filterButton = new JButton("Apply Filters");
        refreshButton = new JButton("Refresh");

        filterButton.setPreferredSize(new Dimension(120, 30));
        refreshButton.setPreferredSize(new Dimension(120, 30));

        refreshButton.addActionListener(this::refreshTable);
        filterButton.addActionListener(this::applyFilters);

        buttonPanel.add(refreshButton);
        buttonPanel.add(filterButton);

        return buttonPanel;
    }

    private void loadTransactionsFromDatabase() {
        try (Connection con = Dbconnect.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT id, amount, category, type, date FROM transactions WHERE user_id = ?")) {
            ps.setString(1, loggedInUsername); // Correctly setting the user ID
            ResultSet rs = ps.executeQuery();

            tableModel.setRowCount(0); // Clear previous data
            int idCounter = 1; // Initialize the ID counter

            while (rs.next()) {
                String id = String.valueOf(idCounter++);
                String amount = "₹ " + String.format("%.2f", rs.getDouble("amount")); // Adding currency symbol
                String category = rs.getString("category");
                String type = rs.getString("type");
                String dateTime = rs.getString("date");

                // Adding the row with the formatted amount
                tableModel.addRow(new Object[]{id, amount, category, type, dateTime});
            }

            // Show message if no transactions are found
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No transactions found for the logged-in user.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

            updateTotals();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading transactions.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void applyFilters(ActionEvent e) {
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            JOptionPane.showMessageDialog(this, "User not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedCategory = (String) categoryFilter.getSelectedItem();
        String selectedType = (String) typeFilter.getSelectedItem();
        Date fromDate = fromDateChooser.getDate();
        Date toDate = toDateChooser.getDate();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        tableModel.setRowCount(0); // Clear the table

        try (Connection conn = Dbconnect.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT id, amount, category, type, date FROM transactions WHERE user_id = ?");

            if (!selectedCategory.equals("All")) {
                sql.append(" AND category = ?");
            }
            if (!selectedType.equals("All")) {
                sql.append(" AND type = ?");
            }
            if (fromDate != null) {
                sql.append(" AND date >= ?");
            }
            if (toDate != null) {
                sql.append(" AND date <= ?");
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
                int paramIndex = 1;
                stmt.setInt(paramIndex++, userId);

                if (!selectedCategory.equals("All")) {
                    stmt.setString(paramIndex++, selectedCategory);
                }
                if (!selectedType.equals("All")) {
                    stmt.setString(paramIndex++, selectedType);
                }
                if (fromDate != null) {
                    stmt.setString(paramIndex++, sdf.format(fromDate));
                }
                if (toDate != null) {
                    stmt.setString(paramIndex++, sdf.format(toDate));
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        double amount = rs.getDouble("amount");
                        String category = rs.getString("category");
                        String type = rs.getString("type");
                        String dateTime = rs.getString("date");
                        String formattedAmount = "₹ " + String.format("%.2f", amount);

                        tableModel.addRow(new Object[]{id, formattedAmount, category, type, dateTime});
                    }

                    if (tableModel.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(this, "No transactions found for the applied filters.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }

                    updateTotals();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error applying filters: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable(ActionEvent e) {
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            JOptionPane.showMessageDialog(this, "User not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        categoryFilter.setSelectedIndex(0);
        typeFilter.setSelectedIndex(0);
        fromDateChooser.setDate(null);
        toDateChooser.setDate(null);

        loadTransactionsFromDatabase(userId); // Correct method call
    }

}
