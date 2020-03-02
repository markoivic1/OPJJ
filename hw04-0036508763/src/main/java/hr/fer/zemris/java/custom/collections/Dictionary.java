package hr.fer.zemris.java.custom.collections;


import java.util.Objects;

/**
 * Collection that stores key and value of a given pair.
 *
 * An adapter is used for this implementation so it's execution will be rather slow for higher number of pairs.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Dictionary<K,V> {

    /**
     * Collection which is used for storing given pairs of {@link DictionaryEntry}.
     */
    private ArrayIndexedCollection<DictionaryEntry> collection;

    private class DictionaryEntry<K,V> {
        /**
         * Key by which the entry is identified.
         */
        K key;
        /**
         * Value assigned to a given key.
         */
        V value;

        /**
         * Constructor used to create a pair of key and entry.
         * @param key Any non-null Object.
         * @param value Any Object including null.
         * @throws NullPointerException Key must not be null.
         */
        private DictionaryEntry(K key, V value) {
            Objects.requireNonNull(key);
            this.key = key;
            this.value = value;
        }

        /**
         * Overridden method equals which compares entries by their key.
         * @param o Other Object which is being compared.
         * @return Returns {@code true} if key value is the same in both Objects, return {@code false} otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DictionaryEntry<?, ?> that = (DictionaryEntry<?, ?>) o;
            return Objects.equals(key, that.key);
        }

        /**
         * Implementation of a proper hash function.
         * @return Returns valid hash code.
         */
        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }

    /**
     * Constructor initializes new array of entries.
     */
    public Dictionary() {
        this.collection = new ArrayIndexedCollection<>();
    }

    /**
     * Checks if the dictionary is empty.
     * @return Returns {@code true} if empty, returns {@code false} if it contains some elements.
     */
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    /**
     * Checks the number of entries currently stored in a dictionary.
     * @return Returns the number of stored entries.
     */
    public int size() {
        return collection.size();
    }

    /**
     * Removes all entries from a collection.
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Insert the given entry to a collection.
     * If an entry with an identical key exists it will overwrite it's value with a new value.
     * If an entry doesn't exist it will create a new one.
     * @param key Key by which the entry is added
     * @param value Value assigned to a key.
     * @throws NullPointerException Key must not be null.
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key);
        DictionaryEntry<K,V> dictionaryEntry = checkIfKeyExistsAndReturnEntry(key);
        if (dictionaryEntry == null) {
            collection.add(new DictionaryEntry<>(key, value));
            return;
        }
        dictionaryEntry.value = value;
    }

    /**
     * Searches the dictionary for a given key.
     * @param key Key which is being searched for in a dictionary.
     * @return If key exists returns value which is assigned to a key, if key doesn't exists returns {@code null}.
     */
    public V get(Object key) {
        Objects.requireNonNull(key);
        DictionaryEntry<K,V> dictionaryEntry = checkIfKeyExistsAndReturnEntry(key);
        if (dictionaryEntry == null) {
            return null;
        }
        return dictionaryEntry.value;
    }

    /**
     * Seaches for a key in a dictionary.
     * Method used to clean up redundant code.
     * @param key Key which is being searched for.
     * @return Returns value if the key exists, returns {@code null} otherwise.
     */
    @SuppressWarnings("unchecked")
    private DictionaryEntry<K,V> checkIfKeyExistsAndReturnEntry(Object key) {
        ElementsGetter<DictionaryEntry> entryElementsGetter = collection.createElementsGetter();
        while (entryElementsGetter.hasNextElement()) {
            DictionaryEntry<K,V> dictionaryEntry = entryElementsGetter.getNextElement();
            if (dictionaryEntry.key.equals(key)) {
                return dictionaryEntry;
            }
        }
        return null;
    }


}
