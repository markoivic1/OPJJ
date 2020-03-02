package hr.fer.zemris.java.custom.collections;


/**
 * Interface Collection implements some general methods which are used to manage and manipulate collections.
 *
 * @author marko
 * @version 1.0.0
 */
public interface Collection {

    /**
     *
     * This method uses collection's {@link Collection#size()} method to determine whether the collection is empty or not.
     *
     * @return Returns {@code true} if collection contains no objects and {@code false} otherwise.
     */
    default boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Method used to get size of a collection.
     *
     * @return Returns the number of currently stored objects in this collections.
     */
    int size();

    /**
     * Adds the given object into this collection.
     *
     * @param value Object which is to be add to collection.
     */
    void add(Object value);

    /**
     * Checks for value in a collection. Uses method equals therefore depends on its implementation to work properly.
     *
     * @param value Checks if the collection contains given value.
     * @return Returns {@code true} if object is contained in a collection, {@code false} otherwise.
     */
    boolean contains(Object value);

    /**
     * Copies all of the collection's content to an array.
     *
     * Expected to be overridden.
     *
     * @return Returns array with copied contents of a given collection. Never returns {@code null} value.
     */
    Object[] toArray();

    /**
     * Method calls processor.process(.) for each element of this collection.
     * The order in which elements will be sent is undefined in this class.
     * @param processor
     */
    default void forEach(Processor processor) {
        ElementsGetter elementsGetter = this.createElementsGetter();
        while (elementsGetter.hasNextElement()) {
            processor.process(elementsGetter.getNextElement());
        }
    }

    /**
     * Method adds all elements from the given collection into the current collection.
     * Collection which is given here as an argument remains unchanged by this method.
     *
     * @param other Collection which elements will be added to current collection.
     */
    default void addAll(Collection other) {
        Processor p = value -> add(value);
        other.forEach(p);
    }

    /**
     * Method which clears all elements from a collection.
     */
    void clear();

    /**
     * Removes given value from a collection.
     * @param value Value to be removed.
     * @return Returns {@code true} if value is successfully removed, returns {@code false} otherwise.
     */
    boolean remove(Object value);

    /**
     * Creates and returns ElementsGetter which is used to iterate over a given collection.
     * @return Returns {@link ElementsGetter} which is used to get get next element or check if it exists.
     */
    ElementsGetter createElementsGetter();

    /**
     * Adds every element from given collection to this collection if it passes the {@link Tester}.
     * @param col  {@link Collection} which is used for checking and copying elements.
     * @param tester {@link Tester} is used to select which element should be added to this collection.
     */
    default void addAllSatisfying(Collection col, Tester tester) {
        ElementsGetter elementsGetter = col.createElementsGetter();
        while (elementsGetter.hasNextElement()) {
            Object currentElement = elementsGetter.getNextElement();
            if (tester.test(currentElement)) {
                add(currentElement);
            }
        }
    }

}
