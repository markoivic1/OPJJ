package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Interface which will monitor mouse activity.
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface Tool {
    /**
     * Registers when mouse is pressed.
     * @param e Mouse event
     */
    public void mousePressed(MouseEvent e);
    /**
     * Registers when mouse is released.
     * @param e Mouse event
     */
    public void mouseReleased(MouseEvent e);
    /**
     * Registers when mouse is clicked.
     * @param e Mouse event
     */
    public void mouseClicked(MouseEvent e);
    /**
     * Registers when mouse is moved.
     * @param e Mouse event
     */
    public void mouseMoved(MouseEvent e);
    /**
     * Registers when mouse is dragged.
     * @param e Mouse event
     */
    public void mouseDragged(MouseEvent e);

    /**
     * Request paint for data currently stored.
     * @param g2d Graphics in which this method will draw.
     */
    public void paint(Graphics2D g2d);
}
