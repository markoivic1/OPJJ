package searching.algorithms;

import java.util.Objects;

/**
 * Node is used to store current state, parent state and cost.
 * This type of data structure is beneficial for our implementation.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Node<S> {
    /**
     * Current state
     */
    private S state;
    /**
     * Current cost
     */
    private double cost;
    /**
     * Parent of this node.
     */
    private Node<S> parent;

    /**
     * Constructor which stores given values.
     * @param parent Parent Node
     * @param state Current state
     * @param cost Cost of this node
     */
    public Node(Node<S> parent, S state, double cost) {
        this.state = state;
        this.cost = cost;
        this.parent = parent;
    }

    /**
     * Getter for state
     * @return Returns state
     */
    public S getState() {
        return this.state;
    }

    /**
     * Getter for cost
     * @return Returns cost.
     */
    public double getCost() {
        return this.cost;
    }

    /**
     * Getter for parent
     * @return Returns parent.
     */
    public Node<S> getParent() {
        return this.parent;
    }

    /**
     * {@inheritDoc}
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(state, node.state);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}
