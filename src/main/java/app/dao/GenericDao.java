package app.dao;

import app.framework.ShowroomFramework;
import app.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.sql.*;
import java.util.List;

@ApplicationScoped
public class GenericDao {


    @PersistenceContext
    private EntityManager em;


    @Inject
    ShowroomFramework showroomFramework;

    @Transactional
    public void insert(Object entity) {

        em.persist(entity);
    }


    @Transactional
    public <T> void update(T entity) {
        em.merge(entity);
    }

    @Transactional
    public <T> void delete(Class<T> clazz, Long id) {
        T entity = em.find(clazz, id);
        if (entity != null) em.remove(entity);
    }

    public <T> T selectById(Class<T> clazz, Long id) {

        return em.find(clazz, id);
    }

    public <T> List<T> selectAll(Class<T> clazz) {
        return em.createQuery(
                "SELECT e FROM " + clazz.getSimpleName() + " e", clazz
        ).getResultList();
    }

    public <T> List<T> selectWhere(Class<T> clazz, String field, Object value) {
        return em.createQuery(
                "SELECT e FROM " + clazz.getSimpleName() + " e WHERE e." + field + " = :v", clazz
        ).setParameter("v", value).getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public <T> T getReference(Class<T> clazz, Long id) {
        return em.getReference(clazz, id);
    }
}








































//    public void insert(Class<?> clazz, Object entity) {
//
//        try (Connection connection = helper.getConnection()) {
//
//            String tableName = clazz.getSimpleName().toLowerCase() + "s";
//            Field[] fields = clazz.getDeclaredFields();
//
//            StringBuilder columns = new StringBuilder();
//            StringBuilder placeholders = new StringBuilder();
//            List<Object> values = new ArrayList<>();
//
//            for (Field field : fields) {
//                field.setAccessible(true);
//                Object value = field.get(entity);
//
//                // Skip relationship object types (instances of domain model classes),
//                // collections, and other complex types that aren't directly persistable.
//                Class<?> ft = field.getType();
//
//                boolean isCollection = java.util.Collection.class.isAssignableFrom(ft);
//                boolean isDomainObject = ft.getPackage() != null && ft.getPackage().getName().startsWith("app.model");
//                boolean isSupported =
//                        ft.isPrimitive() ||
//                                ft.equals(String.class) ||
//                                Number.class.isAssignableFrom(ft) ||
//                                java.util.Date.class.isAssignableFrom(ft) ||
//                                ft.equals(Boolean.class) ||
//                                ft.equals(Character.class) ||
//                                ft.equals(java.sql.Date.class) ||
//                                ft.equals(java.sql.Timestamp.class) ||
//                                ft.equals(Long.class) || ft.equals(Integer.class) || ft.equals(Short.class);
//
//                if (isCollection || isDomainObject || !isSupported) {
//                    // Skip complex/relationship fields like User, Brand, Showroom, Lists, etc.
//                    continue;
//                }
//
//
//                if (value != null) {
//                    if (columns.length() > 0) {
//                        columns.append(", ");
//                        placeholders.append(", ");
//                    }
//                    columns.append(field.getName());
//                    placeholders.append("?");
//                    values.add(value);
//                }
//            }
//
//            if (values.isEmpty()) {
//                throw new RuntimeException("No non-null fields to insert");
//            }
//
//            String sql = "INSERT INTO " + tableName +
//                    " (" + columns + ") VALUES (" + placeholders + ")";
//
//
//            PreparedStatement ps = connection.prepareStatement(sql);
//
//            for (int i = 0; i < values.size(); i++) {
//                ps.setObject(i + 1, values.get(i));
//            }
//
//            ps.executeUpdate();
//            System.out.println("Inserted into " + tableName);
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }



