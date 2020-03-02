package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Specific exception for {@link QueryLexer}.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class LexerException extends RuntimeException{
    /**
     * {@inheritDoc}
     */
    public LexerException() {
    }

    /**
     * {@inheritDoc}
     * @param message
     */
    public LexerException(String message) {
        super(message);
    }
}
