package com.ai_assistant.api.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RecordRetriever extends DatabaseConnection{
    Connection conn = null;
    Statement statement = null;
    int selectedFunction;

    public RecordRetriever(){

    }
    
    public ResultSet getTable(int UID, int selectedFunction) {

        ResultSet resultSet = null;

        try {
            //Establish connection to database
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            
            if (conn != null) {
                System.out.println("Connected to database.");
                statement = (Statement) conn.createStatement();

                String createTableSQL = "SELECT * FROM HISTORY" +
                                        "WHERE UID = " +  UID;

                //Add SQL query constraint if selection filter is set
                if (selectedFunction != 5){
                    String function = DatabaseConnection.getSelection(selectedFunction);
                    createTableSQL = createTableSQL + "AND SELECTION = " + function;
                }

                //Add SQL query constraint if time filter is set
                //To-do code here

                resultSet = statement.executeQuery(createTableSQL);

                return resultSet;
            }
        } catch (Exception e) {
        }
        return null;
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
