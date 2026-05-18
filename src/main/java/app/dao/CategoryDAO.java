package app.dao;

import app.model.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CategoryDAO {

    @Inject
    private GenericDao genericDao;

    public void insert(Category category) {
        genericDao.insert(category);
    }

    public void update(Category category) {
        genericDao.update(category);
    }

    public void delete(Long id) {
        genericDao.delete(Category.class, id);
    }

    public Category findById(Long id) {
        return genericDao.selectById(Category.class, id);
    }

    public List<Category> findAll() {
        return genericDao.selectAll(Category.class);
    }
}
