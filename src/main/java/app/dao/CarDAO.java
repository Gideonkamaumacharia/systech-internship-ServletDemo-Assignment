package app.dao;

import app.model.Car;
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

    public void insert(Car car) { genericDao.insert(car); }
    public void update(Car car) { genericDao.update(car); }
    public void delete(Long id)  { genericDao.delete(Car.class, id); }
}