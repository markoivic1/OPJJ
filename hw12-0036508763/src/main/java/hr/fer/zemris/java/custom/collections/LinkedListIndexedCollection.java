package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * This class implements collection with an indexed linked list.
 * This collection can be navigated with some simple methods.
 * General contract of this collection is: duplicate elements are allowed; storage of null references is not allowed.
 *
 * The main advantage of this collection is that adding elements is always O(1).
 *
 * Disadvantage is that getting, searching and removing an element is O(n).
 * @author marko
 * @version 1.0.0
 */

public class LinkedListIndexedCollection implements List {

    /**
     * This node contains {@code Object} value.
     */
    private static class ListNode {
        /**
         * Previous node in a ListNode.
         */
        ListNode previousNode;
        /**
         * Next node in a ListNode.
         */
        ListNode nextNode;
        /**
         * Value which is store in a ListNode.
         */
        Object value;
    }

    /**
     * Number of TreeNodes in a collection.
     */
    private int size;
    /**
     * First node in a collection.
     */
    private ListNode first;
    /**
     * Last node in a collection.
     */
    private ListNode last;
    /**
     * Number that keeps track of number of modifications that are being made.
     */
    private long modificationCount;

    /**
     * Method which is used to go through all the elements in a collection.
     */
    private static class ElementsGetter implements hr.fer.zemris.java.custom.collections.ElementsGetter {
        /**
         * Current index at which the {@link ElementsGetter} is at.
         */
        private int index;
        /**
         * Current element.
         */
        private ListNode element;
        /**
         * Size of a collection that it goes through-
         */
        private int size;
        /**
         * Collection which it iterates.
         */
        private LinkedListIndexedCollection collection;
        /**
         * This variable initializes in a constructor and sets it's value to collection's modificationCount.
         */
        private long savedModificationCount;

        /**
         * Constructor which takes a collection.
         * @param collection Collection which is being gone through.
         */
        private ElementsGetter(LinkedListIndexedCollection collection) {
            this.collection = collection;
            this.element = collection.first;
            this.size = collection.size;
            index = 0;
            savedModificationCount = collection.modificationCount;
        }

        /**
         * Check whether the next element exists.
         * @return Returns {@code true} if next element exists, {@code false} otherwise
         * @throws ConcurrentModificationException If the given collection has been modified.
         */
        @Override
        public boolean hasNextElement() {
            if (savedModificationCount != collection.modificationCount) {
                throw new ConcurrentModificationException();
            }
            return index < size;
        }

        /**
         * Gets the next element.
         * @return Returns {@link Object} if it exists, in case it doesn't an exception will be thrown.
         * @throws ConcurrentModificationException if the given collection has been modified.
         */
        @Override
        public Object getNextElement() {
            if (savedModificationCount != collection.modificationCount) {
                throw new ConcurrentModificationException();
            }
            if (index >= size) {
                throw new NoSuchElementException();
            }
            index++;
            ListNode listNode = element;
            element = element.nextNode;
            return listNode.value;
        }
    }


    /**
     * Constructor without given arguments. Initializes empty linked list.
     */
    public LinkedListIndexedCollection () {
        this.first = null;
        this.last = null;
        size = 0;
        modificationCount = 0;
    }

    /**
     * Constructor with given existing collection to copy it's contents from.
     * @param collection Existing collection with some elements in it.
     * @throws NullPointerException Throws {@code NullPointerException} when null collection is given.
     */

    public LinkedListIndexedCollection (Collection collection) {
        if (collection == null) {
            throw new NullPointerException();
        }
        addAll(collection);
        modificationCount = 0;
    }

    /**
     * Returns the number of currently stored objects in this collections.
     * @return Returns the number of currently stored objects in this collections.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Adds the given object into this collection.
     * @param value Object which is to be add to collection.
     * @throws NullPointerException Throws {@code NullPointerException} if arguments is {@code null}.
     */
    @Override
    public void add(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        modificationCount++;
        ListNode currentNode = last;
        ListNode newListNode = new ListNode();
        newListNode.previousNode = currentNode;
        newListNode.value = value;
        if (currentNode != null) {
            currentNode.nextNode = newListNode;
            last = newListNode;
        } else {
            first = newListNode;
            last = newListNode;
        }
        this.size++;
    }

    /**
     * Returns the object that is stored in linked list at position index.
     * @param index Valid indexes are ranging from 0 to size - 1.
     * @return Returns the object that is stored in linked list at position index.
     * @throws IndexOutOfBoundsException When invalid index is given.
     */
    public Object get(int index) {
        if (index < 0 || index > size() - 1){
            throw new IndexOutOfBoundsException();
        }
        modificationCount++;
        if (size()/2 > index) {
            return searchFromFirstNode(index);
        }
        return searchFromLastNode(index);
    }

