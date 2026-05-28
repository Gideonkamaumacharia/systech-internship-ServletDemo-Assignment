package app.bean;

import app.dao.CachedDataProvider;
import app.dao.CategoryDAO;
import app.framework.FrameworkDataProvider;
import app.model.AuditLog;
import app.model.Brand;
import app.model.Category;
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
public class CategoryBean {

    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationType.CATEGORY)
    private Validate<Category> validator;

    @Inject
    CategoryDAO dao;

    @Inject
    private Event<AuditLog> auditLogEvent;

    @Inject
    FrameworkDataProvider dataProvider;


    public void createCategory(Category category, User currentUser){

        if(!validator.isValid(category)){
            throw new IllegalArgumentException("Invalid category data");
        }

        dao.insert(category);
        dataProvider.evict(Category.class);

        AuditLog log = new AuditLog();
        log.setActionPerformed("CREATE_CATEGORY");
        log.setDetails("Added Category: " + category.getName() + " : " + category.getDescription());
        log.setTimeStamp(LocalDateTime.now()
        );

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

    public void remove(Long id){

        Category category = dao.findById(id);
        dao.delete(id);
        dataProvider.evict(Category.class);

        AuditLog log = new AuditLog();
        log.setActionPerformed("DELETE_CAR");
        log.setDetails("Deleted " + category + " category.");
        log.setTimeStamp(LocalDateTime.now()
        );

        auditLogEvent.fire(log);
        System.out.println("EVENT FIRED: " + log.getActionPerformed());
    }

    public Category findById(Long id){
        return dao.findById(id);
    }

    public void update(Category updatedCategory){
        Category existingCategory = dao.findById(updatedCategory.getId());

        existingCategory.setName(updatedCategory.getName());
        existingCategory.setDescription(updatedCategory.getDescription());

        dao.update(updatedCategory);
        dataProvider.evict(Category.class);
    }
}
