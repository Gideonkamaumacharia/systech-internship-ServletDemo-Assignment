package app.bean;

import app.dao.GenericDao;
import app.dao.UserDAO;
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
import java.util.Optional;

@Stateless
public class UserBean {

    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationType.USER)
    private Validate<User> validator;

    @Inject
    UserDAO dao;

    @Inject
    private Event<AuditLog> auditLogEvent;


    public void create(User user){

        if(!validator.isValid(user)){
            throw new IllegalArgumentException("Invalid car data");
        }

        dao.insert(user);

        AuditLog log = new AuditLog();
        log.setActionPerformed("REGISTER_USER");
        log.setDetails("Register User: " + user.getUsername() + " as : " + user.getRole());
        log.setTimeStamp(new Date());

        if(user != null){
            log.setUser(user);
        }else {
            log.setDetails(log.getDetails() + " (Action by Anonymous/System)");
        }

        auditLogEvent.fire(log);
        System.out.println("EVENT FIRED: " + log.getActionPerformed());


        System.out.println("UserBean : createUser() called");
    }

    public List<User> getUsers(String showroomId){
        List<User> data;


            if( showroomId != null && !showroomId.isEmpty()){
                data = dao.findByShowroom(Long.parseLong(showroomId));
            }else{
                data = dao.findAll();
            }

            return data;

    }

    public User findByUsername(String username){
        try{
            Optional<User> users = dao. findByUsername(username);
                if (!users.isEmpty()) {
                    return users.get();
            }
        }catch(Exception e){
            e.printStackTrace();

        }return null;
    }

    public void remove(Long id){
        User user = dao.findById(id);
        dao.delete(id);

        AuditLog log = new AuditLog();
        log.setActionPerformed("DELETE_USER");
        log.setDetails("Delete User: " + user.getUsername());
        log.setTimeStamp(new Date());

        if(user != null){
            log.setUser(user);
        }else {
            log.setDetails(log.getDetails() + " (Action by Anonymous/System)");
        }

        auditLogEvent.fire(log);
        System.out.println("EVENT FIRED: " + log.getActionPerformed());

    }

    public User findById(Long id){
       return dao.findById(id);
    }
}
