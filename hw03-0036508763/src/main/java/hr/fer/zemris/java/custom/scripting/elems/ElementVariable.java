package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class used to store names of variables which {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser} has defined.
 * Value which is being stored here is expected to be in form that it starts with a letter and can have any number of letters, number and '_' after it.
 * Variable name is read-only.
 * @author Marko Ivić
 */
public class ElementVariable extends Element {
    /**
     * Name which is being stored.
     */
    private String name;

    @Override
    public String toString() {
        return name;
    }

    /**
     * Name is stored as a value.
     * @param name Name of the variable.
     * @throws NullPointerException Thrown when given value is null.
     */
    public ElementVariable(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
    }

    /**
     * Returns value of name as a string.
     * @return Returns the name of this object.
     */
    public String getName() {
        return name;
    }

    /**
     * Method used for comparing given object with an object of this class.
     * @param o Object which is compared to this one.
     * @return Returns {@code true} if their name is the same, returns {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementVariable that = (ElementVariable) o;
        return Objects.equals(name, that.name);
    }

    /**
     * Method implements proper hashCode.
     * @return Returns hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Returns name as string text.
     * @return Returns name as string.
     */
    @Override
    public String asText() {
        return name;
    }
}
