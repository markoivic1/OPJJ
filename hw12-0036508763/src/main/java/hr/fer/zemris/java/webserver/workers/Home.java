package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker which defines home page which hosts links to other pages supported by {@link hr.fer.zemris.java.webserver.SmartHttpServer} server.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Home implements IWebWorker {
    /**
     * Sets default color and delegates job to the home.smscr script
     * @param context Context
     * @throws Exception Thrown when an error has occurred while executing script
     */
    @Override
    public void processRequest(RequestContext context) throws Exception {
        String color = "7F7F7F";
        if (context.getPersistentParameterNames().contains("bgcolor")) {
            color = context.getPersistentParameter("bgcolor");
        }
        context.setTemporaryParameter("background", color);
        context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
    }
}
