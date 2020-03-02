package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which is used store integer.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class IntegerStorage {
    /**
     * Value stored
     */
    private int value;
    /**
     * List observers.
     */
    private List<IntegerStorageObserver> observers;  // use ArrayList here!!!

    /**
     * Constructor which initializes initialValue.
     * @param initialValue InitalValue.
     */
    public IntegerStorage(int initialValue) {
        this.value = initialValue;
        this.observers = new ArrayList<>();
    }
    /**
     * Method which adds observers to the list of observers.
     * @param observer Observer which will be added.
     */
    public void addObserver(IntegerStorageObserver observer) {
        if (observers.contains(observer) == false) {
            List<IntegerStorageObserver> newElements = new ArrayList<>(observers);
            newElements.add(observer);
            this.observers = newElements;
        }
    }
    /**
     * Removes observer from a list of observers.
     * @param observer Observer which will be removed from observers list.
     */
    public void removeObserver(IntegerStorageObserver observer) {
        List<IntegerStorageObserver> newElements = new ArrayList<>(observers);
        newElements.remove(observer);
        this.observers = newElements;
    }
    /**
     * Delets all observers.
     */
    public void clearObservers() {
        observers.clear();
    }
    /**
     * Getter for value.
     * @return Returns value.
     */
    public int getValue() {
        return value;
    }
    /**
     * Sets value to a new value.
     * @param value new value.
     */
    public void setValue(int value) {// Only if new value is different than the current value:
        if (this.value != value) {// Update current value
            this.value = value;// Notify all registered observers
            if (observers != null) {
                IntegerStorageChange integerStorageChange = new IntegerStorageChange(this);
                for (IntegerStorageObserver observer : observers) {
                    observer.valueChanged(integerStorageChange);
                }
            }
        }
    }
}