    /**
     * Gets an object from a given index.
     *
     * This is method to clean up code in method {@link LinkedListIndexedCollection#get(int)}.
     * Splitting methods like this ensures better performance of method {@link LinkedListIndexedCollection#get(int)}.
     *
     * This method starts with a first node and works it's way to the last.
     *
     * @param index Index from which the Object will be returned.
     * @return Object Returns object at a given index.
     */
    private Object searchFromFirstNode(int index) {
        ListNode currentNode = first;
        int currentIndex = 0;
        while (true) {
            if (currentIndex == index) {
                return currentNode.value;
            }
            currentNode = currentNode.nextNode;
            currentIndex++;
        }
    }

    /**
     * Gets an object from a given index.
     *
     * This is method to clean up code in method {@link LinkedListIndexedCollection#get(int)}.
     * Splitting methods like this ensures better performance of method {@link LinkedListIndexedCollection#get(int)}.
     *
     * This method starts with a last node and works it's way to the first.
     *
     * @param index Index from which the Object will be returned.
     * @return Object Returns object at a given index.
     */
    private Object searchFromLastNode(int index) {
        ListNode currentNode = last;
        int currentIndex = size() - 1;
        while (true) {
            if (currentIndex == index) {
                return currentNode.value;
            }
            currentNode = currentNode.previousNode;
            currentIndex--;
        }
    }

