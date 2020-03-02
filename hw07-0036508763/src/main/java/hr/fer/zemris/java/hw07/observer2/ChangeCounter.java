package hr.fer.zemris.java.hw07.observer2;

/**
 * Class which counts changes that have happened.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ChangeCounter implements IntegerStorageObserver {
    /**
     * Variable used to keep track of changes that had occured.
     */
    private int n;
    /**
     * Constructor which initializes counter to 0.
     */
    public ChangeCounter() {
        this.n = 0;
    }

    /**
     * Method which increases prints number of modifications.
     * @param istorage Storage from which data is used.
     */
    @Override
    public void valueChanged(IntegerStorageChange istorage) {
        System.out.println("Number of value changes since tracking: " + ++n);
    }
}
