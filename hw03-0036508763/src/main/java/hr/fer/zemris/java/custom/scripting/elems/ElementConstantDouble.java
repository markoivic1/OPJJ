package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class used to store double values which {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser} has defined.
 * Variable value is read-only.
 * @author Marko Ivić
 */
public class ElementConstantDouble extends Element{
    /**
     * Value which is stored.
     */
    private double value;

    /**
     * Constructor which takes a value.
     * @param value {@code double} value which is stored in this class.
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * Getter is used as it is defined that this is read-only element.
     * @return {@code double} is returned.
     */
    public double getValue() {
        return value;
    }

    /**
     * Overridden method equals used for easier comparison of two objects.
     * @param o Takes an Object which is than compared.
     * @return Returns {@code true} if values are the same, returns {@code false} if they differ.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementConstantDouble that = (ElementConstantDouble) o;
        return Double.compare(that.value, value) == 0;
    }

    /**
     * Method hashCode returns proper hash for this class.
     * @return Returns hash for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * Returns the only value of this class as string.
     * @return Value as string.
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
