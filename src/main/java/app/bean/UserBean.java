package app.bean;


import app.dao.UserDAO;
import app.framework.FrameworkDataProvider;
import app.framework.ShowroomSecured;
import app.model.AuditLog;
import app.model.Car;
import app.model.Showroom;
import app.model.User;
import app.model.enums.UserRole;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class UserBean {

    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationType.USER)
    private Validate<User> validator;

    @Inject
    UserDAO dao;

    @Inject
    private Event<AuditLog> auditLogEvent;

    @Inject
    FrameworkDataProvider dataProvider;


    public void create(User user){

        if(!validator.isValid(user)){
            throw new IllegalArgumentException("Invalid car data");
        }

        String rawPassword = user.getPasswordHash();

        String hashedPassword = BCrypt.hashpw(rawPassword,BCrypt.gensalt());

        user.setPasswordHash(hashedPassword);

        dao.insert(user);

        AuditLog log = new AuditLog();
        log.setActionPerformed("REGISTER_USER");
        log.setDetails("Register User: " + user.getUsername() + " as : " + user.getRole());
        log.setTimeStamp(LocalDateTime.now()
        );

        if(user != null){
            log.setUser(user);
        }else {
            log.setDetails(log.getDetails() + " (Action by Anonymous/System)");
        }

        auditLogEvent.fire(log);
        System.out.println("EVENT FIRED: " + log.getActionPerformed());


        System.out.println("UserBean : createUser() called");
    }

//    public List<User> getUsers(String showroomId){
//        List<User> data;
//
//
//            if( showroomId != null && !showroomId.isEmpty()){
//                data = dao.findByShowroom(Long.parseLong(showroomId));
//            }else{
//                data = dao.findAll();
//            }
//
//            return data;
//
//    }

    public List<User> getUsers(User caller){
        if (caller.getRole() == UserRole.ADMIN) {
            return dao.findAll();
        }
        return dao.findByShowroom(caller.getShowroom().getId());
    }

    public User findByUsername(String username) {
        return dao.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No user found with username: " + username
                ));
    }


    public void remove(Long id){
        User user = dao.findById(id);
        if (user == null) return;

        AuditLog log = new AuditLog();
        log.setActionPerformed("DELETE_USER");
        log.setDetails("Delete User: " + user.getUsername());
        log.setTimeStamp(LocalDateTime.now()
        );

//        if(user != null){
//            log.setUser(user);
//        }else {
//            log.setDetails(log.getDetails() + " (Action by Anonymous/System)");
//        }
//@TransactionalAttribute(REQUIRED)
        auditLogEvent.fire(log);
        System.out.println("EVENT FIRED: " + log.getActionPerformed());

    }

    public User findById(Long id){

        return dao.findById(id);
    }

    public void update(User updatedUser){
        User existingUser = dao.findById(updatedUser.getId());

        // Keep password — never overwrite
        updatedUser.setPasswordHash(existingUser.getPasswordHash());

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setRole(updatedUser.getRole());

        if(updatedUser.getShowroom() != null){
            existingUser.setShowroom(updatedUser.getShowroom());

            if(updatedUser.getRole() == UserRole.MANAGER){
                dao.assignShowroomManager(
                        updatedUser.getShowroom().getId(),
                        existingUser.getId()
                );
            }
        }
        if (updatedUser.getRole() != UserRole.MANAGER
                && existingUser.getRole() == UserRole.MANAGER) {
            dao.clearManagerReference(existingUser.getId());
        }

        dao.update(existingUser);
        dataProvider.evict(User.class);
        dataProvider.evict(Showroom.class);
    }




    private void checkWriteAccess(Class<?> entityClass, User caller) {
        ShowroomSecured secured = entityClass.getAnnotation(ShowroomSecured.class);
        if (secured == null) return;
        if (secured.readOnly()) {
            throw new SecurityException(entityClass.getSimpleName() + " is read-only.");
        }
        boolean permitted = Arrays.stream(secured.writeRoles())
                .anyMatch(r -> r == caller.getRole());
        if (!permitted) {
            throw new SecurityException(
                    caller.getRole() + " cannot write " + entityClass.getSimpleName()
            );
        }
    }

    private void enforceShowroomOwnership(Car car, User caller) {
        if (caller.getRole() == UserRole.ADMIN) return;
        Showroom callerShowroom = caller.getShowroom();
        if (callerShowroom == null) {
            throw new SecurityException("Manager is not assigned to any showroom.");
        }
        if (car.getShowroom() == null) {
            car.setShowroom(callerShowroom);
            return;
        }
        if (!callerShowroom.getId().equals(car.getShowroom().getId())) {
            throw new SecurityException("Access denied: car belongs to another showroom.");
        }
    }
}
