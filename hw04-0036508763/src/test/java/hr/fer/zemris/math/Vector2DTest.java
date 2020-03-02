package hr.fer.zemris.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
class Vector2DTest {

    private Vector2D vector2D;

    @BeforeEach
    void init() {
        vector2D = new Vector2D(1, 1);
    }

    @Test
    void translationTest() {
        vector2D.translate(new Vector2D(2, 3));
        assertEquals(vector2D.getX(), 3);
        assertEquals(vector2D.getY(), 4);
        Vector2D translatedVector = vector2D.translated(new Vector2D(5, 0));
        assertEquals(translatedVector.getX(), 8);
        assertEquals(translatedVector.getY(), 4);
    }

    @Test
    void rotationTest() {
        Vector2D vector2D = new Vector2D(1, 1);
        Vector2D rotatedVector = vector2D.rotated(Math.PI);
        assertEquals(rotatedVector, new Vector2D(-1, -1));
        rotatedVector.rotate(Math.PI / 2);
        assertEquals(rotatedVector, new Vector2D(1, -1));
        vector2D = new Vector2D(1, 1);
        vector2D.rotate(Math.PI / 2);
        assertNotEquals(vector2D, rotatedVector);
    }



    @Test
    void scaleTest() {
        Vector2D scaledVector = vector2D.scaled(3);
        assertEquals(scaledVector, new Vector2D(3, 3));
        vector2D.scale(0.5);
        assertEquals(vector2D, new Vector2D(0.5, 0.5));
    }

    @Test
    void test() {
        Vector2D copiedVector = vector2D.copy();
        assertEquals(copiedVector, vector2D);
    }
}