package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.*;

/**
 * Models listener which will listens fora a color change.
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface ColorChangeListener {
    /**
     * Executed when listeners are notified.
     * @param source Source of a color
     * @param oldColor Old color
     * @param newColor New color
     */
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
