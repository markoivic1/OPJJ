package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
class StudentDatabaseTest {
    StudentDatabase db;
    @BeforeEach
    void init() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            //
        }
        db = new StudentDatabase(lines);
    }

    @Test
    void forJmbagTest() {
        assertEquals(db.forJMBAG("0000000001"), new StudentRecord("0000000001", "Akšamović", "Marin", 2));
        assertEquals(db.forJMBAG("10"), null);
    }

    @Test
    void filterTest() {
        List<StudentRecord> studentDbAll = db.filter( (r) -> true);
        List<StudentRecord> studentDbEmpty = db.filter( (r) -> false);
        assertEquals(studentDbAll.size(), 63);
        assertEquals(studentDbEmpty.size(), 0);
    }

    public class AlwaysTrueFilter implements IFilter {
        @Override
        public boolean accepts(StudentRecord record) {
            return true;
        }
    }

    public class AlwaysFalseFilter implements IFilter {
        @Override
        public boolean accepts(StudentRecord record) {
            return false;
        }
    }
}