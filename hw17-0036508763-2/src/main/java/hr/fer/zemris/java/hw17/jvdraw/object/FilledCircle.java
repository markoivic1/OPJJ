package hr.fer.zemris.java.hw17.jvdraw.object;

import hr.fer.zemris.java.hw17.jvdraw.object.editor.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.object.editor.GeometricalObjectEditor;

import java.awt.*;

/**
 * Filled circle representation of a {@link GeometricalObject}.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class FilledCircle extends GeometricalObject {

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
     * Color of an outline
     */
    private Color outlineColor;
    /**
     * Color of an area.
     */
    private Color areaColor;

    /**
     * Constructor.
     * @param xCoordinate center x coordinate
     * @param yCoordinate center y coordinate
     * @param radius radius of a circle
     * @param outlineColor color of an outline
     * @param areaColor color of an area
     */
    public FilledCircle(int xCoordinate, int yCoordinate, int radius, Color outlineColor, Color areaColor) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.radius = radius;
        this.outlineColor = outlineColor;
        this.areaColor = areaColor;
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new FilledCircleEditor(this);
    }



    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return "FCIRCLE " + xCoordinate + " " + yCoordinate + " " + radius + " " +
                outlineColor.getRed() + " " + outlineColor.getGreen() + " " + outlineColor.getBlue() +
                " " + areaColor.getRed() + " " + areaColor.getGreen() + " " + areaColor.getBlue();
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
     * Sets area color
     * @param areaColor new area color
     */
    public void setAreaColor(Color areaColor) {
        this.areaColor = areaColor;
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
     * Getter for radius
     * @return Returns radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Gets outline color
     * @return Returns outline color.
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    public Color getAreaColor() {
        return areaColor;
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
