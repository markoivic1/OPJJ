package hr.fer.zemris.java.custom.collections;

/**
 * The Processor is a model of an object capable of performing some operation on the passed object.
 *
 * This class contains only one method which is to be overridden in concrete inherited implementations of this class.
 *
 *
 *
 * @author marko
 * @version 1.0.0
 */

@FunctionalInterface
public interface Processor {
    /**
     * This method is handy for code generification and reusability.
     * Method which is expected to be overridden by classes that inherit this one.
     * @param value Object value here is used to define this method.
     */
    void process(Object value);

}
