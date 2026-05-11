package app.messaging;

import app.utility.Bootstrap.Bootstrap;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import java.util.Properties;

@ApplicationScoped
public class MailSessionProvider implements Bootstrap {

    private Session session;

    @Inject
    @Named("fromEmail")
    private String fromEmail;

    @Inject
    @Named("appPassword")
    private String appPassword;

    @PostConstruct
    public void init() {

        System.out.println("[MAIL] @PostConstruct initializing SMTP session...");

        buildSession();
    }


    @Override
    public void process() {

        System.out.println("[BOOTSTRAP] Validating MailSessionProvider...");

        if (session == null) {
            buildSession();
        }

        System.out.println("[BOOTSTRAP] Mail session ready.");
    }

    private void buildSession() {

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(
                props,
                new Authenticator() {

                    @Override
                    protected PasswordAuthentication
                    getPasswordAuthentication() {

                        return new PasswordAuthentication(
                                fromEmail,
                                appPassword
                        );
                    }
                }
        );

        System.out.println("[MAIL] SMTP Session initialized.");
    }

    public Session getSession() {
        return session;
    }

    public String getFromEmail() {
        return fromEmail;
    }
}