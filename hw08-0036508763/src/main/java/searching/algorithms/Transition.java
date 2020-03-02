package searching.algorithms;

/**
 * Class which defines single state transition and its cost
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Transition<T> {
    /**
     * State used in transition
     */
    private T state;
    /**
     * Cost of transition
     */
    private int cost;

    /**
     * Constructor which stores given values.
     * @param state State used in transition.
     * @param cost Cost of a transition.
     */
    public Transition(T state, int cost) {
        this.state = state;
        this.cost = cost;
    }

    /**
     * Getter for state
     * @return Returns state.
     */
    public T getState() {
        return state;
    }

    /**
     * Getter for cost
     * @return returns cost.
     */
    public double getCost() {
        return cost;
    }
}
