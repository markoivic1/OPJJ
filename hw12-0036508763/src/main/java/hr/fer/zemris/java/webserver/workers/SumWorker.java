package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker which sums to given parameters.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class SumWorker implements IWebWorker {
    /**
     * Sums to given parameters if some parameters are missing or are invalid it sets values to default.
     * Default values:
     *        varA = 1
     *        varB = 2
     * @param context context
     * @throws Exception Thrown when an error has occurred while executing script
     */
    @Override
    public void processRequest(RequestContext context) throws Exception {
        int a;
        int b;
        try {
            a = Integer.parseInt(context.getParameter("a"));
        } catch (NumberFormatException e) {
            a = 1;
        }
        try {
            b = Integer.parseInt(context.getParameter("b"));
        } catch (NumberFormatException e) {
            b = 2;
        }
        int sum = a+b;
        context.setTemporaryParameter("zbroj", String.valueOf(sum));
        context.setTemporaryParameter("varA", String.valueOf(a));
        context.setTemporaryParameter("varB", String.valueOf(b));
        context.setTemporaryParameter("imgName", sum % 2 == 0 ? "alberta.jpg" : "mountain.jpg");
        context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
    }
}
