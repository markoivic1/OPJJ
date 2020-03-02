package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.webserver.RequestContext;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

/**
 * Utility class that cleans up the code and executes given function.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class FunctionUtil {

    /**
     * Static method which executes function
     * @param stack Stack from which data is taken
     * @param function Function which will be executed
     * @param requestContext Context
     */
    public static void executeFunction(Stack<Object> stack, ElementFunction function, RequestContext requestContext) {
        String s = function.asText();
        if ("sin".equals(s)) {
            Object value = stack.pop();
            if (value instanceof Integer) {
                stack.push(Math.sin(Math.toRadians((Integer) value)));
            } else if (value instanceof Double) {
                stack.push(Math.sin(Math.toRadians((Double) value)));
            }

        } else if ("decfmt".equals(s)) {
            String format = (String) stack.pop();
            Object rawValue = stack.pop();
            DecimalFormat decimalFormat = new DecimalFormat(format);
            stack.push(decimalFormat.format(rawValue));
        } else if ("dup".equals(s)) {
            Object duplicate = stack.pop();
            stack.push(duplicate);
            stack.push(duplicate);
        } else if ("swap".equals(s)) {
            Object object1 = stack.pop();
            Object object2 = stack.pop();
            stack.push(object1);
            stack.push(object2);
        } else if ("setMimeType".equals(s)) {
            requestContext.setMimeType((String) stack.pop());
        } else if ("paramGet".equals(s)) {
            Object defValue = stack.pop();
            String name = (String) stack.pop();
            Object value = requestContext.getParameter(name);
            stack.push(value == null ? defValue : value);
        } else if ("pparamGet".equals(s)) {
            Object defValue = stack.pop();
            String name = (String) stack.pop();
            Object value = requestContext.getPersistentParameter(name);
            stack.push(value == null ? defValue : value);
        } else if ("pparamSet".equals(s)) {
            String name = (String) stack.pop();
            Object value = stack.pop();
            requestContext.setPersistentParameter(name, value.toString());
        } else if ("pparamDel".equals(s)) {
            String name = (String) stack.pop();
            requestContext.removePersistentParameter(name);
        } else if ("tparamGet".equals(s)) {
            Object defValue = stack.pop();
            String name = (String) stack.pop();
            Object value = requestContext.getTemporaryParameter(name);
            stack.push(value == null ? defValue : value);
        } else if ("tparamSet".equals(s)) {
            String name = (String) stack.pop();
            Object value = stack.pop();
            requestContext.setTemporaryParameter(name, value.toString());
        } else if ("tparamDel".equals(s)) {
            String name = (String) stack.pop();
            requestContext.removeTemporaryParameter(name);
        } else if ("now".equals(s)) {
            String format = (String) stack.pop();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            String formatDateTime = now.format(formatter);

            stack.push(formatDateTime);
        }
    }
}
