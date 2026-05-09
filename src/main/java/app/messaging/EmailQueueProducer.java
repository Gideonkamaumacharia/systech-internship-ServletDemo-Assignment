package app.messaging;

import app.model.AuditLog;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

@ApplicationScoped
public class EmailQueueProducer {

    @Inject
    private JMSContext context;

    @Resource(lookup = "java:/jms/queue/EmailQueue")
    private Queue queue;

    public void queueEmail(AuditLog auditLog){
        context.createProducer().send(queue,auditLog);

        System.out.println("Email job added to queue");
    }
}
