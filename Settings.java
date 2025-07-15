package budgetbuddy;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Settings extends javax.swing.JFrame {

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
                new Settings().setVisible(true);
            }
        });
    }

    private JTextField usernameField, emailField, phoneField;
    private JPasswordField oldPassword, newPassword;
    private JButton saveButton, fillInfoButton;
    private JPasswordField passwordField;

    public Settings() {

        setTitle("Settings");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tabbed Pane for different sections
        JTabbedPane tabbedPane = new JTabbedPane();

        // Profile Settings Tab
        JPanel profilePanel = createProfilePanel();
        tabbedPane.addTab("Profile Settings", profilePanel);

        // Reports Settings Tab
        JPanel reportsPanel = createReportsPanel();
        tabbedPane.addTab("Reports Settings", reportsPanel);

        add(tabbedPane);
        setVisible(true);

        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 16));

    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        usernameField = createTextField();
        emailField = createTextField();
        phoneField = createTextField();
        oldPassword = createPasswordField();
        newPassword = createPasswordField();

        saveButton = new JButton("Save Profile");
        saveButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        saveButton.addActionListener(e -> updateProfile());

        fillInfoButton = new JButton("Fill Information");
        fillInfoButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        fillInfoButton.addActionListener(e -> fillProfileInfo());

        addToPanel(panel, "Username:", usernameField, gbc, 0);
        addToPanel(panel, "Email:", emailField, gbc, 1);
        addToPanel(panel, "Phone:", phoneField, gbc, 2);
        addToPanel(panel, "Old Password:", oldPassword, gbc, 3);
        addToPanel(panel, "New Password:", newPassword, gbc, 4);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(fillInfoButton, gbc);

        gbc.gridx = 1;
        panel.add(saveButton, gbc);

        return panel;
    }

    private void fillProfileInfo() {
        if (!SessionManager.isUserLoggedIn()) {
            JOptionPane.showMessageDialog(this, "No logged-in user found.");
            return;
        }

        try (Connection conn = Dbconnect.getConnection()) {
            String sql = "SELECT Uname, Uemail, Upno FROM users WHERE Uid = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, SessionManager.getLoggedInUserId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usernameField.setText(rs.getString("Uname"));
                emailField.setText(rs.getString("Uemail"));
                phoneField.setText(rs.getString("Upno"));
                JOptionPane.showMessageDialog(this, "Information filled successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No user found in the database.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateProfile() {
        if (!SessionManager.isUserLoggedIn()) {
            JOptionPane.showMessageDialog(this, "No logged-in user found.");
            return;
        }

        String fullName = usernameField.getText().trim(); // Uname
        String email = emailField.getText().trim(); // Uemail
        String phone = phoneField.getText().trim(); // Upno
        String oldPass = new String(oldPassword.getPassword()).trim(); // Old Password
        String newPass = new String(newPassword.getPassword()).trim(); // New Password

        // ✅ Validate required fields
        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields (full name, email, phone) are required.");
            return;
        }

        // ✅ Email validation
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format.");
            return;
        }

        // ✅ Phone number validation (10 digits)
        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Invalid phone number. Must be exactly 10 digits.");
            return;
        }

        int userId = SessionManager.getLoggedInUserId();
        if (userId <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid user ID: " + userId);
            return;
        }

        try (Connection conn = Dbconnect.getConnection()) {
            // ✅ Step 1: Retrieve the existing password from the database
            String checkPasswordSQL = "SELECT Upass FROM users WHERE Uid = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkPasswordSQL);
            checkStmt.setInt(1, userId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("Upass"); // Get current password from DB

                // ✅ Step 2: Validate old password before updating
                if (!oldPass.isEmpty() && !storedPassword.equals(oldPass)) {
                    JOptionPane.showMessageDialog(this, "Incorrect old password. Please try again.");
                    return; // Stop update if old password is incorrect
                }
            } else {
                JOptionPane.showMessageDialog(this, "User not found.");
                return;
            }

            // ✅ Step 3: Update profile with or without password change
            String sql;
            PreparedStatement pstmt;

            if (!newPass.isEmpty()) { // If new password is provided
                sql = "UPDATE users SET Uemail = ?, Upno = ?, Uname = ?, Upass = ? WHERE Uid = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, email);
                pstmt.setString(2, phone);
                pstmt.setString(3, fullName);
                pstmt.setString(4, newPass);
                pstmt.setInt(5, userId);
            } else { // If no password change
                sql = "UPDATE users SET Uemail = ?, Upno = ?, Uname = ? WHERE Uid = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, email);
                pstmt.setString(2, phone);
                pstmt.setString(3, fullName);
                pstmt.setInt(4, userId);
            }

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Profile updated successfully!");

                // ✅ Clear all fields after successful update
                usernameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                oldPassword.setText("");
                newPassword.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. No rows affected.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(15);
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        return textField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        return passwordField;
    }

    private void addToPanel(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Report Format ComboBox
        String[] formats = {"PDF", "Excel", "CSV"};
        JComboBox<String> formatComboBox = new JComboBox<>(formats);
        formatComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        addToPanel(panel, "Report Format:", formatComboBox, gbc, 0);

        // Auto-Report Generation Checkbox
        JCheckBox autoGenerateCheckBox = new JCheckBox("Enable Auto-Report Generation");
        autoGenerateCheckBox.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(autoGenerateCheckBox, gbc);

        // Generation Frequency ComboBox
        String[] frequencies = {"Daily", "Weekly", "Monthly"};
        JComboBox<String> frequencyComboBox = new JComboBox<>(frequencies);
        frequencyComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        addToPanel(panel, "Report Frequency", frequencyComboBox, gbc, 2);

        // Save Reports Button
        JButton saveReportsButton = new JButton("Save");
        saveReportsButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        saveReportsButton.addActionListener(e -> saveReportSettings(
                (String) formatComboBox.getSelectedItem(),
                autoGenerateCheckBox.isSelected(),
                (String) frequencyComboBox.getSelectedItem()
        ));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(saveReportsButton, gbc);

        return panel;
    }

    private void saveReportSettings(String format, boolean autoGenerate, String frequency) {
        JOptionPane.showMessageDialog(this, "Report Format: " + format + "\n" + "Auto-Generation: " + (autoGenerate ? "Enabled" : "Disabled") + "\n" + "Frequency: " + frequency, "Settings Saved", JOptionPane.INFORMATION_MESSAGE);
    }

}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables




