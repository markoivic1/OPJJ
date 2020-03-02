package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Defines listener for a {@link MultipleDocumentModel}
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface MultipleDocumentListener {
    /**
     * Indicates that the current document had been changed.
     * @param previousModel Previuos model.
     * @param currentModel Current model
     */
    void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

    /**
     * Indicates that a new document was added.
     * @param model new document.
     */
    void documentAdded(SingleDocumentModel model);

    /**
     * Indicates that a document had been removed.
     * @param model Model which is removed.
     */
    void documentRemoved(SingleDocumentModel model);
}
