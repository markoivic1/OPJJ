/**
 * Package that defines class ComplexNumber and it's test class.
 * Package contains subpackage called demo which is used for demonstration.
 */
package hr.fer.zemris.java.hw02;

import static java.lang.Math.*;

/**
 * Complex numbers. In form of a + bi.
 *
 * This class defines some of the elementary methods used with complex numbers.
 * Some math operations for complex numbers are different to those of real numbers.
 *
 */
public class ComplexNumber {

    private final double real;
    private final double imaginary;

    /**
     * Constructor takes real and imaginary value and initializes an object.
     * @param real Real part of the complex number.
     * @param imaginary Imaginary part of the complex number.
     */
    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Static method used for defining complex numbers that contain only real part.
     * @param real Real part of a complex number. (eg. 5)
     * @return return {@code ComplexNumber} which is initialized with given value
     */
    public static ComplexNumber fromReal(double real) {
        return new ComplexNumber(real, 0);
    }

    /**
     * Static method used for defining complex numbers that contain only imaginary part.
     * @param imaginary Imaginary part of a complex namber. (eg. 4i)
     * @return Returns {@code ComplexNumber} object which is initialized with given value.
     */
    public static ComplexNumber fromImaginary(double imaginary) {
        return new ComplexNumber(0, imaginary);
    }

