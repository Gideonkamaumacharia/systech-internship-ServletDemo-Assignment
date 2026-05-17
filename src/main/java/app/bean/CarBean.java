package app.bean;

import app.dao.CarDAO;
import app.dao.GenericDao;
import app.model.*;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Stateless
public class CarBean {

    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationType.CAR)
    private Validate<Car> validator;

    @Inject
    CarDAO dao;

    @Inject
    private Event<AuditLog> auditLogEvent;

    public void create(Car car, User currentUser){

        if(!validator.isValid(car)){
            throw new IllegalArgumentException("Invalid car data");
        }

        dao.insert(car);

        AuditLog log = new AuditLog();
        log.setActionPerformed("CREATE_CAR");
        log.setDetails("Added model: " + car.getCarModel() + " to showroom ID : " + car.getShowroom().getId());
        log.setTimeStamp(new Date());

        if(currentUser != null){
            log.setUser(currentUser);
        }else {
            log.setDetails(log.getDetails() + " (Action by Anonymous/System)");
        }

        auditLogEvent.fire(log);
        System.out.println("EVENT FIRED: " + log.getActionPerformed());

        System.out.println("CarBean: createCar() called");
    }

    public void update(Car car){
        dao.update(car);
    }

    public void remove(Long id){
        dao.delete(id);
    }

    public Car findById(Long id){
        return dao.findById(id);
    }

    public List<Car> getCars(String showroomId)  {
        List<Car> data;

            if (showroomId != null && !showroomId.isEmpty()) {
                System.out.println("EJB filtering: showroomId = " + showroomId);
                data = dao.findByShowroom( Long.parseLong(showroomId));
            } else {
                data = dao.findAll();
            }


            return data;

    }
}




//    private void populateRelationships(Car car) {
//        if (car.getBrandId() != null) {
//            Brand brand =
//                    dao.selectById(Brand.class, car.getBrandId());
//            car.setBrand(brand);
//        }
//
//        if (car.getCategoryId() != null) {
//            Category category =
//                    dao.selectById(Category.class,
//                            car.getCategoryId());
//            car.setCategory(category);
//        }
//
//        if (car.getShowroomId() != null) {
//            Showroom showroom =
//                    dao.selectById(Showroom.class,
//                            car.getShowroomId());
//            car.setShowroom(showroom);
//        }
//    }








//1. Validate
//2. Insert Car
//3. Fire Audit Event
//That IS a transactional workflow.
//Suppose one fails -> EJB transaction behavior: will ROLLBACK EVERYTHING

//unchecked exception will automatically trigger a rollback