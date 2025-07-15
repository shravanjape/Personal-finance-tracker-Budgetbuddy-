package budgetbuddy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlertsNotifications extends javax.swing.JFrame {
 
  /*  public AlertsNotifications() {
        initComponents();
    }*/

    @SuppressWarnings("unchecked")
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

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlertsNotifications().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private DefaultListModel<String> alertListModel;
    private JList<String> alertList;
    private JButton refreshButton;
    
    public AlertsNotifications() {
        setTitle("Alerts & Notifications");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel Setup
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Critical Alerts"));
        
        // List of Alerts
        alertListModel = new DefaultListModel<>();
        alertList = new JList<>(alertListModel);
        JScrollPane scrollPane = new JScrollPane(alertList);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Refresh Button
        refreshButton = new JButton("Refresh Alerts");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAlerts();
            }
        });
        panel.add(refreshButton, BorderLayout.SOUTH);
        
        add(panel);
        loadAlerts(); // Initial load
    }
    
    private void loadAlerts() {
        alertListModel.clear();
        
        // Dummy Data (This should be replaced with database fetch logic)
        alertListModel.addElement("⚠ Budget Exceeded: Groceries by ₹3000");
        alertListModel.addElement("⏳ Savings Goal Reminder:saving goal achieved for this Month.");
        alertListModel.addElement("🔔 New Expense Added: $100 on Entertainment");
        alertListModel.addElement("🔧 Profile Updated successfully.");
       
    }
    
}

