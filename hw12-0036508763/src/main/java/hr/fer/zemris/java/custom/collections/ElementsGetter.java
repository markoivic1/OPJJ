package hr.fer.zemris.java.custom.collections;

/**
 * Interface used to define methods which are to be used with {@code ElementsGetter}.
 */
public interface ElementsGetter {
    /**
     * Checks if the next element in a class that implements {@code ElementsGetter} exists.
     * @return Returns {@code true} if next element exists, returns {@code false} otherwise.
     */
    boolean hasNextElement();

    /**
     * Gets the next element in a class that implements {@code ElementsGetter}.
     */
    Object getNextElement();

    /**
     * This interface offers default implementation which processes every element in a class that implements {@link ElementsGetter}.
     * @param p {@link Processor} is generic representation of some action.
     */
    default void processRemaining(Processor p) {
        while (hasNextElement()) {
            p.process(getNextElement());
        }
    }
}
