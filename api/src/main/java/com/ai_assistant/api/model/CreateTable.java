package com.ai_assistant.api.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTable extends DatabaseConnection implements DatabaseHandler{
    Connection conn = null;
    Statement statement = null;
    
    @Override
    public String connectDatabase(Prompt prompt) {
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
                                        "TIME_STAMP DATETIME" +
                                        ")";
                statement.executeUpdate(createTableSQL);
            }
        } catch (Exception e) {
        }
        return null;
    }
}
