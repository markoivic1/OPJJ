package hr.fer.zemris.math;

/**
 * Defines 3 dimensional vector.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Vector3 {
    /**
     * x direction of a vector
     */
    private double x;
    /**
     * y direction of a vector.
     */
    private double y;
    /**
     * z direction of a vector.
     */
    private double z;

    /**
     * Initializes vector with given values.
     * @param x x direction.
     * @param y y direction.
     * @param z z direction.
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns vector's length.
     * @return Returns norm.
     */
    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Calculates normalized vector.
     * @return Returns normalized vector.
     */
    public Vector3 normalized() {
        double norm = norm();
        return new Vector3(x / norm, y / norm, z / norm);
    }

    /**
     * Adds two {@link Vector3}s.
     * @param other Other vector which is used in addition.
     * @return Returns new vector calculated.
     */
    public Vector3 add(Vector3 other) {
        return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
    }

    /**
     * Subtract given vector from this.
     * @param other Vector which will be used in calculation.
     * @return Returns new calculated vector.
     */
    public Vector3 sub(Vector3 other) {
        return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
    }

    /**
     * Calculates dot projection of two vectors.
     * @param other Other vector which is used in this calculation.
     * @return Returns dot projection's value.
     */
    public double dot(Vector3 other) {
        return x * other.getX() + y * other.getY() + z * other.getZ();
    }

    /**
     * Calculates vector scalar of given vector and this one.
     * @param other Other vector which is used in calculation.
     * @return Returns new calculated vector.
     */
    public Vector3 cross(Vector3 other) {
        return new Vector3(y * other.getZ() - z * other.getY(),
                z * getX() - x * other.getZ(),
                x * other.getY() - y * other.getX());
    }

    /**
     * Scales this vector for a given value.
     * @param s Value for which this vector will be scaled.
     * @return Returns new scaled vector.
     */
    public Vector3 scale(double s) {
        return new Vector3(x*s, y*s, z*s);
    }

    /**
     * Returns cos angle of given vector and this one.
     * @param other Other vector which is used in calculation.
     * @return Returns calculated cos angle.
     */
    public double cosAngle(Vector3 other) {
        return dot(other) / (norm() * other.norm());
    }

    /**
     * Returns vector as an array of doubles.
     * @return Returns array of doubles.
     */
    public double[] toArray() {
        return new double[] {x,y,z};
    }

    /**
     * String format of a vector.
     * @return Returns vector's value in a string format.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    /**
     * Gets x direction.
     * @return Returns x direction.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets y direction.
     * @return Returns y direction.
     */
    public double getY() {
        return y;
    }

    /**
     * Gets z direction.
     * @return Returns z direction.
     */
    public double getZ() {
        return z;
    }
}
