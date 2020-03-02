package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * This class implements method given by the {@link CalcModel} interface.
 * CalcModelImpl defines a way in which the calculator processes input.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class CalcModelImpl implements CalcModel {

    /**
     * List of listeners
     */
    private List<CalcValueListener> listeners;

    /**
     * Current value.
     */
    private double value;
    /**
     * Flag which tells whether the calculator is able to get new input or execute some operation.
     */
    private boolean editable;
    /**
     * Current value in a String representation.
     */
    private String inputString;
    /**
     * Flag which tells whether the current value is positive or negative.
     */
    private boolean positive;

    /**
     * Active operand is number that has previously been a current value.
     * When an operation is execute current value will move to active operand.
     */
    private String activeOperand;
    /**
     * Pending operation.
     */
    private DoubleBinaryOperator pendingOperation;

    /**
     * Constructor which initializes data.
     */
    public CalcModelImpl() {
        inputString = "";
        activeOperand = "";
        positive = true;
        editable = true;
        listeners = new ArrayList<>();
    }

    @Override
    public void addCalcValueListener(CalcValueListener l) {
        Objects.requireNonNull(l);
        listeners.add(l);
    }


    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        listeners.remove(l);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
        inputString = Double.valueOf(value).toString();
        editable = false;
        notifyListeners();
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void clear() {
        value = 0;
        inputString = "";
        editable = true;
        notifyListeners();
    }

    @Override
    public void clearAll() {
        clear();
        activeOperand = "";
        pendingOperation = null;
        // listeners have already been notified.
    }

    @Override
    public void swapSign() throws CalculatorInputException {
        if (!isEditable()) {
            throw new CalculatorInputException("Calculator is not editable.");
        }
        positive = !positive;
        this.value = -1 * this.value;
        if (inputString.length() != 0) {
            if (inputString.charAt(0) == '-') {
                inputString = inputString.substring(1);
            } else {
                inputString = "-" + inputString;
            }
        }
        notifyListeners();
    }

    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        if (inputString.contains(".")) {
            throw new CalculatorInputException("Decimal point already exists");
        }
        if (!isEditable()) {
            throw new CalculatorInputException("Calculator is not editable");
        }
        if (inputString.length() == 0) {
            throw new CalculatorInputException("Please enter some digits before adding decimal point.");
        }
        inputString += ".";
        this.value = Double.parseDouble(inputString);
    }

    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Given digit doesn't fit in a single character.");
        }
        if (!isEditable()) {
            throw new CalculatorInputException("Input is not editable.");
        }

        if (inputString.equals("0") && digit == 0) {
            return;
        }

        if (inputString.equals("0")) { // && digit != 0  -- not necessary here
            inputString = "";
        }
        this.value = Double.parseDouble(inputString + digit);
        if (Double.valueOf(value).isInfinite()) {
            throw new CalculatorInputException("Given inputString exceeds range of a double.");
        }
        inputString += digit;
        notifyListeners();
    }

    @Override
    public boolean isActiveOperandSet() {
        return !activeOperand.equals("");
    }

    @Override
    public double getActiveOperand() throws IllegalStateException {
        if (activeOperand.equals("")) {
            throw new IllegalStateException("Active operand is not set");
        }
        return Double.valueOf(activeOperand);
    }

    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = Double.valueOf(activeOperand).toString();
    }

    @Override
    public void clearActiveOperand() {
        activeOperand = "";
    }

    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return pendingOperation;
    }

    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        if (this.pendingOperation != null) {
            try {
                this.value = pendingOperation.applyAsDouble(Double.parseDouble(activeOperand), value);
            } catch (NumberFormatException ex) {
                System.out.println(ex);
            }
            inputString = String.valueOf(this.value);
        }
        if (op == null) {
            if (this.pendingOperation == null) {
                return;
            }
            notifyListeners();
            this.pendingOperation = null;
            return;
        }
        notifyListeners();
        this.pendingOperation = op;
        activeOperand = String.valueOf(value);
        clear();
    }

    @Override
    public String toString() {
        if (inputString.equals("")) {
            return positive ? "0" : "-0";
        }
        return inputString;
    }

    private void notifyListeners() {
        for (CalcValueListener l : listeners) {
            l.valueChanged(this);
        }
    }
}
