package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Model which represents multiple documents.
 * Documents are of type {@link SingleDocumentModel}.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
    /**
     * List of documents
     */
    private List<SingleDocumentModel> documents;
    /**
     * Current document.
     */
    private SingleDocumentModel currentDocument;
    /**
     * Current listener.
     */
    private SingleDocumentListener currentListener;
    /**
     * List of listeners.
     */
    private List<MultipleDocumentListener> listeners;
    /**
     * Icon of an unsaved document.
     */
    private ImageIcon crossIcon;
    /**
     * Icon of a saved document.
     */
    private ImageIcon tickIcon;

    /**
     * Constructor.
     */
    public DefaultMultipleDocumentModel() {
        documents = new ArrayList<>();
        currentDocument = null;
        listeners = new ArrayList<>();
        loadIcons();

        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (documents.size() == 0) {
                    return;
                }
                SingleDocumentModel oldDocument = currentDocument;
                unregisterListener();
                currentDocument = documents.get(getSelectedIndex());
                registerListener();

                listeners.forEach(l -> l.currentDocumentChanged(oldDocument, currentDocument));
            }
        });
    }

    /**
     * Loads icons.
     */
    private void loadIcons() {
        byte[] bytes = new byte[0];

        try (InputStream is = this.getClass().getResourceAsStream("icons/cross.png")) {
            if (is == null) {
                return;
            }
            bytes = is.readAllBytes();
        } catch (IOException e) {
        }
        crossIcon = new ImageIcon(bytes);

        try (InputStream is = this.getClass().getResourceAsStream("icons/tick.png")) {
            if (is == null) {
                return;
            }
            bytes = is.readAllBytes();
        } catch (IOException e) {
        }
        tickIcon = new ImageIcon(bytes);
    }

    @Override
    public SingleDocumentModel createNewDocument() {
        SingleDocumentModel oldDocument = currentDocument;
        documents.add(new DefaultSingleDocumentModel(null, null));
        unregisterListener();
        currentDocument = documents.get(documents.size() - 1);
        registerListener();

        this.addTab("(unnamed)", new JScrollPane(currentDocument.getTextComponent()));
        setIconAt(documents.indexOf(currentDocument), crossIcon);

        registerListener();

        for (MultipleDocumentListener listener : listeners) {
            listener.documentAdded(currentDocument);
            listener.currentDocumentChanged(oldDocument, documents.get(documents.size() - 1));
        }
        return currentDocument;
    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return currentDocument;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) {
        Objects.requireNonNull(path);
        for (SingleDocumentModel document : documents) {
            if (document.getFilePath() == null) {
                continue;
            }
            if (document.getFilePath().equals(path)) {
                unregisterListener();
                currentDocument = document;
                registerListener();
                setSelectedIndex(documents.indexOf(document));
                return null;
            }
        }

        DefaultSingleDocumentModel singleDocument;
        try {
            singleDocument = new DefaultSingleDocumentModel(path, Files.readString(path));
            documents.add(singleDocument);
            unregisterListener();
            currentDocument = singleDocument;
            registerListener();
        } catch (IOException ex) {
            return null;
        }

        registerListener();

        this.addTab(path.getFileName().toString(), new JScrollPane(singleDocument.getTextComponent()));
        setIconAt(documents.indexOf(currentDocument), tickIcon);

        setSelectedIndex(documents.size() - 1);

        for (MultipleDocumentListener listener : listeners) {
            listener.documentAdded(currentDocument);
        }

        return currentDocument;
    }

    /**
     * Unregisters current listener from a current document.
     */
    private void unregisterListener() {
        if (currentListener == null) {
            return;
        }
        currentDocument.removeSingleDocumentListener(currentListener);
    }

    /**
     * Registers listener to a current document.
     */
    private void registerListener() {
        currentListener = new SingleDocumentListener() {
            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {
                if (model.isModified()) {
                    setIconAt(documents.indexOf(model), crossIcon);
                } else {
                    setIconAt(documents.indexOf(model), tickIcon);
                }
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
                DefaultMultipleDocumentModel.this.setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
            }
        };
        currentDocument.addSingleDocumentListener(currentListener);
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {
        for (SingleDocumentModel document : documents) {
            if (document.getFilePath() == null || document == model) {
                continue;
            }
            if (document.getFilePath().equals(newPath)) {
                JOptionPane.showMessageDialog(this, "This file is already opened", "Unnable to save file", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Path selectedPath = newPath == null ? model.getFilePath() : newPath;

        if (Files.exists(selectedPath)) {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Would you like to overwrite " + selectedPath.getFileName().toString() + "?",
                    "File with this name already exists"
                    , JOptionPane.YES_NO_CANCEL_OPTION);
            if (option == JOptionPane.NO_OPTION) {
                return;
            }
        }

        try {
            Files.writeString(selectedPath, model.getTextComponent().getText());
            model.setFilePath(selectedPath);
        } catch (IOException ex) {
        }

        currentDocument.setModified(false);

        for (MultipleDocumentListener listener : listeners) {
            listener.currentDocumentChanged(currentDocument, currentDocument);
        }
    }

    @Override
    public void closeDocument(SingleDocumentModel model) {
        documents.remove(model);
        for (MultipleDocumentListener listener : listeners) {
            listener.documentRemoved(model);
        }
    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.remove(l);
    }

    @Override
    public int getNumberOfDocuments() {
        return documents.size();
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        return documents.get(index);
    }

    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return documents.iterator();
    }
}
