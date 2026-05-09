package app.messaging;

import app.model.AuditLog;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;

import java.util.Properties;

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

    String fromEmail = System.getProperty("mail.username");
    String appPassword = System.getProperty("mail.password");

    @Override
    public void onMessage(Message message) {

        try {

            ObjectMessage objectMessage = (ObjectMessage) message;

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

        String recipient = "kamaugideonm29@gmail.com";

        String subject =
                "System Alert: "
                        + auditLog.getActionPerformed();

        String body =
                "Action: " + auditLog.getActionPerformed() + "\n" +
                        "Details: " + auditLog.getDetails() + "\n" +
                        "Time: " + auditLog.getTimeStamp();

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(
                props,
                new Authenticator() {
                    protected PasswordAuthentication
                    getPasswordAuthentication() {

                        return new PasswordAuthentication(
                                fromEmail,
                                appPassword
                        );
                    }
                }
        );

        try {

            jakarta.mail.Message emailMessage = new jakarta.mail.internet.MimeMessage(session);


            emailMessage.setFrom(
                    new InternetAddress(fromEmail)
            );

            emailMessage.setRecipients(
                    jakarta.mail.Message.RecipientType.TO,
                    InternetAddress.parse(recipient)
            );

            emailMessage.setSubject(subject);

            emailMessage.setText(body);

            Transport.send(emailMessage);

            System.out.println("Queued email sent!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}