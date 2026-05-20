package app.dao;

import app.model.Brand;
import app.model.Car;
import app.model.Category;
import app.model.Showroom;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
public class CarDAO {

    @Inject
    GenericDao genericDao;

    public Car findById(Long id) {
        return genericDao.selectById(Car.class, id); // relationships auto-loaded by JPA
    }

    public List<Car> findAll() {
        return genericDao.selectAll(Car.class);
    }

    public List<Car> findByShowroom(Long showroomId) {
        return genericDao.selectWhere(Car.class, "showroom_Id", showroomId);
    }

    public void insert(Car car) {
        Long showroomId = car.getShowroomId();
        Showroom showroom =
                genericDao.selectById(Showroom.class,showroomId);

        car.setShowroom(showroom);

        Long brandId = car.getBrandId();
        app.model.Brand brand =
                genericDao.selectById(Brand.class,brandId);

        car.setBrand(brand);

        Long categoryId = car.getCategoryId();
        Category category =
                genericDao.selectById(Category.class,categoryId);

        car.setCategory(category);


        genericDao.insert(car); }

    public void update(Car car) {

        genericDao.update(car);
    }

    public void delete(Long id)  {
        genericDao.delete(Car.class, id);
    }

    public List<Car> findByCriteria(Long showroomId, Long brandId, Long categoryId) {
        StringBuilder jpql = new StringBuilder("SELECT c FROM Car c WHERE 1=1");

        if(showroomId != null){
            jpql.append("AND c.showroom.id = :showroomId");
        }
        if(categoryId != null){
            jpql.append("AND c.category.id = :categoryId");
        }
        if(brandId != null){
            jpql.append("AND c.brand.id = :brandId");
        }

        TypedQuery<Car> query = genericDao.getEm().createQuery(jpql.toString(),Car.class);

        if(showroomId != null) query.setParameter("showroomId",showroomId);
        if(categoryId != null) query.setParameter("categoryId",categoryId);
        if(brandId != null) query.setParameter("brandId",brandId);


        return query.getResultList();
        }
    }