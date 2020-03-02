package hr.fer.zemris.java.custom.collections;

/**
 * Stack is a collection that is working on a principle last in first out.
 * Only {@link ObjectStack#push(Object)} and {@link ObjectStack#pop()} operations are able to manipulate Objects in stack.
 *
 * Some problems have much simpler implementation if using this collection.
 */
public class ObjectStack {
    private ArrayIndexedCollection collection;

    /**
     * Constructor used for stack initialization. Stack is empty when initialized.
     */
    public ObjectStack() {
        this.collection = new ArrayIndexedCollection();
    }

    /**
     * Checks whether the stack is empty.
     * @return Returns {@code true} if the stack is empty and {@code false} otherwise.
     */
    public boolean isEmpty() {
        if (collection.size() != 0) {
            return false;
        }
        return true;
    }

    /**
     * This method is used to get the number of elements in a stack.
     * @return Returns {@code integer} with a size of a stack.
     */
    public int size() {
        return collection.size();
    }

    /**
     * Pushes given value on the stack
     * @param value Object which is being pushed to the stack.
     */
    public void push(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        collection.add(value);
    }

    /**
     * Removes last value pushed on stack from stack and returns it.
     * @return Returns an Object which was last pushed on a stack.
     * @throws EmptyStackException Thrown when stack is empty.
     */
    public Object pop() {
        if (collection.size() == 0) {
            throw new EmptyStackException();
        }
        Object poppedObject = collection.get(size() - 1);
        collection.remove(size() - 1);
        return poppedObject;
    }

    /**
     * Similar as {@link ObjectStack#pop()} but doesn't remove the object from a stack.
     * @return Returns the last pushed Object from a stack without removing it.
     */
    public Object peek() {
        if (collection.size() == 0) {
            throw new EmptyStackException();
        }
        return collection.get(size() - 1);
    }

    /**
     * Clears all elements from a stack and sets it size to 0.
     */
    public void clear() {
        collection.clear();
    }
}
