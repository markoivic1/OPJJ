package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Model which represents single document.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
    /**
     * Path to a file.
     */
    private Path path;
    /**
     * Text which is used to initialize JTextArea.
     */
    private String text;
    /**
     * Text area.
     */
    private JTextArea jTextArea;
    /**
     * Indicates that the document had been modified.
     */
    private boolean modified;
    /**
     * List of listeners.
     */
    private List<SingleDocumentListener> listeners;

    /**
     * Constructor.
     * @param path Path to a file.
     * @param text Text used for JTextArea initialization.
     */
    public DefaultSingleDocumentModel(Path path, String text) {
        this.path = path;
        this.text = text;
        jTextArea = new JTextArea();
        jTextArea.setText(text);
        listeners = new ArrayList<>();
        modified = false;

        jTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (jTextArea.getText().equals(text)) {
                    setModified(false);
                    return;
                }
                setModified(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (jTextArea.getText().equals(text)) {
                    setModified(false);
                    return;
                }
                setModified(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    @Override
    public JTextArea getTextComponent() {
        return jTextArea;
    }

    @Override
    public Path getFilePath() {
        return path;
    }

    @Override
    public void setFilePath(Path path) {
        Objects.requireNonNull(path);

        this.path = path;
        for (SingleDocumentListener listener : listeners) {
            listener.documentFilePathUpdated(this);
        }
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        this.modified = modified;
        for (SingleDocumentListener listener : listeners) {
            listener.documentModifyStatusUpdated(this);
        }
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        listeners.remove(l);
    }

}
