package app.bean;

import app.dao.GenericDao;
import app.dao.ShowroomDAO;
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

    public void createShowroom(Showroom showroom, User currentUser){

        if(!validator.isValid(showroom)){
            throw new IllegalArgumentException("Invalid car data");
        }

        User manager = dao.findByUserId(showroom.getManagerId());

        showroom.setManager(manager);

        dao.insert(showroom);

        AuditLog auditLog = new AuditLog();
        auditLog.setDetails("CREATE_SHOWROOM");
        auditLog.setActionPerformed("Added showroom: "+ showroom.getLocationName());
        auditLog.setTimeStamp(new Date());

        if(currentUser != null){
            auditLog.setUser(currentUser);
        }else {
            auditLog.setDetails(auditLog.getDetails() + " (Action by Anonymous/System)");
        }

        auditLogEvent.fire(auditLog);
        System.out.println("EVENT FIRED: " + auditLog.getActionPerformed());

        System.out.println("ShowroomBean: createShowroom() called");

    }


    public List<Showroom> getShowrooms() throws SQLException {

        return dao.findAll();
    }
}
