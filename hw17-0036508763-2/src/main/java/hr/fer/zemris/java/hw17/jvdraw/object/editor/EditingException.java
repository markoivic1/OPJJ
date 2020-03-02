package hr.fer.zemris.java.hw17.jvdraw.object.editor;

/**
 * Exception which is thrown when editing data is invalid.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class EditingException extends RuntimeException {
    public EditingException() {
    }

    public EditingException(String message) {
        super(message);
    }
}
