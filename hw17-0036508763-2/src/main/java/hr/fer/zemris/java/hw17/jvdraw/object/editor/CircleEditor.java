package hr.fer.zemris.java.hw17.jvdraw.object.editor;

import hr.fer.zemris.java.hw17.jvdraw.object.Circle;

import javax.swing.*;
import java.awt.*;

/**
 * Editor for circle.
 * Defines JPanel which supports change of parameters stored in a {@link Circle} given in the constructor.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */

public class CircleEditor extends GeometricalObjectEditor {

    /**
     * Circle which will be used for getting and setting new data.
     */
    private Circle circle;
    /**
     * Textfield which stores x coordinate.
     */
    private JTextField tfXCoordinate;
    /**
     * TextField which stores y coordinate.
     */
    private JTextField tfYCoordinate;
    /**
     * TextField which stores radius.
     */
    private JTextField tfRadius;

    /**
     * Constructor
     * @param circle Circle which will be changed.
     */
    public CircleEditor(Circle circle) {
        this.circle = circle;
        initGUI();
    }

    /**
     * Initializes layout and components.
     */
    private void initGUI() {
        this.setLayout(new GridLayout(3, 2));
        this.add(new Label("Center x coordinate: "));
        tfXCoordinate = new JTextField();
        tfXCoordinate.setText(String.valueOf(circle.getxCoordinate()));
        this.add(tfXCoordinate);
        this.add(new Label("Center y coordinate: "));
        tfYCoordinate = new JTextField();
        tfYCoordinate.setText(String.valueOf(circle.getyCoordinate()));
        this.add(tfYCoordinate);
        this.add(new Label("Radius: "));
        tfRadius = new JTextField();
        tfRadius.setText(String.valueOf(circle.getRadius()));
        this.add(tfRadius);
    }

    @Override
    public void checkEditing() {
        try {
            int radius = Integer.parseInt(tfRadius.getText());
            int xCoor = Integer.parseInt(tfXCoordinate.getText());
            int yCoor = Integer.parseInt(tfYCoordinate.getText());
            if (radius <= 0) {
                throw new EditingException("Radius must be greater than 0.");
            }
        } catch (NumberFormatException e) {
            throw new EditingException("Invalid coordinates or radius.");
        }
    }

    @Override
    public void acceptEditing() {
        circle.setxCoordinate(Integer.parseInt(tfXCoordinate.getText()));
        circle.setyCoordinate(Integer.parseInt(tfYCoordinate.getText()));
        circle.setRadius(Integer.parseInt(tfRadius.getText()));
    }
}
