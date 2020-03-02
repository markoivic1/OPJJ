package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

/**
 * Class which represents text in between the tags.
 * @author Marko Ivić
 */
public class TextNode extends Node {
    /**
     * Text which is given by the constructor.
     */
    private String text;

    /**
     * Constructor takes a given text and stores it.
     * @param text
     * @throws NullPointerException Thrown when given text is null.
     */
    public TextNode(String text) {
        if (text == null) {
            throw new NullPointerException();
        }
        this.text = text;
    }

    public void accept(INodeVisitor visitor) {
        visitor.visitTextNode(this);
    }

    /**
     * Getter for text
     * @return Returns text.
     */
    public String getText() {
        return text;
    }

    /**
     * Method which is used for comparing.
     * @param o Other object which will be compared to this one.
     * @return Returns true if every children and it's type recursively are the same and it's own data is the same.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TextNode textNode = (TextNode) o;
        if (!Objects.equals(text, textNode.text)) {
            return false;
        }
        return super.equals(o);
    }

    /**
     * Proper implementation of hash code.
     * @return Returns hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }

    /**
     * An output from this method can be parsed back to this same exact type and it's all of the aspects would be the same.
     * @return Returns a string with \ replaced with \\ and { replaced with \{.
     */
    @Override
    public String toString() {
        return text.replace("\\", "\\\\").replace("{$", "\\{$");
    }
}
