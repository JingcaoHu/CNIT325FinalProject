package com.ai_assistant.api.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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

                String createTableSQL = "SELECT * FROM HISTORY " +
                                        "WHERE UID = " +  UID;

                //Add SQL query constraint if selection filter is set
                if (selectedFunction != 5){
                    String function = DatabaseConnection.getSelection(selectedFunction);
                    createTableSQL = createTableSQL + " AND SELECTION = '" + function + "'";
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
        // Class.forName("com.mysql.jdbc.Driver").newInstance(); // No need for this in modern JDBC
        //  Prompt test = new Prompt(1, 1, null, null);
        //  test.setTimeStamp("25-04-21 03:26:15");
        //  CreateTable table = new CreateTable();
        //  table.connectDatabase(test);

        RecordRetriever retriever = new RecordRetriever();
        //  You'll need to replace 123 with an actual UID from your database.
        ResultSet rs = retriever.getTable(123, 5); // 5 for all records, change as needed.

        try {
            if (rs != null) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Print column names
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(metaData.getColumnName(i) + "\t");
                }
                System.out.println();

                // Print the data
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(rs.getString(i) + "\t");
                    }
                    System.out.println();
                }
                rs.close();
            } else {
                System.out.println("Result set is null.  Check your database connection and query.");
            }
        } catch (SQLException e) {
            System.err.println("Error processing result set: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources in a finally block
            try {
                if (retriever.statement != null) {
                    retriever.statement.close();
                }
                if (retriever.conn != null) {
                    retriever.conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
   
}
