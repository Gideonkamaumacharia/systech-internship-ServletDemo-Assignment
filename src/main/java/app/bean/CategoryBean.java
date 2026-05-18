package app.bean;

import app.dao.CategoryDAO;
import app.dao.GenericDao;
import app.model.AuditLog;
import app.model.Category;
import app.model.Showroom;
import app.model.User;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

import java.util.Date;
import java.util.List;


@Stateless
public class CategoryBean {

    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationType.CATEGORY)
    private Validate<Category> validator;

    @Inject
    CategoryDAO dao;

    @Inject
    private Event<AuditLog> auditLogEvent;


    public void createCategory(Category category, User currentUser){

        if(!validator.isValid(category)){
            throw new IllegalArgumentException("Invalid category data");
        }

        dao.insert(category);

        AuditLog log = new AuditLog();
        log.setActionPerformed("CREATE_CATEGORY");
        log.setDetails("Added Category: " + category.getName() + " : " + category.getDescription());
        log.setTimeStamp(new Date());

        if(currentUser != null){
            log.setUser(currentUser);
        }else {
            log.setDetails(log.getDetails() + " (Action by Anonymous/System)");
        }

        auditLogEvent.fire(log);

        System.out.println("EVENT FIRED: " + log.getActionPerformed());
        System.out.println("CategoryBean: createBean() called");

    }

    public List<Category> getCategories(){
        return dao.findAll();
    }
}
