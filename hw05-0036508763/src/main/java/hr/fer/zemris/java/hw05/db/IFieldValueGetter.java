package hr.fer.zemris.java.hw05.db;

/**
 * Functional interface used to get certain data from the {@link StudentRecord}
 * @author Marko Ivić
 * @version 1.0.0
 */
@FunctionalInterface
public interface IFieldValueGetter {
    /**
     * Gets some property as string.
     * @param record Record from which this data is used.
     * @return Returns certain element as string.
     */
    public String get(StudentRecord record);
}
