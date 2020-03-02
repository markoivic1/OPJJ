package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
class FieldValueGettersTest {
    @Test
    void gettersTest() {
        StudentRecord record = new StudentRecord("0000000001", "Olovka", "Olovkić", 4);
        assertEquals(FieldValueGetters.FIRST_NAME.get(record), "Olovka");
        assertEquals(FieldValueGetters.LAST_NAME.get(record), "Olovkić");
        assertEquals(FieldValueGetters.JMBAG.get(record), "0000000001");
        /*System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
        System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
        System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));*/
    }

}