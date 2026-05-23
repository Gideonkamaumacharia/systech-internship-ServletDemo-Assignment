package app.dao;

import app.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserDAO {

    @Inject
    private GenericDao genericDao;
//
//    // Direct EntityManager access for queries that need JPQL
//    // beyond what GenericDao.selectWhere() covers (e.g. joining Showroom).
//    @PersistenceContext
//    private EntityManager em;

    public void insert(User user) {
        genericDao.insert(user);
    }

    public void update(User user) {
        genericDao.update(user);
    }

    public void delete(Long id) {
        genericDao.delete(User.class, id);
    }

    public User findById(Long id) {
        return genericDao.selectById(User.class, id);
    }

    public List<User> findAll() {
        return genericDao.selectAll(User.class);
    }

    /**
     * Finds all users belonging to a given showroom.
     * Navigates the @ManyToOne showroom relationship on User.
     */
    public List<User> findByShowroom(Long showroomId) {
        return genericDao.getEm().createQuery(
                        "SELECT u FROM User u WHERE u.showroom.id = :showroomId", User.class)
                .setParameter("showroomId", showroomId)
                .getResultList();
    }

    public Optional<User> findByUsername(String username) {
        List<User> results = genericDao.selectWhere(User.class, "username", username);
        return results.stream().findFirst();
    }

    public List<User> findByRole(String role) {
        return genericDao.selectWhere(User.class, "role", role);
    }
}