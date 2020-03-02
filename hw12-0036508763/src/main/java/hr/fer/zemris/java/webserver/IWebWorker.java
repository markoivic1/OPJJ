package hr.fer.zemris.java.webserver;

/**
 * Class which defines worker.
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface IWebWorker {
    /**
     * Defines action which worker processes
     * @param context context
     * @throws Exception exception
     */
    public void processRequest(RequestContext context) throws Exception;
}
