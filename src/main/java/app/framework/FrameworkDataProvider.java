package app.framework;

import java.util.List;

public interface FrameworkDataProvider {

    // Load all records of a given class for select dropdowns
    <T> List<T> selectAll(Class<T> clazz);

    // Get a proxy reference for relationship binding
    <T> T getReference(Class<T> clazz, Long id);

    void evict(Class<?> clazz);
}