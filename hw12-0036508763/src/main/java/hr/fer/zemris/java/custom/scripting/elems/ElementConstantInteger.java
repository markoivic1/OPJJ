package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class used to store integer values which {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser} has defined.
 * Variable value is read-only.
 * @author Marko Ivić
 */
public class ElementConstantInteger extends Element {
    /**
     * Value which is being stored.
     */
    private int value;

    /**
     * Value is given by the constructor.
     * @param value Any integer which is stored in value.
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * Method getValue is used as this class is read-only.
     * @return Returns the value of this class as integer.
     */
    public int getValue() {
        return value;
    }

    /**
     * Method equals is used for easier comparison.
     * @param o Parameter is compared to and object of this class.
     * @return Returns {@code true} if objects are equal, return {@code false} if the differ.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementConstantInteger that = (ElementConstantInteger) o;
        return value == that.value;
    }

    /**
     * Method returns proper hashCode for this class.
     * @return Returns hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * Value is parsed to string.
     * @return Returns value of this class as string.
     */
    @Override
    public String asText() {
        return String.valueOf(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
