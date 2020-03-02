package hr.fer.zemris.java.hw07.observer1;

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
    public void valueChanged(IntegerStorage istorage) {
        System.out.printf("Provided new value: %d, square is %d\n", istorage.getValue(), (int) Math.pow(istorage.getValue(), 2));
    }
}
