package app.listener;


import app.model.*;
import app.utility.Bootstrap.Bootstrap;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


import java.sql.*;


@WebListener
public class AppContextListener implements ServletContextListener {

    @Inject
    @Any
    private Instance<Bootstrap> bootstraps;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        for (Bootstrap bootstrap : bootstraps)
            bootstrap.process();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);


    }
}
