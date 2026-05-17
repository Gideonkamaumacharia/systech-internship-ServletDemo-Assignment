package app.bean;

import app.dao.GenericDao;
import app.model.AuditLog;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

import java.util.Date;
import java.util.List;

@Singleton
public class AuditLogBean {

    @Inject
    GenericDao dao;

    @Inject
    JMSContext context;

    @Resource(lookup = "java:/jms/queue/MyShowroomQueue")
    private Queue auditQueue;

    public void save(@Observes AuditLog auditLog){
        if (auditLog.getTimeStamp() == null) {
            auditLog.setTimeStamp(new Date());
        }
        try {
            dao.insert(auditLog);
            System.out.println("Audit Log saved: " + auditLog.getActionPerformed());
            context.createProducer().send(auditQueue,auditLog.getActionPerformed());
        } catch (Exception e) {
            System.err.println("Failed to insert Audit Log: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<AuditLog> list(){
        return dao.selectAll(AuditLog.class);
    }
}
