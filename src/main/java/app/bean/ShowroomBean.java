package app.bean;

import app.dao.CachedDataProvider;
import app.dao.ShowroomDAO;
import app.framework.FrameworkDataProvider;
import app.model.AuditLog;
import app.model.Car;
import app.model.Showroom;
import app.model.User;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Stateless
public class ShowroomBean {

    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationType.SHOWROOM)
    private Validate<Showroom> validator;

    @Inject
    ShowroomDAO dao;

    @Inject
    private Event<AuditLog> auditLogEvent;

    @Inject
    FrameworkDataProvider dataProvider;

    public void createShowroom(Showroom showroom, User currentUser){

        if(!validator.isValid(showroom)){
            throw new IllegalArgumentException("Invalid car data");
        }

        User manager = dao.findByUserId(showroom.getManagerId());

        showroom.setManager(manager);

        dao.insert(showroom);
        dataProvider.evict(Showroom.class);

        AuditLog auditLog = new AuditLog();
        auditLog.setDetails("CREATE_SHOWROOM");
        auditLog.setActionPerformed("Added showroom: "+ showroom.getLocationName());
        auditLog.setTimeStamp(LocalDateTime.now()
        );

        if(currentUser != null){
            auditLog.setUser(currentUser);
        }else {
            auditLog.setDetails(auditLog.getDetails() + " (Action by Anonymous/System)");
        }

        auditLogEvent.fire(auditLog);
        System.out.println("EVENT FIRED: " + auditLog.getActionPerformed());

        System.out.println("ShowroomBean: createShowroom() called");

    }


    public List<Showroom> getShowrooms() {

        return dao.findAll();
    }

    public void remove(Long id,User currentUser){

        Showroom showroom = dao.findByShowroomId(id);
        dao.delete(id);
        dataProvider.evict(Showroom.class);

        AuditLog log = new AuditLog();
        log.setActionPerformed("DELETE_SHOWROOM");
        log.setDetails("Deleted : "  + showroom.getLocationName());
        log.setTimeStamp(LocalDateTime.now()
        );

        auditLogEvent.fire(log);
        System.out.println("EVENT FIRED: " + log.getActionPerformed());
    }

    public Showroom findById(Long id){
        return dao.findByShowroomId(id);
    }

    public void update(Showroom updatedShowroom){
        Showroom existingShowroom = dao.findByShowroomId(updatedShowroom.getId());

        existingShowroom.setLocationName(updatedShowroom.getLocationName());
        existingShowroom.setCapacity(updatedShowroom.getCapacity());

        dao.update(existingShowroom);
        dataProvider.evict(Showroom.class);
    }
}
