package hr.fer.zemris.java.hw17.jvdraw.object;

import hr.fer.zemris.java.hw17.jvdraw.object.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.object.editor.LineEditor;

import java.awt.*;

/**
 * Line representation of {@link GeometricalObject}.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Line extends GeometricalObject {

    /**
     * starting x coordinate for a line.
     */
    private int xStart;
    /**
     * starting y coordinate for a line.
     */
    private int yStart;
    /**
     * ending x coordinate for a line.
     */
    private int xEnd;
    /**
     * ending y coordinate for a line.
     */
    private int yEnd;
    /**
     * Color of a line.
     */
    private Color color;

    /**
     * Constructor
     * @param color Color of a circle
     * @param xStart starting x coordinate for a line.
     * @param yStart starting y coordinate for a line.
     * @param xEnd ending x coordinate for a line.
     * @param yEnd ending y coordinate for a line.
     */
    public Line(Color color, int xStart, int yStart, int xEnd, int yEnd) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.color = color;
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new LineEditor(this);
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return "LINE " + xStart + " " + yStart + " " + xEnd + " " + yEnd + " " +
                color.getRed() + " " + color.getGreen() + " " + color.getBlue();
    }

    /**
     * Getter for color
     * @return Returns color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter for starting x coordinate.
     * @return returns x start
     */
    public int getxStart() {
        return xStart;
    }

    /**
     * Getter for starting y coordinate.
     * @return returns y start
     */
    public int getyStart() {
        return yStart;
    }

    /**
     * Getter for ending x coordinate.
     * @return returns x end
     */
    public int getxEnd() {
        return xEnd;
    }

    /**
     * Getter for ending y coordinate.
     * @return returns y end.
     */
    public int getyEnd() {
        return yEnd;
    }

    /**
     * Sets x start
     * @param xStart new x start coordinate
     */
    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    /**
     * Sets y start
     * @param yStart new y start coordinate.
     */
    public void setyStart(int yStart) {
        this.yStart = yStart;
    }

    /**
     * Sets x end.
     * @param xEnd ending x coordinate.
     */
    public void setxEnd(int xEnd) {
        this.xEnd = xEnd;
    }

    /**
     * Sets y end.
     * @param yEnd ending y coordinate.
     */
    public void setyEnd(int yEnd) {
        this.yEnd = yEnd;
    }
}
