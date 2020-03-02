package hr.fer.zemris.java.hw06.shell.lexer;

/**
 * Exception specific for NameBuilderLexer.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class LexerException extends RuntimeException {
    /**
     * Default constructor.
     */
    public LexerException() {
    }

    /**
     * Passing message to super constructor.
     * @param message The message which will be passed.
     */
    public LexerException(String message) {
        super(message);
    }

    /**
     * Passing message and cause to a super constructor.
     * @param message The message which will be passed.
     * @param cause Cause of a exception.
     */
    public LexerException(String message, Throwable cause) {
        super(message, cause);
    }
}
