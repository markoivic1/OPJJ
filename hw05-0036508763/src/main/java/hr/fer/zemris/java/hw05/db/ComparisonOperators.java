package hr.fer.zemris.java.hw05.db;

/**
 * Class which offers static implementations of comparison operators.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ComparisonOperators {
    /**
     * Compares two strings and returns true if first is less than the second, returns false otherwise.
     */
    public static final IComparisonOperator LESS = (s1, s2) -> s1.compareTo(s2) < 0;
    /**
     * Compares two strings and returns true if first is less or equal to the second, return false otherwise.
     */
    public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) <= 0;
    /**
     * Compares two strings and returns true if first is greater than the second, return false otherwise.
     */
    public static final IComparisonOperator GREATER = (s1, s2) -> s1.compareTo(s2) > 0;
    /**
     * Compares two strings and returns true if first is greater or equal to the second, return false otherwise.
     */
    public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) >= 0;
    /**
     * Compares two strings and returns true if first is equal to the second, return false otherwise.
     */
    public static final IComparisonOperator EQUALS = (s1, s2) -> s1.compareTo(s2) == 0;

    /**
     * Compares two strings and returns true if first is not equal to the second, return false otherwise.
     */
    public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> s1.compareTo(s2) != 0;

    /**
     * Checks if the first string meets the pattern given in the second string.
     */
    public static final IComparisonOperator LIKE = (s1, s2) -> {
        if (s2.equals("*")) {
            return true;
        } else if (!s2.contains("*")) {
            return s1.equals(s2);
        }
        int numberOfLikeOperators = 0;
        for (int i = 0; i < s2.length(); i++) {
            if (s2.charAt(i) == '*') {
                numberOfLikeOperators++;
            }
        }
        if (numberOfLikeOperators != 1) {
            throw new IllegalArgumentException("Invalid number of wildcard operators");
        }
        String[] splitLike = s2.split("\\*");
        int indexOfAWildcard = s2.indexOf('*');
        if (indexOfAWildcard == 0) {
            return s1.endsWith(splitLike[1]);
        } else if (indexOfAWildcard == s2.length() - 1) {
            return s1.startsWith(splitLike[0]);
        }
        // indexes overlap
        if (indexOfAWildcard > s1.length() - splitLike[1].length()) {
            return false;
        }

        String firstPart = s1.substring(0, indexOfAWildcard);
        String lastPart = s1.substring(s1.length() - splitLike[1].length());

        return (splitLike[0].equals(firstPart) && splitLike[1].equals(lastPart));
    };
}