    /**
     * Complex numbers can be defined with a magnitude and an angle.
     * Given values will be converted to (a+bi) format.
     * @param magnitude Magnitude of a complex number (sqrt(a^2 + b^2))
     * @param angle An angle of a complex number (atan(b/a))
     * @return returns {@code ComplexNumber} converted to (a+bi) format
     * @throws IllegalArgumentException Thrown when magnitude is 0.
     */
    public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
        if (magnitude == 0) {
            throw new IllegalArgumentException();
        }
        return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
    }

    /**
     * Parses ComplexNumber from a given string.
     * @param s Complex number written as a string
     * @return Returns {@code ComplexNumber} which had been parsed from a given string.
     * @throws NumberFormatException Thrown when given string is not in a proper format.
     */
    public static ComplexNumber parse(String s) {
        if (!s.contains("i")) {
            return new ComplexNumber(Double.parseDouble(s), 0);
        }
        // Edge cases
        int indexAtWhichImaginaryStarts = 0;
        if (s.indexOf("+", 1) != - 1) {
            indexAtWhichImaginaryStarts = s.indexOf("+", 1);
        } else if (s.indexOf("-", 1) != - 1) {
            indexAtWhichImaginaryStarts = s.indexOf("-", 1);
        } else {
            String imaginaryPart = s.substring(indexAtWhichImaginaryStarts, s.length() - 1);
            if (imaginaryPart.equals("-")) {
                return new ComplexNumber(0, -1);
            } else if (imaginaryPart.equals("+") || s.equals("i")) {
                return new ComplexNumber(0, 1);
            }
            try {
                return new ComplexNumber(0, Double.parseDouble(imaginaryPart));
            } catch (NullPointerException ex) {
                throw new NumberFormatException();
            }
        }

        // Normal format
        String realPart = s.substring(0, indexAtWhichImaginaryStarts);
        String imaginaryPart = s.substring(indexAtWhichImaginaryStarts, s.length() - 1);
        if (imaginaryPart.equals("-")) {
            return new ComplexNumber(Double.parseDouble(realPart), -1);
        } else if (imaginaryPart.equals("+")) {
            return new ComplexNumber(Double.parseDouble(realPart), 1);
        }
        return new ComplexNumber(Double.parseDouble(realPart), Double.parseDouble(imaginaryPart));
    }

    /**
     * Converts {@code ComplexNumber} to a string. (a+bi)
     * @return Returns complex number written as a string.
     */
    public String toString() {
        String imaginaryPartAsString;
        String realPartAsString = Double.toString(real);
        if (abs(imaginary - 1) < 1e-10) {
            imaginaryPartAsString = "+i";
        } else if (imaginary >= 0) {
            imaginaryPartAsString = "+" + Double.toString(imaginary) + "i";
        } else {
            imaginaryPartAsString = Double.toString(imaginary) + "i";
        }
        return realPartAsString + imaginaryPartAsString;
    }

    /**
     * Method used for adding two complex numbers.
     * @param c {@code ComplexNumber} which is being added to.
     * @return Returns sum
     */
    public ComplexNumber add(ComplexNumber c) {
        double realPart = getReal() + c.getReal();
        double imaginaryPart = getImaginary() + c.getImaginary();
        return new ComplexNumber(realPart, imaginaryPart);
    }

    /**
     * Subtracts given {@code ComplexNumber} from a {@code ComplexNumber} upon which this method is called
     * @param c {@code ComplexNumber} used for subtraction
     * @return Returns new {@code ComplexNumber} which is the difference.
     */
    public ComplexNumber sub(ComplexNumber c) {
        double realPart = getReal() - c.getReal();
        double imaginaryPart = getImaginary() - c.getImaginary();
        return new ComplexNumber(realPart, imaginaryPart);
    }

    /**
     * Multiplication of two {@code ComplexNumber}s.
     * @param c {@code ComplexNumber} which is being used as one of the factors.
     * @return Returns new {@code ComplexNumber} which is saved as a result of multiplication.
     */
    public ComplexNumber mul(ComplexNumber c) {
        double realPart = getReal()*c.getReal() - getImaginary()*c.getImaginary();
        double imaginaryPart = getReal()*c.getImaginary() + getImaginary()*c.getReal();
        return new ComplexNumber(realPart, imaginaryPart);
    }

    /**
     * Division of {@code ComplexNumber}s.
     * @param c {@code ComplexNumber} used as a divider.
     * @return returns new {@code ComplexNumber} which has been calculated.
     */
    public ComplexNumber div(ComplexNumber c) {
        double a = getImaginary()*c.getReal() - getReal()*c.getImaginary();
        double realPart = (getReal()*c.getReal() + getImaginary()*c.getImaginary()) /
                (pow(c.getReal(), 2) + pow(c.getImaginary(), 2));
        double imaginaryPart = (getImaginary()*c.getReal() - getReal()*c.getImaginary()) /
                (pow(c.getReal(), 2) + pow(c.getImaginary(), 2));
        return new ComplexNumber(realPart, imaginaryPart);
    }

    /**
     * Calculates the power of a {@code ComplexNumber}.
     * @param n {@code integer} n represents the power. Power must be a positive {@code integer}.
     * @return returns the n-th power of a {@code ComplexNumber}
     */
    public ComplexNumber power(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        double magnitude = pow(getMagnitude(), n);
        double angle = getAngle() * n;
        return ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);
    }

    /**
     * Calculates the root of a {@code ComplexNumber}.
     * @param n {@code integer} n represents the n-th root of a {@code ComplexNumber}. Root must be an {@code integer} greater than '.
     * @return Returns an array of {@code ComplexNumber} with calculated n-th roots.
     */
    public ComplexNumber[] root(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        ComplexNumber[] rootsOfComplexNumber = new ComplexNumber[n];
        double magnitude = pow(getMagnitude(), 1.0/n);
        for (int k = 0; k < n; k++) {
            double realPart = magnitude * cos((getAngle() + 2*k*PI) / 2);
            double imaginaryPart = magnitude * sin((getAngle() + 2*k*PI) / 2);
            rootsOfComplexNumber[k] = new ComplexNumber(realPart, imaginaryPart);
        }
        return rootsOfComplexNumber;
    }

    /**
     * Method used for getting the value of real part in a {@code ComplexNumber}.
     * @return Returns {@code double}.
     */
    public double getReal() {
        return real;
    }

    /**
     * Method used for getting the value of imaginary part in a {@code ComplexNumber}.
     * @return Returns {@code double}.
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * Calculates a magnitude of a {@code ComplexNumber}.
     * @return Returns {@code double}
     */
    public double getMagnitude() {
        return sqrt(pow(real,2) + pow(imaginary, 2));
    }

    /**
     * Calculates an angle of a {@code ComplexNumber}.
     * 1+i 0.7853981633974483
     * 1-i -0.7853981633974483
     * -1+i -0.7853981633974483
     * -1-i 0.7853981633974483
     * @return Returns {@code double}.
     */
    public double getAngle() {
        Double angle = atan2(imaginary, real);
        if (angle < 0) {
            angle += 2 * PI;
        }
        return angle;
    }
}
