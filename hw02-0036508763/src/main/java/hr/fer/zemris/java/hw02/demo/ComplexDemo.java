/**
 * Package that contains class for demonstrating class ComplexNumber.
 */
package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * Class which is used to test methods of {@link ComplexNumber}.
 */
public class ComplexDemo {

    /**
     * Every method in class {@link ComplexNumber} is used here to test it's implementation.
     * @param args No program arguments are used in this method.
     */
    public static void main(String[] args) {
        ComplexNumber c1 = new ComplexNumber(2, 3);
        ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
        ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
        System.out.println(c3);

        // Naknadno dodano
        // @marko dodano naknadno 2+i, -2+i, -2-i i 2-i:

        ComplexNumber c6 = new ComplexNumber(2, 1);
        ComplexNumber c7 = new ComplexNumber(2, -1);
        ComplexNumber c8 = new ComplexNumber(-2, 1);
        ComplexNumber c9 = new ComplexNumber(-2, -1);
        System.out.println(c6.getAngle()+ " " + c7.getAngle() + " " + c8.getAngle() + " " + c9.getAngle());

        ComplexNumber c4 = new ComplexNumber(3, 3);
        ComplexNumber c5 = ComplexNumber.parse(c4.toString());
        System.out.println(c5);
    }
}
