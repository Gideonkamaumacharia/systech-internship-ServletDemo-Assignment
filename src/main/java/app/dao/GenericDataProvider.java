package app.dao;

import app.framework.FrameworkDataProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.util.List;

@ApplicationScoped
public class GenericDataProvider implements FrameworkDataProvider {

    @Inject
    GenericDao genericDao;

    @Override
    public <T> List<T> selectAll(Class<T> clazz) {
        return genericDao.selectAll(clazz);
    }

    @Override
    public <T> T getReference(Class<T> clazz, Long id) {
        return genericDao.getReference(clazz, id);
    }

    @Override
    public void evict(Class<?> clazz) {

    }
}