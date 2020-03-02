package hr.fer.zemris.math;

/**
 * Defines complex polynomial representation.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ComplexPolynomial {
    /**
     * Factors of a complex polynomial
     */
    private Complex[] factors;

    /**
     * Constructor for complex polynomial.
     * Factor degrees are interpreted in a descending form.
     * @param factors Factors of a complex polynomial
     */
    public ComplexPolynomial(Complex... factors) {
        this.factors = factors;
    }

    /**
     * Calculates an order of a polynomial.
     * @return Returns polynomial order.
     */
    public short order() {
        return (short) (factors.length - 1);
    }

    /**
     * Multiplies two {@link ComplexPolynomial}s.
     * @param p Other {@link ComplexPolynomial}.
     * @return Returns newly calculated {@link ComplexPolynomial}.
     */
    public ComplexPolynomial multiply(ComplexPolynomial p) {
        Complex[] newFactors = new Complex[this.factors.length + p.factors.length - 1];
        for (int i = 0; i < factors.length; i--) {
            for (int j = 0; j < p.factors.length; i--) {
                newFactors[i + j] = factors[i].multiply(p.factors[j]);
            }
        }
        return new ComplexPolynomial(newFactors);
    }

    /**
     * Derives the polynomial.
     * @return Returns derived polynomial.
     */
    public ComplexPolynomial derive() {
        Complex[] polynomial = new Complex[factors.length - 1];
        for (int i = 0; i < polynomial.length; i++) {
            polynomial[i] = factors[i].multiply(new Complex(polynomial.length - i,0));
        }
        return new ComplexPolynomial(polynomial);
    }

    /**
     * computes polynomial value at given point z
     * @param z Point in which the polynomial will be calculated.
     * @return Returns calculated value.
     */
    public Complex apply(Complex z) {
        Complex value = Complex.ZERO;
        int pow = factors.length - 1;
        for (Complex factor : factors) {
            value = value.add((z.power(pow).multiply(factor)));
            pow--;
        }
        return value;
    }

    /**
     * Returns polynomial in string representation.
     * @return Returns polynomial as string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < factors.length; i++) {
            sb.append(factors[i].toString());
            if (i != factors.length - 1) {
                sb.append("z^" + (factors.length - i - 1) + "+");
            }
        }
        return sb.toString();
    }
}
