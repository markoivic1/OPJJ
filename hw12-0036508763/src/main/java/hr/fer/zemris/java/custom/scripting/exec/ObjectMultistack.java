package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * Data structure which allows us to have multiple stacks differentiated by keys.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ObjectMultistack {

    /**
     * Multistack in which the keys and stacks will be stored.
     */
    private Map<String, MultistackEntry> multiStack;

    /**
     * Constructor which initializes ObjectMultistack.
     */
    public ObjectMultistack() {
        multiStack = new HashMap<>();
    }

    /**
     * Static subclass which defines single entry in ObjectMultistack under one key.
     */
    private static class MultistackEntry {
        /**
         * Last entry that has been added to stack.
         */
        private MultistackEntry previous;
        /**
         * Value of a current entry.
         */
        private ValueWrapper value;

        /**
         * Constructor which maps given arguments to local variables.
         * @param previous Last entry.
         * @param value Value of a new entry.
         */
        public MultistackEntry(MultistackEntry previous, ValueWrapper value) {
            this.previous = previous;
            this.value = value;
        }
    }

    /**
     * Pushes the given value to a stack under a given key.
     * @param keyName Key of a stack in which the value will be stored.
     * @param valueWrapper Value that will be stored.
     */
    public void push(String keyName, ValueWrapper valueWrapper) {
        MultistackEntry newEntry = new MultistackEntry(multiStack.get(keyName), valueWrapper);
        multiStack.put(keyName, newEntry);
    }

    /**
     * Pops the element from a stack under a given key.
     * @param keyName Key from which stack it will be popped.
     * @return Returns popped value.
     */
    public ValueWrapper pop(String keyName) {
        if (multiStack.get(keyName) == null) {
            throw new NullPointerException("Stack is empty.");
        }
        MultistackEntry entry = multiStack.get(keyName);
        multiStack.put(keyName, entry.previous);
        return entry.value;
    }

    /**
     * Returns last pushed value to a stack identified by the key but doesn't remove it.
     * @param keyName Key of a stack from which the value will be retrieved.
     * @return Returns value but doesn't remove it form the stack.
     */
    public ValueWrapper peek(String keyName) {
        if (multiStack.get(keyName) == null) {
            throw new NullPointerException("Stack is empty.");
        }
        return multiStack.get(keyName).value;
    }

    /**
     * Checks if the stack under a given key is empty.
     * @param keyName Stack which will be tested if enmpty.
     * @return Returns true if emtpy, false otherwise.
     */
    public boolean isEmpty(String keyName) {
        return multiStack.get(keyName) == null;
    }


}
