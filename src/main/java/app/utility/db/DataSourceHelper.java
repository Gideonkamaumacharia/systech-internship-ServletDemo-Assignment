//package app.utility.db;
//import jakarta.annotation.Resource;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.servlet.ServletContext;
//
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//@ApplicationScoped
//public class DataSourceHelper {
//
//    @Resource(lookup = "java:jboss/datasources/ShowroomDS")
//    private DataSource dataSource;
//
//    public DataSource getDataSource() {
//        return dataSource;
//    }
//
//    public Connection getConnection() throws SQLException {
//        return this.getDataSource().getConnection();
//    }
//
//}