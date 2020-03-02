package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.nio.charset.StandardCharsets;

/**
 * Worker which echos given parameters as a HTML table.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class EchoParams implements IWebWorker {
    /**
     * Formats given parameters
     * @param context Context
     * @throws Exception Thrown when HTML table has failed to be written in context.
     */
    @Override
    public void processRequest(RequestContext context) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>").append(" <thead>");
        for (String name : context.getParameterNames()) {
            sb.append(" <tr>");
            sb.append("  <td>").append(name).append("</td>");
            sb.append("  <td>").append(context.getParameter(name)).append("</td>");
            sb.append(" </tr>");
        }
        sb.append(" </thead>").append("</table>");
        context.write(sb.toString().getBytes(StandardCharsets.UTF_8));
    }
}
