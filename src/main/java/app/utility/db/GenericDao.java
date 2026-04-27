package app.utility.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GenericDao {

    @Inject
    private DataSource dataSource;

    public void insert(Class<?> clazz, Object entity) {

        try (Connection connection = DatabaseManager.getInstance().getConnection()) {

            String tableName = clazz.getSimpleName().toLowerCase() + "s";
            Field[] fields = clazz.getDeclaredFields();

            StringBuilder columns = new StringBuilder();
            StringBuilder placeholders = new StringBuilder();
            List<Object> values = new ArrayList<>();

            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(entity);

                if (value != null) {
                    if (columns.length() > 0) {
                        columns.append(", ");
                        placeholders.append(", ");
                    }
                    columns.append(field.getName());
                    placeholders.append("?");
                    values.add(value);
                }
            }

            if (values.isEmpty()) {
                throw new RuntimeException("No non-null fields to insert");
            }

            String sql = "INSERT INTO " + tableName +
                    " (" + columns + ") VALUES (" + placeholders + ")";


            PreparedStatement ps = connection.prepareStatement(sql);

            for (int i = 0; i < values.size(); i++) {
                ps.setObject(i + 1, values.get(i));
            }

            ps.executeUpdate();
            System.out.println("Inserted into " + tableName);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> selectAll(Class<T> clazz) {

        List<T> entities = new ArrayList<>();
        String tableName = clazz.getSimpleName().toLowerCase()+"s";
        String sql = "SELECT * FROM " + tableName;

        try (Connection connection = DatabaseManager.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Field[] fields = clazz.getDeclaredFields();

            while (rs.next()) {
                T entity = clazz.getDeclaredConstructor().newInstance();

                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        Object value = rs.getObject(field.getName());
                        if (value != null) {
                            field.set(entity, value);
                        }
                    } catch (SQLException e) {
                        System.out.println("Column not found: " + field.getName());
                    }
                }

                entities.add(entity);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return entities;
    }
}
