package hr.fer.zemris.java.custom.collections;

/**
 * Interface used for easier generification of code.
 */
public interface Tester <T> {
    /**
     * Given Object is tested and returns {@code true or false} accordingly.
     * @param obj Object which is being tested.
     * @return Returns {@code true or false} according to users needs.
     */
    boolean test(T obj);
}
