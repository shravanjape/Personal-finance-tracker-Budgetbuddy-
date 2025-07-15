package budgetbuddy;

import java.sql.*;

public class Dbconnect {

    private static Connection conn = null;

    // Static method to get a database connection
    public static synchronized Connection getConnection() {
        try {
            // Check if the connection is null or closed, then reconnect
            if (conn == null || conn.isClosed()) {
                try {
                    // Establish a database connection
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/budgetbuddy", "budgetbuddy_user", "Sai@1234");
                    System.out.println("Database connection established successfully.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.err.println("Error establishing database connection: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error checking database connection status: " + e.getMessage());
        }
        return conn;
    }
}
