package hr.fer.zemris.java.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ReviewSuggestionsTest {
    @Test
    void testContainsNull() {
        SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(3);

        table.put("sun", null);
        table.put("rain", 4);
        table.put("wind", 4);

        assertTrue(table.containsValue(null));
        assertFalse(table.containsKey(null));
    }

    @Test
    void testNoSuchElement() {
        SimpleHashtable<String, Integer> nature = new SimpleHashtable<String, Integer>(10);

        nature.put("rain", 1);
        Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = nature.iterator();
        iter.next();

        assertThrows(NoSuchElementException.class, () -> iter.next());
    }

    @Test
    void testNonExisting() {
        SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(5);

        table.put("sun", 3);
        table.put("rain", 4);
        table.put("wind", 4);


        assertEquals(null, table.get("snow"));
        assertEquals(null, table.get(null));
    }

    @Test
    void testIterHasNext() {
        SimpleHashtable<String, Integer> nature = new SimpleHashtable<String, Integer>(1);
        nature.put("sun", 1);
        nature.put("rain", 2);
        nature.put("river", 3);

        nature.put("sun", 4);
        nature.put("rain", 7);
        nature.put("river", 8);

        assertEquals(3, nature.size());
    }
}
