package hr.fer.zemris.java.hw07.observer2;

/**
 * Class that multiplies value by 2.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class DoubleValue implements IntegerStorageObserver {
    /**
     * Number of times that this value will be executed.
     */
    int n;

    /**
     * Number of times this value had been executed.
     */
    int numberOfCalls;

    /**
     * Constructor which takes number of times this value will be executed.
     * @param n Number of executions.
     */
    public DoubleValue(int n) {
        this.n = n;
        numberOfCalls = 0;
    }

    /**
     * Doubles the value stored in istorage and prints it.
     * @param istorage Is used to get values.
     */
    @Override
    public void valueChanged(IntegerStorageChange istorage) {
        System.out.println("Double value: " + istorage.getIstorage().getValue()*2);
        numberOfCalls++;
        if (n == numberOfCalls) {
            istorage.getIstorage().removeObserver(this);
        }
    }
}
