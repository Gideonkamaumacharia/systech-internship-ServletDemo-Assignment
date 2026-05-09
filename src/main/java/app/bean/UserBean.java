package app.bean;

import app.dao.GenericDao;
import app.model.AuditLog;
import app.model.Car;
import app.model.Showroom;
import app.model.User;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.eclipse.tags.shaded.org.apache.xpath.objects.XNull;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Stateless
public class UserBean {

    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationType.USER)
    private Validate<User> validator;

    @Inject
    GenericDao dao;

    @Inject
    private Event<AuditLog> auditLogEvent;


    public void create(User user){

        if(!validator.isValid(user)){
            throw new IllegalArgumentException("Invalid car data");
        }

        dao.insert(User.class,user);

        AuditLog log = new AuditLog();
        log.setActionPerformed("REGISTER_USER");
        log.setDetails("Register User: " + user.getUsername() + " as : " + user.getRole());
        log.setTimeStamp(new Date());

        if(user != null){
            log.setUserId(user.getId());
        }else {
            log.setDetails(log.getDetails() + " (Action by Anonymous/System)");
        }

        auditLogEvent.fire(log);
        System.out.println("EVENT FIRED: " + log.getActionPerformed());


        System.out.println("UserBean : createUser() called");
    }

    public List<User> getUsers(String showroomId){
        List<User> data;

        try{
            if( showroomId != null && !showroomId.isEmpty()){
                data = dao.selectWhere(User.class,"showroomId",Long.parseLong(showroomId));
            }else{
                data = dao.selectAll(User.class);
            }

            for (User user : data) {
                dao.populateRelationships(user);
            }

            return data;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByUsername(String username){
        try{
            List<User> users = dao.selectWhere(User.class,"username",username);
                if (!users.isEmpty()) {
                    return users.get(0);
            }
        }catch(Exception e){
            e.printStackTrace();

        }return null;
    }
}
