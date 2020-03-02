package hr.fer.zemris.java.hw17.jvdraw.object.editor;

import hr.fer.zemris.java.hw17.jvdraw.object.Line;

import javax.swing.*;
import java.awt.*;

/**
 * Editor for {@link Line}.
 * Defines JPanel which supports change of parameters stored in a {@link Line} given in the constructor.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class LineEditor extends GeometricalObjectEditor{
    /**
     * Line
     */
    private Line line;
    /**
     * TextField containing x start coordinate
     */
    private JTextField tfXStart;
    /**
     * TextField containing y start coordinate
     */
    private JTextField tfYStart;
    /**
     * TextField containing x end coordinate
     */
    private JTextField tfXEnd;
    /**
     * TextField containing y end coordinate
     */
    private JTextField tfYEnd;

    /**
     * Constructor.
     * @param line Used in editing
     */
    public LineEditor(Line line) {
        this.line = line;
        initGUI();
    }

    /**
     * Initializes JPanel with fields for data entry.
     */
    private void initGUI() {
        this.setLayout(new GridLayout(4, 2));
        this.add(new Label("First x coordinate: "));
        tfXStart = new JTextField();
        tfXStart.setText(String.valueOf(line.getxStart()));
        this.add(tfXStart);
        this.add(new Label("First y coordinate: "));
        tfYStart = new JTextField();
        tfYStart.setText(String.valueOf(line.getyStart()));
        this.add(tfYStart);
        this.add(new Label("Second x coordinate: "));
        tfXEnd = new JTextField();
        tfXEnd.setText(String.valueOf(line.getxEnd()));
        this.add(tfXEnd);
        this.add(new Label("Second y coordinate: "));
        tfYEnd = new JTextField();
        tfYEnd.setText(String.valueOf(line.getyEnd()));
        this.add(tfYEnd);
    }

    @Override
    public void checkEditing(){
        try {
            Integer.parseInt(tfXStart.getText());
            Integer.parseInt(tfYStart.getText());
            Integer.parseInt(tfXEnd.getText());
            Integer.parseInt(tfYEnd.getText());
        } catch (NumberFormatException e) {
            throw new EditingException("Invalid numbers were given");
        }
    }

    @Override
    public void acceptEditing() {
        line.setxStart(Integer.parseInt(tfXStart.getText()));
        line.setyStart(Integer.parseInt(tfYStart.getText()));
        line.setxEnd(Integer.parseInt(tfXEnd.getText()));
        line.setyEnd(Integer.parseInt(tfYEnd.getText()));
    }
}
