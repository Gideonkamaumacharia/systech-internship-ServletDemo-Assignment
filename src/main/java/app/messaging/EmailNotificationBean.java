package app.messaging;

import app.model.AuditLog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import jakarta.inject.Inject;

@ApplicationScoped
public class EmailNotificationBean {
    
    @Inject
    private EmailQueueProducer producer;

    public void onAuditLog(@Observes AuditLog auditLog) {

        producer.queueEmail(auditLog);

        System.out.println("Audit event queued for email processing");

    }

}
