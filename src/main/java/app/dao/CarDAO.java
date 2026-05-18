package app.dao;

import app.model.Brand;
import app.model.Car;
import app.model.Category;
import app.model.Showroom;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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

    public void delete(Long id)  { genericDao.delete(Car.class, id); }
}