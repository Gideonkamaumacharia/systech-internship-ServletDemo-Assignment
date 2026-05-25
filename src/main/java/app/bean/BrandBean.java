package app.bean;

import app.dao.BrandDAO;
import app.model.AuditLog;
import app.model.Brand;
import app.model.Car;
import app.model.User;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Stateless
public class BrandBean {

    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationType.BRAND)
    private Validate<Brand> validator;

    @Inject
    BrandDAO dao;

    @Inject
    Event<AuditLog> auditLogEvent;

    public void create(Brand brand, User currentUser){

        if(!validator.isValid(brand)){
            throw new IllegalArgumentException("Invalid brand data");
        }

        dao.insert(brand);

        AuditLog log = new AuditLog();
        log.setActionPerformed("CREATE_BRAND");
        log.setDetails("Added brand: " + brand.getName() + " from : " + brand.getCountryOfOrigin());
        log.setTimeStamp(LocalDateTime.now()
        );

        if(currentUser != null){
            log.setUser(currentUser);
        }else {
            log.setDetails(log.getDetails() + " (Action by Anonymous/System)");
        }

        auditLogEvent.fire(log);

        System.out.println("EVENT FIRED: " + log.getActionPerformed());
        System.out.println("BrandBean: createBean() called");
    }

    public List<Brand> getbrands()  {
        List<Brand> data;

        data = dao. findAll();

        return data;
    }

    public void remove(Long id){

        Brand brand = dao.findById(id);
        dao.delete(id);

        AuditLog log = new AuditLog();
        log.setActionPerformed("DELETE_BRAND");
        log.setDetails("Deleted " + brand + " car brand.");
        log.setTimeStamp(LocalDateTime.now()
        );

        auditLogEvent.fire(log);
        System.out.println("EVENT FIRED: " + log.getActionPerformed());
    }

    public Brand findById(Long id){
        return dao.findById(id);
    }

    public void update(Brand updatedBrand){
        Brand existingBrand = dao.findById(updatedBrand.getId());

        existingBrand.setName(updatedBrand.getName());
        existingBrand.setCountryOfOrigin(updatedBrand.getCountryOfOrigin());


        dao.update(updatedBrand);
    }
}
