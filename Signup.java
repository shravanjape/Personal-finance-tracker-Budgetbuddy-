package budgetbuddy;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

public class Signup extends javax.swing.JFrame {

    public Signup() {
        initComponents();
        setTitle("Signup");
        setSize(800, 600); // or using setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // This line forces the frame to maximize
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Signup().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSignup;
    private javax.swing.JLabel lblCALogo;
    private javax.swing.JLabel lblCAQuestion;
    private javax.swing.JLabel lblCATitle;
    private javax.swing.JLabel lblCPass;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFName;
    private javax.swing.JLabel lblPNo;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblUsrName;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtPNo;
    private javax.swing.JTextField txtUName;
    private javax.swing.JTextField txtUsrName;
    private JPasswordField txtPass;
    private JPasswordField txtCPass;

    // End of variables declaration                   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lblCALogo = new javax.swing.JLabel();
        lblCATitle = new javax.swing.JLabel();
        lblFName = new javax.swing.JLabel();
        txtUName = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        btnSignup = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        lblEmail = new javax.swing.JLabel();
        lblPNo = new javax.swing.JLabel();
        lblUsrName = new javax.swing.JLabel();
        lblPass = new javax.swing.JLabel();
        lblCAQuestion = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtPNo = new javax.swing.JTextField();
        txtUsrName = new javax.swing.JTextField();
        lblCPass = new javax.swing.JLabel();
        txtPass = new JPasswordField();
        txtCPass = new JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.WHITE);  // Set background to white

// Use a layout that supports centering
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);  // Add some padding

        // Create a panel for the logo and title with FlowLayout for same-row alignment
        JPanel logoTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)); // Reduced gaps between components
        logoTitlePanel.setBackground(Color.WHITE);  // Set background to white

// Adjust image size and scaling
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/budgetbuddy/signup.png"));
        Image img = originalIcon.getImage();
        Image scaledImg = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH); // Adjust size as needed
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

// Set the image icon to the label
        lblCALogo.setIcon(scaledIcon);
        lblCALogo.setHorizontalAlignment(SwingConstants.CENTER); // Center align the logo

// Set title properties
        lblCATitle.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 25)); // Bold, large font
        lblCATitle.setHorizontalAlignment(SwingConstants.CENTER); // Center align the title

// Adjust margins for compact look
        lblCATitle.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 0)); // Top, Left, Bottom, Right

// Add image and title to the panel in the same row
        logoTitlePanel.add(lblCALogo);
        logoTitlePanel.add(lblCATitle);

// Set border for the panel to reduce top and bottom spacing
        logoTitlePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Reduced margin

