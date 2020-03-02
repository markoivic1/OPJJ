package hr.fer.zemris.java.custom.collections;

/**
 * An interface used to further generify {@link ArrayIndexedCollection} and {@link LinkedListIndexedCollection}.
 * It ensures that every class that implements this interface has both methods from this interface and methods from {@link Collection} interface.
 */
public interface List extends Collection {
    /**
     * Method which is used to get an Object from a given index.
     * @param index Index from which the Object will be returned.
     * @return Returns Object which is at a given index.
     */
    Object get(int index);

    /**
     * Inserts an Object in a given position in a collection.
     * All elements that come after this element will be shifted to to the right by one.
     * @param value Given value is inserted in a collection.
     * @param position Index at which the value is inserted.
     */
    void insert(Object value, int position);

    /**
     * Searches for an Object in a collection and returns an index if found.
     * @param value Object which is being searched for.
     * @return Returns 1 if the Object is found and -1 if not.
     */
    int indexOf(Object value);

    /**
     * Removes an Object from a given index. All elements that come after it will be shifted to the left by one.
     * @param index An index from which the element will be removed.
     */
    void remove(int index);
}
