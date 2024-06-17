package javaClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://itnt0005;databaseName=SWB_DB2_Projekt;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "swb4";
    private static final String PASSWORD = "swb4";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
