package hr.fer.zemris.java.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
class SimpleHashtableTest {

    SimpleHashtable<String, Integer> table;

    @BeforeEach
    void setUp() {
        table = new SimpleHashtable<>(2);
        table.put("Ivana", 2);
        table.put("Ante", 2);
        table.put("Jasna", 2);
        table.put("Pero", 3);
        table.put("Kristina", 5);
        table.put("Ivana", 5);
    }

    @Test
    void constructorWithoutCapacity() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
        hashtable.put("Ivana", 2);
        hashtable.put("Ante", 2);
        hashtable.put("Jasna", 2);
        hashtable.put("Pero", 3);
        hashtable.put("Kristina", 5);
        hashtable.put("Ivana", 5);
        assertEquals(hashtable.size(), 5);
    }

    @Test
    void constructorWithSmallCapacity() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(1);
        hashtable.put("Ivana", 2);
        hashtable.put("Ante", 2);
        hashtable.put("Jasna", 2);
        hashtable.put("Pero", 3);
        hashtable.put("Kristina", 5);
        hashtable.put("Ivana", 5);
        assertEquals(hashtable.size(), 5);
    }

    @Test
    void putTest() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
        assertThrows(NullPointerException.class, () -> hashtable.put(null, null));
        assertThrows(NullPointerException.class, () -> hashtable.put(null, Integer.valueOf(2)));
        hashtable.put("Boca", null);
        hashtable.put("Normal", Integer.valueOf(100));
        assertEquals(hashtable.size(), 2);
        assertEquals(hashtable.get("Boca"), null);
        assertEquals(hashtable.get("Normal"), Integer.valueOf(100));
    }

    @Test
    void getTest() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();
        assertNull(hashtable.get("Non existent key"));
        assertNull(table.get("Non Existent key"));
        assertEquals(table.get("Ivana"), Integer.valueOf(5));
    }

    @Test
    void size() {
        assertEquals(table.size(), 5);
        table.clear();
        assertEquals(table.size(), 0);
        table.put("Marina", 5);
        assertEquals(table.size(), 1);
    }

    @Test
    void containsKey() {
        assertTrue(table.containsKey("Ivana"));
        assertTrue(table.containsKey("Pero"));
        assertFalse(table.containsKey("Antiša"));
        assertFalse(table.containsKey(Integer.valueOf(5)));
    }

    @Test
    void containsValue() {
        assertTrue(table.containsValue(Integer.valueOf(5)));
        assertTrue(table.containsValue(Integer.valueOf(2)));
        assertTrue(table.containsValue(Integer.valueOf(3)));
        assertFalse(table.containsValue(Integer.valueOf(1)));
        assertFalse(table.containsValue("Value"));
    }

    @Test
    void remove() {
        table.remove(null);
        table.remove("Jasna");
        assertEquals(table.size(), 4);
        table.remove("Pero");
        table.remove(null);
        table.remove("Ivana");
        table.remove("Kristina");
        table.remove("Ante");
        table.remove(Integer.valueOf(5));
        table.remove("Non existent key");
        assertEquals(table.size(), 0);
    }

    @Test
    void isEmpty() {
        assertFalse(table.isEmpty());
        table.clear();
        assertTrue(table.isEmpty());
    }

    @Test
    void clear() {
        table.clear();
        assertNull(table.get("Ivana"));
        assertEquals(table.size(), 0);
    }

    @Test
    void tableToString() {
        assertEquals(table.toString(), "[Pero=3, Ante=2, Ivana=5, Jasna=2, Kristina=5]");
        SimpleHashtable<String, Integer> emptyHashtable = new SimpleHashtable<>();
        assertEquals(emptyHashtable.toString(), "[]");
    }

    @Test
    void iterator() {
        Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = table.iterator();
        assertEquals(iter.next().getKey(), "Pero");
        table.put("Another", 0);
        try {
            iter.next();
            fail("This method should throw a ConcurrentModificationException");
        } catch (ConcurrentModificationException ex) {
            //
        }
        iter = table.iterator();
        iter.next();
        iter.remove();
        try {
            iter.remove();
            fail("This method should throw an IllegalStateException");
        } catch (IllegalStateException ex) {
            //
        }
    }
}