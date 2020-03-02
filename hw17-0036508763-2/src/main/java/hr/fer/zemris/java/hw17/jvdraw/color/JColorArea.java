package hr.fer.zemris.java.hw17.jvdraw.color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Square area which will represent selected color.
 * When clicked on this area, JFileChooser will ask user to select new color.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class JColorArea extends JComponent implements IColorProvider {
    /**
     * Selected color
     */
    private Color selectedColor;
    /**
     * List of listeners.
     */
    private java.util.List<ColorChangeListener> listeners;

    /**
     * Constructor which takes initial color.
     * Registers mouse listener for this area which will monitor clicks on this area.
     * @param selectedColor Initial color
     */
    public JColorArea(Color selectedColor) {
        this.selectedColor = selectedColor;
        listeners = new ArrayList<>();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color oldColor = JColorArea.this.selectedColor;
                Color newColor = JColorChooser.showDialog(JColorArea.this, "Choose new color.", selectedColor);
                if (newColor != null) {
                    JColorArea.this.selectedColor = newColor;
                    notifyListeners(oldColor);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    /**
     * Paints area with currentyl selected color.
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getCurrentColor());
        g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
    }

    /**
     * Notifies listeners when color is changed.
     * @param oldColor Old color
     */
    private void notifyListeners(Color oldColor) {
        for (ColorChangeListener listener : listeners) {
            listener.newColorSelected(this, oldColor, this.selectedColor);
        }
    }

    @Override
    public Color getCurrentColor() {
        return selectedColor;
    }

    @Override
    public void addColorChangeListener(ColorChangeListener l) {
        listeners.add(l);
    }

    @Override
    public void removeColorChangeListener(ColorChangeListener l) {
        listeners.remove(l);
    }

    /**
     * Sets preferred size on fixed Dimension(15,15),
     * @return returns Dimension with 15 width and 15 height.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(15, 15);
    }

    /**
     * Sets maximum size on fixed Dimension(20,20),
     * @return returns Dimension with 20 width and 20 height.
     */
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(20, 20);
    }
}
