package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class used to store operator which {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser} has defined.
 * Variable symbol is read-only.
 * @author Marko Ivić
 */
public class ElementOperator extends Element {
    /**
     * Operator is stored as a string.
     */
    private String symbol;

    /**
     * Stores operator as a string in a variable symbol.
     * @param symbol String variable used to store operator.
     * @throws NullPointerException Thrown when null value is given as an argument;
     */
    public ElementOperator(String symbol) {
        if (symbol == null) {
            throw new NullPointerException();
        }
        this.symbol = symbol;
    }

    /**
     * Method used to get symbol (operator) as a string.
     * @return Returns symbol.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Method used for easier comparison of this object.
     * @param o Object which is compared to this one.
     * @return Returns {@code true} if symbols are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementOperator that = (ElementOperator) o;
        return Objects.equals(symbol, that.symbol);
    }

    /**
     * Method hashCode has its proper implementation.
     * @return Returns hash code of an object of class.
     */
    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }

    /**
     * Returns symbol as string.
     * @return Returns symbol.
     */
    @Override
    public String asText() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
