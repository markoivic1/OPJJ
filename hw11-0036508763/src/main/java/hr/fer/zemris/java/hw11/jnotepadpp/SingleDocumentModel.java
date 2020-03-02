package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.nio.file.Path;

/**
 * Defines single document model.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface SingleDocumentModel {
    /**
     * Getter text component.
     *
     * @return Returns text component.
     */
    JTextArea getTextComponent();

    /**
     * Getter for file path.
     *
     * @return Returns file path.
     */
    Path getFilePath();

    /**
     * Sets path to a given path.
     *
     * @param path new path.
     */
    void setFilePath(Path path);

    /**
     * Check if the document had been modified.
     *
     * @return Returns true if the document was modified; returns false otherwise.
     */
    boolean isModified();

    /**
     * Sets modification flag to a given value.
     *
     * @param modified new value of a modification flag.
     */
    void setModified(boolean modified);

    /**
     * Adds document listener.
     *
     * @param l Listener which will be added.
     */
    void addSingleDocumentListener(SingleDocumentListener l);

    /**
     * Removes document listener.
     *
     * @param l Listener which will be removed.
     */
    void removeSingleDocumentListener(SingleDocumentListener l);
}
