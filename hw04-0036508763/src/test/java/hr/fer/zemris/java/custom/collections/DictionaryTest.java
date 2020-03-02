package hr.fer.zemris.java.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 */
class DictionaryTest {

    Dictionary<String, String> dictionary;

    @BeforeEach
    void init() {
        dictionary = new Dictionary<>();
        dictionary.put("Key", "Value");
        dictionary.put("This is a key", "This is a value");
        dictionary.put("Hello", "hi");
    }

    @Test
    void puttingSomeEntriesIntoMap() {
        System.out.println("");
        assertEquals(dictionary.get("Key"), "Value");
        assertNotEquals(dictionary.get("Key"), "value");
        assertEquals(dictionary.get("Key"), "Value");
        assertThrows(NullPointerException.class, () -> dictionary.put(null, "value"));
    }

    @Test
    void rewritingExistingEntries() {
        assertEquals(dictionary.size(), 3);
        assertEquals(dictionary.get("Key"), "Value");
        dictionary.put("Key", "Different value");
        assertEquals(dictionary.size(), 3);
        assertEquals(dictionary.get("Key"), "Different value");
        assertNotEquals("Key", "Value");
    }

    @Test
    void testSize() {
        dictionary.put("Another", "Stuff");
        dictionary.put("Another", "But different stuff");
        dictionary.put("Blue", "Pen");
        assertEquals(dictionary.size(), 5);
        dictionary.clear();
        assertEquals(dictionary.size(), 0);
    }

    @Test
    void testUsingNullAsKey() {
        assertThrows(NullPointerException.class, () -> dictionary.put(null, "value"));
        assertThrows(NullPointerException.class, () -> dictionary.get(null));
        dictionary.put("Values can be null", null);
        assertEquals(dictionary.get("Values can be null"), null);
        dictionary.put("Values can be null", "But they can be changed to an object");
        assertEquals(dictionary.get("Values can be null"), "But they can be changed to an object");
    }

}