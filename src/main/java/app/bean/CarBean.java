package app.bean;

import app.dao.BrandDAO;
import app.dao.CarDAO;
import app.dao.CategoryDAO;
import app.dao.ShowroomDAO;
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
import java.util.List;

@Stateless
public class CarBean {

    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationType.CAR)
    private Validate<Car> validator;


    @Inject
    private CarDAO dao;

    @Inject
    private BrandDAO brandDAO;

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private ShowroomDAO showroomDAO;

    @Inject
    private Event<AuditLog> auditLogEvent;

    @Inject
    private UserBean userBean;

    public void create(Car car, User caller) {
        if (caller.getRole() != UserRole.ADMIN && caller.getRole() != UserRole.MANAGER) {
            throw new SecurityException("Only ADMIN or MANAGER can create cars.");
        }

        checkWriteAccess(Car.class, caller);      // ← pass caller
        enforceShowroomOwnership(car, caller);    // ← pass caller

        if (!validator.isValid(car)) {
            throw new IllegalArgumentException("Invalid car data");
        }

        Showroom showroom = showroomDAO.findByShowroomId(car.getShowroom().getId());
        dao.insert(car);

        AuditLog log = new AuditLog();
        log.setActionPerformed("CREATE_CAR");
        log.setDetails("Added model: " + car.getCarModel() + " to: " + showroom.getLocationName());
        log.setTimeStamp(LocalDateTime.now());
        log.setUser(caller);                      // ← set caller on log
        auditLogEvent.fire(log);
    }

    public void update(Car updatedCar, User caller) {
        checkWriteAccess(Car.class, caller);
        enforceShowroomOwnership(updatedCar, caller);

        Car existing = dao.findById(updatedCar.getId());
        existing.setCarModel(updatedCar.getCarModel());
        existing.setEngineType(updatedCar.getEngineType());
        existing.setYear(updatedCar.getYear());
        existing.setPrice(updatedCar.getPrice());
        dao.update(existing);

        AuditLog log = new AuditLog();
        log.setActionPerformed("UPDATE_CAR");
        log.setDetails("Updated model: " + existing.getCarModel());
        log.setTimeStamp(LocalDateTime.now());
        log.setUser(caller);
        auditLogEvent.fire(log);
    }

    public void remove(Long id, User caller) {
        if (caller.getRole() != UserRole.ADMIN && caller.getRole() != UserRole.MANAGER) {
            throw new SecurityException("Only ADMIN or MANAGER can delete cars.");
        }

        Car car = dao.findById(id);
        enforceShowroomOwnership(car, caller);
        dao.delete(id);

        AuditLog log = new AuditLog();
        log.setActionPerformed("DELETE_CAR");
        log.setDetails("Deleted: " + car.getCarModel() + " from: " + car.getShowroom().getLocationName());
        log.setTimeStamp(LocalDateTime.now());
        log.setUser(caller);
        auditLogEvent.fire(log);
    }

    public List<Car> findAll(User caller) {
        if (caller.getRole() == UserRole.ADMIN) {
            return dao.findAll();
        }
        return dao.findByShowroom(caller.getShowroom().getId());
    }

    public Car findById(Long id) {
        return dao.findById(id);
    }

    public List<Car> getCars(String showroomId, String brandId, String categoryId,User caller) {

        User freshCaller = userBean.findById(caller.getId());

        Long parsedShowroomId = (showroomId != null && !showroomId.isEmpty()) ? Long.parseLong(showroomId) : null;
        Long parsedBrandId    = (brandId    != null && !brandId.isEmpty())    ? Long.parseLong(brandId)    : null;
        Long parsedCategoryId = (categoryId != null && !categoryId.isEmpty()) ? Long.parseLong(categoryId) : null;

        if (freshCaller.getRole() == UserRole.ADMIN) {

            return dao.findByCriteria(
                    parsedShowroomId,
                    parsedBrandId,
                    parsedCategoryId
            );
        }

        Long callerShowroomId =
                caller.getShowroom().getId();

        return dao.findByCriteria(callerShowroomId, parsedBrandId, parsedCategoryId);
    }



/*  carBean.getCars("3",null,"3")
    converts strings to Longs (null stays null)
dao.findByCriteria(3L, null, 5L)
        ↓  builds:
   SELECT c FROM Car c WHERE 1=1
    AND c.showroom.id = :showroomId
  AND c.category.id = :categoryId
   ORDER BY c.carModel ASC
            ↓  returns List<Car>  */


























    // ── Helpers — caller passed in, no SecurityContext ────────

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