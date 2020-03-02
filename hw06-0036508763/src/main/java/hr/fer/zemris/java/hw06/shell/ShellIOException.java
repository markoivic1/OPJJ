package hr.fer.zemris.java.hw06.shell;

/**
 * Exception used when working with {@link MyShell}.
 * Used when running {@link MyShell} specific programs.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ShellIOException extends RuntimeException {
    /**
     * Default constructor which calls super().
     */
    public ShellIOException() {
    }

    /**
     * Constructor which takes message and calls super constructor which takes this message.
     * @param message Message which will be passed to the super constructor.
     */
    public ShellIOException(String message) {
        super(message);
    }
}
