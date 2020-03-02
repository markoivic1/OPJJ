package hr.fer.zemris.math;

/**
 * Defines complex rooted polynomial representation.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ComplexRootedPolynomial {

    /**
     * Constant of a given polynomial.
     */
    private Complex constant;
    /**
     * Roots of a polynomial.
     */
    private Complex[] roots;

    /**
     * Constructor which takes the constant and roots.
     * @param constant Constant which is multiplied with a polynomial.
     * @param roots Roots of a polynomial.
     */
    public ComplexRootedPolynomial(Complex constant, Complex... roots) {
        this.constant = constant;
        this.roots = roots;
    }

    /**
     * Computes polynomial value at given point z
     * @param z Point in which the polynomial will be calculated.
     * @return Returns calculated point
     */
    public Complex apply(Complex z) {
        Complex complexNumber = constant;
        for (Complex complex : roots) {
            complexNumber = complexNumber.multiply(z.sub(complex));
        }
        return complexNumber;
    }

    /**
     * Converts {@link ComplexRootedPolynomial} to {@link ComplexPolynomial}.
     * @return Returns converted {@link ComplexPolynomial}
     */
    public ComplexPolynomial toComplexPolynom() {
        Complex[] polynomial = new Complex[roots.length + 1];
        Complex[] tmpPoly = new Complex[roots.length + 1];
        polynomial[0] = constant; //TODO vratiti ovo
        //polynomial[0] = Complex.ONE;
        for (int i = 1; i < roots.length + 1; i++) {
            for (int j = 0; j < i; j++) {
                tmpPoly[j] = polynomial[j];
            }
            for (int j = i; j > 0; j--) {
                polynomial[j] = polynomial[j - 1];
            }
            polynomial[0] = Complex.ZERO;

            for (int j = 0; j < i; j++) {
                tmpPoly[j] = tmpPoly[j].multiply(roots[i - 1]);
            }
            for (int j = 0; j < i; j++) {
                polynomial[j] = polynomial[j].add(tmpPoly[j]);
            }
        }
        for (int i = 0; i < polynomial.length; i++) {
            polynomial[i] = polynomial[i].negate();
        }
        return new ComplexPolynomial(polynomial);
    }

    /**
     * Returns rooted polynomial in appropriate representation.
     * @return Returns string representation of a rooted polynomial.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(constant);
        for (Complex complex : roots) {
            sb.append("*").append("(z-").append(complex.toString()).append(")");
        }
        return sb.toString();
    }

    /**
     * Finds the index of the root closest to the given point z that is within treshold.
     * @param z Point z which is used in comparisons.
     * @param treshold Treshold
     * @return Returns index of a root if it's value is below the treshold, otherwise returns -1.
     */
    public int indexOfClosestRootFor(Complex z, double treshold) {
        Complex closestRoot = roots[0];
        int indexOfClosestRoot = 0;
        int index = 0;
        for (Complex complex : roots) {
            if (z.sub(complex).module() < z.sub(closestRoot).module()) {
                closestRoot = complex;
                indexOfClosestRoot = index;
            }
            index++;
        }
        if (z.sub(closestRoot).module() > treshold) {
            return -1;
        }
        return indexOfClosestRoot;
    }
}
