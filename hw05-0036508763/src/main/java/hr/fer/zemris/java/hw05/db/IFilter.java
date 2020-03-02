package hr.fer.zemris.java.hw05.db;

/**
 * Functional interface defines under which condition it accepts certain record
 * @author Marko Ivić
 * @version 1.0.0
 */
@FunctionalInterface
public interface IFilter {
    /**
     * Defines under which condition the given record is accepted.
     * @param record Record which is being checked.
     * @return Returns true if the record meets the conditions, returns false otherwise.
     */
    public boolean accepts(StudentRecord record);
}
