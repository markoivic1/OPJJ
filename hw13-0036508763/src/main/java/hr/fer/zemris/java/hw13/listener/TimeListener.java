package hr.fer.zemris.java.hw13.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class implements {@link ServletContextListener} which means it executes every time the server runs.
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebListener
public class TimeListener implements ServletContextListener {

    /**
     * Saves current time in milliseconds in a servlet context attribute "time".
     * @param sce {@link ServletContextEvent}
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("time", System.currentTimeMillis());
    }

    /**
     * Does nothing.
     * @param sce {@link ServletContextEvent}
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}