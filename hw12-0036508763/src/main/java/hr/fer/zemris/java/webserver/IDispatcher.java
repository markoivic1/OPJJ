package hr.fer.zemris.java.webserver;

/**
 * Interface which defines how requests will be dispatched.
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface IDispatcher {
    /**
     * Dispatches requests.
     * @param urlPath Url which is used to dispatch requests
     * @throws Exception Exception
     */
    void dispatchRequest(String urlPath) throws Exception;
}
