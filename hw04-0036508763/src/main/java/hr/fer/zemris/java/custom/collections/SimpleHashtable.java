package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Hashtable is used for fast average access to entries.
 * It relies on a hash function to have lowest possible average time.
 * Ideally putting new entries in this collection should be O(1).
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

    /**
     * Default capacity of a hash table.
     */
    private final int DEFAULT_CAPACITY = 16;
    /**
     * Threshold at which the table will be reallocated.
     * Each time the hash table filled to a 75% of maximum capacity it is resized doubling its maximum capacity.
     * All elements are then reinserted to a new table which might change their indexes.
     */
    private final double REALLOCATION_THRESHOLD = 0.75;

    /**
     * Table in which all entries will be stored.
     */
    private TableEntry<K, V>[] table;
    /**
     * Number of entries currently contained in the table.
     */
    private int size;
    /**
     * Number of modifications performed on the hash table.
     */
    private long modificationCount;

    /**
     * Subclass which is used to model a single entry.
     *
     * @param <K> Key by which the entry will be hashed.
     * @param <V> Value assigned to a given key.
     */
    public static class TableEntry<K, V> {
        /**
         * Key by which an object of this class will be hashed.
         */
        private K key;
        /**
         * Value assigned to a key.
         */
        private V value;
        /**
         * Used when a hash function produces the same hash code for two different keys.
         * Newer entry is stored in a next reference of an old entry.
         */
        private TableEntry<K, V> next;

        /**
         * Constructor which is used to model a single entry.
         *
         * @param key   Key by which the entry is hashed.
         * @param value Value assigned to a key
         * @throws NullPointerException Thrown when key is null.
         */
        public TableEntry(K key, V value) {
            Objects.requireNonNull(key);
            this.key = key;
            this.value = value;
        }

        /**
         * Getter for key.
         *
         * @return Returns key.
         */
        public K getKey() {
            return key;
        }

        /**
         * Getter for value.
         *
         * @return Returns value.
         */
        public V getValue() {
            return value;
        }

        /**
         * Setter for value.
         *
         * @param value Existing value will be set to a given value.
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * Overridden equals method.
         * Compares entries by their key and value.
         *
         * @param o Other Object which is being compared to this one.
         * @return Returns {@code true} if key and value are the same, returns {@code false} otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableEntry<?, ?> that = (TableEntry<?, ?>) o;
            return Objects.equals(key, that.key) &&
                    Objects.equals(value, that.value);
        }

        /**
         * Overridden hash value.
         * Key is used for hashing.
         *
         * @return Returns hash code.
         */
        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }

    /**
     * Initializes hash table to the size of 16.
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable() {
        modificationCount = 0;
        table = (TableEntry<K, V>[]) new TableEntry[DEFAULT_CAPACITY];
    }

    /**
     * Initializes hash table with its size set to the first next power of 2.
     * If the given capacity is already a power of 2 that capacity will be used.
     *
     * @param capacity Capacity from which the closest greater power of 2 will be selected.
     * @throws IllegalArgumentException Throws when given capacity is less than 1.
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Given capacity must be greater than zero.");
        }
        modificationCount = 0;
        int calculatedCapacity = calculateCapacityToFirstBiggerPowerOfTwo(capacity);
        table = (TableEntry<K, V>[]) new TableEntry[calculatedCapacity];
    }

    /**
     * Method used to create iterators.
     * This is also called "iterator factory".
     * It allows us to use for each loop.
     */
    private class IteratorImpl implements Iterator<TableEntry<K, V>> {
        /**
         * Number of modifications is saved to detect when the table has been tempered with.
         */
        long savedModificationCount = modificationCount;
        /**
         * Keeps the number of iterated entries to determine whether the all elements have been iterated.
         */
        int numberOfIteratedEntries = 0;
        /**
         * A number of a current slot (index) is kept to make for an easier skipping of empty slots.
         */
        int currentSlot = 0;
        /**
         * Size at initialization is saved so when some elements are removed we can still iterate over the whole table.
         */
        int initialSize = size;
        /**
         * Current entry which is being returned with a call of method {@link IteratorImpl#next()}
         */
        TableEntry<K, V> currentEntry = null;
        /**
         * Checks if the object has already been removed.
         */
        boolean alreadyRemoved = false;

        /**
         * Method check if the iterator has a next entry.
         *
         * @return Returns {@code true} if the next entry exists, returns {@code false} otherwise.
         * @throws ConcurrentModificationException if the table has been modified.
         */
        public boolean hasNext() {
            if (savedModificationCount != modificationCount) {
                throw new ConcurrentModificationException("Concurrent modification has been attempted");
            }
            return numberOfIteratedEntries < initialSize;
        }

        /**
         * Gets the next entry.
         *
         * @return Returns the next entry.
         * @throws ConcurrentModificationException if the table has been modified.
         */
        public SimpleHashtable.TableEntry<K, V> next() {
            if(!this.hasNext()) {
                throw new NoSuchElementException();
            }
            alreadyRemoved = false;
            if (savedModificationCount != modificationCount) {
                throw new ConcurrentModificationException("Concurrent modification has been attempted");
            }
            if (currentEntry == null) {
                while (table[currentSlot] == null) {
                    currentSlot++;
                }
                numberOfIteratedEntries++;
                currentEntry = table[currentSlot];
                return table[currentSlot];
            }
            if (currentEntry.next != null) {
                currentEntry = currentEntry.next;
                numberOfIteratedEntries++;
                return currentEntry;
            }
            currentSlot++;
            while (table[currentSlot] == null) {
                currentSlot++;
            }
            currentEntry = table[currentSlot];
            numberOfIteratedEntries++;
            return currentEntry;
        }

        /**
         * Removes the current entry.
         *
         * @throws ConcurrentModificationException if the table has been modified.
         * @throws IllegalStateException           if the entry has already been removed.
         */
        @Override
        public void remove() {
            if (savedModificationCount != modificationCount) {
                throw new ConcurrentModificationException("Concurrent modification has been attempted");
            }
            savedModificationCount++;
            if (alreadyRemoved) {
                throw new IllegalStateException("This element has already been removed.");
            }
            alreadyRemoved = true;
            SimpleHashtable.this.remove(currentEntry.key);
        }
    }


    /**
     * Method used to put a new entry in the table.
     * If an entry with the same key exists a new value will be overwritten over the old one.
     * <p>
     * If the capacity after put method exceeds 75% of maximum capacity, a new table will be allocated with double the capacity.
     * This might have impact on performance when dealing with larger tables.
     *
     * @param key   Key at which the entry is hashed and stored in a hash table.
     * @param value Value assigned to a key.
     * @throws NullPointerException Thrown when the given key is null.
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key);
        int slot = Math.abs(key.hashCode()) % table.length;
        if (table[slot] == null) {
            modificationCount++;
            table[slot] = new TableEntry<>(key, value);
        } else {
            TableEntry<K, V> slotNode = table[slot];
            while (slotNode.next != null) {
                if (slotNode.getKey().equals(key)) {
                    slotNode.setValue(value);
                    return;
                }
                slotNode = slotNode.next;
            }
            if (slotNode.getKey().equals(key)) {
                slotNode.setValue(value);
                return;
            }
            modificationCount++;
            slotNode.next = new TableEntry<>(key, value);
        }

        size++;
        if (((double) size() / table.length) >= REALLOCATION_THRESHOLD) {
            modificationCount++;
            resizeAndReallocateTable();
        }
    }

    /**
     * Initializes iterator over the hash table.
     *
     * @return Returns an iterator.
     */

    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl();
    }

    /**
     * Gets a value saved under a given key.
     * If the key doesn't exist null will be returned.
     *
     * @param key Key at which the value is stored.
     * @return Returns value if the key exists, returns null if the key doesn't exist.
     */
    public V get(Object key) {
        if (key == null) {
            return null;
        }

        TableEntry<K, V> tableEntry = searchForAKeyAndReturnTableEntry(key);
        return tableEntry != null ? tableEntry.getValue() : null;
    }

    /**
     * Returns current size of the table.
     *
     * @return Returns the sized of the table.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the table contains the given key.
     *
     * @param key Key which is being searched for in the table.
     * @return Returns true if the key is contained in the table, returns false otherwise.
     */
    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }
        if (searchForAKeyAndReturnTableEntry(key) == null) {
            return false;
        }
        return true;
    }

    /**
     * Searches the table for a given value.
     *
     * @param value Value which is being searched for.
     * @return Returns true if the value is contained in the table, returns false otherwise.
     */
    public boolean containsValue(Object value) {
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                if (entry.getValue() == value) {
                    return true;
                }
                if (entry.getValue().equals(value)) {
                    return true;
                }
                entry = entry.next;
            }
        }
        return false;
    }

    /**
     * Removes an entry which has the given key.
     * All the links will be reconnected accordingly.
     * <p>
     * If the key doesn't exist it does nothing.
     *
     * @param key Key of an entry which is being removed.
     */
    public void remove(Object key) {
        if (key == null) {
            return;
        }
        modificationCount++;
        int slot = Math.abs(key.hashCode()) % table.length;
        TableEntry<K, V> slotNode = table[slot];

        if (slotNode == null) {
            return;
        } else if (slotNode.getKey().equals(key)) {
            table[slot] = slotNode.next;
        } else {
            while (slotNode.next != null) {
                if (slotNode.next.getKey().equals(key)) {
                    slotNode.next = slotNode.next.next;
                    size--;
                    return;
                }
                slotNode = slotNode.next;
            }
        }
        size--;
    }

    /**
     * Checks if the table is empty.
     *
     * @return Returns true if the table is empty, returns false if it contains some elements.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes every entry in the table.
     */
    public void clear() {
        modificationCount++;
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    /**
     * Method which is used to reallocate entries in a new table.
     */
    @SuppressWarnings("unchecked")
    private void resizeAndReallocateTable() {
        TableEntry<K, V>[] resizedTable = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                TableEntry<K, V> next = entry.next;
                storeEntryInResizedTable(entry, resizedTable);
                entry = next;
            }
        }
        table = resizedTable;
    }

    /**
     * Given entry is properly put in the resized table.
     * @param entry Entry which is put in the table.
     * @param resizedTable Table in which the entry will be put.
     */
    private void storeEntryInResizedTable(TableEntry<K, V> entry, TableEntry<K, V>[] resizedTable) {
        int slot = Math.abs(entry.getKey().hashCode()) % resizedTable.length;
        TableEntry<K, V> slotEntry = resizedTable[slot];
        if (slotEntry == null) {
            entry.next = null;
            resizedTable[slot] = entry;
            return;
        }
        while (slotEntry.next != null) {
            if (slotEntry.getKey().equals(entry.getKey())) {
                slotEntry.setValue(entry.getValue());
                return;
            }
            slotEntry = slotEntry.next;
        }
        entry.next = null;
        slotEntry.next = entry;
    }

    /**
     * Method which returns string of the table entries.
     * Format at which this is returned is defined in a homework.
     * @return Returns table entries as a string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                sb.append(entry.key);
                sb.append("=");
                sb.append(entry.getValue());
                sb.append(", ");
                entry = entry.next;
            }
            if (entry != null) {
                sb.append(", ");
            }
        }
        if (sb.length() != 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Method which searches for a key and returns the whole entry.
     * @param key Key by which the entry is searched for.
     * @return Returns entry which has corresponding key, returns null if the entry doesn't exist.
     */
    private TableEntry<K, V> searchForAKeyAndReturnTableEntry(Object key) {
        int slot = Math.abs(key.hashCode()) % table.length;
        TableEntry<K, V> slotNode = table[slot];
        while (slotNode != null) {
            if (slotNode.getKey().equals(key)) {
                return slotNode;
            }
            slotNode = slotNode.next;
        }
        return null;
    }

    /**
     * Method which is used to calculate the next power of 2.
     * If the given capacity is already a power of 2 the same exact capacity will be returned.
     * @param capacity Capacity from which a new capacity is calculated.
     * @return Returns a new calculated capacity.
     */
    private int calculateCapacityToFirstBiggerPowerOfTwo(int capacity) {
        int power = 0;
        while ((capacity > 1)) {
            capacity %= 2;
            power++;
        }
        return (int) Math.pow(2, power);
    }
}