    /**
     * This method removes all content from a collection.
     */
    @Override
    public void clear() {
        modificationCount++;
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Inserts (does not overwrite) the given value at the given position in linked-list.
     * Elements starting from this position are shifted one position.
     *
     * @param value Object which is to be inserted.
     * @param position {@code integer} values ranging from 0 to size
     * @throws IndexOutOfBoundsException Thrown when position is invalid.
     * @throws NullPointerException Thrown when given object is null.
     */
    public void insert(Object value, int position) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException();
        }
        if (value == null) {
            throw new NullPointerException();
        }
        modificationCount++;
        if (position == 0) {
            if (size() == 0) {
                add(value);
                return;
            }
            ListNode newNode = new ListNode();
            newNode.value = value;
            newNode.nextNode = first;
            first.previousNode = newNode;
            first = newNode;
            first.previousNode = null;
            size++;
            return;
        }
        if (size()/2 > position) {
            insertFromFirstNode(value, position);
            return;
        }
        insertFromLastNode(value, position);
    }

    /**
     * Inserts an Object to a given index starting from the first node.
     * This is method to clean up code in method {@link LinkedListIndexedCollection#insert(Object, int)}.
     * Splitting methods like this ensures better performance of method {@link LinkedListIndexedCollection#insert(Object, int)}.
     *
     * This method starts with a first node and works it's way to the last.
     *
     * @param value An Object which is being inserted into collection.
     * @param position An index at which the Object will be inserted.
     */
    private void insertFromFirstNode(Object value, int position) {
        ListNode newNode = new ListNode();
        newNode.value = value;
        ListNode currentNode = first;
        int currentPosition = 0;
        while (currentPosition < position + 1) {
            if (currentPosition == position) {
                newNode.previousNode = currentNode.previousNode;
                currentNode.previousNode.nextNode = newNode;
                currentNode.previousNode = newNode;
                newNode.nextNode = currentNode;
                size++;
                return;
            }
            currentPosition++;
            currentNode = currentNode.nextNode;
        }
    }

    /**
     * Inserts an Object to a given index starting from the last node.
     * This is method to clean up code in method {@link LinkedListIndexedCollection#insert(Object, int)}.
     * Splitting methods like this ensures better performance of method {@link LinkedListIndexedCollection#insert(Object, int)}.
     *
     * This method starts with a last node and works it's way to the first.
     *
     * @param value An Object which is being inserted into collection.
     * @param position An index at which the Object will be inserted.
     */
    private void insertFromLastNode(Object value, int position) {
        if (size() == position) {
            add(value);
            return;
        }
        ListNode newNode = new ListNode();
        newNode.value = value;
        ListNode currentNode = last;
        int currentPosition = size() - 1;
        while (currentPosition > position - 1) {
            if (currentPosition == position) {
                newNode.previousNode = currentNode.previousNode;
                newNode.nextNode = currentNode;
                currentNode.previousNode.nextNode = newNode;
                currentNode.previousNode = newNode;
                size++;
                return;
            }
            currentNode = currentNode.previousNode;
            currentPosition--;
        }
    }

    /**
     * Searches the collection and returns the index of the first occurrence of the given value.
     * Search relies on method .equals() to determine whether the correct Object has been found.
     * @param value {@code Object} that is searched for in a collection.
     * @return Returns index of an object if it's found, -1 otherwise.
     */
    public int indexOf(Object value) {
        if (value == null) {
            return -1;
        }
        ListNode currentNode = first;
        int index = 0;
        while (currentNode != null) {
            if(currentNode.value.equals(value)) {
                return index;
            }
            index++;
            currentNode = currentNode.nextNode;
        }
        return -1;
    }

    /**
     * Removes element at specified index from collection.
     * Element that was previously at location (index + 1) after this operation is on location (index).
     * @param index Object at a given object will be removed.
     * @throws IndexOutOfBoundsException Thrown if given index is lower than 0 and greater than size - 1.
     */
    public void remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        modificationCount++;
        if (size()/2 > index) {
            removeFromFirst(index);
            return;
        }
        removeFromLast(index);
    }

    /**
     * Removes an Object from a given index starting from the first node
     * Method used to clean up code in {@link LinkedListIndexedCollection#remove(Object)}.
     * Splitting methods like this ensures better performance of method {@link LinkedListIndexedCollection#remove(Object)}.
     *
     * This method starts with a first node and works it's way to the last.
     * @param index Removes an Object with a given index.
     */
    private void removeFromFirst(int index) {
        int currentIndex = 0;
        ListNode currentNode = first;
        if (index == 0) {
            first = first.nextNode;
            first.previousNode = null;
            currentNode = null;
            size--;
            return;
        }
        while (currentIndex < index + 1) {
            if (currentIndex == index) {
                currentNode.previousNode.nextNode = currentNode.nextNode;
                currentNode.nextNode.previousNode = currentNode.previousNode;
                size--;
                return;
            }
            currentIndex++;
            currentNode = currentNode.nextNode;
        }
        throw new NoSuchElementException();
    }

    /**
     * Removes an Object from a given index starting from the last node
     * Method used to clean up code in {@link LinkedListIndexedCollection#remove(Object)}.
     * Splitting methods like this ensures better performance of method {@link LinkedListIndexedCollection#remove(Object)}.
     *
     * This method starts with a last node and works it's way to the first.
     * @param index Removes an Object with a given index.
     */
    private void removeFromLast(int index) {
        int currentIndex = 0;
        ListNode currentNode = last;
        if (index == size - 1) {
            last = last.previousNode;
            last.nextNode = null;
            size--;
            return;
        }
        while (currentIndex > index - 1) {
            if (currentIndex == index) {
                currentNode.nextNode.previousNode = currentNode.previousNode;
                currentNode.previousNode = currentNode.nextNode;
                size--;
                return;
            }
            currentIndex++;
            currentNode = currentNode.previousNode;
        }
        throw new NoSuchElementException();
    }

    /**
     * Searches the collection for a given value and first occurrence of said object in a collection is deleted.
     * @param value Object which is to be removed
     * @return Returns {@code true} if value is successfully removed, {@code false} otherwise.
     * @throws NullPointerException Thrown if given value is {@code null}.
     */
    @Override
    public boolean remove(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        if (size() == 0) {
            return false;
        }
        modificationCount++;
        ListNode currentNode = first;
        if (currentNode.value.equals(value)) {
            first = first.nextNode;
            first.previousNode = null;
            size--;
            return true;
        }
        while (currentNode.nextNode != null) {
            if (currentNode.value.equals(value)) {
                currentNode.previousNode.nextNode = currentNode.nextNode;
                currentNode.nextNode.previousNode = currentNode.previousNode;
                size--;
                return true;
            }
            currentNode = currentNode.nextNode;
        }
        return false;
    }

    /**
     * Searches the collection for a given value.
     * @param value Checks if the collection contains given value.
     * @return Returns {@code true} if a collection contains value, {@code false} otherwise.
     */
    @Override
    public boolean contains(Object value) {
        ListNode currentNode = first;
        while (currentNode != null) {
            if (currentNode.value.equals(value)) {
                return true;
            }
            currentNode = currentNode.nextNode;
        }
        return false;
    }

    /**
     * Takes all elements of a collection and returns them as an array of Objects.
     * @return Returns an array of Objects.
     */
    @Override
    public Object[] toArray() {
        ListNode currentNode = first;
        Object[] arrayOfObjects = new Object[size()];
        int i = 0;
        while (currentNode != null) {
            arrayOfObjects[i] = currentNode.value;
            currentNode = currentNode.nextNode;
            i++;
        }
        return arrayOfObjects;
    }

    /**
     * Method used to use processor on a reversed collection.
     * @param processor processor is used to conveniently call different methods.
     */
    public void forEachBackwards(Processor processor) { // Although not required it helped to test other methods such as insert.
        if (processor == null) {
            throw new NullPointerException();
        }
        ListNode currentNode = last;
        while(currentNode != null) {
            processor.process(currentNode.value);
            currentNode = currentNode.previousNode;
        }
    }

    /**
     * Method which is used to initialize an {@link ElementsGetter} from this collection.
     * @return Returns {@link ElementsGetter} with this collection sent to a constructor.
     */
    @Override
    public hr.fer.zemris.java.custom.collections.ElementsGetter createElementsGetter() {
        return new ElementsGetter(this);
    }
}
