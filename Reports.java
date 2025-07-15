package budgetbuddy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.InputStream;
import com.itextpdf.text.Image;

public class Reports extends javax.swing.JFrame {

    /* @SuppressWarnings("unchecked")
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
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
*/
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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
    }// </editor-fold>                        
    private static final String DEFAULT_USERNAME = "Guest";

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new Reports(DEFAULT_USERNAME).setVisible(true));
    }

    public Reports(String username) {
        setTitle("Financial Summary Report");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel summaryPanel = createSummaryPanel();
        add(summaryPanel, BorderLayout.CENTER);

        JButton downloadReportBtn = new JButton("Download Full Financial Report");
        downloadReportBtn.setPreferredSize(new Dimension(300, 30));
        downloadReportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePDFReport();
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(downloadReportBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("BudgetBuddy Financial Summary"));

        JLabel userLabel = new JLabel("User Name: Loading...");
        JLabel useridLabel = new JLabel("User ID: Loading...");
        JLabel transactionsLabel = new JLabel("Total Transactions: Loading...");
        JLabel incomeLabel = new JLabel("Total Income: Loading...");
        JLabel expenseLabel = new JLabel("Total Expense: Loading...");
        JLabel savingsLabel = new JLabel("Total Savings: Loading...");
        JLabel budgetLabel = new JLabel("<html>Budget Summary: Loading...</html>");
        JLabel goalLabel = new JLabel("Saving Goals: Loading...");

        // Setting preferred size and wrapping the budget label in a scroll pane
        budgetLabel.setVerticalAlignment(SwingConstants.TOP);
        budgetLabel.setPreferredSize(new Dimension(350, 100));
        JScrollPane budgetScrollPane = new JScrollPane(budgetLabel);
        budgetScrollPane.setPreferredSize(new Dimension(350, 100));
        budgetScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Fetch user data and update labels
        try (Connection conn = Dbconnect.getConnection(); Statement stmt = conn.createStatement()) {
            int userId = SessionManager.getLoggedInUserId();

            // User Info
            String userSql = "SELECT Uname, Uid FROM users WHERE Uid = " + userId;
            ResultSet userRs = stmt.executeQuery(userSql);
            if (userRs.next()) {
                userLabel.setText("User Name: " + userRs.getString("Uname"));
                useridLabel.setText("User ID: " + userRs.getString("Uid"));
            }

            // Transaction Summary
            String transactionSql = "SELECT COUNT(*) AS totalTransactions, "
                    + "SUM(CASE WHEN type = 'Income' THEN amount ELSE 0 END) AS totalIncome, "
                    + "SUM(CASE WHEN type = 'Expense' THEN amount ELSE 0 END) AS totalExpense "
                    + "FROM transactions WHERE user_id = " + userId;
            ResultSet transactionRs = stmt.executeQuery(transactionSql);
            if (transactionRs.next()) {
                int totalTransactions = transactionRs.getInt("totalTransactions");
                double totalIncome = transactionRs.getDouble("totalIncome");
                double totalExpense = transactionRs.getDouble("totalExpense");

                transactionsLabel.setText("Total Transactions: " + totalTransactions);
                incomeLabel.setText("Total Income: ₹ " + String.format("%.2f", totalIncome));
                expenseLabel.setText("Total Expense: ₹ " + String.format("%.2f", totalExpense));
            }

            // Saving Goals Summary
            String goalsSql = "SELECT COUNT(*) AS totalGoals, SUM(saved_amount) AS totalSavings FROM saving_goals WHERE user_id = " + userId;
            ResultSet goalsRs = stmt.executeQuery(goalsSql);
            if (goalsRs.next()) {
                int totalGoals = goalsRs.getInt("totalGoals");
                double totalSavings = goalsRs.getDouble("totalSavings");
                goalLabel.setText("Saving Goals: " + totalGoals);
                savingsLabel.setText("Total Savings: ₹ " + String.format("%.2f", totalSavings));
            }

            // Budget Summary (Report Panel)
            String budgetSql = "SELECT category, SUM(amount) AS totalAmount FROM transactions WHERE user_id = "
                    + SessionManager.getLoggedInUserId() + " GROUP BY category";
            ResultSet budgetRs = stmt.executeQuery(budgetSql);
            StringBuilder budgetSummary = new StringBuilder("<html>Budget Summary:<br>");
            while (budgetRs.next()) {
                budgetSummary.append(budgetRs.getString("category"))
                        .append(": ₹ ")
                        .append(String.format("%.2f", budgetRs.getDouble("totalAmount")))
                        .append("<br>");
            }
            budgetSummary.append("</html>");
            budgetLabel.setText(budgetSummary.toString());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Loading Summary Data!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Add components to the panel
        panel.add(userLabel);
        panel.add(useridLabel);
        panel.add(transactionsLabel);
        panel.add(incomeLabel);
        panel.add(expenseLabel);
        panel.add(savingsLabel);
        panel.add(budgetScrollPane);  // Adding the scroll pane instead of the label directly
        panel.add(goalLabel);

        return panel;
    }

    private void generatePDFReport() {
        try (Connection conn = Dbconnect.getConnection(); Statement stmt = conn.createStatement()) {

            // Generate file path for the PDF
            String fileName = "Financial_Report_" + new SimpleDateFormat("yyyy_MM").format(new Date()) + ".pdf";
            String filePath = System.getProperty("user.home") + "/Documents/" + fileName;

            // Create the document and PDF writer
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();  // Open the document first

            // Load logo image (Ensure the file path is correct)
            // Load logo image (Ensure the file path is correct)
            try {
                // Load the logo image using the class loader
                InputStream inputStream = getClass().getResourceAsStream("/budgetbuddy/logo1.png");
                if (inputStream != null) {
                    byte[] imageBytes = inputStream.readAllBytes();
                    Image logo = Image.getInstance(imageBytes);
                    logo.scaleToFit(100, 100);
                    logo.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    document.add(logo);
                } else {
                    System.err.println("Logo image not found!");
                }
            } catch (Exception e) {
                System.err.println("Error loading logo image: " + e.getMessage());
            }

            // Project Name
            Paragraph projectName = new Paragraph("BudgetBuddy - Financial Summary Report",
                    new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            projectName.setAlignment(Element.ALIGN_CENTER);
            document.add(projectName);

            // Motivational Quote
            Paragraph quote = new Paragraph("\"Smart Financial Planning for Your Future!\"",
                    new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC));
            quote.setAlignment(Element.ALIGN_CENTER);
            quote.setSpacingAfter(20);
            document.add(quote);

            // Add spacing before content
            document.add(new Paragraph("\n"));

            // Budget Summary Section
            PdfPTable budgetTable = new PdfPTable(2); // 2 columns
            budgetTable.setWidthPercentage(100);
            PdfPCell budgetCell = new PdfPCell(new Paragraph("Budget Summary"));
            budgetCell.setColspan(2);
            budgetCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            budgetTable.addCell(budgetCell);
            budgetTable.addCell("Category");
            budgetTable.addCell("Amount");

            // Add spacing before tables
            document.add(new Paragraph("\n"));

            // Calling additional summary methods
            generateUserSummary(document, stmt);
            generateTransactionSummary(document, stmt);
            generateBudgetSummary(document, stmt);
            generateSavingGoalsSummary(document, stmt);

            // Close the document
            document.close();
            JOptionPane.showMessageDialog(this, "PDF Report Downloaded Successfully! Location: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Generating PDF Report!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateUserSummary(Document document, Statement stmt) throws SQLException {
        try {
            int userId = SessionManager.getLoggedInUserId();
            String sql = "SELECT Uname, Uid FROM users WHERE Uid = " + userId;
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String userName = rs.getString("Uname");
                userId = rs.getInt("Uid");

                PdfPTable userTable = new PdfPTable(2);
                userTable.setWidthPercentage(100);
                userTable.addCell("User Name:");
                userTable.addCell(userName);
                userTable.addCell("User ID:");
                userTable.addCell(String.valueOf(userId));

                document.add(userTable);
                document.add(new Paragraph("\n"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Generating User Summary!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateTransactionSummary(Document document, Statement stmt) throws SQLException {
        try {
            int userId = SessionManager.getLoggedInUserId();
            String sql = "SELECT COUNT(*) AS totalTransactions, "
                    + "SUM(CASE WHEN type = 'Income' THEN amount ELSE 0 END) AS totalIncome, "
                    + "SUM(CASE WHEN type = 'Expense' THEN amount ELSE 0 END) AS totalExpense "
                    + "FROM transactions WHERE user_id = " + userId;
            ResultSet rs = stmt.executeQuery(sql);

            PdfPTable transactionTable = new PdfPTable(3); // 3 columns: Total Transactions, Total Income, Total Expense
            transactionTable.setWidthPercentage(100);
            transactionTable.addCell("Total Transactions");
            transactionTable.addCell("Total Income");
            transactionTable.addCell("Total Expense");

            if (rs.next()) {
                int totalTransactions = rs.getInt("totalTransactions");
                double totalIncome = rs.getDouble("totalIncome");
                double totalExpense = rs.getDouble("totalExpense");

                transactionTable.addCell(String.valueOf(totalTransactions));
                transactionTable.addCell("₹ " + String.format("%.2f", totalIncome));  // Formatted with currency symbol
                transactionTable.addCell("₹ " + String.format("%.2f", totalExpense));  // Formatted with currency symbol
            }

            document.add(new Paragraph("Transaction Summary:"));
            document.add(new Paragraph("\n"));
            document.add(transactionTable); // Correctly adding the transactionTable
            document.add(new Paragraph("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error Generating Transaction Summary!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateBudgetSummary(Document document, Statement stmt) throws SQLException {
        try {
            String sql = "SELECT category, SUM(amount) AS totalAmount FROM transactions WHERE user_id = " + SessionManager.getLoggedInUserId() + " GROUP BY category";
            ResultSet rs = stmt.executeQuery(sql);

            PdfPTable budgetTable = new PdfPTable(2); // 2 columns for category and amount
            budgetTable.setWidthPercentage(100);
            budgetTable.addCell("Category");
            budgetTable.addCell("Total Amount");

            while (rs.next()) {
                // Clean the category data by removing any HTML tags directly
                String category = rs.getString("category").replaceAll("<[^>]*>", "");
                double totalAmount = rs.getDouble("totalAmount");
                budgetTable.addCell(category);
                budgetTable.addCell(String.format("%.2f", totalAmount));
            }

            document.add(new Paragraph("Budget Summary:\n"));
            document.add(budgetTable);
            document.add(new Paragraph("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Generating Budget Summary!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateSavingGoalsSummary(Document document, Statement stmt) throws SQLException {
        try {
            int userId = SessionManager.getLoggedInUserId();
            String sql = "SELECT goal_name, target_amount, saved_amount FROM saving_goals WHERE user_id = " + userId;
            ResultSet rs = stmt.executeQuery(sql);

            PdfPTable savingGoalsTable = new PdfPTable(3); // 3 columns: Goal Name, Target Amount, Saved Amount
            savingGoalsTable.setWidthPercentage(100);
            savingGoalsTable.addCell("Goal Name");
            savingGoalsTable.addCell("Target Amount");
            savingGoalsTable.addCell("Saved Amount");

            while (rs.next()) {
                String goalName = rs.getString("goal_name");
                double targetAmount = rs.getDouble("target_amount");
                double savedAmount = rs.getDouble("saved_amount");

                savingGoalsTable.addCell(goalName);
                savingGoalsTable.addCell(String.format("%.2f", targetAmount));
                savingGoalsTable.addCell(String.format("%.2f", savedAmount));
            }

            document.add(new Paragraph("Saving Goals Summary:"));
            document.add(new Paragraph("\n"));
            document.add(savingGoalsTable);
            document.add(new Paragraph("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Generating Saving Goals Summary!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
