package hr.fer.zemris.java.hw02;

import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;

class ComplexNumberTest {

    @Test
    void fromReal() {
        ComplexNumber complexNumberWithZero = ComplexNumber.fromReal(0);
        ComplexNumber complexNumberPositive = ComplexNumber.fromReal(1);
        ComplexNumber complexNumberNegative = ComplexNumber.fromReal(-1);

        assertEquals(complexNumberWithZero.getReal(), 0);
        assertEquals(complexNumberWithZero.getImaginary(), 0);

        assertEquals(complexNumberPositive.getReal(), 1);
        assertEquals(complexNumberPositive.getImaginary(), 0);

        assertEquals(complexNumberNegative.getReal(), -1);
        assertEquals(complexNumberNegative.getImaginary(), -0);
    }

    @Test
    void fromImaginary() {
        ComplexNumber complexNumberWithZero = ComplexNumber.fromImaginary(0);
        ComplexNumber complexNumberPositive = ComplexNumber.fromImaginary(1);
        ComplexNumber complexNumberNegative = ComplexNumber.fromImaginary(-1);

        assertEquals(complexNumberWithZero.getReal(), 0);
        assertEquals(complexNumberWithZero.getImaginary(), 0);

        assertEquals(complexNumberPositive.getReal(), 0);
        assertEquals(complexNumberPositive.getImaginary(), 1);

        assertEquals(complexNumberNegative.getReal(), 0);
        assertEquals(complexNumberNegative.getImaginary(), -1);
    }

