package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.util.Objects;

/**
 * Node class defines some of the common methods which will be used in all inherited classes.
 * @author Marko Ivić
 */
public class Node {
    /**
     * Collection of children
     */
    private ArrayIndexedCollection collection;

    /**
     * Adds a new child to a collection.
     * @param child
     */
    public void addChildNode(Node child) {
        if (collection == null) {
            collection = new ArrayIndexedCollection();
        }
        collection.add(child);
    }

    /**
     * Return a number of children in a collection.
     * @return Returns the number of children in a collection.
     */
    public int numberOfChildren() {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    /**
     * Gets a child at a given index.
     * @param index An index from which the child will be retrieved.
     * @return Returns a child from a given index.
     * @throws SmartScriptParserException Thrown when an index is out of range and when given collection is null.
     */
    public Node getChild(int index) {
        if ((index < 0) || (index > numberOfChildren())) {
            throw new SmartScriptParserException();
        }
        if (collection == null) {
            throw new SmartScriptParserException();
        }
        return (Node) collection.get(index);
    }

    /**
     * Method used for comparing.
     * @param o Other object which will be compared to this one.
     * @return Returns true if every children and it's data are the same and the collections are the same.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        int numberOfChildren = numberOfChildren();
        if (numberOfChildren != ((Node) o).numberOfChildren()) {
            return false;
        }
        for (int i = 0; i < numberOfChildren; i++) {
            if (!this.getChild(i).equals(((Node) o).getChild(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method which implements proper hash code.
     * @return Returns hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(collection);
    }
}
