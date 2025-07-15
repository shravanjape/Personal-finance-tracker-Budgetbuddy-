package budgetbuddy;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;

public class SavingGoals extends javax.swing.JFrame {

    private JTextField goalNameField, targetAmountField;
    private JProgressBar progressBar;
    private JLabel requiredSavingsLabel, totalSavedLabel, goalStatusLabel;
    private JButton addButton, viewButton, deleteButton, refreshButton;
    private JTextArea goalsListArea;
    private JDateChooser endDateChooser;

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
    public SavingGoals() {
        setTitle("Saving Goals");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        goalNameField = new JTextField(18);
        goalNameField.setPreferredSize(new Dimension(160, 28));
        targetAmountField = new JTextField(18);
        targetAmountField.setPreferredSize(new Dimension(160, 28));
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        requiredSavingsLabel = new JLabel("Required Savings per Month: ₹0");
        totalSavedLabel = new JLabel("Total Saved: ₹0");
        goalStatusLabel = new JLabel("Goal Status: Not Started");

        addButton = new JButton("Add Goal");
        viewButton = new JButton("View Goals");
        deleteButton = new JButton("Delete Goal");
        refreshButton = new JButton("Refresh");

        goalsListArea = new JTextArea(10, 42);
        goalsListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(goalsListArea);

        endDateChooser = new JDateChooser();
        endDateChooser.setDateFormatString("yyyy-MM-dd");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Goal Name:"), gbc);
        gbc.gridx = 1;
        panel.add(goalNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Target Amount (₹):"), gbc);
        gbc.gridx = 1;
        panel.add(targetAmountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("End Date:"), gbc);
        gbc.gridx = 1;
        panel.add(endDateChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(addButton, gbc);
        gbc.gridx = 1;
        panel.add(viewButton, gbc);
        gbc.gridx = 2;
        panel.add(deleteButton, gbc);
        gbc.gridx = 3;
        panel.add(refreshButton, gbc);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> handleAddButton());
        viewButton.addActionListener(e -> handleViewButton());
        deleteButton.addActionListener(e -> handleDeleteButton());
        refreshButton.addActionListener(e -> refreshGoals());
    }

    private void handleAddButton() {
        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            JOptionPane.showMessageDialog(this, "No user logged in. Please log in to set a saving goal.");
            return;
        }

        String goalName = goalNameField.getText().trim();
        String targetAmountText = targetAmountField.getText().trim();
        java.util.Date selectedDate = endDateChooser.getDate();

        if (goalName.isEmpty() || targetAmountText.isEmpty() || selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please fill all fields and select a date.");
            return;
        }

        if (!goalName.matches("^[A-Za-z ]+$")) {
            JOptionPane.showMessageDialog(this, "Invalid goal name. Only alphabetic characters and spaces are allowed.");
            return;
        }

        if (!targetAmountText.matches("^[1-9][0-9]*$")) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Only positive digits are allowed.");
            return;
        }

        int targetAmount = Integer.parseInt(targetAmountText);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String endDate = dateFormat.format(selectedDate);

