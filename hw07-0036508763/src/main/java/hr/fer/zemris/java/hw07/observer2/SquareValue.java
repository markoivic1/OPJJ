package hr.fer.zemris.java.hw07.observer2;

/**
 * Class which squares given value.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class SquareValue implements IntegerStorageObserver {
    /**
     * Squares given value
     * @param istorage Storage from which the data will be taken.
     */
    @Override
    public void valueChanged(IntegerStorageChange istorage) {
        System.out.printf("Provided new value: %d, square is %d\n", istorage.getIstorage().getValue(), (int) Math.pow(istorage.getIstorage().getValue(), 2));
    }
}
