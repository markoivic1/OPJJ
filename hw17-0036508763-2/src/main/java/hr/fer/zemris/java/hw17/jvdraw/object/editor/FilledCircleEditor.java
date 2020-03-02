package hr.fer.zemris.java.hw17.jvdraw.object.editor;

import hr.fer.zemris.java.hw17.jvdraw.object.FilledCircle;

import javax.swing.*;
import java.awt.*;

/**
 * Editor for filled circle.
 * Defines JPanel which supports change of parameters stored in a {@link FilledCircle} given in the constructor.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

    /**
     * Filled circle which will be used for editing.
     */
    private FilledCircle filledCircle;
    /**
     * TextField containing x coordinate.
     */
    private JTextField tfXCoordinate;
    /**
     * TextField containing y coordinate.
     */
    private JTextField tfYCoordinate;
    /**
     * TextField containing radius.
     */
    private JTextField tfRadius;
    /**
     * TextField containing fill color
     */
    private JTextField tfColor;

    /**
     * Constructor.
     * @param filledCircle Used in editing.
     */
    public FilledCircleEditor(FilledCircle filledCircle) {
        this.filledCircle = filledCircle;
        initGUI();
    }

    /**
     * Initializes layout and components.
     */
    private void initGUI() {
        this.setLayout(new GridLayout(4, 2));
        this.add(new Label("Center x coordinate: "));
        tfXCoordinate = new JTextField();
        tfXCoordinate.setText(String.valueOf(filledCircle.getxCoordinate()));
        this.add(tfXCoordinate);
        this.add(new Label("Center y coordinate: "));
        tfYCoordinate = new JTextField();
        tfYCoordinate.setText(String.valueOf(filledCircle.getyCoordinate()));
        this.add(tfYCoordinate);
        this.add(new Label("Radius: "));
        tfRadius = new JTextField();
        tfRadius.setText(String.valueOf(filledCircle.getRadius()));
        this.add(tfRadius);
        this.add(new Label("Color: "));
        tfColor = new JTextField();
        tfColor.setText(Integer.toHexString(filledCircle.getAreaColor().getRGB()).substring(2));
        this.add(tfColor);
    }

    @Override
    public void checkEditing() {
        try {
            int radius = Integer.parseInt(tfRadius.getText());
            Integer.parseInt(tfXCoordinate.getText());
            Integer.parseInt(tfYCoordinate.getText());
            Color.decode("0x" + tfColor.getText());
            if (radius <= 0) {
                throw new EditingException("Radius must be greater than 0.");
            }
        } catch (Exception e) {
            throw new EditingException("Invalid coordinates or radius.");
        }
    }

    @Override
    public void acceptEditing() {
        filledCircle.setxCoordinate(Integer.parseInt(tfXCoordinate.getText()));
        filledCircle.setyCoordinate(Integer.parseInt(tfYCoordinate.getText()));
        filledCircle.setRadius(Integer.parseInt(tfRadius.getText()));
        filledCircle.setAreaColor(Color.decode("0x" + tfColor.getText()));
    }
}
