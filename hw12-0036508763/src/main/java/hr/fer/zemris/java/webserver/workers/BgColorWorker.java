package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker which sets color by predefined behaviour.
 * Default color is #F7F7F7
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class BgColorWorker implements IWebWorker {
    /**
     * Sets color to a given parameter if the parameter is valid.
     * Otherwise it doesn't change the color.
     * Notifies user whether the color was changed or not.
     * @param context
     * @throws Exception
     */
    @Override
    public void processRequest(RequestContext context) throws Exception {
        String color = context.getParameter("bgcolor");
        context.setMimeType("text/html");
        try {
            if (color != null && color.length() == 6) {
                Integer.parseInt(color, 16);
                context.setPersistentParameter("bgcolor", color);
                context.setTemporaryParameter("updated", "\nColor was updated");
                context.setTemporaryParameter("index", "/index2.html");
                context.getDispatcher().dispatchRequest("/index2.html");
                return;
            }
        } catch (NumberFormatException ex) {
            System.out.println("An error has occured while executing bg color Worker");
        }
        context.setTemporaryParameter("updated", "\nColor was not updated");
        context.setTemporaryParameter("index", "/index2.html");
        context.getDispatcher().dispatchRequest("/index2.html");
    }
}
