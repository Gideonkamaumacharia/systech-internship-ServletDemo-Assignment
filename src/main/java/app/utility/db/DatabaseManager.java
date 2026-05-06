//package app.utility.db;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class DatabaseManager {
//
//    private static DatabaseManager instance;
//    private static HikariDataSource dataSource;
//
//    // Private constructor to prevent direct instantiation
//    private DatabaseManager() {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl("jdbc:mysql://localhost:3308/showroom");
//        config.setUsername("root");
//        config.setPassword("root123");
//
//
//        dataSource = new HikariDataSource(config);
//    }
//
//    public static synchronized DatabaseManager getInstance() {
//        if (instance == null) {
//            instance = new DatabaseManager();
//        }
//        return instance;
//    }
//
//    public Connection getConnection() throws SQLException {
//        // This returns a connection from the pool
//        return dataSource.getConnection();
//    }
//}
package app.utility.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ApplicationScoped
public class DatabaseManager {

    @Inject
    private DataSourceHelper helper;

    private DataSource dataSource;

    private DataSource getDataSource(){
        if(dataSource == null){
            dataSource = helper.createDataSource();
        }
        return dataSource;
    }

    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
}