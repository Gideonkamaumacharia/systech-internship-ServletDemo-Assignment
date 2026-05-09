package app.bean;

import app.dao.GenericDao;
import app.model.AuditLog;
import app.model.Brand;
import app.model.Car;
import app.model.User;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Stateless
public class BrandBean {

    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationType.BRAND)
    private Validate<Brand> validator;

    @Inject
    GenericDao dao;

    @Inject
    Event<AuditLog> auditLogEvent;

    public void create(Brand brand, User currentUser){

        if(!validator.isValid(brand)){
            throw new IllegalArgumentException("Invalid brand data");
        }

        dao.insert(Brand.class,brand);

        AuditLog log = new AuditLog();
        log.setActionPerformed("CREATE_BRAND");
        log.setDetails("Added brand: " + brand.getName() + " from : " + brand.getCountryOfOrigin());
        log.setTimeStamp(new Date());

        if(currentUser != null){
            log.setUserId(currentUser.getId());
        }else {
            log.setDetails(log.getDetails() + " (Action by Anonymous/System)");
        }

        auditLogEvent.fire(log);

        System.out.println("EVENT FIRED: " + log.getActionPerformed());
        System.out.println("BrandBean: createBean() called");
    }

    public List<Brand> getbrands()  {
        List<Brand> data;

        data = dao.selectAll(Brand.class);

        for (Brand brand : data) {
            dao.populateRelationships(brand);
        }

        return data;
    }
}
