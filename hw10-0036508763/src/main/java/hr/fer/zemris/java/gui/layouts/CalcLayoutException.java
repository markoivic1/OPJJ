package hr.fer.zemris.java.gui.layouts;

/**
 * CalcLayout exception.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class CalcLayoutException extends RuntimeException {
    /**
     * Constructor.
     */
    public CalcLayoutException() {
    }

    /**
     * Constructor.
     * @param message Message which will be passed on.
     */
    public CalcLayoutException(String message) {
        super(message);
    }
}
