package hr.fer.zemris.java.hw17.jvdraw.object.editor;

import hr.fer.zemris.java.hw17.jvdraw.object.FilledTriangle;

import javax.swing.*;
import java.awt.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
public class FilledTriangleEditor extends GeometricalObjectEditor {

    private FilledTriangle filledTriangle;
    private JTextField tfOutlineColor;
    private JTextField tfFillColor;

    public FilledTriangleEditor(FilledTriangle filledTriangle) {
        this.filledTriangle = filledTriangle;
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new GridLayout(42, 2));
        this.add(new Label("Outline Color: "));
        tfOutlineColor = new JTextField();
        tfOutlineColor.setText(Integer.toHexString(filledTriangle.getOutlineColor().getRGB()).substring(2));
        this.add(tfOutlineColor);

        this.add(new Label("Fill Color: "));
        tfFillColor = new JTextField();
        tfFillColor.setText(Integer.toHexString(filledTriangle.getFillColor().getRGB()).substring(2));
        this.add(tfFillColor);
    }

    @Override
    public void checkEditing() {
        try {
            Color.decode("0x" + tfOutlineColor.getText());
            Color.decode("0x" + tfFillColor.getText());
        } catch (Exception e) {
            throw new EditingException("Invalid coordinates or radius.");
        }
    }

    @Override
    public void acceptEditing() {
        filledTriangle.setFillColor(Color.decode("0x" + tfFillColor.getText()));
        filledTriangle.setOutlineColor(Color.decode("0x" + tfOutlineColor.getText()));
    }
}
