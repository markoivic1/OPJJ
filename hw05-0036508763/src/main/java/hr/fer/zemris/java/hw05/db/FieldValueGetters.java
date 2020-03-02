package hr.fer.zemris.java.hw05.db;

/**
 * Class which uses lambda expressions to get certain elements from the {@link StudentRecord}.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class FieldValueGetters {
    /**
     * Gets the first name.
     */
    public static final IFieldValueGetter FIRST_NAME = (r) -> r.getFirstName();
    /**
     * Gets the last name.
     */
    public static final IFieldValueGetter LAST_NAME = (r) -> r.getLastName();
    /**
     * Gets JMBAG.
     */
    public static final IFieldValueGetter JMBAG = (r) -> r.getJmbag();

}
