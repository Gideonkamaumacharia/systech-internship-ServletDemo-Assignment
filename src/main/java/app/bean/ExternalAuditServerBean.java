package app.bean;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.TextMessage;

@MessageDriven(
        activationConfig = {
            @ActivationConfigProperty(propertyName = "destinationLookup",propertyValue = "java:/jms/queue/MyShowroomQueue"),
            @ActivationConfigProperty( propertyName = "destinationType",propertyValue = "jakarta.jms.Queue")
        }
)
public class ExternalAuditServerBean implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("************* JMS message: " + textMessage.getText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
