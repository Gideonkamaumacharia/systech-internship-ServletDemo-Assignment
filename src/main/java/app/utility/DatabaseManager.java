package app.utility;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static DatabaseManager instance;
    private static final String URL = "jdbc:mysql://localhost:3308/showroom";//Create db conn first
    private static final String USER = "root";
    private static final String PASS = "root123";

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (instance == null)
            instance = new DatabaseManager();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

}