    @Test
    void fromMagnitudeAndAngle() {
        try {
            ComplexNumber complexNumberWithZeroMagnitudeAndSomeAngle = ComplexNumber.fromMagnitudeAndAngle(0,PI);
            fail("This should throw an IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            //
        }

        ComplexNumber complexNumber = ComplexNumber.fromMagnitudeAndAngle(5, PI/2);

        assertTrue(doublesAreEqual(complexNumber.getReal(), 0));
        assertTrue(doublesAreEqual(complexNumber.getImaginary(), 5));
        assertTrue(doublesAreEqual(complexNumber.getMagnitude(), 5));
        assertTrue(doublesAreEqual(complexNumber.getAngle(), PI/2));
    }

    @Test
    void parseStringToComplexNumber() {
        ComplexNumber parsedComplexNumberWithPlus = ComplexNumber.parse("+10.0-43.3i");
        assertTrue(doublesAreEqual(parsedComplexNumberWithPlus.getReal(), 10.0));
        assertTrue(doublesAreEqual(parsedComplexNumberWithPlus.getImaginary(), -43.3));

        ComplexNumber parsedComplexNumber = ComplexNumber.parse("10.0-i");
        assertTrue(doublesAreEqual(parsedComplexNumber.getReal(), 10.0));
        assertTrue(doublesAreEqual(parsedComplexNumber.getImaginary(), -1));

        ComplexNumber negativeRealPart = ComplexNumber.parse("-16.55123+51.1i");
        assertTrue(doublesAreEqual(negativeRealPart.getReal(), -16.55123));
        assertTrue(doublesAreEqual(negativeRealPart.getImaginary(), 51.1));


        ComplexNumber onlyRealPart = ComplexNumber.parse("1002.1");
        assertTrue(doublesAreEqual(onlyRealPart.getReal(), 1002.1));
        assertTrue(doublesAreEqual(onlyRealPart.getImaginary(), 0));

        ComplexNumber onlyImaginaryPart = ComplexNumber.parse("1552.1i");
        assertTrue(doublesAreEqual(onlyImaginaryPart.getReal(), 0));
        assertTrue(doublesAreEqual(onlyImaginaryPart.getImaginary(), 1552.1));

        ComplexNumber onlyImaginaryPartNegative = ComplexNumber.parse("-1552.1i");
        assertTrue(doublesAreEqual(onlyImaginaryPartNegative.getReal(), 0));
        assertTrue(doublesAreEqual(onlyImaginaryPartNegative.getImaginary(), -1552.1));

        ComplexNumber onlyImaginaryUnitNegative = ComplexNumber.parse("-i");
        assertTrue(doublesAreEqual(onlyImaginaryUnitNegative.getReal(), 0));
        assertTrue(doublesAreEqual(onlyImaginaryUnitNegative.getImaginary(), -1));

        ComplexNumber onlyImaginaryUnitPositive = ComplexNumber.parse("i");
        assertTrue(doublesAreEqual(onlyImaginaryUnitPositive.getReal(), 0));
        assertTrue(doublesAreEqual(onlyImaginaryUnitPositive.getImaginary(), 1));
        try {
            ComplexNumber illegalArgumentsComplexNumber = ComplexNumber.parse("++4-1");
            fail("This is an illegal argument and should throw an exception");
        } catch (IllegalArgumentException ex) {
            //
        }

        try {
            ComplexNumber complexNumberSurroundedWithOperators = ComplexNumber.parse("+3.2-");
            fail("This is an illegal argument and should throw an exception");
        } catch (IllegalArgumentException ex) {
            //
        }

        try {
            ComplexNumber complexNumberWithExtraSigns = ComplexNumber.parse("-3+2i-");
            fail("This is an illegal argument and should throw an exception");
        } catch (IllegalArgumentException ex) {
            //
        }

        try {
            ComplexNumber reversedComplexNumber = ComplexNumber.parse("i3+4");
            fail("This is an illegal argument and should throw an exception");
        } catch (IllegalArgumentException ex) {
            //
        }
    }

    @Test
    void parsingInvalidStrings() {
        try {
            ComplexNumber complexNumberWithDoubledSigns = ComplexNumber.parse("++2+3i");
            ComplexNumber complexNumberWithAlternatingSigns = ComplexNumber.parse("2.1+-3.2i");
            ComplexNumber complexNumberWithDoubledSignsForImaginary = ComplexNumber.parse("--2i");
            ComplexNumber complexNumberWithImaginarySignInFirstPlace = ComplexNumber.parse("i2+3");

            fail("These methods should throw an IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            //
        }
    }

    @Test
    void complexNumberToString() {
        ComplexNumber complexNumberWithRealPart = new ComplexNumber(5.1200,0.0);
        assertEquals(complexNumberWithRealPart.toString(), "5.12+0.0i");

        ComplexNumber complexNumberWithImaginaryPart = new ComplexNumber(0, 512.32);
        assertEquals(complexNumberWithImaginaryPart.toString(), "0.0+512.32i");

        ComplexNumber complexNumberWithBothParts = new ComplexNumber(9123.21, 2.0);
        assertEquals(complexNumberWithBothParts.toString(), "9123.21+2.0i");
    }

    @Test
    void addTwoComplexNumbers() {
        ComplexNumber firstComplexNumber = new ComplexNumber(2.2, 1.3);
        ComplexNumber secondComplexNumber = new ComplexNumber(-1, 3.3);
        ComplexNumber addedComplexNumber = firstComplexNumber.add(secondComplexNumber);
        assertTrue(doublesAreEqual(addedComplexNumber.getReal(), 1.2));
        assertTrue(doublesAreEqual(addedComplexNumber.getImaginary(), 4.6));
    }

    @Test
    void subTwoComplexNumbers() {
        ComplexNumber firstComplexNumber = new ComplexNumber(-2.3, 1.3);
        ComplexNumber secondComplexNumber = new ComplexNumber(-1.22, 2);
        ComplexNumber subbedComplexNumber = firstComplexNumber.sub(secondComplexNumber);
        assertTrue(doublesAreEqual(subbedComplexNumber.getReal(), -1.08));
        assertTrue(doublesAreEqual(subbedComplexNumber.getImaginary(), -0.7));
    }

    @Test
    void multiplicationOfComplexNumbers() {
        ComplexNumber firstComplexNumber = new ComplexNumber(-1.3, 0);
        ComplexNumber secondComplexNumber = new ComplexNumber(-1.22, 2);
        ComplexNumber multipliedComplexNumber = firstComplexNumber.mul(secondComplexNumber);
        assertTrue(doublesAreEqual(multipliedComplexNumber.getReal(), 1.586));
        assertTrue(doublesAreEqual(multipliedComplexNumber.getImaginary(), -2.6));
    }

    @Test
    void divisionOfComplexNumbers() {
        ComplexNumber firstComplexNumber = new ComplexNumber(-3, 1);
        ComplexNumber secondComplexNumber = new ComplexNumber(-2, 2);
        ComplexNumber multipliedComplexNumber = firstComplexNumber.div(secondComplexNumber);
        assertTrue(doublesAreEqual(multipliedComplexNumber.getReal(), 1));
        assertTrue(doublesAreEqual(multipliedComplexNumber.getImaginary(), 0.5));
    }

    @Test
    void powerOfComplexNumbers() {
        ComplexNumber complexNumber = new ComplexNumber(2,3.5);
        complexNumber = complexNumber.power(2);
        assertTrue(doublesAreEqual(complexNumber.getReal(), -8.25));
        assertTrue(doublesAreEqual(complexNumber.getImaginary(), 14));
    }

    @Test
    void zeroPowerOfAComplexNumber() {
        ComplexNumber complexNumber = new ComplexNumber(3, 2);
        assertFalse(doublesAreEqual(complexNumber.getMagnitude(),1));
        complexNumber = complexNumber.power(0);
        assertTrue(doublesAreEqual(complexNumber.getMagnitude(), 1));
    }

    @Test
    void rootOfComplexNumbers() {
        ComplexNumber complexNumber = new ComplexNumber(3.1,-2.4);
        complexNumber = complexNumber.root(2)[0];
        assertTrue(doublesAreEqual(complexNumber.getReal(), 1.8735606684575938448));
        assertTrue(doublesAreEqual(complexNumber.getImaginary(), -0.6404916692598475036));
    }

    @Test
    void getRealPartOfAComplexNumber() {
        ComplexNumber complexNumber = new ComplexNumber(3.1,-2.4);
        assertTrue(doublesAreEqual(complexNumber.getReal(), 3.1));
    }

    @Test
    void getImaginary() {
        ComplexNumber complexNumber = new ComplexNumber(3.1,-132);
        assertTrue(doublesAreEqual(complexNumber.getImaginary(), -132));
    }

    @Test
    void getMagnitude() {
        ComplexNumber complexNumber = new ComplexNumber(3.1,-2.4);
        assertTrue(doublesAreEqual(complexNumber.getMagnitude(), 3.92046));
    }

    @Test
    void getAngle() {
        ComplexNumber complexNumber = new ComplexNumber(3.1,-2.4);
        assertTrue(doublesAreEqual(complexNumber.getAngle(), -0.658806));
    }

    @Test
    void radiansForDifferentAngles() {
        ComplexNumber complexNumberPlusPlus = new ComplexNumber(1,1);
        ComplexNumber complexNumberPlusMinus = new ComplexNumber(1,-1);
        ComplexNumber complexNumberMinusPlus = new ComplexNumber(-1,1);
        ComplexNumber complexNumberMinusMinus = new ComplexNumber(-1, -1);

        System.out.println("1+i " + complexNumberPlusPlus.getAngle());
        System.out.println("1-i " + complexNumberPlusMinus.getAngle());
        System.out.println("-1+i " + complexNumberMinusPlus.getAngle());
        System.out.println("-1-i " + complexNumberMinusMinus.getAngle());
    }

    public boolean doublesAreEqual(double first, double second) {
        return (abs(first - second) <  1e-6);
    }
}