// Add the panel to the frame with proper layout constraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(logoTitlePanel, gbc);

        lblCATitle.setFont(new java.awt.Font("Times New Roman", 1, 32)); 
        lblCATitle.setText("Create Your BudgetBuddy Account");

        lblFName.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        lblFName.setText("  Full Name :");

        txtUName.setFont(new java.awt.Font("Times New Roman", 0, 14)); 

        btnLogin.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        btnLogin.setText("Log in");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnSignup.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        btnSignup.setText("Sign up");
        btnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignupActionPerformed(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        lblEmail.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        lblEmail.setText("  Email :");

        lblPNo.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        lblPNo.setText("  Phone No :");

        lblUsrName.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        lblUsrName.setText("  Username :");

        lblPass.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        lblPass.setText("  Password :");

        lblCAQuestion.setFont(new java.awt.Font("Times New Roman", 1, 14));
        lblCAQuestion.setText("Already signed up?");

        txtEmail.setFont(new java.awt.Font("Times New Roman", 0, 14)); 

        txtPNo.setFont(new java.awt.Font("Times New Roman", 0, 14)); 

        txtUsrName.setFont(new java.awt.Font("Times New Roman", 0, 14)); 

        txtPass.setFont(new java.awt.Font("Times New Roman", 0, 14)); 

        txtCPass.setFont(new java.awt.Font("Times New Roman", 0, 14)); 

        lblCPass.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        lblCPass.setText("  Confirm Password :");

        // Ensure no field is selected when the signup frame opens
        SwingUtilities.invokeLater(() -> {
            getContentPane().requestFocusInWindow();
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
layout.setHorizontalGroup(
    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
        .addGroup(layout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblCALogo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(lblCATitle, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(20, 20, 20))
        .addGroup(layout.createSequentialGroup()
            .addGap(50, 50, 50)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(lblFName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblPNo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblUsrName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblPass, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblCPass, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(txtUName, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtPNo, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtUsrName, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtCPass, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(50, 50, 50))
        .addGroup(layout.createSequentialGroup()
            .addGap(155, 155, 155)  // Proper alignment for buttons
            .addComponent(btnSignup, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(30, 30, 50)  // Space between SignUp and Clear buttons
            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGroup(layout.createSequentialGroup()
            .addGap(110, 135, 150)  // Proper alignment for the question label
            .addComponent(lblCAQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
);

layout.setVerticalGroup(
    layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(lblCALogo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lblCATitle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(20, 20, 20)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(lblFName)
            .addComponent(txtUName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(15, 15, 15)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(lblEmail)
            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(15, 15, 15)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(lblPNo)
            .addComponent(txtPNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(15, 15, 15)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(lblUsrName)
            .addComponent(txtUsrName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(15, 15, 15)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(lblPass)
            .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(15, 15, 15)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(lblCPass)
            .addComponent(txtCPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(20, 20, 20)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(btnSignup, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(20, 20, 20)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(lblCAQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(30, 30, 30)
);

    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        Login login = new Login();
        System.out.println("Successfully Redirected to Login page!");
        login.setVisible(true);

        Signup signUpFrame = new Signup();
        this.dispose();
        System.out.println("Successfully Signup page is closed!");
    }

    private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {

        String fullName = txtUName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPNo.getText().trim();
        String username = txtUsrName.getText().trim();

        String password = new String(txtPass.getPassword());
        String confirmPassword = new String(txtCPass.getPassword());

        // Check if any field is empty and display a message for the first empty field found
        if (fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Full Name is required.");
            return;
        } else if (!fullName.matches("^[A-Za-z]{4,}(\\s[A-Za-z\\s]*)?$")) {
            JOptionPane.showMessageDialog(this, "Full Name should start with at least 4 letters, followed by optional spaces and letters.");
            return;
        }

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email is required.");
            return;
        } else if (!email.matches("^[A-Za-z]{3,}[A-Za-z0-9._%+-]*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(this, "Invalid Email. It should start with at least 3 letters and follow a valid format.");
            return;
        }

        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Phone number is required.");
            return;
        } else if (!phone.matches("^\\d{10}$")) {
            JOptionPane.showMessageDialog(this, "Phone number should be exactly 10 digits.");
            return;
        }

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username is required.");
            return;
        } else if (!username.matches("^[A-Za-z]{4,}[A-Za-z0-9\\s]*$")) {
            JOptionPane.showMessageDialog(this, "Username must start with at least 4 letters and can contain letters, digits, and spaces.");
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password is required.");
            return;
        } else if (password.length() < 6 || password.length() > 12) {
            JOptionPane.showMessageDialog(this, "Password length must be between 6 and 12 characters.");
            return;
        }

        if (confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Confirm Password is required.");
            return;
        } else if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return;
        }

        try {
            Connection conn = Dbconnect.getConnection();
            if (conn != null) {
                System.out.println("Database connection successful!");
                String sql = "INSERT INTO users (Uname, Uemail, Upno, Username, Upass) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, fullName);
                pstmt.setString(2, email);
                pstmt.setString(3, phone);
                pstmt.setString(4, username);
                pstmt.setString(5, password);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Sign up successful! You can now log in.");
                    System.out.println("Record inserted successfully!");
                    txtUName.setText("");
                    txtEmail.setText("");
                    txtPNo.setText("");
                    txtUsrName.setText("");
                    txtPass.setText("");
                    txtCPass.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Sign up failed. Please try again.");
                }

                pstmt.close();
                conn.close();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to establish a database connection.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {
        txtUName.setText("");
        txtEmail.setText("");
        txtPNo.setText("");
        txtUsrName.setText("");
        txtPass.setText("");
        txtCPass.setText("");
    }

    private void txtUNameActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txtPNoActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txtUsrNameActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txtPassComponentShown(java.awt.event.ComponentEvent evt) {
        // TODO add your handling code here:
    }

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
}
