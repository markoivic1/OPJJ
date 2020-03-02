package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.color.ColorJLabel;
import hr.fer.zemris.java.hw17.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.object.*;
import hr.fer.zemris.java.hw17.jvdraw.object.editor.EditingException;
import hr.fer.zemris.java.hw17.jvdraw.object.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.tools.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of JVDraw.
 * Drawing lines, circles and filled circles is supported.
 * Opening and saving of raw files is supported.
 * Exporting drawn image as jpg, png a d gif is supported.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class JVDraw extends JFrame {
    /**
     * Current state
     */
    private Tool currentState;
    /**
     * Drawing model which contains {@link GeometricalObject}s
     */
    private DrawingModel drawingModel;
    /**
     * Canvas on which objects will be drawn.
     */
    private JDrawingCanvas drawingCanvas;
    /**
     * Path to a file in which data is saved.
     */
    private Path source;

    /**
     * Constructor which initializes GUI.
     */
    public JVDraw() {
        drawingModel = new DrawingModelImpl();
        initGUI();
    }

    /**
     * Initialzies GUI.
     */
    private void initGUI() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLayout(new BorderLayout());
        this.drawingCanvas = new JDrawingCanvas(() -> currentState, drawingModel);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        initToolBar(topPanel);
        initMenu(topPanel);
        this.add(topPanel, BorderLayout.NORTH);
        add(drawingCanvas, BorderLayout.CENTER);
        initList();
        initActions();
    }

    /**
     * Initializes actions with their proper names, short descriptions, accelerator keys and mnemonic keys.
     */
    private void initActions() {
        openJVD.putValue(Action.NAME, "Open");
        openJVD.putValue(Action.SHORT_DESCRIPTION, "Open existing file.");
        openJVD.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openJVD.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

        saveAsJVD.putValue(Action.NAME, "Save As");
        saveAsJVD.putValue(Action.SHORT_DESCRIPTION, "Save current file as...");
        saveAsJVD.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
        saveAsJVD.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

        saveJVD.putValue(Action.NAME, "Save");
        saveJVD.putValue(Action.SHORT_DESCRIPTION, "Save current file.");
        saveJVD.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveJVD.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

        exportJPG.putValue(Action.NAME, "jpg");
        exportJPG.putValue(Action.SHORT_DESCRIPTION, "Export current file.");
        exportJPG.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 1"));
        exportJPG.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_J);

        exportPNG.putValue(Action.NAME, "png");
        exportPNG.putValue(Action.SHORT_DESCRIPTION, "Export current file.");
        exportPNG.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 2"));
        exportPNG.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);

        exportGIF.putValue(Action.NAME, "gif");
        exportGIF.putValue(Action.SHORT_DESCRIPTION, "Export current file.");
        exportGIF.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 3"));
        exportGIF.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);

        exit.putValue(Action.NAME, "Exit");
        exit.putValue(Action.SHORT_DESCRIPTION, "Exit.");
        exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control q"));
        exit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
    }

    /**
     * Initializes menu with open, save, save as and export.
     * @param panel Panel in which this menu will be added
     */
    private void initMenu(JPanel panel) {
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        bar.add(menu);
        menu.add(new JMenuItem(openJVD));
        menu.add(new JMenuItem(saveJVD));
        menu.add(new JMenuItem(saveAsJVD));
        JMenu export = new JMenu("Export As...");
        export.add(new JMenuItem(exportJPG));
        export.add(new JMenuItem(exportPNG));
        export.add(new JMenuItem(exportGIF));
        menu.addSeparator();
        menu.add(export);
        menu.addSeparator();
        menu.add(new JMenuItem(exit));
        panel.add(bar, BorderLayout.NORTH);
    }

    /**
     * Action which models opening existing .jvd file.
     */
    private final Action openJVD = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Open file");
            if (jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            Path filePath = jfc.getSelectedFile().toPath();
            if (!Files.isReadable(filePath) || !filePath.toString().endsWith(".jvd")) {
                JOptionPane.showMessageDialog(JVDraw.this,
                        "File" + filePath + " couldn't be read.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<GeometricalObject> objectList = new ArrayList<>();
            try {
                for (String line : Files.readAllLines(filePath)) {
                    objectList.add(createGeometricalObject(line));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(JVDraw.this,
                        "File" + filePath + " is corrupted.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            drawingModel.clear();
            for (GeometricalObject object : objectList) {
                drawingModel.add(object);
            }
            source = jfc.getSelectedFile().toPath();
        }
    };

    /**
     * Parses given line from .jvd and creates appropriate {@link GeometricalObject}.
     * This method can parse toString methods for {@link Line}, {@link Circle}, {@link FilledCircle}.
     * @param line Line which will be parsed.
     * @return Returns {@link GeometricalObject}.
     */
    private GeometricalObject createGeometricalObject(String line) {
        String[] data = line.split(" ");
        Color color;
        switch (data[0]) {
            case "LINE":
                color = new Color(Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]));
                return new Line(color,
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]));
            case "CIRCLE":
                color = new Color(Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]));
                return new Circle(
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]),
                        color);
            case "FCIRCLE":
                color = new Color(Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]));
                Color areaColor = new Color(Integer.parseInt(data[7]), Integer.parseInt(data[8]), Integer.parseInt(data[9]));
                return new FilledCircle(
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]),
                        color,
                        areaColor);
            case "FTRIANGLE":
                Color outlineColor = new Color(Integer.parseInt(data[7]), Integer.parseInt(data[8]), Integer.parseInt(data[9]));
                Color fillColor = new Color(Integer.parseInt(data[10]), Integer.parseInt(data[11]), Integer.parseInt(data[12]));
                return new FilledTriangle(
                        new Point(Integer.parseInt(data[1]),
                                Integer.parseInt(data[2])),
                        new Point(Integer.parseInt(data[3]),
                                Integer.parseInt(data[4])),
                        new Point(Integer.parseInt(data[5]),
                                Integer.parseInt(data[6])),
                        outlineColor,
                        fillColor);
            default:
                throw new IllegalArgumentException("Geometrical object " + data[0] + "is not supported.");
        }
    }

    /**
     * Action models save as behaviour for saving raw data in a .jvd file using toString methods from {@link GeometricalObject}.
     */
    private final Action saveAsJVD = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Save document");
            if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(JVDraw.this, "File is not saved", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                source = Paths.get(jfc.getSelectedFile().toPath().toString() + ".jvd");
                Files.writeString(source, geometricalObjectsToString());
                drawingModel.clearModifiedFlag();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(JVDraw.this, "Unable to save file.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    };

    /**
     * Action models saving data to existing file if set, otherwise delegate action to saveAsJvd Action
     */
    private final Action saveJVD = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (source == null) {
                saveAsJVD.actionPerformed(e);
                return;
            }
            try {
                Files.writeString(source, geometricalObjectsToString());
                drawingModel.clearModifiedFlag();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(JVDraw.this, "Unable to save file.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    };

    /**
     * Action which models exiting behaviour.
     * If changes were not saved it will prompt user to save them.
     * After that it will prompt user whether it is sure to close.
     */
    private final Action exit = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (drawingModel.isModified() &&
                    JOptionPane.showConfirmDialog(JVDraw.this, "Do you want to save this image?", "Save?", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                saveJVD.actionPerformed(e);
            }
            if (JOptionPane.showConfirmDialog(JVDraw.this, "Do you want to exit?", "Exit?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
    };

    /**
     * Action which calls method for exporting jpg image.
     */
    private final Action exportJPG = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            exportImage("jpg");
        }
    };
    /**
     * Action which calls method for exporting png image.
     */
    private final Action exportPNG = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            exportImage("png");
        }
    };
    /**
     * Action which calls method for exporting gif image.
     */
    private final Action exportGIF = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            exportImage("gif");
        }
    };

    /**
     * Exports image created from {@link DrawingModel} with an extension given in the arguments.
     * @param extension Extension of a file. Will be used in deciding format of an image.
     */
    private void exportImage(String extension) {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Export image as " + extension);
        if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(JVDraw.this, "File is not exported", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
        for (int i = 0; i < drawingModel.getSize(); i++) {
            drawingModel.getObject(i).accept(bbcalc);
        }
        Rectangle box = bbcalc.getBoundingBox();
        BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = image.createGraphics();
        g.translate(-box.x, -box.y);
        GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
        for (int i = 0; i < drawingModel.getSize(); i++) {
            drawingModel.getObject(i).accept(painter);
        }
        g.dispose();
        try {
            ImageIO.write(image, extension, new File(jfc.getSelectedFile().toString() + "." + extension));
            JOptionPane.showMessageDialog(JVDraw.this, "Image has been successfully exported", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(JVDraw.this,
                    "Export failed",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Returns geometrical objects as a single string divided in lines.
     * Each line represents single {@link GeometricalObject}
     * Order is preserved.
     * @return Returns string representation of geometrical objects.
     */
    private String geometricalObjectsToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < drawingModel.getSize(); i++) {
            sb.append(drawingModel.getObject(i).toString());
            sb.append("\n");
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Initializes list which shows existing objects which are drwan on the canvas.
     */
    private void initList() {
        ListModel list = new DrawingObjectListModel(drawingModel);
        JList<GeometricalObject> jList = new JList<GeometricalObject>(list);
        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showEditor(jList);
                }
            }
        });
        jList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (jList.getSelectedIndex() == -1) {
                    return;
                }
                if (e.getKeyChar() == '+') {
                    drawingModel.changeOrder(drawingModel.getObject(drawingModel.getSize() - jList.getSelectedIndex() - 1), 1);
                    jList.setSelectedIndex(jList.getSelectedIndex() - 1);
                } else if (e.getKeyChar() == '-') {
                    drawingModel.changeOrder(drawingModel.getObject(drawingModel.getSize() - jList.getSelectedIndex() - 1), -1);
                    jList.setSelectedIndex(jList.getSelectedIndex() + 1);
                } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    drawingModel.remove(drawingModel.getObject(drawingModel.getSize() - jList.getSelectedIndex() - 1));
                }
            }
        });
        add(jList, BorderLayout.EAST);
    }

    /**
     * Editor is shown when mouse is double clicked.
     * @param jList JList from which data will be used.
     */
    private void showEditor(JList<GeometricalObject> jList) {
        GeometricalObject geometricalObject = drawingModel.getObject(drawingModel.getSize() - jList.getSelectedIndex() - 1);
        GeometricalObjectEditor editor = geometricalObject.createGeometricalObjectEditor();
        if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Editor", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                editor.checkEditing();
                editor.acceptEditing();
            } catch (Exception ex) {
                if (ex instanceof EditingException) {
                    JOptionPane.showMessageDialog(this, "Invalid numbers", "Invalid", JOptionPane.ERROR_MESSAGE);
                    showEditor(jList);
                }
            }
        }
        drawingCanvas.repaint();
    }

    /**
     * Initalizes toolbar.
     * @param panel Adds toolbar to a given panel
     */
    private void initToolBar(JPanel panel) {
        JToolBar toolbar = new JToolBar();

        JColorArea fgColor = new JColorArea(Color.BLUE);
        fgColor.addColorChangeListener((source, oldColor, newColor) -> repaint());
        toolbar.addSeparator();
        JColorArea bgColor = new JColorArea(Color.RED);
        bgColor.addColorChangeListener((source, oldColor, newColor) -> repaint());
        toolbar.addSeparator();
        toolbar.add(fgColor, BorderLayout.NORTH);
        toolbar.add(bgColor, BorderLayout.NORTH);


        this.add(new ColorJLabel(fgColor, bgColor), BorderLayout.SOUTH);

        ButtonGroup buttonGroup = new ButtonGroup();
        JToggleButton lineButton = new JToggleButton("Line");
        lineButton.addActionListener(l -> currentState = new LineState(drawingModel, fgColor, drawingCanvas));
        JToggleButton circleButton = new JToggleButton("Circle");
        circleButton.addActionListener(l -> currentState = new CircleState(drawingModel, fgColor, drawingCanvas));
        JToggleButton filledCircleButton = new JToggleButton("Filled circle");
        filledCircleButton.addActionListener(l -> currentState = new FilledCircleState(drawingModel, fgColor, bgColor, drawingCanvas));

        JToggleButton filledTriangleButton = new JToggleButton("Triangle button");
        filledTriangleButton.addActionListener(l -> currentState = new FilledTriangleState(drawingModel, fgColor, bgColor, drawingCanvas));


        buttonGroup.add(lineButton);
        buttonGroup.add(circleButton);
        buttonGroup.add(filledCircleButton);
        buttonGroup.add(filledTriangleButton);
        buttonGroup.setSelected(lineButton.getModel(), true);
        currentState = new LineState(drawingModel, fgColor, drawingCanvas);

        toolbar.add(lineButton);
        toolbar.add(circleButton);
        toolbar.add(filledCircleButton);
        toolbar.add(filledTriangleButton);

        panel.add(toolbar, BorderLayout.SOUTH);
    }

    /**
     * Executes JVDraw.
     * @param args No arguments are used.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));
    }
}