//    public <T> List<T> selectAll(Class<T> clazz) {
//
//        List<T> entities = new ArrayList<>();
//        String tableName = clazz.getSimpleName().toLowerCase() + "s";
//        String sql = "SELECT * FROM " + tableName;
//
//        try (Connection connection = helper.getConnection();
//             Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            Field[] fields = clazz.getDeclaredFields();
//
//            while (rs.next()) {
//                T entity = clazz.getDeclaredConstructor().newInstance();
//
//                for (Field field : fields) {
//                    field.setAccessible(true);
//
//                    Class<?> ft = field.getType();
//                    boolean isCollection = java.util.Collection.class.isAssignableFrom(ft);
//                    boolean isDomainObject = ft.getPackage() != null && ft.getPackage().getName().startsWith("app.model");
//                    boolean hasRelationship = field.isAnnotationPresent(app.framework.ShowroomRelationship.class);
//
//                    if (isCollection || isDomainObject || hasRelationship) {
//
//                        continue;
//                    }
//
//                    try {
//                        Object value = rs.getObject(field.getName());
//                        if (value != null) {
//
//                            if (field.getType().equals(java.util.Date.class) && value instanceof java.time.LocalDateTime) {
//                                java.time.LocalDateTime ldt = (java.time.LocalDateTime) value;
//                                value = java.util.Date.from(ldt.atZone(java.time.ZoneId.systemDefault()).toInstant());
//                            }
//
//                            field.set(entity, value);
//                        }
//                    } catch (SQLException e) {
//                        System.out.println("Column not found: " + field.getName());
//                    }
//                }
//
//                entities.add(entity);
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        return entities;
//    }
//
//    public <T> T selectById(Class<T> clazz, Long id) {
//        String tableName = clazz.getSimpleName().toLowerCase() + "s";
//        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
//
//        try (Connection connection = helper.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setLong(1, id);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    T entity = clazz.getDeclaredConstructor().newInstance();
//                    for (Field field : clazz.getDeclaredFields()) {
//                        field.setAccessible(true);
//
//                        Class<?> ft = field.getType();
//                        boolean isCollection = java.util.Collection.class.isAssignableFrom(ft);
//                        boolean isDomainObject = ft.getPackage() != null && ft.getPackage().getName().startsWith("app.model");
//                        boolean hasRelationship = field.isAnnotationPresent(app.framework.ShowroomRelationship.class);
//
//                        if (isCollection || isDomainObject || hasRelationship) {
//
//                            continue;
//                        }
//
//                        Object value = rs.getObject(field.getName());
//                        if (value != null) field.set(entity, value);
//                    }
//                    return entity;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public <T> List<T> selectWhere(Class<T> clazz, String column, Object value) throws SQLException {
//        List<T> list = new ArrayList<>();
//        String tableName = clazz.getSimpleName().toLowerCase() + "s";
//        String sql = "SELECT * FROM " + tableName + " WHERE " + column + " = ?";
//
//        try (Connection connection = helper.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setObject(1, value);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                Field[] fields = clazz.getDeclaredFields();
//
//                while (rs.next()) {
//                    T entity = clazz.getDeclaredConstructor().newInstance();
//
//                    for (Field field : fields) {
//                        field.setAccessible(true);
//
//                        Class<?> ft = field.getType();
//                        boolean isCollection = java.util.Collection.class.isAssignableFrom(ft);
//                        boolean isDomainObject = ft.getPackage() != null && ft.getPackage().getName().startsWith("app.model");
//                        boolean hasRelationship = field.isAnnotationPresent(app.framework.ShowroomRelationship.class);
//
//                        if (isCollection || isDomainObject || hasRelationship) {
//
//                            continue;
//                        }
//
//                        try {
//                            value = rs.getObject(field.getName());
//                            if (value != null) {
//                                field.set(entity, value);
//                            }
//                        } catch (SQLException e) {
//                            System.out.println("Column not found: " + field.getName());
//                        }
//                    }
//                    list.add(entity);
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            } catch (InvocationTargetException e) {
//                throw new RuntimeException(e);
//            } catch (InstantiationException e) {
//                throw new RuntimeException(e);
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            } catch (NoSuchMethodException e) {
//                throw new RuntimeException(e);
//            }
//            return list;
//        }
//    }
//
//    public <T> void update(Class<T> clazz, T entity) {
//        String tableName = clazz.getSimpleName().toLowerCase() + "s";
//
//        try (Connection connection = helper.getConnection()) {
//
//            Field[] fields = clazz.getDeclaredFields();
//
//            StringBuilder sql = new StringBuilder(
//                    "UPDATE " + tableName + " SET "
//            );
//
//            List<Object> values = new ArrayList<>();
//
//            Object idValue = null;
//
//            for (Field field : fields) {
//                field.setAccessible(true);
//                Class<?> ft = field.getType();
//
//                boolean isCollection =
//                        java.util.Collection.class.isAssignableFrom(ft);
//
//                boolean isDomainObject =
//                        ft.getPackage() != null &&
//                                ft.getPackage().getName().startsWith("app.model");
//
//                boolean hasRelationship =
//                        field.isAnnotationPresent(
//                                ShowroomRelationship.class
//                        );
//
//                if (isCollection || isDomainObject || hasRelationship) {
//                    continue;
//                }
//
//                Object value = field.get(entity);
//
//                // Store ID separately for WHERE clause
//                if (field.getName().equalsIgnoreCase("id")) {
//                    idValue = value;
//                    continue;
//                }
//
//                if (value != null) {
//
//                    if (!values.isEmpty()) {
//                        sql.append(", ");
//                    }
//
//                    sql.append(field.getName()).append(" = ?");
//                    values.add(value);
//                }
//            }
//
//            if (idValue == null) {
//                throw new RuntimeException(
//                        "Cannot update entity without ID"
//                );
//            }
//
//            sql.append(" WHERE id = ?");
//
//            PreparedStatement ps =
//                    connection.prepareStatement(sql.toString());
//
//            for (int i = 0; i < values.size(); i++) {
//                ps.setObject(i + 1, values.get(i));
//            }
//
//            ps.setObject(values.size() + 1, idValue);
//
//            int rows = ps.executeUpdate();
//
//            System.out.println(
//                    "Updated " + rows +
//                            " row(s) in " + tableName
//            );
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public <T> void delete(Class<T> clazz, Long id) {
//
//        String tableName =
//                clazz.getSimpleName().toLowerCase() + "s";
//
//        String sql =
//                "DELETE FROM " + tableName + " WHERE id = ?";
//
//        try (Connection connection = helper.getConnection();
//             PreparedStatement ps =
//                     connection.prepareStatement(sql)) {
//
//            ps.setLong(1, id);
//
//            int rows = ps.executeUpdate();
//
//            System.out.println(
//                    "Deleted " + rows +
//                            " row(s) from " + tableName
//            );
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//        public <T > void populateRelationships (T entity){
//            Class<?> clazz = entity.getClass();
//            for (Field field : clazz.getDeclaredFields()) {
//                if (!field.isAnnotationPresent(ShowroomRelationship.class))
//                    continue;
//
//                ShowroomRelationship rel = field.getAnnotation(ShowroomRelationship.class);
//                field.setAccessible(true);
//                System.out.println("Found relationship for field: " + field.getName());
//
//                try {
//                    // CASE 1: It's a List (One-to-Many)
//                    if (field.getType() == List.class) {//List<?>
//                        // Get the generic type of the list (e.g., Car.class)
//                        ParameterizedType listType = (ParameterizedType) field.getGenericType();//List<Car>
//                        Class<?> childClass = (Class<?>) listType.getActualTypeArguments()[0];//Car.class
//
//                        // Fetch the ID of the current entity (e.g., showroom.id)
//                        Object idValue = showroomFramework.getFieldValue(entity, "id");
//                        System.out.println("Populating List for Showroom ID: " + idValue);
//
//
//                        // SELECT * FROM cars WHERE showroomId = ?
//                        List<?> children = selectWhere(childClass, rel.mappedBy(), idValue);
//                        field.set(entity, children);
//
//                        System.out.println("Found " + children.size() + " cars for showroom " + idValue);
//                    }
//
//                    // CASE 2: It's a single Object (Many-to-One / One-to-One)
//                    else {
//                        Class<?> parentClass = field.getType();
//                        // Get the ID field name (e.g., if field is 'manager', look for 'managerId')
//                        String fkFieldName = field.getName() + "Id";
//                        Object fkValue = showroomFramework.getFieldValue(entity, fkFieldName);
//
//                        if (fkValue != null) {
//                            Object parent = selectById(parentClass, (Long) fkValue);
//                            field.set(entity, parent);
//                        }
//                        System.out.println("FK Value for Manager: " + fkValue);
//                    }
//                } catch (Exception e) {
//                    System.out.println("Failed to populate relationship: " + field.getName());
//                }
//            }
//        }



