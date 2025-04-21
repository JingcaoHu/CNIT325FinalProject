package com.ai_assistant.api.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InsertRecord extends DatabaseConnection implements DatabaseHandler{
    Connection conn = null;
    Statement statement = null;
    
    public String getSelection(int selection){
        switch (selection){
            case 0:
                return "Suggestion";
            case 1:
                return "Debug";
            case 3:
                return "Generic";
            default:
                throw new AssertionError();
            }
    }

    @Override
    public String connectDatabase(Prompt prompt) {
        try {
            //Establish connection to database
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            
            if (conn != null) {
                System.out.println("Connected to database.");
                statement = conn.createStatement();

                String insertRecordSQL = "INSERT INTO HISTORY (UID, SELECTION, CONTENT, RESPONSE, TIME_STAMP) " +
                                        "VALUES (" + prompt.getUID() + ", '" + getSelection(prompt.getSelection()) +
                                        "', '" + prompt.getContent() + "', '" + prompt.getResponse() + "'," +
                                        prompt.getTimeStamp() + ");" +
                statement.executeUpdate(insertRecordSQL);
            }
        } catch (Exception e) {
        }





        return null;
    }
    

   
}
