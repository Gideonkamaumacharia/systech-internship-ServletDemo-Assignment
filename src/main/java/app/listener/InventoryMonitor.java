package app.listener;

import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.annotation.WebListener;

@WebListener(value = "InventoryMonitor")
public class InventoryMonitor implements ServletContextAttributeListener {
    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        if ("totalCars".equals(event.getName())){
            System.out.println("[MONITOR] Initialized: "+ event.getName() + " is now set to "+ event.getValue());
        }
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        if ("totalCars".equals(event.getName())){
            System.out.println("[MONITOR] Update: " + event.getName() + " changed from " + event.getValue());
        }
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        System.out.println("[MONITOR] Data Deleted: " + event.getName());    }
}
