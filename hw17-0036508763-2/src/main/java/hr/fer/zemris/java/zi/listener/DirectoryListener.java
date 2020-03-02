package hr.fer.zemris.java.zi.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebListener
public class DirectoryListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Path path = Paths.get(sce.getServletContext().getRealPath("WEB-INF/images"));
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println("Unable to create directory" +
                        sce.getServletContext().getRealPath("WEB-INF/images"));
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
