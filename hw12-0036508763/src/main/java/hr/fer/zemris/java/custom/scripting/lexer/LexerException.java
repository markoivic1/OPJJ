package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class used to throw an lexer specific exception.
 * @author Marko Ivić
 */
public class LexerException extends RuntimeException {
    /**
     * Constructor which calls super() constructor.
     */
    public LexerException() {
        super();
    }

    /**
     * Constructo passes given string to a super() constructor.
     * @param message message which is to be passed to a super constructor.
     */
    public LexerException(String message) {
        super(message);
    }
}
