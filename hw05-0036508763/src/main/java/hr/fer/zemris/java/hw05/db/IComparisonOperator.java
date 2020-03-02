package hr.fer.zemris.java.hw05.db;

/**
 * Functional interface which defines whether some two strings satisfy given condition.
 * @author Marko Ivić
 * @version 1.0.0
 */
@FunctionalInterface
public interface IComparisonOperator {
    /**
     * Performs the operation which compares the two given values and returns true if they satisfy certain condition,
     * return false if they don't.
     * @param value1 First value in the comparison.
     * @param value2 Second value in the comparison
     * @return Returns true if the satisfy the condition returns false otherwise.
     */
    public boolean satisfied(String value1, String value2);
}
