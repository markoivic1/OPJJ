package hr.fer.zemris.lsystems.impl.collections;

/**
 * The Processor is a model of an object capable of performing some operation on the passed value.
 *
 * This class contains only one method which is to be overridden in concrete inherited implementations of this class.
 *
 *
 *
 * @author marko
 * @version 1.0.0
 */

@FunctionalInterface
public interface Processor<T> {
    /**
     * This method is handy for code generification and reusability.
     * Method which is expected to be overridden by classes that inherit this one.
     * @param value Value value here is used to define this method.
     */
    void process(T value);

}
