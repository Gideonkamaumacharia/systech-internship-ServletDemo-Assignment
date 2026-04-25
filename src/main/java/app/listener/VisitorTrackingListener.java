package app.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

public class VisitorTrackingListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();

        Integer activeUsers = (Integer) context.getAttribute("activeUsers");
        if (activeUsers == null) activeUsers = 0;

        context.setAttribute("activeUsers",activeUsers + 1);
        System.out.println("[SESSION] New visitor arrived. Total active: "+ (activeUsers + 1));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();

        Integer activeUsers = (Integer) context.getAttribute("activeUsers");
        if ( activeUsers != null && activeUsers > 0){
            context.setAttribute("activeUsers",activeUsers - 1);
        }
        System.out.println("[SESSION] A visitor left. Total active: " + (activeUsers - 1));
    }
}
