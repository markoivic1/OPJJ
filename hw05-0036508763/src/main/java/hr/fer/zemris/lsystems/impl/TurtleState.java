package hr.fer.zemris.lsystems.impl;

import java.awt.*;

/**
 * Defines a turtle state.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class TurtleState {
    /**
     * Turtle's position of a turtle.
     */
    private Vector2D currentPosition;
    /**
     * Turtle's angle.
     */
    private Vector2D turtleAngle;
    /**
     * Turtle's color.
     */
    private Color turtleColor;
    /**
     * Turtle's effective length.
     */
    private double effectiveLength;

    /**
     * Constructor takes given arguments and stores them in the variables.
     * @param currentPosition Position of a turtle.
     * @param turtleAngle Angle of a turtle.
     * @param turtleColor Color of a turtle.
     * @param effectiveLength Effective length of a turtle.
     */
    public TurtleState(Vector2D currentPosition, Vector2D turtleAngle, Color turtleColor, double effectiveLength) {
        this.currentPosition = currentPosition;
        this.turtleAngle = turtleAngle;
        this.turtleColor = turtleColor;
        this.effectiveLength = effectiveLength;
    }

    /**
     * Getter for current position.
     * @return Returns current position.
     */
    public Vector2D getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Setter for the current position.
     * @param currentPosition position to which the turtle will be set to.
     */
    public void setCurrentPosition(Vector2D currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * Getter for the turtle's angle.
     * @return Returns turtle angle.
     */
    public Vector2D getTurtleAngle() {
        return turtleAngle;
    }

    /**
     * Setter for the turtle's angle
     * @param turtleAngle Sets turtle's angle to a given value in degrees.
     */
    public void setTurtleAngle(double turtleAngle) {
        this.turtleAngle.rotate(turtleAngle);
    }

    /**
     * Getter for turtle's color.
     * @return Returns turtle's color.
     */
    public Color getTurtleColor() {
        return turtleColor;
    }

    /**
     * Setter for a turtle's color.
     * @param turtleColor Sets turtle's color to a given color.
     */
    public void setTurtleColor(Color turtleColor) {
        this.turtleColor = turtleColor;
    }

    /**
     * Setter for turtle's effective length.
     * @param effectiveLength Sets turtle's effective length to a given value.
     */
    public void setEffectiveLength(double effectiveLength) {
        this.effectiveLength = effectiveLength;
    }

    /**
     * Getter for effective length.
     * @return Returns turtle's effective length.
     */
    public double getEffectiveLength() {
        return effectiveLength;
    }

    /**
     * Creates a new {@link TurtleState} with every element copied so they can be completely independent.
     * @return Returns copies {@link TurtleState}.
     */
    public TurtleState copy() {
        return new TurtleState(currentPosition.copy(), turtleAngle.copy(), new Color(turtleColor.getRGB()), effectiveLength);
    }

}
