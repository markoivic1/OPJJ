package hr.fer.zemris.java.gui.charts;

/**
 * Defines class that holds the pair of x and y values.
 * Presumably used in coordinate system.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class XYValue {
    /**
     * x value
     */
    private int x;
    /**
     * y value
     */
    private int y;

    /**
     * Constructor.
     * @param x x value
     * @param y y value
     */
    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x value.
     * @return Returns x.
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for y value.
     * @return Returns y.
     */
    public int getY() {
        return y;
    }
}
