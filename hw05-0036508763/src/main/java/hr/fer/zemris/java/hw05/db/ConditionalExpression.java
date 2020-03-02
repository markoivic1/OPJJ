package hr.fer.zemris.java.hw05.db;

/**
 * Stores single query with its appropriate implementation.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ConditionalExpression {
    /**
     * Getter for the field which is being used in the comparison.
     */
    private IFieldValueGetter fieldGetter;
    /**
     * String which is given in the query.
     */
    private String stringLiteral;
    /**
     * Operator used to compare {@link StudentRecord} with the comparison operator.
     */
    private IComparisonOperator comparisonOperator;

    /**
     * Constructor which stores given values in their appropriate variables.
     * @param fieldValueGetter Tells us which argument to get in order to do the comparison.
     * @param stringLiteral String which is used in the comparison.
     * @param operator Operator which does the comparison
     */
    public ConditionalExpression(IFieldValueGetter fieldValueGetter, String stringLiteral, IComparisonOperator operator) {
        this.fieldGetter = fieldValueGetter;
        this.stringLiteral = stringLiteral;
        this.comparisonOperator = operator;
    }

    /**
     * Getter for field getter lambda.
     * @return Returns field getter lambda
     */
    public IFieldValueGetter getFieldGetter() {
        return fieldGetter;
    }

    /**
     * Returns string literal given in the constructor
     * @return Returns string literal.
     */
    public String getStringLiteral() {
        return stringLiteral;
    }

    /**
     * Getter for the comparison operator.
     * @return Returns comparison operator.
     */
    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }
}
