package hr.fer.zemris.java.custom.scripting.nodes.prob1;

/**
 * Lexer specific exception. Used to better specify the cause of the exception.
 * @author Marko Ivić
 */
public class LexerException extends RuntimeException {
    /**
     * Default constructor.
     */
    public LexerException() {
        super();
    }

    /**
     * Constructor which passes the message to a super constructor.
     * @param s
     */
    public LexerException(String s) {
        super(s);
    }
}
