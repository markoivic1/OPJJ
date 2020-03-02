package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception used to better specify the cause of the exception.
 * @author Marko Ivić
 */
public class SmartScriptParserException extends RuntimeException {
    /**
     * Default constructor is called.
     */
    public SmartScriptParserException() {
    }

    /**
     * Super constructor is colled with passed message which is given as an argument.
     * @param message Message to be passed to a super constructor.
     */
    public SmartScriptParserException(String message) {
        super(message);
    }

    public SmartScriptParserException(Throwable cause) {
        super(cause);
    }
}
