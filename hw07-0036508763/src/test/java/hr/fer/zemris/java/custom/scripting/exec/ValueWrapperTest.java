package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
class ValueWrapperTest {

    private ValueWrapper valueWrapper;

    @BeforeEach
    void init() {
        valueWrapper = new ValueWrapper("10");
    }

    @Test
    void add() {
        valueWrapper.add(3);
        assertEquals(valueWrapper.getValue(),13);
        valueWrapper.add(0.5);
        assertEquals(valueWrapper.getValue(), 13.5);
    }

    @Test
    void subtract() {
        valueWrapper.subtract(3);
        assertEquals(valueWrapper.getValue(),7);
        valueWrapper.subtract(0.5);
        assertEquals(valueWrapper.getValue(), 6.5);
    }

    @Test
    void multiply() {
        valueWrapper.multiply(3);
        assertEquals(valueWrapper.getValue(),30);
        valueWrapper.multiply(0.5);
        assertEquals(valueWrapper.getValue(), 15.0);
    }

    @Test
    void divide() {
        valueWrapper.multiply(3);
        valueWrapper.divide(3);
        assertEquals(valueWrapper.getValue(),10);
        valueWrapper.divide(0.5);
        assertEquals(valueWrapper.getValue(), 20.0);
    }

    @Test
    void numCompare() {
        assertEquals(valueWrapper.numCompare(5),5);
        assertEquals(valueWrapper.numCompare(13.5), -3);
    }

    @Test
    void getValue() {
        assertEquals(valueWrapper.getValue(), 10);
    }

    @Test
    void setValue() {
        assertEquals(valueWrapper.getValue(), 10);
        valueWrapper.setValue("4.5");
        assertEquals(valueWrapper.getValue(), 4.5);
    }
}