package hr.fer.zemris.java.hw17.jvdraw.object.editor;

import javax.swing.*;

/**
 * Abstract class which models {@link hr.fer.zemris.java.hw17.jvdraw.object.GeometricalObject} editor.
 */
public abstract class GeometricalObjectEditor extends JPanel {
    /**
     * Checks data whether it is valid.
     * If data is invalid it will throw {@link EditingException}.
     */
    public abstract void checkEditing();

    /**
     * Given data will be stored in a {@link hr.fer.zemris.java.hw17.jvdraw.object.GeometricalObject}.
     */
    public abstract void acceptEditing();
}