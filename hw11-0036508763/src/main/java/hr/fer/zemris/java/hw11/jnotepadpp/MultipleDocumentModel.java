package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Interface which models multiple document model.
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
    /**
     * Creates new document.
     * @return Returns newly created document.
     */
    SingleDocumentModel createNewDocument();

    /**
     * Getter for current document.
     * @return Returns current document.
     */
    SingleDocumentModel getCurrentDocument();

    /**
     * Loads document from a given path.
     * @param path Path from which the document will be loaded.
     * @return Returns loaded document.
     */
    SingleDocumentModel loadDocument(Path path);

    /**
     * Saves document preferably to a newPath.
     * @param model Document which will be saved.
     * @param newPath Path
     */
    void saveDocument(SingleDocumentModel model, Path newPath);

    /**
     * Closes given document and removes it from collection.
     * @param model Model which will be removed.
     */
    void closeDocument(SingleDocumentModel model);

    /**
     * Adds listeners
     * @param l Listener which will be added
     */
    void addMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Removes listeners.
     * @param l Listener which will be removed.
     */
    void removeMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Getter for number of documents.
     * @return Returns number of documents.
     */
    int getNumberOfDocuments();

    /**
     * Gets document at a given index.
     * @param index Index from which the document will be retrieved.
     * @return Returns document at a given idnex.
     */
    SingleDocumentModel getDocument(int index);
}