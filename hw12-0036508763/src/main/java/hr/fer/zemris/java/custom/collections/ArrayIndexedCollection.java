package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * This class implements collection with an indexed array of object.
 * This collection can be navigated with some simple methods.
 * General contract of this collection is: duplicate elements are allowed; storage of null references is not allowed.
 *
 * Benefits of an indexed array are that average complexity for some methods are constant.
 * Such as adding a new object, removing or getting an existing object. Which in return guarantee fast methods.
 *
 * Downsides are that some other methods require checking every object in an array.
 * Main downside is that an array needs to be copied over to a bigger array every time it exceeds limit of objects.
 *
 * @author marko
 * @version 1.0.0
 */
public class ArrayIndexedCollection implements List {

    /**
     * Default array capacity is set to 16.
     */
    private static final int DEFAULT_CAPACITY = 16;
    /**
     * Current number of Objects in array.
     */
    private int size;
    /**
     * Array of Objects.
     */
    private Object[] elements;

    /**
     * This variable stores number of modifications. It is increades by 1 every time an element is add, removed or cleared from a collection.
     */
    private long modificationCount;

    /**
     * Class used to define methods which are used to iterate over a collection elements.
     */
    private static class ElementsGetter implements hr.fer.zemris.java.custom.collections.ElementsGetter {
        /**
         * Current index in a collection.
         */
        private int index;
        /**
         * Size of a collection.
         */
        private int size;
        /**
         *
         */
        private ArrayIndexedCollection collection;
        /**
         * This value is initialized in a constructor. Later used to check whether the collection has been modified.
         */
        private long savedModificationCount;

        /**
         * Constructor takes an existing collection.
         * @param collection An existing collection.
         */
        private ElementsGetter(ArrayIndexedCollection collection) {
            this.size = collection.size;
            this.index = 0;
            this.collection = collection;
            savedModificationCount = collection.modificationCount;
        }

        /**
         * Checks whether the {@link ElementsGetter} has next element.
         * @return Returns {@code true} if next elements exists, returns {@code false} otherwise.
         * @throws ConcurrentModificationException if a collection has been modified.
         */
        public boolean hasNextElement() {
            if (savedModificationCount != collection.modificationCount) {
                throw new ConcurrentModificationException();
            }
            return index < this.size;
        }

        /**
         * Gets next element in a {@link ElementsGetter}.
         * @return Next element.
         * @throws ConcurrentModificationException if a collection has been modified.
         */
        public Object getNextElement() {
            if (savedModificationCount != collection.modificationCount) {
                throw new ConcurrentModificationException();
            }
            if (index >= this.size) {
                throw new NoSuchElementException();
            }
            return collection.elements[index++];
        }
    }



    /**
     * Constructor without an arguments sets objects array to default size.
     */
    public ArrayIndexedCollection() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructor sets the size of an array to a given value.
     * @param initialCapacity {@code integer} greater than 0.
     */
    public ArrayIndexedCollection(int initialCapacity) {
        modificationCount = 0;
        if (initialCapacity < 1) {
            throw new IllegalArgumentException();
        }
        elements = new Object[initialCapacity];
    }

    /**
     * Constructor takes an existing collection and copies an array of objects.
     *
     * @param collection Existing {@code Collection} whose contents will be copied to a new Collection.
     */
    public ArrayIndexedCollection(Collection collection) {
        this(collection, DEFAULT_CAPACITY);

    }

    /**
     * Constructor takes an existing collection and initial capacity.
     * If content of a given collection does not fit in the given initial capacity, initial capacity will be ignored.
     *
     * @param collection Existing {@code Collection} whose contents will be copies to a new Collection.
     * @param initialCapacity Capacity sets the size of an array.
     * @throws NullPointerException If {@code null} value is passed as a collection in the argument.
     */
    public ArrayIndexedCollection(Collection collection, int initialCapacity) {
        modificationCount = 0;
        if (collection == null) {
            throw new NullPointerException();
        }
        if (initialCapacity < collection.size()){
            this.elements = new Object[collection.size()];
        } else {
            this.elements = new Object[initialCapacity];
        }
        addAll(collection);
    }

    /**
     * Size of an array in a collection.
     * @return Returns number of {@code objects} in the collection.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Allocates new array with size equals to the size of this collections,
     * fills it with collection content and returns the array
     *
     * @return Returns an array of {@code Objects}.
     */
    @Override
    public Object[] toArray() {
        Object[] resizedArray = new Object[size()];
        for (int i = 0; i < size(); i++) {
            resizedArray[i] = this.get(i);
        }
        return resizedArray;
    }

