package hr.fer.zemris.java.hw01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {

    @Test
    public void negativeValue() {
        try {
            Factorial.factorial(-1);
            fail("Expected IllegalArgumentException");
        } catch(IllegalArgumentException ex){
            //
        }
    }

    @Test
    public void notAnOverflow() {
        long number = Factorial.factorial(20);
        assertTrue(number >= 1);
    }

    @Test
    public void numberZero() {
        long number = Factorial.factorial(0);
        assertEquals(number, 1);
    }


}