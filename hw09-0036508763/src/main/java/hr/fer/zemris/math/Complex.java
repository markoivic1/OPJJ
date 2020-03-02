package hr.fer.zemris.math;

import static java.lang.Math.*;

/**
 * Class which defines complex numbers.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Complex {
    /**
     * Real part of a complex number.
     */
    private double real;
    /**
     * Imaginary part of a complex number.
     */
    private double imaginary;
    /**
     * Precision used when comparing two double values.
     */
    private static final double DECIMAL_PRECISION = 0.000001;

    /**
     * Predefined complex number with real part set to 0 and imaginary set to 0
     */
    public static final Complex ZERO = new Complex(0, 0);
    /**
     * Predefined complex number with real part set to 1 and imaginary set to 0
     */
    public static final Complex ONE = new Complex(1, 0);
    /**
     * Predefined complex number with real part set to -1 and imaginary set to 0
     */
    public static final Complex ONE_NEG = new Complex(-1, 0);
    /**
     * Predefined complex number with real part set to 0 and imaginary set to 1
     */
    public static final Complex IM = new Complex(0, 1);/**
     * Predefined complex number with real part set to 0 and imaginary set to -1
     */
    public static final Complex IM_NEG = new Complex(0, -1);

    /**
     * Constructor which takes real and imaginary part of a complex number.
     * @param re Real part.
     * @param im Imaginary part
     */
    public Complex(double re, double im) {
        this.real = re;
        this.imaginary = im;
    }

    /**
     * Method used for adding two complex numbers.
     *
     * @param c {@code Complex} which is being added to.
     * @return Returns sum
     */
    public Complex add(Complex c) {
        double realPart = getReal() + c.getReal();
        double imaginaryPart = getImaginary() + c.getImaginary();
        return new Complex(realPart, imaginaryPart);
    }

    /**
     * Subtracts given {@code Complex} from a {@code Complex} upon which this method is called
     *
     * @param c {@code Complex} used for subtraction
     * @return Returns new {@code Complex} which is the difference.
     */
    public Complex sub(Complex c) {
        double realPart = getReal() - c.getReal();
        double imaginaryPart = getImaginary() - c.getImaginary();
        return new Complex(realPart, imaginaryPart);
    }

    /**
     * Multiplication of two {@code Complex}s.
     *
     * @param c {@code Complex} which is being used as one of the factors.
     * @return Returns new {@code Complex} which is saved as a result of multiplication.
     */
    public Complex multiply(Complex c) {
        double realPart = getReal() * c.getReal() - getImaginary() * c.getImaginary();
        double imaginaryPart = getReal() * c.getImaginary() + getImaginary() * c.getReal();
        return new Complex(realPart, imaginaryPart);
    }

    /**
     * Division of {@code Complex}s.
     *
     * @param c {@code Complex} used as a divider.
     * @return returns new {@code Complex} which has been calculated.
     */
    public Complex divide(Complex c) {
        double a = getImaginary() * c.getReal() - getReal() * c.getImaginary();
        double realPart = (getReal() * c.getReal() + getImaginary() * c.getImaginary()) /
                (pow(c.getReal(), 2) + pow(c.getImaginary(), 2));
        double imaginaryPart = (getImaginary() * c.getReal() - getReal() * c.getImaginary()) /
                (pow(c.getReal(), 2) + pow(c.getImaginary(), 2));
        return new Complex(realPart, imaginaryPart);
    }

    /**
     * Calculates the power of a {@code Complex}.
     *
     * @param n {@code integer} n represents the power. Power must be a positive {@code integer}.
     * @return returns the n-th power of a {@code Complex}
     */
    public Complex power(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        double magnitude = pow(module(), n);
        double angle = getAngle() * n;
        return Complex.fromMagnitudeAndAngle(magnitude, angle);
    }

    /**
     * Calculates the root of a {@code Complex}.
     *
     * @param n {@code integer} n represents the n-th root of a {@code Complex}. Root must be an {@code integer} greater than '.
     * @return Returns an array of {@code Complex} with calculated n-th roots.
     */
    public Complex[] root(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        Complex[] rootsOfComplex = new Complex[n];
        double magnitude = pow(module(), 1.0 / n);
        for (int k = 0; k < n; k++) {
            double realPart = magnitude * cos((getAngle() + 2 * k * PI) / 2);
            double imaginaryPart = magnitude * sin((getAngle() + 2 * k * PI) / 2);
            rootsOfComplex[k] = new Complex(realPart, imaginaryPart);
        }
        return rootsOfComplex;
    }

    /**
     * Method used to invert complex number
     * @return new Complex number with inverted values.
     */
    public Complex negate() {
        return new Complex(-1 * real, -1 * imaginary);
    }

    /**
     * Calculates module of a complex number.
     * @return Returns module
     */
    public double module() {
        return sqrt(pow(real, 2) + pow(imaginary, 2));
    }

    /**
     * Calculates an angle of real part and imaginary part.
     * @return Returns angle.
     */
    private double getAngle() {
        Double angle = atan2(imaginary, real);
        if (angle < 0) {
            angle += 2 * PI;
        }
        return angle;
    }

    /**
     * Calculates complex number from magnitude and angle.
     * @param magnitude Magnitude.
     * @param angle Angle
     * @return Returns new Complex number calculated from given values.
     */
    public static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
        if (magnitude == 0) {
            throw new IllegalArgumentException();
        }
        return new Complex(magnitude * cos(angle), magnitude * sin(angle));
    }

    /**
     * Gets real part.
     * @return Returns real part.
     */
    public double getReal() {
        return real;
    }

    /**
     * Gets imaginary part.
     * @return Returns imaginary part.
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * Parses complex number from a given text.
     * @param s String which will be parsed.
     * @return Returns parsed complex number.
     */
    public static Complex parse(String s) {
        if (!s.contains("i")) {
            return new Complex(Double.parseDouble(s), 0);
        }
        // Edge cases
        s = s.replace(" ","");
        int indexAtWhichImaginaryStarts = 0;
        if (s.indexOf("+", 1) != - 1) {
            indexAtWhichImaginaryStarts = s.indexOf("+", 1);
        } else if (s.indexOf("-", 1) != - 1) {
            indexAtWhichImaginaryStarts = s.indexOf("-", 1);
        } else {
            String imaginaryPart = s.substring(indexAtWhichImaginaryStarts, s.length() - 1);
            if (imaginaryPart.equals("-")) {
                return new Complex(0, -1);
            } else if (imaginaryPart.equals("+") || s.equals("i")) {
                return new Complex(0, 1);
            }
            try {
                return new Complex(0, Double.parseDouble(imaginaryPart));
            } catch (NullPointerException ex) {
                throw new NumberFormatException();
            }
        }

        // Normal format
        String realPart = s.substring(0, indexAtWhichImaginaryStarts);
        String imaginaryPart = s.substring(indexAtWhichImaginaryStarts).replace("i","");
        if (imaginaryPart.equals("-")) {
            return new Complex(Double.parseDouble(realPart), -1);
        } else if (imaginaryPart.equals("+")) {
            return new Complex(Double.parseDouble(realPart), 1);
        }
        return new Complex(Double.parseDouble(realPart), Double.parseDouble(imaginaryPart));
    }

    /**
     * Returns complex number as a string.
     * @return Returns complex number as a string.
     */
    @Override
    public String toString() {
        String im = "";
        String re = "";
        if (Math.abs(imaginary) < DECIMAL_PRECISION) {
            im = "+i0.0";
        }
        if (Math.abs(real) < DECIMAL_PRECISION) {
            re = "0.0";
        }
        if (imaginary < 0 && im.equals("")) {
            im = "-i" + Math.abs(imaginary);
        } else if (imaginary > 0 && im.equals("")) {
            im = "+i" + Math.abs(imaginary);
        }
        if (re.equals("")) {
            re = Double.toString(real);
        }
        return "(" + re + im + ")";
    }
}