        try (Connection conn = Dbconnect.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT target_amount, end_date, start_date, status FROM saving_goals WHERE user_id = ? AND goal_name = ?"
            );
            pstmt.setInt(1, userId);
            pstmt.setString(2, goalName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int existingAmount = rs.getInt("target_amount");
                String existingEndDate = rs.getString("end_date");
                String existingStartDate = rs.getString("start_date");
                String existingStatus = rs.getString("status");
                int newAmount = existingAmount + targetAmount;

                // Determine the latest end date
                String finalEndDate = endDate.compareTo(existingEndDate) > 0 ? endDate : existingEndDate;

                pstmt = conn.prepareStatement(
                        "UPDATE saving_goals SET target_amount = ?, end_date = ? WHERE user_id = ? AND goal_name = ?"
                );
                pstmt.setInt(1, newAmount);
                pstmt.setString(2, finalEndDate);
                pstmt.setInt(3, userId);
                pstmt.setString(4, goalName);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Goal amount updated successfully!");
            } else {
                // Insert the new goal with current date and active status
                pstmt = conn.prepareStatement(
                        "INSERT INTO saving_goals (user_id, goal_name, target_amount, saved_amount, start_date, end_date, status) "
                        + "VALUES (?, ?, ?, 0, CURDATE(), ?, 'Active')"
                );
                pstmt.setInt(1, userId);
                pstmt.setString(2, goalName);
                pstmt.setInt(3, targetAmount);
                pstmt.setString(4, endDate);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "New goal added successfully!");
            }

            // Display only the newly added goal
            displayNewlyAddedGoal(goalName);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }

    private void displayNewlyAddedGoal(String goalName) {
        try (Connection conn = Dbconnect.getConnection()) {
            int userId = SessionManager.getLoggedInUserId();
            if (userId <= 0) {
                goalsListArea.setText("No user logged in. Please log in to view saving goals.");
                return;
            }

            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT goal_name, target_amount, saved_amount, start_date, end_date, status FROM saving_goals WHERE user_id = ? AND goal_name = ?"
            );
            pstmt.setInt(1, userId);
            pstmt.setString(2, goalName);
            ResultSet rs = pstmt.executeQuery();

            StringBuilder goalDetails = new StringBuilder();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (rs.next()) {
                String name = rs.getString("goal_name");
                int targetAmount = rs.getInt("target_amount");
                int savedAmount = rs.getInt("saved_amount");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                String status = rs.getString("status");

                goalDetails.append("Goal Name: ").append(name).append("\n");
                goalDetails.append("Target Amount: ").append(targetAmount).append("\n");
                goalDetails.append("Saved Amount: ").append(savedAmount).append("\n");
                goalDetails.append("Start Date: ").append(startDate).append("\n");
                goalDetails.append("End Date: ").append(endDate).append("\n");
                goalDetails.append("Status: ").append(status).append("\n");
                goalDetails.append("------------------------------\n");

                goalNameField.setText("");
                targetAmountField.setText("");
                endDateChooser.setDate(null);
                goalsListArea.setText(""); // Hide goals

            } else {
                goalDetails.append("No goal found with the name: ").append(goalName).append("\n");
            }

            // Update the display area with the newly added goal
            goalsListArea.setText(goalDetails.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            goalsListArea.setText("Error displaying newly added goal: " + e.getMessage());
        }

    }

    private void handleViewButton() {
        try (Connection conn = Dbconnect.getConnection()) {
            int userId = SessionManager.getLoggedInUserId();
            if (userId <= 0) {
                goalsListArea.setText("No user logged in. Please log in to view saving goals.");
                return;
            }

            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT goal_name, target_amount, saved_amount, start_date, end_date, status FROM saving_goals WHERE user_id = ?"
            );
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            StringBuilder goalDetails = new StringBuilder();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (!rs.isBeforeFirst()) {
                goalDetails.append("No saving goals available.");
            } else {
                while (rs.next()) {
                    String goalName = rs.getString("goal_name");
                    int targetAmount = rs.getInt("target_amount");
                    int savedAmount = rs.getInt("saved_amount");
                    String startDate = rs.getString("start_date");
                    String endDate = rs.getString("end_date");
                    String status = rs.getString("status");

                    goalDetails.append("Goal Name: ").append(goalName).append("\n");
                    goalDetails.append("Target Amount: ₹ ").append(targetAmount).append("\n");
                    goalDetails.append("Saved Amount: ₹ ").append(savedAmount).append("\n");
                    goalDetails.append("Start Date: ").append(startDate).append("\n");
                    goalDetails.append("End Date: ").append(endDate).append("\n");
                    goalDetails.append("Status: ").append(status).append("\n");
                    goalDetails.append("------------------------------\n");
                }
            }

            // Update the display area on the frame
            goalsListArea.setText(goalDetails.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            goalsListArea.setText("Error fetching goals: " + e.getMessage());
        }
    }

    private void handleDeleteButton() {
        String goalName = goalNameField.getText().trim();
        if (goalName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the goal name to delete.");
            return;
        }

        try (Connection conn = Dbconnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement("DELETE FROM saving_goals WHERE goal_name = ?");) {
            pstmt.setString(1, goalName);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Saving goal deleted successfully!");
                refreshGoals();
            } else {
                JOptionPane.showMessageDialog(this, "No matching goal found.");
            }

            goalNameField.setText("");
            targetAmountField.setText("");
            endDateChooser.setDate(null);
            handleViewButton();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }

    private void refreshGoals() {
        try (Connection conn = Dbconnect.getConnection()) {
            int userId = SessionManager.getLoggedInUserId();
            if (userId <= 0) {
                goalsListArea.setText("No user logged in. Please log in to view saving goals.");
                return;
            }

            // Clear the text fields and hide goals list to ensure a clean display
            goalNameField.setText("");
            targetAmountField.setText("");
            endDateChooser.setDate(null);
            goalsListArea.setText(""); // Hide goals

        } catch (SQLException e) {
            e.printStackTrace();
            goalsListArea.setText("Error refreshing goals: " + e.getMessage());
        }
    }

}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
