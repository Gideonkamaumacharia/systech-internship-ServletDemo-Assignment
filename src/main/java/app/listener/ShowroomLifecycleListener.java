package app.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ShowroomLifecycleListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Showroom is open for business!");
        ServletContext context = sce.getServletContext();
        context.setAttribute("totalCars",0);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Showroom closed. Final car count "+ sce.getServletContext().getAttribute("totalCars"));
    }
}
