package app.messaging;


import app.model.AuditLog;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;

import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(
                        propertyName = "destinationLookup",
                        propertyValue = "java:/jms/queue/EmailQueue"
                ),
                @ActivationConfigProperty(
                        propertyName = "destinationType",
                        propertyValue = "jakarta.jms.Queue"
                )
        }
)
public class EmailWorker implements MessageListener {

    @Inject
    private MailSessionProvider mailProvider;

    @Override
    public void onMessage(Message message) {

        try {

            ObjectMessage objectMessage =
                    (ObjectMessage) message;

            AuditLog auditLog =
                    (AuditLog) objectMessage.getObject();

            System.out.println(
                    "Processing queued email for: "
                            + auditLog.getActionPerformed()
            );

            sendEmail(auditLog);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(AuditLog auditLog) {

        try {

            MimeMessage emailMessage =
                    new MimeMessage(
                            mailProvider.getSession()
                    );

            emailMessage.setFrom(
                    new InternetAddress(
                            mailProvider.getFromEmail()
                    )
            );

            emailMessage.setRecipients(
                    jakarta.mail.Message.RecipientType.TO,
                    InternetAddress.parse(
                            "kamaugideonm29@gmail.com"
                    )
            );

            emailMessage.setSubject(
                    "System Alert: "
                            + auditLog.getActionPerformed()
            );

            emailMessage.setText(
                    "Action: "
                            + auditLog.getActionPerformed()
                            + "\nDetails: "
                            + auditLog.getDetails()
            );

            Transport.send(emailMessage);

            System.out.println("Queued email sent!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}