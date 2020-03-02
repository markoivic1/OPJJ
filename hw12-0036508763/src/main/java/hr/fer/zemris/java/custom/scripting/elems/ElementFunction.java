package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class used to store functions which {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser} has defined.
 * Variable name is read-only.
 * @author Marko Ivić
 */
public class ElementFunction extends Element{
    /**
     * Name of the function.
     */
    private String name;

    /**
     * Name of the function is given as an argument in a constructor.
     * @param name Name of the function.
     * @throws NullPointerException Thrown when given value is null.
     */
    public ElementFunction(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
    }

    /**
     * Method used for getting a name of this function.
     * @return Retruns name of the function as String.
     */
    public String getName() {
        return name;
    }

    /**
     * Method used for comparison of this class.
     * @param o Object which is being compared to object of this class.
     * @return Returns {@code true} if their name is the same, return false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementFunction that = (ElementFunction) o;
        return Objects.equals(name, that.name);
    }

    /**
     * Method hashCode returns proper hash code.
     * @return Returns hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Returns name as text.
     * @return Returns name as string.
     */
    @Override
    public String asText() {
        return name;
    }

    @Override
    public String toString() {
        return "@" + name;
    }
}
