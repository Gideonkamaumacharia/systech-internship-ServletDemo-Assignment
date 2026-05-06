package app.bean;

import app.model.AuditLog;
import jakarta.ejb.Singleton;
import jakarta.enterprise.event.Observes;

@Singleton
public class EmailNotificationBean {

    public void onAuditLog(@Observes AuditLog auditLog) {

        String recipient = "admin@showroom.com";
        String subject = "System Alert: " + auditLog.getActionPerformed();

        System.out.println("Email queued for " + recipient);
    }
}
