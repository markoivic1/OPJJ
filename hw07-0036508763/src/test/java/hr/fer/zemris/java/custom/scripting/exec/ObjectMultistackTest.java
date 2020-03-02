package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
class ObjectMultistackTest {

    ObjectMultistack multistack;

    @BeforeEach
    void init() {
        multistack = new ObjectMultistack();
        multistack.push("time", new ValueWrapper("10.5"));
        multistack.push("time", new ValueWrapper("5"));
        multistack.push("date", new ValueWrapper("2015"));
        multistack.push("date", new ValueWrapper("2000"));
    }

    @Test
    void push() {
        multistack.push("time", new ValueWrapper("1000"));
        assertEquals(multistack.peek("time").getValue(), 1000);
        assertEquals(multistack.pop("date").getValue(), 2000);
        multistack.push(null, new ValueWrapper(2000));
        assertEquals(multistack.pop(null).getValue(), 2000);
        multistack.push(null, new ValueWrapper(null));
        assertEquals(multistack.pop(null).getValue(), 0);
    }

    @Test
    void pop() {
        assertEquals(multistack.pop("time").getValue(), 5);
        assertEquals(multistack.pop("time").getValue(), 10.5);
    }

    @Test
    void peek() {
        assertEquals(multistack.peek("time").getValue(), 5);
        assertEquals(multistack.peek("time").getValue(), 5);
    }

    @Test
    void isEmpty() {
        assertTrue(multistack.isEmpty("nonExistentKey"));
        assertFalse(multistack.isEmpty("time"));
    }
}