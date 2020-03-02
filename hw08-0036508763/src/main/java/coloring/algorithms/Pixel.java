package coloring.algorithms;

import java.util.Objects;

/**
 * Class which is used to define properties of a single pixel
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Pixel {
    /**
     * X coordinate of a pixel
     */
    private int x;
    /**
     * Y coordinate of a pixel
     */
    private int y;


    /**
     * Constructor which takes values and stores them accordingly.
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for X coordinate.
     * @return Returns X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for Y coordinate
     * @return Returns Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Returns pixel as in a string format
     * @return Pixel as a string.
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * {@inheritDoc}
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pixel pixel = (Pixel) o;
        return x == pixel.x &&
                y == pixel.y;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
