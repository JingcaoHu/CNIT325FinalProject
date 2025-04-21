package com.ai_assistant.api.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertRecord extends DatabaseConnection implements DatabaseHandler{
    Connection conn = null;
    PreparedStatement statement = null;
    
    public String getSelection(int selection){
        switch (selection){
            case 0:
                return "Suggestion";
            case 1:
                return "Debug";
            case 3:
                return "Generic";
            default:
                throw new IllegalArgumentException("Invalid selection value: " + selection);
        }
    }

    @Override
    public String connectDatabase(Prompt prompt) {
        try {
            //Establish connection to database
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            
            if (conn != null) {
                System.out.println("Connected to database.");

                String insertRecordSQL = "INSERT INTO HISTORY (UID, SELECTION, CONTENT, RESPONSE, TIME_STAMP) " +
                                        "VALUES (?, ?, ?, ?, ?)";
                statement = conn.prepareStatement(insertRecordSQL);
                statement.setInt(1, prompt.getUID());
                statement.setString(2, getSelection(prompt.getSelection()));
                statement.setString(3, prompt.getContent());
                statement.setString(4, prompt.getResponse());
                statement.setString(5, prompt.getTimeStamp());


                int rowsAffected = statement.executeUpdate();
                System.out.println("SQL executed.");

                if (rowsAffected == 0){
                    System.out.println("Error: Insertion failed.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: Insertion failed: " + e.getMessage());
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
        Prompt test = new Prompt(1, 1, "test con", "test response");
        test.setTimeStamp("25-04-21 03:26:15");
        InsertRecord record = new InsertRecord();
        record.connectDatabase(test);
    }
   
}
