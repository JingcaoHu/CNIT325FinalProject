package com.ai_assistant.api.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable extends DatabaseConnection implements DatabaseHandler{
    Connection conn = null;
    Statement statement = null;
    
    @Override
    public String connectDatabase(Prompt prompt) {
        try {
            //Establish connection to database
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connected to database for table creation.");

            if (conn != null) {
                System.out.println("Connected to database.");
                statement = conn.createStatement();

                String createTableSQL = "CREATE TABLE IF NOT EXISTS HISTORY (" +
                                        "RECORD_ID INT AUTO_INCREMENT PRIMARY KEY," +
                                        "UID VARCHAR(6) NOT NULL," +
                                        "SELECTION VARCHAR(10) NOT NULL,"+
                                        "CONTENT VARCHAR(4096)," +
                                        "RESPONSE VARCHAR(4096)," +
                                        "TIME_STAMP DATETIME" +
                                        ")";
                statement.executeUpdate(createTableSQL);
                System.out.println("HISTORY table created or already exists.");

            }
        } catch (SQLException e) {
            System.err.println("Error: Cannot create table: " + e.getMessage());
        } finally {
            closeResources(conn, statement, null);
        }
        return null;
    }

    private static void closeResources(Connection conn, Statement stmt, java.sql.ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            System.err.println("Error closing ResultSet: " + e.getMessage());
        }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            System.err.println("Error closing Statement: " + e.getMessage());
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing Connection: " + e.getMessage());
        }
    }

    //Test main
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        // Class.forName("com.mysql.jdbc.Driver").newInstance();
        Prompt test = new Prompt(1, 1, null, null);
        test.setTimeStamp("25-04-21 03:26:15");
        CreateTable table = new CreateTable();
        table.connectDatabase(test);
    }
}
