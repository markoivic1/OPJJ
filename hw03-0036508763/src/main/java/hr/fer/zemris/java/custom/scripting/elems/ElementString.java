package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;
/**
 * Class used to store string values which {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser} has defined.
 * Variable value is read-only.
 * @author Marko Ivić
 */
public class ElementString extends Element{
    /**
     * String value which stores given string.
     */
    private String value;

    @Override
    public String toString() {
        return value;
    }

    /**
     * Stores given value as string.
     * @param value Value which is store as string.
     */
    public ElementString(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        this.value = value;
    }

    /**
     * Method used for easier comparison.
     * @param o Object which is compared to an object of this class.
     * @return Returns {@code true} if values are the same, returns {@code false} if the differ.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementString that = (ElementString) o;
        return Objects.equals(value, that.value);
    }

    /**
     * Method which implements proper hashCode for this class.
     * @return Returns hash code of this class.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * Some bits of string are added so this string can be parsed to the same exact value.
     * \ is replaced with \\
     * '\n' is replaced with \n
     * '\r' is replaced with \r
     * '\t' is replaced with \t
     * '\"' is replaced with \"
     * @return Returns string which can be parsed to this same value.
     */
    @Override
    public String asText() {
        String reformattedString = value.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                .replace("\"", "\\\"");
        return "\"" + reformattedString + "\"";
    }
}
