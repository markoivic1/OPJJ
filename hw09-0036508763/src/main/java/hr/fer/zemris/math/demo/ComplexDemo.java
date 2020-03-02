package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ComplexDemo {
    public static void main(String[] args) {
        ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2, 0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
        ComplexPolynomial cp = crp.toComplexPolynom();
        System.out.println(crp);
        System.out.println(cp);
        System.out.println(cp.derive());
    }
}
