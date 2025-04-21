package com.ai_assistant.api.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnector implements ConnectionHandler{
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ai_assistant_database";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "rootroot";


    @Override
    public String runConnection(int port, String address, Prompt passedInfo) {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            //Establish connection to database
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            
            if (conn != null) {
                System.out.println("Connected to database.");
                statement = (Statement) conn.createStatement();

                String createTableSQL = "CREATE TABLE IF NOT EXISTS HISTORY (" +
                                        "RECORD_ID INT AUTO_INCREMENT PRIMARY KEY," +
                                        "UID VARCHAR(6) NOT NULL," +
                                        "SELECTION VARCHAR(10) NOT NULL,"+
                                        "CONTENT VARCHAR(4096)," +
                                        "RESPONSE VARCHAR(4096)," +
                                        "TIME_STAMP TIMESTAMP" +
                                        ")";
                statement.executeUpdate(createTableSQL);
            }
        } catch (Exception e) {
        }





        return null;
    }
    

   
}
