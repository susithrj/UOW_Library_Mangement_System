package services;

import java.sql.*;

public class DatabaseConnector {
    public static Connection connectToDatabase(){
        Connection logDatabase = null;
        try {
            //connecting to database
            logDatabase = DriverManager.getConnection("jdbc:mysql://localhost/library",
                    "Rajitha","Test123");

        } catch (SQLException e) {
            System.err.println("Connection Error");
        }
        return logDatabase;
    }
    public static ResultSet getData(String tableName, String columnName){
        //common method to get data from database
        String selectStm = "select  " + columnName + " from " + tableName;
        ResultSet result;
        try {
            Connection logDatabase = DatabaseConnector.connectToDatabase();
            PreparedStatement preStatment = logDatabase.prepareStatement(selectStm);
            result = preStatment.executeQuery();
            return result;

        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean deleteData(String table, String attribute , String value){
        //common method to delete data from database
        String deleteStm ="DELETE FROM " + table + " WHERE " + attribute + " =" + value + ";";

        try {
            Connection dbConn = DatabaseConnector.connectToDatabase();
            PreparedStatement stm = dbConn.prepareStatement(deleteStm, Statement.RETURN_GENERATED_KEYS);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }
}
