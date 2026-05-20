package app.bean;

import app.dao.CarDAO;
import app.model.*;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;


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
        log.setDetails("Added model: " + car.getCarModel() + " to : " + car.getShowroom().getLocationName());
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

    public void update(Car updatedCar){
        Car existingCar = dao.findById(updatedCar.getId());

        existingCar.setCarModel(updatedCar.getCarModel());
        existingCar.setPrice(updatedCar.getPrice());

        dao.update(existingCar);
    }

    public void remove(Long id){

        Car car = dao.findById(id);
        dao.delete(id);

        AuditLog log = new AuditLog();
        log.setActionPerformed("DELETE_CAR");
        log.setDetails("Deleted a car: " + car.getCarModel() + " from : " + car.getShowroom().getLocationName());
        log.setTimeStamp(new Date());

        auditLogEvent.fire(log);
        System.out.println("EVENT FIRED: " + log.getActionPerformed());
    }

    public Car findById(Long id){

        return dao.findById(id);
    }

    public List<Car> findAll() {
       return dao.findAll();

    }


    public List<Car> getCars(String showroomId,String brandId, String categoryId)  {
        List<Car> data;

        Long parsedShowroomId  = (showroomId  != null && !showroomId.isEmpty())  ? Long.parseLong(showroomId)  : null;
        Long parsedBrandId     = (brandId     != null && !brandId.isEmpty())     ? Long.parseLong(brandId)     : null;
        Long parsedCategoryId  = (categoryId  != null && !categoryId.isEmpty())  ? Long.parseLong(categoryId)  : null;

        return dao.findByCriteria(parsedShowroomId, parsedBrandId, parsedCategoryId);

        //        if (showroomId != null && !showroomId.isEmpty()) {
//            System.out.println("EJB filtering: showroomId = " + showroomId);
//            data = dao.findByShowroom( Long.parseLong(showroomId));
//        } else {
//            data = dao.findAll();
//        }
        //  return data;
    }


}












//1. Validate
//2. Insert Car
//3. Fire Audit Event
//That IS a transactional workflow.
//Suppose one fails -> EJB transaction behavior: will ROLLBACK EVERYTHING

//unchecked exception will automatically trigger a rollback