    /**
     * Returns true only if the collection contains given value, as determined by .equals() method.
     *
     * @param value Checks if the collection contains given value.
     * @return Returns {@code true} if collection contains and object and {@code false} otherwise.
     */
    @Override
    public boolean contains(Object value) {
        for (int i = 0; i < size; i++) {
            if (this.get(i).equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the given object into this collection.
     * @param value Object which is to be add to collection.
     * @throws NullPointerException If {@code null} is given as a value.
     */
    @Override
    public void add(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        modificationCount++;
        Object[] expandedArray;
        if (elements.length == size) {
            expandedArray = new Object[size*2];
            for (int i = 0; i < size; i++) {
                expandedArray[i] = elements[i];
            }
            elements = expandedArray;
        }
        elements[size++] = value;
    }

    /**
     * Returns the object that is stored in backing array at position index.
     * Average complexity of this method is constant.
     * @param index Takes {@code integer} of a position from which to get an {@code Object}.
     * @return Returns Object at a given index. Index is {@code integer} ranging from 0 to size of a collection minus 1.
     * @throws IndexOutOfBoundsException If an index is less than zero or exceeds size of a collection.
     */
    public Object get(int index) {
        if (index < 0 || index > size-1) {
            throw new IndexOutOfBoundsException();
        }
        return elements[index];
    }

    /**
     * Removes all elements from the collection.
     */
    @Override
    public void clear() {
        modificationCount++;
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Inserts the given value at the given position in array. It does not overwrite an existing value.
     * All elements that at a greater indexes will be shifted.
     *
     * @param value Takes an {@code Object} which needs to be inserted into a collection. Position is an {@code integer} ranging between 0 to size of a collection.
     * @param position Position at which an {@code Object} will be inserted.
     * @throws IndexOutOfBoundsException Thrown when position is invalid.
     */
    public void insert(Object value, int position) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException();
        }
        modificationCount++;
        int numberOfElementsToShift = size - position;
        // Objects at indexes higher than a given index for insertion.
        Object[] arrayWithInsertedObject = new Object[numberOfElementsToShift];
        int i = 0;
        // Objects from higher indexes are copied to an array.
        while (numberOfElementsToShift > 0) {
            arrayWithInsertedObject[i] = elements[position + i];
            i++;
            numberOfElementsToShift--;
        }
        // Objects at higher indexes are removed.
        int numberOfObjectsToDelete = size() - position;
        while (numberOfObjectsToDelete > 0) {
            remove(position);
            numberOfObjectsToDelete--;
        }
        // new value is inserted
        add(value);
        // Old objects at higher indexes are added back to a collection.
        for (int j = 0; j < arrayWithInsertedObject.length; j++) {
            add(arrayWithInsertedObject[j]);
        }
    }

    /**
     * Searches the collection and returns the index of the first occurrence of the given value
     * @param value Value which is searched for in a collection.
     * @return Returns an index if the Object is found, otherwise -1
     */
    public int indexOf (Object value) {
        if (value == null) {
            return -1;
        }
        for (int i = 0; i < size(); i++) {
            if (this.get(i).equals(value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes element at specified index from collection.
     * Elements are shifted by one place to the left to fill the empty position left by a deleted element.
     *
     * @param index Removes an {@code Object} at a given index. Index can range from 0 to size of a collection minus 1.
     * @throws IndexOutOfBoundsException Thrown when given index is out of range. Valid range is from 0 to size - 1.
     */
    public void remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        modificationCount++;
        int numberOfObjectsThatNeedShifting = size - index - 1;
        int i = 0;
        while (numberOfObjectsThatNeedShifting > 0) {
            elements[index + i] = elements[index + i + 1];
            numberOfObjectsThatNeedShifting--;
            i++;
        }
        elements[size() - 1] = null;
        size--;
    }

    /**
     * Removes first occurrence of this {@code Object}
     * @param value {@code Object} which is to be removed.
     * @return Returns {@code true} if object is successfully removed, {@code false} otherwise.
     * @throws NullPointerException Thrown when given value is null.
     */
    @Override
    public boolean remove(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        if (!contains(value)) {
            return false;
        }
        if (size() == 0) {
            return false;
        }
        modificationCount++;
        int i = 0;
        int numberOfCheckedObjects = 0;
        while (numberOfCheckedObjects < size()) {
            if (get(i).equals(value)) {
                break;
            }
            if (numberOfCheckedObjects == size() - 1) {
                return false;
            }
            i++;
            numberOfCheckedObjects++;
        }
        int j = 0;
        int numberOfObjectsThatNeedShifting = size - numberOfCheckedObjects - 1;
        while (numberOfObjectsThatNeedShifting > 0) {
            elements[numberOfCheckedObjects + j] = elements[numberOfCheckedObjects + j + 1];
            numberOfObjectsThatNeedShifting--;
            j++;
        }
        elements[size() - 1] = null;
        size--;
        return true;
    }


    /**
     * Method used to create {@link ElementsGetter} from this collection.
     * @return Returns {@link ElementsGetter}.
     */
    @Override
    public hr.fer.zemris.java.custom.collections.ElementsGetter createElementsGetter() {
        return new ElementsGetter(this);
    }
}
