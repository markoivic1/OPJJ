package hr.fer.zemris.java.hw07.observer1;

/**
 * Interface which defines Observers which do something on value change.
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface IntegerStorageObserver {
    /**
     * Method which is executed on change of value.
     * @param istorage Storage from which the data will be taken.
     */
    public void valueChanged(IntegerStorage istorage);
}
