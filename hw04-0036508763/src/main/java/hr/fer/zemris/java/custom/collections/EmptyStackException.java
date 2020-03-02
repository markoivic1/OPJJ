package hr.fer.zemris.java.custom.collections;

/**
 * Exception made to better describe {@link ObjectStack} collection.
 *
 * @author marko
 * @version 1.0.0
 */
public class EmptyStackException extends RuntimeException {
    /**
     * Constructor which calls constructor of a {@link RuntimeException}.
     */
    public EmptyStackException() {
        super();
    }

    /**
     * Constructor which calls constructor of a {@link RuntimeException} and passes it a string.
     */
    public EmptyStackException(String s) {
        super(s);
    }
}
