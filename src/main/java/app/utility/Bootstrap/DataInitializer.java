package app.utility.Bootstrap;

import app.dao.GenericDao;
import app.model.User;
import app.model.enums.UserRole;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mindrot.jbcrypt.BCrypt;

@Singleton
@Startup
public class DataInitializer {

    @Inject
    GenericDao dao;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {

        Long count = em.createQuery(
                "SELECT COUNT(u) FROM User u",
                Long.class
        ).getSingleResult();

        if (count == 0) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setPasswordHash( BCrypt.hashpw("admin123", BCrypt.gensalt()));
            admin.setRole(UserRole.ADMIN);;

            dao.insert(admin);

            System.out.println("Default admin created");
        }
    }
}
