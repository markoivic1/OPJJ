package hr.fer.zemris.java.hw07.observer2;

/**
 * Class which is used as a read only wrapper of its contents.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class IntegerStorageChange {
    /**
     * Storage of integers.
     */
    private IntegerStorage istorage;
    /**
     * old value
     */
    private int oldValue;
    /**
     * new value.
     */
    private int newValue;

    /**
     * Constructor which take a storage and stores values
     * @param istorage
     */
    public IntegerStorageChange(IntegerStorage istorage) {
        this.istorage = istorage;
        this.oldValue = this.newValue;
        this.newValue = istorage.getValue();
    }

    /**
     * Getter for storage
     * @return Returns {@link IntegerStorage}
     */
    public IntegerStorage getIstorage() {
        return istorage;
    }

    /**
     * Getter for old value
     * @return Returns old value.
     */
    public int getOldValue() {
        return oldValue;
    }

    /**
     * Getter for new value
     * @return Returns new value.
     */
    public int getNewValue() {
        return newValue;
    }
}
