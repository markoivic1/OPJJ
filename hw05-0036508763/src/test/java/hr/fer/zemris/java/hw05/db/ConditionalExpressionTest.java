package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
class ConditionalExpressionTest {
    @Test
    void expressionTest() {
        ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*", ComparisonOperators.LIKE);
        StudentRecord record = new StudentRecord("000000001", "Jedan", "Bos", 4);
        assertTrue(expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record),  // returns lastName from given record
                expr.getStringLiteral()             // returns "Bos*"
                ));
    }

}