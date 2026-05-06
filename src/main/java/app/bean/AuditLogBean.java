package app.bean;

import app.dao.GenericDao;
import app.model.AuditLog;
import jakarta.ejb.Singleton;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.util.Date;
import java.util.List;

@Singleton
public class AuditLogBean {

    @Inject
    GenericDao dao;

    public void save(@Observes AuditLog auditLog){
        if (auditLog.getTimeStamp() == null) {
            auditLog.setTimeStamp(new Date());
        }
        try {
            dao.insert(AuditLog.class,auditLog);
            System.out.println("Audit Log saved: " + auditLog.getActionPerformed());
        } catch (Exception e) {
            System.err.println("Failed to insert Audit Log: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<AuditLog> list(AuditLog filter){
        return dao.selectAll(AuditLog.class);
    }
}
