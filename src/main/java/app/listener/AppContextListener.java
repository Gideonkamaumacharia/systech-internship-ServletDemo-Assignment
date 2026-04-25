package app.listener;


import app.model.*;
import app.utility.DatabaseManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseManager.getInstance();

        List<Class<?>> modelClasses = Arrays.asList(User.class, Car.class, Brand.class, Showroom.class, Category.class,AuditLog.class);
        for(Class<?> clazz: modelClasses){
            try {
                createTableIfNotExists(clazz);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void  createTableIfNotExists(Class<?> clazz) throws SQLException {

        try(Connection connection = DatabaseManager.getInstance().getConnection();
            Statement statement = connection.createStatement()){

            String tableName = clazz.getSimpleName().toLowerCase();
            Field[] fields = clazz.getDeclaredFields();

            StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (id INT AUTO_INCREMENT PRIMARY KEY");

            for(Field field: fields){
                field.setAccessible(true);
                String columnName = field.getName();
                String sqlType = getSqlType(field.getType());
                sql.append(", ").append(columnName).append(" ").append(sqlType);
            }
            sql.append(")");
            System.out.println("Creating table: " + sql);

            statement.executeUpdate(sql.toString());
            System.out.println("Table created successfully: " + tableName);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getSqlType(Class<?> javaType){
        if (javaType == String.class) return "VARCHAR(255)";
        if (javaType == int.class || javaType == Integer.class) return "INT";
        if (javaType == long.class || javaType == Long.class) return "BIGINT";
        if (javaType == double.class || javaType == Double.class) return "DOUBLE";
        if (javaType == Date.class) return "DATE";
        return "VARCHAR(255)";
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
