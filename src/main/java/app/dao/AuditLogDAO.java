package app.dao;

import app.model.AuditLog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class AuditLogDAO {

    @Inject
    private GenericDao genericDao;

    // Direct EM needed for relationship-traversal queries (u.showroom.id, etc.)
    @PersistenceContext
    private EntityManager em;

    public void insert(AuditLog log) {
        genericDao.insert(log);
    }

    public void update(AuditLog log) {
        genericDao.update(log);
    }

    public void delete(Long id) {
        genericDao.delete(AuditLog.class, id);
    }

    public AuditLog findById(Long id) {
        return genericDao.selectById(AuditLog.class, id);
    }

    public List<AuditLog> findAll() {
        return genericDao.selectAll(AuditLog.class);
    }

    /**
     * All audit logs produced by a specific user.
     * Relies on the @ManyToOne user relationship on AuditLog.
     */
    public List<AuditLog> findByUser(Long userId) {
        return em.createQuery(
                        "SELECT a FROM AuditLog a WHERE a.user.id = :userId", AuditLog.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    /**
     * All audit logs for every user inside a showroom.
     * Traverses AuditLog → user → showroom (two hops, one JOIN in SQL).
     */
    public List<AuditLog> findByShowroom(Long showroomId) {
        return em.createQuery(
                        "SELECT a FROM AuditLog a WHERE a.user.showroom.id = :showroomId", AuditLog.class)
                .setParameter("showroomId", showroomId)
                .getResultList();
    }

    /**
     * Audit logs filtered by action type (e.g. "INSERT", "UPDATE", "DELETE").
     * Assumes AuditLog has a plain String field called `action`.
     */
    public List<AuditLog> findByAction(String action) {
        return genericDao.selectWhere(AuditLog.class, "actionPerformed", action);
    }
}
