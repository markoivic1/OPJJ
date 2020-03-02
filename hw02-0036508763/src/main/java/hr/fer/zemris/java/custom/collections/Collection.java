package hr.fer.zemris.java.custom.collections;


/**
 * Class Collection implements some general methods which are used to manage and manipulate collections.
 *
 * Not all methods are implemented correctly and some are just place holders. It is expected to override these methods
 * in classes that inherits this one.
 *
 * @author marko
 * @version 1.0.0
 */
public class Collection {
    Collection() {

    }


    /**
     *
     * This method uses collection's {@link Collection#size()} method to determine whether the collection is empty or not.
     * Default value for {@link Collection#size()} is 0 therefore this method will always return true if {@link Collection#size()} is not overridden.
     *
     * @return Returns {@code true} if collection contains no objects and {@code false} otherwise.
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Method used to get size of a collection. Default value is set to 0.
     * Expected to be overridden.
     * @return Returns the number of currently stored objects in this collections.
     */
    public int size() {
        return 0;
    }

    /**
     * Adds the given object into this collection. Does nothing here.
     * Expected to be overridden.
     *
     * @param value Object which is to be add to collection.
     */
    public void add(Object value) {
        // undefined
    }

    /**
     * Checks for value in a collection. Uses method equals therefore depends on its implementation to work properly.
     * Expected to be overridden.
     *
     * @param value Checks if the collection contains given value.
     * @return Always returns false, must be overridden.
     */
    public boolean contains(Object value) {
        return false;
    }

    /**
     * Copies all of the collection's content to an array.
     *
     * Expected to be overridden.
     *
     * @return Returns array with copied contents of a given collection. Never return {@code null} value.
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * Method calls processor.process(.) for each element of this collection.
     * The order in which elements will be sent is undefined in this class.
     * @param processor
     */
    public void forEach(Processor processor) {
        // undefined
    }

    /**
     * Method adds all elements from the given collection into the current collection.
     * Collection which is passed here as an argument remains unchanged by this method.
     * @param other Collection which elements will be added to current collection.
     */
    public void addAll(Collection other) {
        class AddingProcessor extends Processor {
            @Override
            public void process (Object value) {
                add(value);
            }
        }
        other.forEach(new AddingProcessor());
    }

    /**
     * Method which clears all elements from a collection.
     *
     * Expected to be overridden. Here does nothing.
     */
    public void clear() {
        // undefined
    }

    public boolean remove(Object value) {
        return false;
    }

}
