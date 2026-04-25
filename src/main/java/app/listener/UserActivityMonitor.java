package app.listener;


import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

public class UserActivityMonitor implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {

        if ("userAuthenticated".equals(event.getName())) {
            System.out.println("[AUDIT] Security change: A user has LOGGED IN. Session ID: "
                    + event.getSession().getId());
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        System.out.println("[AUDIT] Session Data Updated: " + event.getName()
                + " was " + event.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        // Fires when the session is invalidated or attribute removed
        if ("userAuthenticated".equals(event.getName())) {
            System.out.println("[AUDIT] Security change: A user has LOGGED OUT.");
        }
    }
}