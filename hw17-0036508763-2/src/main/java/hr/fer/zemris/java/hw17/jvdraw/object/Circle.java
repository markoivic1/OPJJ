package hr.fer.zemris.java.hw17.jvdraw.object;

import hr.fer.zemris.java.hw17.jvdraw.object.editor.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.object.editor.GeometricalObjectEditor;

import java.awt.*;

/**
 * Circle representation in {@link GeometricalObject}.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Circle extends GeometricalObject {

    /**
     * Center x coordinate.
     */
    private int xCoordinate;
    /**
     * Center y coordinate.
     */
    private int yCoordinate;
    /**
     * Radius of a circle
     */
    private int radius;
    /**
     * Color of an outline for this circle
     */
    private Color color;

    /**
     * Constructor
     * @param xCoordinate center x coordinate
     * @param yCoordinate center y coordinate
     * @param radius radius of a circle
     * @param color color of an outline.
     */
    public Circle(int xCoordinate, int yCoordinate, int radius, Color color) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.radius = radius;
        this.color = color;
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new CircleEditor(this);
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return "CIRCLE " + xCoordinate + " " + yCoordinate + " " + radius + " "+
                color.getRed() + " " + color.getGreen() + " " + color.getBlue();
    }

    /**
     * Setter for x coordinate
     * @param xCoordinate Sets x coordinate.
     */
    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * Setter for y coordinate
     * @param yCoordinate Sets y coordinate.
     */
    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    /**
     * Setter for radius
     * @param radius Sets radius.
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Getter for center x coordinate
     * @return Returns x coordinate
     */
    public int getxCoordinate() {
        return xCoordinate;
    }

    /**
     * Getter for center y coordinate
     * @return returns y coordinate.
     */
    public int getyCoordinate() {
        return yCoordinate;
    }

    /**
     * Getter for radius of a circle
     * @return Returns radius.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Getter for color of a circle
     * @return Returns color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter for a x coordinate of a first point of a square which surrounds this circle
     * @return Returns x start
     */
    public int getxStart() {
        return xCoordinate - radius;
    }

    /**
     * Getter for a y coordinate of a first point of a square which surrounds this circle
     * @return Returns y start
     */
    public int getyStart() {
        return yCoordinate - radius;
    }

    /**
     * Getter for a x coordinate of a diagonal point of a square which surrounds this circle
     * @return Returns x end
     */
    public int getxEnd() {
        return xCoordinate + radius;
    }

    /**
     * Getter for a y coordinate of a diagonal point of a square which surrounds this circle
     * @return Returns y end
     */
    public int getyEnd() {
        return yCoordinate + radius;
    }
}
