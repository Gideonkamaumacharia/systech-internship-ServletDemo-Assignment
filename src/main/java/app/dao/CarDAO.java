package app.dao;

import app.model.Car;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class CarDAO {

    @Inject
    GenericDao genericDao;

    public void insert(Car car) {
        genericDao.insert(Car.class, car);
    }

    public void update(Car car) {
        genericDao.update(Car.class, car);
    }

    public void delete(Long id) {
        genericDao.delete(Car.class, id);
    }


    public List<Car> findAll() {
        List<Car> cars = genericDao.selectAll(Car.class);

        for (Car car : cars) {
            genericDao.populateRelationships(car);
        }

        return cars;
    }

    public List<Car> findByShowroom(Long showroomId) throws SQLException {
        List<Car> cars =
                genericDao.selectWhere(Car.class,
                        "showroomId",
                        showroomId);

        for (Car car : cars) {
            genericDao.populateRelationships(car);
        }

        return cars;
    }

    public Car findById(Long id) {
        Car car = genericDao.selectById(Car.class, id);

        if (car != null) {
            genericDao.populateRelationships(car);
        }

        return car;
    }
}