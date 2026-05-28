package app.dao;

import app.framework.FrameworkDataProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Strategy 2 — serves from memory, only hits DB once per class
@ApplicationScoped
@Alternative
public class CachedDataProvider implements FrameworkDataProvider {

    @Inject
    GenericDao genericDao;

    // In-memory store — key is the class, value is the cached list
    private final Map<Class<?>, List<?>> cache = new ConcurrentHashMap<>();
    // {"Brand": ["Toyota","Volkswagen", "Mercedes"]}

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> selectAll(Class<T> clazz) {

        if (cache.containsKey(clazz)) {
            System.out.println("CACHE HIT: " + clazz.getSimpleName());
            return (List<T>) cache.get(clazz);
        }

        System.out.println("CACHE MISS: loading " + clazz.getSimpleName()
                + " from DB");
        List<T> result = genericDao.selectAll(clazz);
        cache.put(clazz, result);
        return result;
    }

    @Override
    public <T> T getReference(Class<T> clazz, Long id) {
        return genericDao.getReference(clazz, id);
    }

    // Call this when a brand/category/showroom is added or updated
    // so the cache does not serve stale data
    @Override
    public void evict(Class<?> clazz) {
        cache.remove(clazz);
        System.out.println("CACHE EVICTED: " + clazz.getSimpleName());
    }
}