package hr.fer.zemris.java.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ObjectStackTest {

    ObjectStack<List<String>> stack;
    @BeforeEach
    void init() {
        stack = new ObjectStack<>();
        List<String> list = new ArrayIndexedCollection<>();
        list.add("Hello world");
        stack.push(list);
    }

    @Test
    void pop() {
        assertEquals(stack.pop().get(0), "Hello world");
        assertEquals(stack.size(), 0);
    }

    @Test
    void peek() {
        assertEquals(stack.peek().get(0), "Hello world");
        assertEquals(stack.size(), 1);
    }
}
