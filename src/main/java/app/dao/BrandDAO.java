package app.dao;

import app.model.Brand;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class BrandDAO {

    @Inject
    private GenericDao genericDao;

    public void insert(Brand brand) {
        genericDao.insert(brand);
    }

    public void update(Brand brand) {
        genericDao.update(brand);
    }

    public void delete(Long id) {
        genericDao.delete(Brand.class, id);
    }

    public Brand findById(Long id) {
        return genericDao.selectById(Brand.class, id);
    }

    public List<Brand> findAll() {
        return genericDao.selectAll(Brand.class);
    }
}