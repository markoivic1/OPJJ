package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
class ComparisonOperatorsTest {
    @Test
    void operatorsTest() {
        IComparisonOperator oper1 = ComparisonOperators.LESS;
        assertTrue(oper1.satisfied("Ana", "Jasna"));
        IComparisonOperator oper = ComparisonOperators.LIKE;
        assertFalse(oper.satisfied("Zagreb", "Aba*"));  // false
        assertFalse(oper.satisfied("AAA", "AA*AA"));    // false
        assertTrue(oper.satisfied("AAAA", "AA*AA"));   // true
        assertTrue(oper.satisfied("BAA", "*A"));
        assertTrue(oper.satisfied("BCCCAA", "B*A"));
        assertFalse(oper.satisfied("BCCCAA", "B*AB"));
        assertTrue(oper.satisfied("A", "*A"));
        IComparisonOperator oper2 = ComparisonOperators.EQUALS;
        assertTrue(oper2.satisfied("Ante", "Ante"));
        assertFalse(oper2.satisfied("Monday", "Sunday"));
    }

}