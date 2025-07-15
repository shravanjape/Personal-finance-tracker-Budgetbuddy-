package budgetbuddy;

import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class Login extends javax.swing.JFrame {

    public Login() {

        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // This line forces the frame to maximize
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Ensure no field is selected when the login frame opens
        SwingUtilities.invokeLater(() -> {
            getContentPane().requestFocusInWindow();
        });
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
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration at class                   
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSignup;
    private javax.swing.JLabel lblCpass;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblQuestion;
    private javax.swing.JLabel lblQuote;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsrName;
    private javax.swing.JLabel lblquote;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUName;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lblLogo = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblquote = new javax.swing.JLabel();
        lblUsrName = new javax.swing.JLabel();
        lblCpass = new javax.swing.JLabel();
        txtUName = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        lblQuestion = new javax.swing.JLabel();
        btnSignup = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        lblQuote = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.WHITE);  // Set background to white

// Use a layout that supports centering
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);  // Add some padding

// Add a component listener to keep the login content centered on resize
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                setLocationRelativeTo(null);
                revalidate();
                repaint();
            }
        });

        lblLogo.setFont(new java.awt.Font("Times New Roman", 0, 14)); 
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/budgetbuddy/logo1.png"))); 
        lblLogo.setText("jLabel1");

        lblTitle.setFont(new java.awt.Font("Times New Roman", 1, 54)); 
        lblTitle.setText(" BudgetBuddy");

        lblquote.setFont(new java.awt.Font("Times New Roman", 0, 14)); 
        lblquote.setText("“Take control of your money, and your future will thank you.”");

        lblUsrName.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        lblUsrName.setText("  Username :");

        lblCpass.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        lblCpass.setText(" Password : ");

        // Placeholder for Username field
        txtUName.setText("Enter User Name");
        txtUName.setForeground(new java.awt.Color(153, 153, 153));

        txtUName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtUName.getText().equals("Enter User Name")) {
                    txtUName.setText("");
                    txtUName.setForeground(new java.awt.Color(0, 0, 0));
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtUName.getText().isEmpty()) {
                    txtUName.setText("Enter User Name");
                    txtUName.setForeground(new java.awt.Color(153, 153, 153));
                }
            }
        });

// Placeholder for Password field
        txtPass.setText("Enter Password");
        txtPass.setForeground(new java.awt.Color(153, 153, 153));

        txtPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (new String(txtPass.getPassword()).equals("Enter Password")) {
                    txtPass.setText("");
                    txtPass.setForeground(new java.awt.Color(0, 0, 0));
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (new String(txtPass.getPassword()).isEmpty()) {
                    txtPass.setText("Enter Password");
                    txtPass.setForeground(new java.awt.Color(153, 153, 153));
                }
            }
        });

        // Ensure no field is selected when the login frame opens
        SwingUtilities.invokeLater(() -> {
            getContentPane().requestFocusInWindow();
        });

        btnLogin.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        btnLogin.setText("Log in");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        lblQuestion.setFont(new java.awt.Font("Times New Roman", 0, 14)); 
        lblQuestion.setText("Don't have an account? ");

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

        lblQuote.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        lblQuote.setText("      Secure and simple money management.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(130, 130, 130)
                                                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lblquote, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(281, 281, 281)
                                                                .addComponent(lblQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(btnLogin)
                                                                .addGap(28, 28, 28)))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btnSignup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(lblQuote, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(264, 264, 264)
                                                                        .addComponent(lblUsrName))
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(270, 270, 270)
                                                                        .addComponent(lblCpass, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(txtUName, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(158, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(96, 96, 96)
                                                .addComponent(lblquote, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(lblLogo)
                                                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblUsrName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtUName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblCpass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnLogin)
                                        .addComponent(btnClear))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSignup, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                                .addGap(136, 136, 136)
                                .addComponent(lblQuote)
                                .addGap(40, 40, 40))
        );

        // Ensure no field is selected when the login frame opens
        SwingUtilities.invokeLater(() -> {
            getContentPane().requestFocusInWindow();
        });
        pack();

    }// </editor-fold>                        

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {
        txtUName.setText("");
        txtPass.setText("");
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        String username = txtUName.getText().trim();
        String password = new String(txtPass.getPassword()).trim();

        // Placeholder validation
        if (username.equals("Enter Username") || password.equals("Enter Password")) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        // Validation for Username
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username is required.");
            return;
        } else if (!username.matches("^[A-Za-z]{4,}[A-Za-z0-9\\s]*$")) {
            JOptionPane.showMessageDialog(this, "Username must start with at least 4 letters and can contain letters, digits, and spaces.");
            return;
        }

        // Validation for Password
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password is required.");
            return;
        } else if (password.length() < 6 || password.length() > 12) {
            JOptionPane.showMessageDialog(this, "Password length must be between 6 and 12 characters.");
            return;
        }

        // Attempting to connect to the database and validate credentials
        try (Connection conn = Dbconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND upass = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                int userId = rs.getInt("Uid");
                System.out.println("uid=" + userId);
                SessionManager.setLoggedInUserId(userId);
                JOptionPane.showMessageDialog(this, "Login successful!"); // Navigate to Dashboard or the next frame

                Dashboard dashboard = new Dashboard(username);  // Pass the username to the Dashboard
                dashboard.setVisible(true);  // Show the dashboard
                this.dispose();  // Close the login frame

                System.out.println("Login successful for user: " + username);
            } else {
                JOptionPane.showMessageDialog(this, "Username or Password is incorrect.", "Login Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Login failed for user: " + username);
            }

            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {
    try {
        // Check if the Signup class exists and can be instantiated
        Signup signUpFrame = new Signup();
        if (signUpFrame == null) {
            throw new NullPointerException("Signup frame could not be initialized.");
        }
        signUpFrame.setVisible(true);
        this.dispose();
        System.out.println("Successfully redirected to the Sign Up page!");
    } catch (NullPointerException e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Unexpected error while opening Sign Up page: " + e.getMessage());
        e.printStackTrace();
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
