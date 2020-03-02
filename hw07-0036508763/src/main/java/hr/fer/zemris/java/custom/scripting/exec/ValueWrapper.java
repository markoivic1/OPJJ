package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * Class which wraps values and defines some operations which will be supported by these valuse.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ValueWrapper {
    /**
     * Current value that is stored.
     */
    private Object value;

    /**
     * Constructor that takes value and in case if it's a String it will convert it to Double or Integer.
     * @param value
     */
    public ValueWrapper(Object value) {
        Object convertedValue = doOperation(value, value, (v1,v2) -> v1);
        this.value = convertedValue;
    }

    /**
     * Adding given value with currently stored value.
     * @param incValue Value which will be added to stored value.
     */
    public void add(Object incValue) {
        incValue = (incValue == null) ? Integer.valueOf(0) : incValue;
        this.value = doOperation(value, incValue, (v1, v2) -> v1 + v2);
    }

    /**
     * Subtracts given value from stored value.
     * @param decValue Value which will be subtracted from store value.
     */
    public void subtract(Object decValue) {
        decValue = (decValue == null) ? Integer.valueOf(0) : decValue;
        this.value = doOperation(value, decValue, (v1,v2) -> v1 - v2);
    }

    /**
     * Multiplies given value with stored value.
     * @param mulValue Value which will be multiplied by the stored value.
     */
    public void multiply(Object mulValue) {
        mulValue = (mulValue == null) ? Integer.valueOf(0) : mulValue;
        this.value = doOperation(value, mulValue, (v1,v2) -> v1 * v2);
    }

    /**
     * Divides stored value by given value.
     * @param divValue Value by which the stored value will be divided.
     */
    public void divide(Object divValue) {
        this.value = doOperation(value, divValue, (v1,v2) -> v1 / v2);
    }

    /**
     * Compares given value and stored value.
     * @param withValue Number with which the stored number will be compared.
     * @return Return number greater than zero if the stored value is greater, returns number lower than zero if given number is greater. Returns 0 if numbers are equal.
     */
    public int numCompare(Object withValue) {
        withValue = (withValue == null) ? Integer.valueOf(0) : withValue;
        Object result = doOperation(value, withValue, (v1,v2) -> v1 - v2);
        return (result instanceof Double) ? ((Double)result).intValue() : (Integer)result;
    }

    /**
     * Strategy design pattern used to simplify some parts of code. As some operations happen frequently.
     * @param value1 First value used in BiFunction
     * @param value2 Second value used in BiFunction
     * @param calculate BiFunction which will be used to calculate given values.
     * @return Returns calculated Object.
     */
    private Object doOperation(Object value1, Object value2, BiFunction<Double, Double, Object> calculate) {
        checkArguments(value1, value2);
        if (isDouble(value1) || isDouble(value2)) {
            return calculate.apply(convertToDouble(value1), convertToDouble(value2));
        }
        return ((Double)calculate.apply(convertToDouble(value1), convertToDouble(value2))).intValue();
    }

    /**
     * Converts given value to Double.
     * @param value Value which will be converted to Double.
     * @return Returns converted Double.
     */
    private Double convertToDouble(Object value) {
        if (value == null) {
            return 0.0;
        }
        String valueAsString = value.toString();
        return Double.parseDouble(valueAsString);
    }

    /**
     * Checks if arguments are supported.
     * @param value1 First argument that is being checked.
     * @param value2 Second argument that is being checked.
     * @throws RuntimeException Throws RuntimeException if values are not supported.
     */
    private void checkArguments(Object value1, Object value2) throws RuntimeException {
        throwExceptionIfNotSupported(value1);
        throwExceptionIfNotSupported(value2);
    }

    /**
     * Check if the given value is Double.
     * @param value Double which will be tested.
     * @return Returns true if it is a Double, returns false otherwise.
     */
    private boolean isDouble(Object value) {
        if (value == null) {
            return false;
        }
        if (value.getClass() == Double.class) {
            return true;
        }
        String valueAsString = value.toString();
        return ((valueAsString.contains(".")) || (valueAsString.contains("E")));
    }

    /**
     * Checks given value against some predetermined rules and throws an Exception if they dont apply-
     * @param value Value which is being tested.
     * @throws RuntimeException Thrown when unsupported data type is given.
     */
    private void throwExceptionIfNotSupported(Object value) {
        if (value == null) {
            return;
        }
        if ((value.getClass() == Integer.class) ||
                (value.getClass() == Double.class) ||
                (value.getClass() == String.class)) {
            return;
        }
        throw new RuntimeException("Unsupported arithmetic for this class.");
    }

    /**
     * Getter for value.
     * @return Returns value.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Stores given value and converts it to proper type.
     * @param value Value which will be stored.
     */
    public void setValue(Object value) {
        Object convertedValue = doOperation(value, value, (v1,v2) -> v1);
        this.value = convertedValue;
    }
}
