package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.*;

/**
 * Interface models color providers with support for registration and de-registration of listeners.
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface IColorProvider {
    /**
     * Gets current color from this provider.
     * @return Returns current color
     */
    public Color getCurrentColor();

    /**
     * Register given listener.
     * Listener will be notified on color change.
     * @param l Listener which will be added to collection of listeners.
     */
    public void addColorChangeListener(ColorChangeListener l);

    /**
     * Removes given listener from inner collection of listeners.
     * @param l Listnener which will be removed.
     */
    public void removeColorChangeListener(ColorChangeListener l);
}
