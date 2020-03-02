package hr.fer.zemris.java.hw11.jnotepadpp;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.activation.ActivationID;
import java.text.Collator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * JNotepadPP is made as an improved Notepad which comes standard with Windows.
 * Some of its features were inspired by Notepad++ hence the name.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class JNotepadPP extends JFrame {

    /**
     * Holds current documents.
     */
    private DefaultMultipleDocumentModel documents;
    /**
     * Represents clipboard.
     */
    private String clipBoard;
    /**
     * Represents label in status bar which displays length.
     */
    private LJLabel lengthLabel;
    /**
     * Represents label in status bar which displays selected line.
     */
    private LJLabel lnLabel;
    /**
     * Represents label in status bar which displays selected column.
     */
    private LJLabel colLabel;
    /**
     * Represents label in status bar which displays number of selected characters.
     */
    private LJLabel selLabel;
    /**
     * Represents label in status bar which displays time.
     */
    private JLabel clock;
    /**
     * Used to provide proper translations.
     */
    private FormLocalizationProvider flp;
    /**
     * Defines properties for specific locale.
     */
    private Locale locale;
    /**
     * Used for methods which require localization.
     */
    private Collator collator;

    /**
     * Constructor.
     * Initializes GUI.
     */
    public JNotepadPP() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(10, 10);
        setSize(500, 500);

        flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
        locale = new Locale(flp.getCurrentLanguage());
        collator = Collator.getInstance(locale);

        clipBoard = "";
        initGUI();

        initListeners();
    }

    /**
     * Initializes GUI.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JPanel content = new JPanel(new BorderLayout());
        documents = new DefaultMultipleDocumentModel();

        content.add(documents, BorderLayout.CENTER);

        JPanel statusBar = initStatusBar();
        content.add(statusBar, BorderLayout.SOUTH);

        statusBar.repaint();

        cp.add(content, BorderLayout.CENTER);

        createActions();
        createMenus();
        cp.add(createToolbar(), BorderLayout.NORTH);
    }

    /**
     * Initializes status bar located at the bottom of a frame.
     *
     * @return Returns initializes status bar.
     */
    private JPanel initStatusBar() {
        JPanel statusBar = new JPanel(new GridLayout(1, 3));

        lengthLabel = new LJLabel("length", LocalizationProvider.getInstance());
        statusBar.add(lengthLabel);

        lnLabel = new LJLabel("ln", LocalizationProvider.getInstance());
        colLabel = new LJLabel("col", LocalizationProvider.getInstance());
        selLabel = new LJLabel("sel", LocalizationProvider.getInstance());

        JPanel caretInfo = new JPanel(new GridLayout(1, 3));
        caretInfo.add(lnLabel);
        caretInfo.add(colLabel);
        caretInfo.add(selLabel);

        statusBar.add(caretInfo);

        clock = new JLabel();
        statusBar.add(clock);

        new Clock();

        return statusBar;
    }

    /**
     * Clock which is used display time in the status bar.
     */
    class Clock extends JComponent {

        /**
         * serial
         */
        private static final long serialVersionUID = 1L;

        /**
         * Formats time in a given pattern.
         */
        private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        /**
         * Constructor.
         */
        public Clock() {
            updateTime();

            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (Exception ex) {
                    }
                    SwingUtilities.invokeLater(() -> {
                        updateTime();
                    });
                }
            });
            t.setDaemon(true);
            t.start();
        }

        /**
         * Updates time in status bar.
         */
        private void updateTime() {
            LocalDateTime date = LocalDateTime.now();
            String time = date.format(formatter);
            JNotepadPP.this.clock.setText(time);
        }
    }

    /**
     * This method is used to update status bar.
     * Usually used when listeners are fired.
     */
    private void updateStatusBar() {
        JTextArea document;
        if (documents.getCurrentDocument() == null) {
            document = new JTextArea();
        } else {
            document = documents.getCurrentDocument().getTextComponent();
        }

        lengthLabel.setText(flp.getString("length") + ": " + String.valueOf(document.getText().length()));

        int start = Math.min(document.getCaret().getDot(), document.getCaret().getMark());
        int len = Math.abs(document.getCaret().getDot() - document.getCaret().getMark());

        if (len == 0) {
            toLowerCaseSelected.setEnabled(false);
            toUpperCaseSelected.setEnabled(false);
            invertSelectedPart.setEnabled(false);
            sortAscending.setEnabled(false);
            sortDescending.setEnabled(false);
            uniqueLines.setEnabled(false);
            copySelectedPart.setEnabled(false);
            pasteSelectedPart.setEnabled(false);
            cutSelectedPart.setEnabled(false);
        } else {
            toLowerCaseSelected.setEnabled(true);
            toUpperCaseSelected.setEnabled(true);
            invertSelectedPart.setEnabled(true);
            sortAscending.setEnabled(true);
            sortDescending.setEnabled(true);
            uniqueLines.setEnabled(true);
            copySelectedPart.setEnabled(true);
            pasteSelectedPart.setEnabled(true);
            cutSelectedPart.setEnabled(true);
        }

        lnLabel.addAdditionalText(": " + countLine(document, start));
        colLabel.addAdditionalText(": " + calculateColumn(document, start));
        selLabel.addAdditionalText(": " + len);
    }

    /**
     * Counts lines from position 0 to given start in a document.
     *
     * @param document Document in lines are counted.
     * @param start    Index until which the lines are calculated.
     * @return Returns number of lines.
     */
    private int countLine(JTextArea document, int start) {
        int lines = 1;
        char[] data = document.getText().toCharArray();
        for (int i = 0; i < start; i++) {
            if (data[i] == '\n') {
                lines++;
            }
        }

        return lines;
    }

    /**
     * Caluculates current column at which the caret points.
     *
     * @param document Document in which the column is calculated.
     * @param start    Current position.
     * @return Returns calculated column.
     */
    private int calculateColumn(JTextArea document, int start) {
        String text = "";
        try {
            text = document.getText(0, start);
        } catch (BadLocationException e) {

        }
        char[] data = text.toCharArray();
        int currentColumn = 0;

        for (int i = 0; i < start; i++) {
            if (data[i] == '\n') {
                currentColumn = 0;
                continue;
            }
            currentColumn++;
        }
        return ++currentColumn;
    }

    /**
     * Used to initialize listeners.
     */
    private void initListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(null);
            }
        });

        documents.addMultipleDocumentListener(new MultipleDocumentListener() {
            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                updateStatusBar();
                if (currentModel.getFilePath() == null) {
                    setTitle("(unnamed) - JNotePad++");
                    return;
                }
                setTitle(currentModel.getFilePath().toString() + " - JNotePad++");
            }

            @Override
            public void documentAdded(SingleDocumentModel model) {
                infoOnDocument.setEnabled(true);
                saveDocument.setEnabled(true);
                saveAsDocument.setEnabled(true);/*
                copySelectedPart.setEnabled(true);
                pasteSelectedPart.setEnabled(true);
                cutSelectedPart.setEnabled(true);*/
                if (model == null || model.getFilePath() == null) {
                    setTitle("(unnamed) - JNotePad++");
                    documents.setSelectedIndex(documents.getNumberOfDocuments() - 1);
                    return;
                }
                setTitle(model.getFilePath().toString() + " - JNotePad++");

            }

            @Override
            public void documentRemoved(SingleDocumentModel model) {
                if (JNotepadPP.this.documents.getNumberOfDocuments() == 0) {
                    infoOnDocument.setEnabled(false);
                }
                if (documents.getNumberOfDocuments() == 0) {
                    saveDocument.setEnabled(false);
                    saveAsDocument.setEnabled(false);
                    copySelectedPart.setEnabled(false);
                    pasteSelectedPart.setEnabled(false);
                    cutSelectedPart.setEnabled(false);
                }
            }
        });

        documents.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (documents.getCurrentDocument().getFilePath() == null) {
                    setTitle("(unnamed) - JNotePad++");
                } else {
                    setTitle(documents.getCurrentDocument().getFilePath().getFileName().toString() + " - JNotePad++");
                }
                updateStatusBar();
                documents.getCurrentDocument().getTextComponent().addCaretListener(new CaretListener() {
                    @Override
                    public void caretUpdate(CaretEvent e) {
                        updateStatusBar();
                    }
                });
            }
        });
    }

    /**
     * Creates actions and defines their unique properties such as name, description and shortcuts.
     */
    private void createActions() {
        setNamesAndDescription();

        openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

        saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveDocument.setEnabled(false);

        saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
        saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
        saveAsDocument.setEnabled(false);

        cutSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt X"));
        cutSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

        pasteSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt V"));
        pasteSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
        pasteSelectedPart.setEnabled(false);

        copySelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt C"));
        copySelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

        exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
        exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);

        newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
        newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

        closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
        closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);

        infoOnDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
        infoOnDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
        infoOnDocument.setEnabled(false);

        invertSelectedPart.setEnabled(false);
        toUpperCaseSelected.setEnabled(false);
        toLowerCaseSelected.setEnabled(false);
        sortAscending.setEnabled(false);
        sortDescending.setEnabled(false);
        uniqueLines.setEnabled(false);
        copySelectedPart.setEnabled(false);
        pasteSelectedPart.setEnabled(false);
        cutSelectedPart.setEnabled(false);

        english.putValue(Action.NAME, "English");
        croatian.putValue(Action.NAME, "Croatian");
        german.putValue(Action.NAME, "German");
    }

    /**
     * Sets name and descriptions for actions.
     */
    private void setNamesAndDescription() {
        openDocument.putValue(Action.NAME, flp.getString("open"));
        openDocument.putValue(Action.SHORT_DESCRIPTION, flp.getString("open_desc"));

        saveDocument.putValue(Action.NAME, flp.getString("save"));
        saveDocument.putValue(Action.SHORT_DESCRIPTION, flp.getString("save_desc"));

        saveAsDocument.putValue(Action.NAME, flp.getString("saveas"));
        saveAsDocument.putValue(Action.SHORT_DESCRIPTION, flp.getString("save_desc"));

        cutSelectedPart.putValue(Action.NAME, flp.getString("cut"));
        cutSelectedPart.putValue(Action.SHORT_DESCRIPTION, flp.getString("cut_desc"));

        pasteSelectedPart.putValue(Action.NAME, flp.getString("paste"));
        pasteSelectedPart.putValue(Action.SHORT_DESCRIPTION, flp.getString("paste_desc"));

        copySelectedPart.putValue(Action.NAME, flp.getString("copy"));
        copySelectedPart.putValue(Action.SHORT_DESCRIPTION, flp.getString("copy_desc"));

        exitAction.putValue(Action.NAME, flp.getString("exit"));
        exitAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("exit_desc"));

        newDocument.putValue(Action.NAME, flp.getString("new"));
        newDocument.putValue(Action.SHORT_DESCRIPTION, flp.getString("new_desc"));

        closeDocument.putValue(Action.NAME, flp.getString("close"));
        closeDocument.putValue(Action.SHORT_DESCRIPTION, flp.getString("exit_desc"));

        infoOnDocument.putValue(Action.NAME, flp.getString("info"));
        infoOnDocument.putValue(Action.SHORT_DESCRIPTION, flp.getString("exit_desc"));

        invertSelectedPart.putValue(Action.NAME, flp.getString("invert"));
        invertSelectedPart.putValue(Action.SHORT_DESCRIPTION, flp.getString("invert_desc"));

        toUpperCaseSelected.putValue(Action.NAME, flp.getString("uppercase"));
        toUpperCaseSelected.putValue(Action.SHORT_DESCRIPTION, flp.getString("uppercase_desc"));

        toLowerCaseSelected.putValue(Action.NAME, flp.getString("lowercase"));
        toLowerCaseSelected.putValue(Action.SHORT_DESCRIPTION, flp.getString("lowercase_desc"));

        sortAscending.putValue(Action.NAME, flp.getString("ascending"));
        sortAscending.putValue(Action.SHORT_DESCRIPTION, flp.getString("ascending_desc"));

        sortDescending.putValue(Action.NAME, flp.getString("descending"));
        sortDescending.putValue(Action.SHORT_DESCRIPTION, flp.getString("descending_desc"));

        uniqueLines.putValue(Action.NAME, flp.getString("unique"));
        uniqueLines.putValue(Action.SHORT_DESCRIPTION, flp.getString("unique_desc"));
    }

    /**
     * Creates menus.
     */
    private void createMenus() {
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu(flp.getString("file"));

        mb.add(file);
        file.add(new JMenuItem(newDocument));
        file.add(new JMenuItem(closeDocument));
        file.add(new JMenuItem(openDocument));
        file.add(new JMenuItem(saveDocument));
        file.add(new JMenuItem(saveAsDocument));
        file.addSeparator();
        file.add(new JMenuItem(exitAction));

        JMenu edit = new JMenu(flp.getString("edit"));
        mb.add(edit);
        edit.add(new JMenuItem(copySelectedPart));
        edit.add(new JMenuItem(pasteSelectedPart));
        edit.add(new JMenuItem(cutSelectedPart));
        edit.add(new JMenuItem(infoOnDocument));

        JMenu languages = new JMenu(flp.getString("languages"));
        mb.add(languages);
        languages.add(new JMenuItem(english));
        languages.add(new JMenuItem(croatian));
        languages.add(new JMenuItem(german));

        JMenu tools = new JMenu(flp.getString("tools"));
        JMenu changeCase = new JMenu(flp.getString("changecase"));

        changeCase.add(new JMenuItem(toLowerCaseSelected));
        changeCase.add(new JMenuItem(toUpperCaseSelected));
        changeCase.add(new JMenuItem(invertSelectedPart));

        JMenu sort = new JMenu(flp.getString("sort"));
        sort.add(new JMenuItem(sortAscending));
        sort.add(new JMenuItem(sortDescending));
        sort.add(new JMenuItem(uniqueLines));

        tools.add(changeCase);
        tools.add(sort);

        mb.add(tools);
        flp.addLocalizationListener(new ILocalizationListener() {
            @Override
            public void localizationChanged() {
                file.setText(flp.getString("file"));
                languages.setText(flp.getString("languages"));
                edit.setText(flp.getString("edit"));
                tools.setText(flp.getString("tools"));
                sort.setText(flp.getString("sort"));
                changeCase.setText(flp.getString("changecase"));
                setNamesAndDescription();
                updateStatusBar();
                locale = new Locale(flp.getCurrentLanguage());
                collator = Collator.getInstance(locale);
            }
        });

        setJMenuBar(mb);
    }

    /**
     * Creates toolbar.
     *
     * @return Returns created toolbar.
     */
    private JToolBar createToolbar() {
        JToolBar tb = new JToolBar();
        tb.setFloatable(true);

        tb.add(new JButton(newDocument));
        tb.add(new JButton(openDocument));
        tb.add(new JButton(closeDocument));
        tb.add(new JButton(saveDocument));
        tb.add(new JButton(saveAsDocument));
        tb.addSeparator();
        tb.add(new JButton(copySelectedPart));
        tb.add(new JButton(cutSelectedPart));
        tb.add(new JButton(pasteSelectedPart));
        tb.addSeparator();
        tb.add(new JButton(exitAction));

        return tb;
    }

    /**
     * Method used for cleaner look of the code.
     *
     * @return Returns JTextArea of current document's text component.
     */
    private JTextArea getJTextArea() {
        return documents.getCurrentDocument().getTextComponent();
    }

    /**
     * Action which defines information window.
     */
    private final Action infoOnDocument = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Document document = documents.getCurrentDocument().getTextComponent().getDocument();
            int noOfChars = 0;
            int noOfNonBlank = 0;
            int noOfEnters = 0;
            try {
                noOfChars = document.getLength();
                noOfNonBlank = document.getText(0, noOfChars).replace(" ", "")
                        .replace("\n", "").replace("\t", "").length();
                noOfEnters = countEnters(document.getText(0, noOfChars));

            } catch (BadLocationException ex) {

            }
            JOptionPane.showMessageDialog(JNotepadPP.this,
                    "Number of characters: " + noOfChars
                            + "\nNumber of non blank characters: " + noOfNonBlank
                            + "\nNumber of enters: " + noOfEnters,
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    };

    /**
     * Counts number of line breaks.
     *
     * @param text Text in which the line breaks are calculated.
     * @return Number of line breaks.
     */
    private int countEnters(String text) {
        char[] data = text.toCharArray();
        int counter = 0;
        for (char singleChar : data) {
            if (singleChar == '\n') {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Defines action which creates new document.
     */
    private final Action newDocument = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            documents.createNewDocument();
        }
    };

    /**
     * Defines action which opens existing document.
     */
    private final Action openDocument = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Open file");
            if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            Path filePath = jfc.getSelectedFile().toPath();
            if (!Files.isReadable(filePath)) {
                JOptionPane.showMessageDialog(JNotepadPP.this,
                        "File" + filePath + " couldn't be read.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            documents.loadDocument(filePath);
        }
    };

    /**
     * Defines action which saves current document as chosen file.
     */
    private final Action saveAsDocument = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Save document");
            if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(JNotepadPP.this, "File is not saved", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            documents.saveDocument(documents.getCurrentDocument(), jfc.getSelectedFile().toPath());

        }
    };

    /**
     * Defines action which closes document. It prompts user with some warnings.
     */
    private final Action closeDocument = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name;
            if (documents.getCurrentDocument().getFilePath() == null) {
                name = "(unnamed)";
            } else {
                name = documents.getCurrentDocument().getFilePath().getFileName().toString();
            }
            int option = JOptionPane.showConfirmDialog(
                    JNotepadPP.this,
                    "Would you like to save " + name,
                    "Document is not saved"
                    , JOptionPane.YES_NO_CANCEL_OPTION);
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            }
            if (documents.getCurrentDocument().isModified() && option == JOptionPane.YES_OPTION) {
                saveAsDocument.actionPerformed(e);
            }
            documents.closeDocument(documents.getCurrentDocument());
            documents.remove(documents.getSelectedComponent());

            copySelectedPart.setEnabled(false);
            pasteSelectedPart.setEnabled(false);
            cutSelectedPart.setEnabled(false);
        }
    };

    /**
     * Defines action which saves current document.
     */
    private final Action saveDocument = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (documents.getCurrentDocument().getFilePath() == null) {
                saveAsDocument.actionPerformed(e);
                return;
            }
            documents.saveDocument(documents.getCurrentDocument(), documents.getCurrentDocument().getFilePath());
        }
    };

    /**
     * Action which closes every window with appropriate message and closes the frame itself.
     * But only if for every document yes or no options have been selected.
     */
    private final Action exitAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (SingleDocumentModel document : documents) {
                if (document.isModified() || document.getFilePath() == null) {
                    String nameOfAFile = document.getFilePath() != null ? document.getFilePath().getFileName().toString() : "(unnamed)";
                    int option = JOptionPane.showConfirmDialog(
                            JNotepadPP.this,
                            "Would you like to save " + nameOfAFile,
                            "Document is not saved"
                            , JOptionPane.YES_NO_CANCEL_OPTION);

                    if (option == JOptionPane.YES_OPTION) {
                        Path path = document.getFilePath();
                        JFileChooser jfc = new JFileChooser();
                        jfc.setDialogTitle("Save document");
                        if (document.getFilePath() == null) {
                            if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                                JOptionPane.showMessageDialog(JNotepadPP.this, "This file is not saved.", "Info", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }
                            path = jfc.getSelectedFile().toPath();
                        }

                        documents.saveDocument(document, path);
                    } else if (option == JOptionPane.NO_OPTION) {
                        continue;
                    } else {
                        return;
                    }
                }
            }
            dispose();
        }
    };

    /**
     * Defines action which cuts selected part of a text.
     */
    private final Action cutSelectedPart = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea jta = documents.getCurrentDocument().getTextComponent();
            int start = Math.min(jta.getCaret().getDot(), jta.getCaret().getMark());
            int len = Math.abs(jta.getCaret().getDot() - jta.getCaret().getMark());

            Document doc = getJTextArea().getDocument();
            try {
                clipBoard = doc.getText(start, len);
                doc.remove(start, len);
                if (clipBoard.length() != 0) {
                    pasteSelectedPart.setEnabled(true);
                } else {
                    pasteSelectedPart.setEnabled(false);
                }
            } catch (BadLocationException ex) {
            }
        }
    };

    /**
     * Defines action which copies selected part of a text.
     */
    private final Action copySelectedPart = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea jta = documents.getCurrentDocument().getTextComponent();
            int start = Math.min(jta.getCaret().getDot(), jta.getCaret().getMark());
            int len = Math.abs(jta.getCaret().getDot() - jta.getCaret().getMark());

            Document doc = getJTextArea().getDocument();
            try {
                clipBoard = doc.getText(start, len);
                if (clipBoard.length() != 0) {
                    pasteSelectedPart.setEnabled(true);
                } else {
                    pasteSelectedPart.setEnabled(false);
                }
            } catch (BadLocationException ex) {
            }
        }
    };

    /**
     * Defines action which pastes text from clipboard to caret position.
     */
    private final Action pasteSelectedPart = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea jta = documents.getCurrentDocument().getTextComponent();
            int start = Math.min(jta.getCaret().getDot(), jta.getCaret().getMark());

            Document doc = getJTextArea().getDocument();
            try {
                doc.insertString(start, clipBoard, null);
            } catch (BadLocationException ex) {
            }
        }
    };

    /**
     * Defines action which switches selected cases to lower.
     */
    private final Action toLowerCaseSelected = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            convertCases((t) -> toLowerCase(t));
        }
    };

    /**
     * Method which convert given text to lowercase.
     *
     * @param text Text which will be converted.
     * @return Lowercase string made from given text.
     */
    private String toLowerCase(String text) {
        char[] data = text.toCharArray();
        for (int i = 0; i < data.length; i++) {
            data[i] = Character.toLowerCase(data[i]);
        }
        return String.valueOf(data);
    }

    /**
     * Converts cases using given function.
     *
     * @param function Function which is used to convert part of the text once it had been extracted.
     */
    private void convertCases(Function<String, String> function) {
        JTextArea editor = documents.getCurrentDocument().getTextComponent();
        int start = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
        int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());

        if (len < 0) return;
        Document doc = editor.getDocument();

        try {
            String text = doc.getText(start, len);
            text = function.apply(text);

            doc.remove(start, len);
            doc.insertString(start, text, null);
        } catch (BadLocationException ex) {
        }
    }

    /**
     * Defines action which switches selected cases to upper case.
     */
    private final Action toUpperCaseSelected = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            convertCases(t -> toUpperCase(t));
        }
    };

    /**
     * Switches given text to upper case.
     *
     * @param text Text which will be converted.
     * @return Uppercase text.
     */
    private String toUpperCase(String text) {
        char[] data = text.toCharArray();
        for (int i = 0; i < data.length; i++) {
            data[i] = Character.toUpperCase(data[i]);
        }
        return String.valueOf(data);
    }

    /**
     * Defines action which switches uppercase for lowercase and vice versa.
     */
    private final Action invertSelectedPart = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            convertCases(t -> toggleCase(t));
        }

        private String toggleCase(String text) {
            char[] chars = text.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (Character.isUpperCase(chars[i])) {
                    chars[i] = Character.toLowerCase(chars[i]);
                } else if (Character.isLowerCase(chars[i])) {
                    chars[i] = Character.toUpperCase(chars[i]);
                }
            }
            return new String(chars);
        }
    };

    /**
     * Defines action that sorts selected lines in descending order.
     */
    private final Action sortDescending = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea editor = documents.getCurrentDocument().getTextComponent();

            Document doc = editor.getDocument();

            int[] startAndLen = getLinesStartAndEnd();
            int start = startAndLen[0];
            int len = startAndLen[1];


            try {
                String sortedText = sort(false);
                doc.remove(start, len);
                doc.insertString(start, sortedText, null);
            } catch (BadLocationException ex) {
            }
        }
    };

    /**
     * Defines action that sorts selected lines in ascending order.
     */
    private final Action sortAscending = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea editor = documents.getCurrentDocument().getTextComponent();
            Document doc = editor.getDocument();

            int[] startAndLen = getLinesStartAndEnd();
            int start = startAndLen[0];
            int len = startAndLen[1];


            try {
                String sortedText = sort(true);
                doc.remove(start, len);
                doc.insertString(start, sortedText, null);
            } catch (BadLocationException ex) {
            }
        }
    };

    /**
     * Method which sorts selected lines.
     *
     * @param ascending Used to decide whether to sort ascending or descending.
     * @return Returns sorted String.
     */
    private String sort(boolean ascending) {
        JTextArea editor = documents.getCurrentDocument().getTextComponent();
        Document doc = editor.getDocument();
        int[] startAndLen = getLinesStartAndEnd();
        int start = startAndLen[0];
        int len = startAndLen[1];

        StringBuilder sb = new StringBuilder();
        List<String> list = extractLines();
        list.sort(ascending ? collator : collator.reversed());

        for (String string : list) {
            sb.append(string);
            if (!string.endsWith("\n")) {
                sb.append("\n");
            }
        }

        try {
            if (start + len == doc.getLength() || doc.getText(start, len + 1).endsWith("\n")) {
                sb.deleteCharAt(sb.length() - 1);
            }
        } catch (BadLocationException e) {
        }
        return sb.toString();
    }

    /**
     * Extracts selected lines.
     *
     * @return Returns list of selected lines.
     */
    private List<String> extractLines() {
        JTextArea editor = documents.getCurrentDocument().getTextComponent();
        Document doc = editor.getDocument();
        int[] startAndLen = getLinesStartAndEnd();
        int start = startAndLen[0];
        int len = startAndLen[1];

        try {
            String text = doc.getText(start, len);
            //return Arrays.stream(text.split("\n")).collect(Collectors.toList());
            return splitLines(text);
        } catch (BadLocationException ex) {
            return null;
        }
    }

    /**
     * Splits lines by line breakers.
     * @param text Text which will be split.
     * @return Returns list of lines.
     */
    private List<String> splitLines(String text) {
        int start = 0;
        int count = 1;
        char[] data = text.toCharArray();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i] == '\n' || i == data.length - 1) {
                list.add(String.valueOf(data, start, count));
                start = i + 1;
                count = 1;
                continue;
            }
            count++;
        }
        if (data.length > 0 && data[data.length - 1] == '\n') {
            list.add("\n");
        }
        return list;
    }

    /**
     * Method which extracts line start and line end position using caret mark and caret dot.
     *
     * @return Returns array with two elements. First element indicates starting position. Second element indicates length.
     */
    private int[] getLinesStartAndEnd() {
        JTextArea editor = documents.getCurrentDocument().getTextComponent();
        int start = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
        int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());

        if (len < 0) return null;

        Document doc = editor.getDocument();

        boolean startFound = false;
        boolean endFound = false;
        while (true) {
            if (start == 0) {
                startFound = true;
            }
            if (start + len == doc.getLength()) {
                endFound = true;
            }
            try {
                String text = doc.getText(start, len);
                if (!startFound && text.startsWith("\n")) {
                    startFound = true;
                    start++;
                    len--;
                }
                if (!endFound && text.endsWith("\n")) {
                    endFound = true;
                    len--;
                }
            } catch (BadLocationException ex) {
            }
            if (!startFound) {
                start--;
                len++;
            }
            if (!endFound) {
                len++;
            }
            if (startFound && endFound) {
                int[] startAndLen = new int[2];
                startAndLen[0] = start;
                startAndLen[1] = len;
                return startAndLen;
            }
        }
    }

    /**
     * Defines method which leaves only first occurrence of each line.
     */
    private final Action uniqueLines = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea editor = documents.getCurrentDocument().getTextComponent();
            Document doc = editor.getDocument();

            int[] startAndLen = getLinesStartAndEnd();
            int start = startAndLen[0];
            int len = startAndLen[1];

            List<String> lines = extractLines();

            Set<String> uniqueLines = new LinkedHashSet<>();
            if (lines == null) {
                return;
            }

            for (String line : lines) {
                if (line.endsWith("\n") && !line.equals("\n")) {
                    uniqueLines.add(line.substring(0, line.length() - 1));
                } else {
                    uniqueLines.add(line);
                }
            }


            StringBuilder sb = new StringBuilder();

            for (String line : uniqueLines) {
                if (line.equals("\n")) {
                    sb.append(line);
                    continue;
                }
                sb.append(line).append("\n");
            }

            try {
                try {
                    if (start + len == doc.getLength() || doc.getText(start, len + 1).endsWith("\n")) {
                        if (sb.length() - 1 > 0) {
                            sb.deleteCharAt(sb.length() - 1);
                        }
                    }
                } catch (BadLocationException ex) {
                }
                String uniqueText = sb.toString();
                doc.remove(start, len);
                doc.insertString(start, uniqueText, null);
            } catch (BadLocationException ex) {
            }
            finally {

            }
        }
    };

    /**
     * Defines action which sets localization position to Croatian.
     */
    private final Action croatian = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("hr");
        }
    };
    /**
     * Defines action which sets localization position to English.
     */
    private final Action english = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("en");
        }
    };
    /**
     * Defines action which sets localization position to German.
     */
    private final Action german = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("de");
        }
    };

    /**
     * Main method. Used to run this class.
     * @param args Uses no arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SwingUtilities.invokeLater(() -> {
                new JNotepadPP().setVisible(true);
            });
        });
    }
}
