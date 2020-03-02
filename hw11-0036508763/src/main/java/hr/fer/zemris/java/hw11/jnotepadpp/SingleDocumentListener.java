package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Defines listeners for single document model.
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface SingleDocumentListener {
    /**
     * Notifies listeners that the document's modify status was changed.
     * @param model Model which was changed.
     */
    void documentModifyStatusUpdated(SingleDocumentModel model);

    /**
     * Notifes listeners that the document's path was changed.
     * @param model Model whose path was changed.
     */
    void documentFilePathUpdated(SingleDocumentModel model);
}
