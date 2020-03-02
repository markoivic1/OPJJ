package hr.fer.zemris.math;

import java.util.Objects;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
/**
 * Vector class defined in two dimensions.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Vector2D {
    /**
     * Precision used in equals method as an error when comparing two doubles.
     */
    private static final double PRECISION = 0.000001;

    /**
     * X coordinate.
     */
    private double x;
    /**
     * Y coordinate.
     */
    private double y;

    /**
     * Constructor which takes x and y coordinate.
     * @param x X coordinate.
     * @param y Y coordinate.
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X coordinate.
     * @return Returns the X coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the Y coordinate.
     * @return Returns the Y coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Translates vector by value from a given offset vector.
     * @param offset Vector which is used to translate this vector.
     */
    public void translate(Vector2D offset) {
        this.x += offset.getX();
        this.y += offset.getY();
    }

    /**
     * New {@link Vector2D} is made when this vector is translated by a given offseet.
     * @param offset Vector which is used to translate this vector.
     * @return Returns new translated {@link Vector2D}.
     */
    public Vector2D translated(Vector2D offset) {
        return new Vector2D(this.getX() + offset.getX(), this.getY() + offset.getY());
    }

    /**
     * Rotates the vector by the angle given in radians.
     * @param angle Angle given in radians.
     */
    public void rotate(double angle) {
        double oldX = x;
        double oldY = y;
        this.x = cos(angle)*oldX - sin(angle)*oldY;
        this.y = sin(angle)*oldX + cos(angle)*oldY;
    }

    /**
     * New {@link Vector2D} is made using this vector rotated by the angle.
     * @param angle Angle in radians.
     * @return Returns new rotated {@link Vector2D}.
     */
    public Vector2D rotated(double angle) {
        double newX = cos(angle)*x - sin(angle)*y;
        double newY = sin(angle)*x + cos(angle)*y;
        return new Vector2D(newX, newY);
    }

    /**
     * Rescales the vector by the given scaler.
     * @param scaler Scaler by which the vector is scaled.
     */
    public void scale(double scaler) {
        this.x *= scaler;
        this.y *= scaler;
    }

    /**
     * New {@link Vector2D} is made from this vector scaled to a given scaler.
     * @param scaler Scaler by which the vector is scaled.
     * @return Retuns the new scaled {@link Vector2D}.
     */
    public Vector2D scaled(double scaler) {
        return new Vector2D(x*scaler, y*scaler);
    }

    /**
     * Copies existing properties of this vector to a new one.
     * @return Returns new vector with same properties as this one.
     */
    public Vector2D copy() {
        return new Vector2D(this.x, this.y);
    }

    /**
     * Equals compares x and y coordinates of this and a given vector.
     * @param o Other object which is compared.
     * @return Returns {@code true} if the x and y coordinates are the same, returns {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return (Math.abs(x - vector2D.getX()) < PRECISION) &&
                (Math.abs(y - vector2D.getY()) < PRECISION);
    }

    /**
     * Proper hash function implementation.
     * It uses x and y coordinate to hash.
     * @return Returns hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
