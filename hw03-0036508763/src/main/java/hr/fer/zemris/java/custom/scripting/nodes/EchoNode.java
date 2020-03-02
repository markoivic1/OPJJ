package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

import java.util.Arrays;

/**
 * Class which represents echo tag.
 * Echo node which can store every type of {@link Element}.
 * @author Marko Ivić
 */
public class EchoNode extends Node {
    /**
     * An array of {@link Element}s.
     */
    private Element[] elements;

    /**
     * An array of given elements is stored.
     * @param elements
     */
    public EchoNode(Element[] elements) {
        this.elements = elements;
    }

    /**
     * Returns this class as a string which can be parsed to this same exact object.
     * @return Returns string with added opening and closing tags.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{$= ");
        for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i].asText());
            sb.append(" ");
        }
        sb.append("$}");
        return sb.toString();
    }

    /**
     * Method used to compare an object of this class to another object.
     * @param o Object which is being compared.
     * @return Returns true if every element in an array is the same, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EchoNode echoNode = (EchoNode) o;
        if (!super.equals(o)) {
            return false;
        }
        return Arrays.equals(elements, echoNode.elements);
    }

    /**
     * Returns an array of elements.
     * @return Returns elements.
     */
    public Element[] getElements() {
        return elements;
    }

    /**
     * Method implements proper hashcode for this class.
     * @return Returns hash code.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }
}
