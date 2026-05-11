

package app.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

@ApplicationScoped
public class MailConfigProducer {

    @Produces
    @Named("fromEmail")
    public String produceFromEmail() {

        return System.getProperty("mail.username");
    }

    @Produces
    @Named("appPassword")
    public String produceAppPassword() {

        return System.getProperty("mail.password");
    }
}
